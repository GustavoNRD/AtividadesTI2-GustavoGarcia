public class Produtos {
	private int ID;
	private String nome;
	private double preco;
	private int validade;
	
	
	public Produtos()  // construtor padrão
	{
		this.ID = 0;
		this.nome = "";
		this.preco = 0;
		this.validade = 0;
	}
	
	public Produtos (int ID, String nome, double preco, int validade )
	{
		this.ID = ID;
		this.nome = nome;
		this.preco = preco;
		this.validade = validade;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setID(int ID)
	{
		this.ID = ID;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public double getPreco()
	{
		return preco;
	}
	
	public void setPreco(double preco)
	{
		this.preco = preco;
	}
	
	public int getValidade()
	{
		return validade;
	}
	public void setValidade(int validade)
	{
		this.validade = validade;
	}
	
	//para escrever??
	@Override
	public String toString() {
		return "Produto [ID=" + ID + ", Nome=" + nome + ", Preço=" + preco + ", Validade=" + validade + "]";
	}	
	
}