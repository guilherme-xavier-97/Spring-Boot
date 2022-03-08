package com.guilherme.cursospring.domain.enums;

/*
 Essa do tipo "enum" é uma classe enumerada, ou seja, eu associo um numero a um valor. Nesse caso, ao inves de ficar digitando
 Pessoa Fisica ou Pessoa juridica toda vez, eu simplesmte crio um código pra elas (aqui foi 1 e 2, mas poderia ser qualquer um).
 O JAVA permite que isso seja feito automaticamente, eu poderia simplesmente colocar minhas variaveis e o proprio programa ia atri-
 buir valores padrao, começando do 0 e indo até o fim. POREMMMMMM isso é perigoso!! 
 
 Digamos que eu tenha 3 atributos, nessa ordem: A, B, C, por padrão, o JAVA iria atribuir A=0, B=1 e C=2
 Mas ai precisou colocar mais um atributo, o D, só que o programador não se atentou na ordem, e ao inves de fazer A, B, C, D
 ele colocou o D logo no começo, ficando D, A, B, C. 
 
 Pronto, ja fodeu tudo, pq agora o D vai receber 0, que era o valor do A, e consequentemente todos os outros atributos vão ficar
 com valores errados.
 
 Por isso eu crio o construtor e os metodos get pra que EU defina os valores que serão dados a cada atributo.
 
 */

public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	//construtor de tipos enumerados são private e não public como os outros
	private TipoCliente (int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
		
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum (Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (TipoCliente x: TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
