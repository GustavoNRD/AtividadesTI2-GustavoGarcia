package model;

public class Produto {
	private int num;
	private String nome;
	private String tipo;
	private int nive;
	private String regiao;	
	
	public Produto() {
		num = -1;
		nome = "";
		tipo = "";
		nive = 0;
		regiao = "";
	}

	public Produto(int num, String nome, String tipo, String regiao, int nive) {
		setNum(num);
		setNome(nome);
		setTipo(tipo);
		setRegiao(regiao);
		setNive(nive);
	}		
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNive() {
		return nive;
	}
	
	public void setNive(int nive) {
		this.nive = nive;
	}
	
	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Pokemon: " + nome + "   Tipo:" + tipo + "   Dex num: " + num + "   Regiao: "
				+ regiao  + "   nivel: " + nive;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getNum() == ((Produto) obj).getNum());
	}	
}