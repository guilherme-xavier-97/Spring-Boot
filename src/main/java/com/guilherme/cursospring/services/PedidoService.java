package com.guilherme.cursospring.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guilherme.cursospring.domain.Cliente;
import com.guilherme.cursospring.domain.ItemPedido;
import com.guilherme.cursospring.domain.PagamentoComBoleto;
import com.guilherme.cursospring.domain.Pedido;
import com.guilherme.cursospring.domain.enums.EstadoPagamento;
import com.guilherme.cursospring.repositories.ItemPedidoRepository;
import com.guilherme.cursospring.repositories.PagamentoRepository;
import com.guilherme.cursospring.repositories.PedidosRepository;
import com.guilherme.cursospring.security.UserSpringSecurity;
import com.guilherme.cursospring.services.exceptions.AuthorizationException;
import com.guilherme.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired //serve pra instanciar objetos por meio da injeção de dependecia
	private PedidosRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtosService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido read(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + 
		Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.readOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtosService.read(ip.getProduto().getId()));
			ip.setPreco(produtosService.read(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());	
		emailService.sendOrderConfirmationEmailHTML(obj);
		return obj;
	}
	
	public Page<Pedido> findPage (Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		UserSpringSecurity user = UserService.authenticated();
		if(user==null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.readOne(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}
