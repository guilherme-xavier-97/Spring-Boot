package com.guilherme.cursospring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.guilherme.cursospring.domain.Categoria;
import com.guilherme.cursospring.domain.Cidades;
import com.guilherme.cursospring.domain.Cliente;
import com.guilherme.cursospring.domain.Endereco;
import com.guilherme.cursospring.domain.Estado;
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
import com.guilherme.cursospring.repositories.PagamentoRepository;
import com.guilherme.cursospring.repositories.PedidosRpository;
import com.guilherme.cursospring.repositories.ProdutosRepository;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner { // Essa interface chama o método run automaticamente
	
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
	private PedidosRpository pedidosRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
//---------------------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produtos p1 = new Produtos(null, "Computador", 2000.00);
		Produtos p2 = new Produtos(null, "Impressora", 800.00);
		Produtos p3 = new Produtos(null, "Mouse", 80.00);
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategoria().addAll(Arrays.asList(cat1));
		p2.getCategoria().addAll(Arrays.asList(cat1, cat2));
		p3.getCategoria().addAll(Arrays.asList(cat1));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2)); // esse Arrays as list permite que eu salve varios objetos de uma vez
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
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
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "mariasilva@gmail.com", "536537745", TipoCliente.PESSOAFISICA);
		
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
		
	}


}
