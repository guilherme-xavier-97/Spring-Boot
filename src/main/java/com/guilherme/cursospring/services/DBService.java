package com.guilherme.cursospring.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.domain.Cidades;
import com.guilherme.cursospring.domain.Cliente;
import com.guilherme.cursospring.domain.Endereco;
import com.guilherme.cursospring.domain.Estado;
import com.guilherme.cursospring.domain.ItemPedido;
import com.guilherme.cursospring.domain.Pagamento;
import com.guilherme.cursospring.domain.PagamentoComBoleto;
import com.guilherme.cursospring.domain.PagamentoComCartao;
import com.guilherme.cursospring.domain.Pedido;
import com.guilherme.cursospring.domain.Produtos;
import com.guilherme.cursospring.domain.enums.EstadoPagamento;
import com.guilherme.cursospring.domain.enums.TipoCliente;
import com.guilherme.cursospring.repositories.CategoriaRepository;
import com.guilherme.cursospring.repositories.CidadesRepository;
import com.guilherme.cursospring.repositories.ClienteRepository;
import com.guilherme.cursospring.repositories.EnderecoRepository;
import com.guilherme.cursospring.repositories.EstadoRepository;
import com.guilherme.cursospring.repositories.ItemPedidoRepository;
import com.guilherme.cursospring.repositories.PagamentoRepository;
import com.guilherme.cursospring.repositories.PedidosRepository;
import com.guilherme.cursospring.repositories.ProdutosRepository;


@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutosRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadesRepository cidadesRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidosRepository pedidosRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
	
		
		Produtos p1 = new Produtos(null, "Computador", 2000.00);
		Produtos p2 = new Produtos(null, "Impressora", 800.00);
		Produtos p3 = new Produtos(null, "Mouse", 80.00);
		Produtos p4 = new Produtos(null, "Mesa de escritório", 300.00);
		Produtos p5 = new Produtos(null, "Toalha", 50.00);
		Produtos p6 = new Produtos(null, "Colcha", 200.00);
		Produtos p7 = new Produtos(null, "TV true color", 1200.00);
		Produtos p8 = new Produtos(null, "Roçadeira", 800.00);
		Produtos p9 = new Produtos(null, "Abajour", 100.00);
		Produtos p10 = new Produtos(null, "Pendente", 180.00);
		Produtos p11 = new Produtos(null, "Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategoria().addAll(Arrays.asList(cat1));
		p2.getCategoria().addAll(Arrays.asList(cat1, cat2));
		p3.getCategoria().addAll(Arrays.asList(cat1));

		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategoria().addAll(Arrays.asList(cat1, cat4));
		p2.getCategoria().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategoria().addAll(Arrays.asList(cat1, cat4));
		p4.getCategoria().addAll(Arrays.asList(cat2));
		p5.getCategoria().addAll(Arrays.asList(cat3));
		p6.getCategoria().addAll(Arrays.asList(cat3));
		p7.getCategoria().addAll(Arrays.asList(cat4));
		p8.getCategoria().addAll(Arrays.asList(cat5));
		p9.getCategoria().addAll(Arrays.asList(cat6));
		p10.getCategoria().addAll(Arrays.asList(cat6));
		p11.getCategoria().addAll(Arrays.asList(cat7));;
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7)); // esse Arrays as list permite que eu salve varios objetos de uma vez
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
//---------------------------------------------------------------------------------------------------------------------------------
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidades c1 = new Cidades(null, "Uberlândia", est1);
		Cidades c2 = new Cidades(null, "São Paulo", est2);
		Cidades c3 = new Cidades(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		cidadesRepository.saveAll(Arrays.asList(c1, c2, c3));
		
//---------------------------------------------------------------------------------------------------------------------------------
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "m@hotmail.com", "536537745", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("123"));
		
		cli1.getTelefones().addAll(Arrays.asList("456345", "452346"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "apto 203", "Jardins", "8657", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matos", "105", "sala 800", "Centro", "8658", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
//---------------------------------------------------------------------------------------------------------------------------------
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); //É uma mascara pra data
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		
		
		Pedido ped1 = new Pedido(null, sdf.parse("08/03/2022 14:01"), e1, cli1);
		Pedido ped2 = new Pedido(null, sdf.parse("08/03/2022 14:05"), e2, cli1);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf2.parse("09/03/2022"), null);
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidosRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
//---------------------------------------------------------------------------------------------------------------------------------
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 200.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}
	

}

