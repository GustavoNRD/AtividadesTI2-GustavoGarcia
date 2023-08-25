
import java.sql.*;

public class DAO {
	private Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
	    String driverName = "org.postgresql.Driver";                    
	    String serverName = "localhost";
	    String mydatabase = "teste";
	    int porta = 5432;
	    String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
	    String username = "postgres";
	    String password = "JoaninhA123";
	    boolean status = false;

	    try {
	        Class.forName(driverName);
	        conexao = DriverManager.getConnection(url, username, password);
	        status = (conexao != null);
	        System.out.println("Conexão efetuada com o PostgreSQL!");
	    } catch (ClassNotFoundException e) { 
	        System.err.println("Conexão NÃO efetuada com o PostgreSQL -- Driver não encontrado -- " + e.getMessage());
	    } catch (SQLException e) {
	        System.err.println("Conexão NÃO efetuada com o PostgreSQL -- Erro SQL -- " + e.getMessage());
	    }

	    return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	

	
	public boolean inserirProduto(Produtos produto) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate(produto.getID()+ produto.getNome() + produto.getPreco() + produto.getValidade());
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Produtos[] getProdutos() {
		Produtos[] produto = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Produtos");		
	         if(rs.next()){
	             rs.last();
	             produto = new Produtos[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                produto[i] = new Produtos(rs.getInt("ID"), rs.getString("nome"), 
	                		                  rs.getDouble("preço"), rs.getInt("validade"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produto;
	}
	
	public boolean excluirProduto(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE codigo = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean atualizarProduto(int id, String Nnome, double NPreco, int NValidade) 
	{
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE Produto SET nome = '" + Nnome + "', preco = '"  
				       + NPreco + "', validade = '" + NValidade + "'"
					   + " WHERE ID = " + id;
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

}