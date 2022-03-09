package com.guilherme.cursospring.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guilherme.cursospring.domain.enums.EstadoPagamento;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) /* essa estratégia cria uma tabela pra cada subclasse e faz o join com a classe mãe
												 por isso eu crio entidades para as subclasses. Se eu usasse a estratégia "single"
												 não precisaria disso, pq seria uma tabela só pras duas subclasses. Nessa abordagem
												 quando os dados forem pra subclasse A, os campos da subclasse B são preenchidos como
												 null, e vice versa. Quando as subclasses tem poucos atributos, pode-se usar o "single"
												 mas quando tem muitos atributos melhor usar o 'joined" pra evitar muitos valores nulos*/


public abstract class Pagamento implements Serializable { /*sendo abstrata eu nunca vou conseguir instanciar só a classe "pagamento"
															sempre serão suas subclasses*/
	
	
	private static final long serialVersionUID = 1L; 
	
	@Id
	private Integer id;
	private Integer estado; //Internamente salva como um inteiro, mas externamente o sistema vai ver como um tipo EstadoPagamento
	
	
	@JsonIgnore /* Como a referência ja foi definida em outra entidade, aqui eu uso essa anotação pro programa 
	entender que a referencia ja ta la, senao ele vai buscar os relacionamentos associados infinitamente*/
	@OneToOne
	@JoinColumn(name = "pedido_id")
	@MapsId //essa anotação compara os ids nas duas tabelas pra ver se são iguais
	private Pedido pedido;
	
	public Pagamento() {
		
	}

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		super();
		this.id = id;
		//operador ternario, pra caso não tenha um Estado de pagamento, ele atribua nulo, caso tenha, ele da o código
		this.estado = (estado==null) ? null: estado.getCod(); 
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
