package com.guilherme.cursospring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guilherme.cursospring.domain.enums.PerfilDeUsuario;
import com.guilherme.cursospring.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	private String cpfOucnpj;
	private Integer tipo; //Internamente salva como um inteiro, mas externamente o sistema vai ver como um tipo TipoCliente
	@JsonIgnore
	private String senha;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@JsonIgnore /* Como a referência ja foi definida em outra entidade, aqui eu uso essa anotação pro programa 
	entender que a referencia ja ta la, senao ele vai buscar os relacionamentos associados infinitamente*/
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>(); /*A classe telefone é tão simples q nem precisa de uma classe só pra ela
														eu posso criar uma lista do tipo SET (q nao aceita repetição) pra ela */
	
	
	//O EAGER define que quando um cliente for buscado, o perfil dele seja buscado junto
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	
	public Cliente() {
		//Com isso sempre que eu criar um usuario novo, por padrão ele terá perfil de cliente
		addPerfilDeUsuario(PerfilDeUsuario.CLIENTE);
	}

	public Cliente(Integer id, String nome, String email, String cpfOucnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOucnpj = cpfOucnpj;
		//operador ternario, pra caso não tenha um tipo, ele atribua nulo, caso tenha, ele da o código
		this.tipo = (tipo==null) ? null : tipo.getCod();
		this.senha = senha;
		addPerfilDeUsuario(PerfilDeUsuario.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOucnpj() {
		return cpfOucnpj;
	}

	public void setCpfOucnpj(String cpfOucnpj) {
		this.cpfOucnpj = cpfOucnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<PerfilDeUsuario> getPerfis() {
		//essa função vai percorrer a lista de perfis de usuario e converter (por meio do to Enum) as strings em inteiros
		return perfis.stream().map(x -> PerfilDeUsuario.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfilDeUsuario(PerfilDeUsuario perfilDeUsuario) {
		perfis.add(perfilDeUsuario.getCod());
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
