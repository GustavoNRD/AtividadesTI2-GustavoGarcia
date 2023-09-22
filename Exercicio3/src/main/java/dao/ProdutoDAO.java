package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO extends DAO {	
	public ProdutoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Produto pokemon) {
	    boolean status = false;
	    try {
	        String sql = "INSERT INTO pokemon (num, nome, tipo, regiao, nive) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement st = conexao.prepareStatement(sql);
	        st.setInt(1, pokemon.getNum());
	        st.setString(2, pokemon.getNome());
	        st.setString(3, pokemon.getTipo());
	        st.setString(4, pokemon.getRegiao());
	        st.setInt(5, pokemon.getNive());
	        
	        int rowsInserted = st.executeUpdate();
	        if (rowsInserted > 0) {
	            status = true;
	        }
	        
	        st.close();
	    } catch (SQLException u) {
	        throw new RuntimeException(u);
	    }
	    return status;
	}

	
	public Produto get(int num) {
		Produto pokemon = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pokemon WHERE id="+num;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 pokemon = new Produto(rs.getInt("num"), rs.getString("nome"), rs.getString("tipo"), 
	        			                rs.getString("regiao"), rs.getInt("nive"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pokemon;
	}
	
	
	public List<Produto> get() {
		return get("");
	}

	
	public List<Produto> getOrderByNum() {
		return get("num");		
	}
	
	
	public List<Produto> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Produto> getOrderByNive() {
		return get("nive");		
	}
	
	
	private List<Produto> get(String orderBy) {
		List<Produto> pokemon = new ArrayList<Produto>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pokemon" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Produto p = new Produto(rs.getInt("num"), rs.getString("nome"), rs.getString("tipo"), 
			                rs.getString("regiao"), rs.getInt("nive"));
	            pokemon.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pokemon;
	}
	
	
	public boolean update(Produto pokemon) {
		boolean status = false;
		try {  
			String sql = "UPDATE pokemon SET nome = '" + pokemon.getNome() + "', "
					   + "num = " + pokemon.getNum() + ", " 
					   + "tipo = " + pokemon.getTipo() + ","
					   + "nive = " + pokemon.getNive()+ ", " 
					   + "regiao = "+pokemon.getRegiao()+" WHERE num = " + pokemon.getNum();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int num) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM pokemon WHERE num = " + num);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}