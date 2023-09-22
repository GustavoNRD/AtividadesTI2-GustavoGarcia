package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.ProdutoDAO;
import model.Produto;
import spark.Request;
import spark.Response;


public class ProdutoService {

	private ProdutoDAO produtoDAO = new ProdutoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_NUM = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_NIVE = 3;
	
	
	public ProdutoService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Produto(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Produto(), orderBy);
	}

	
	public void makeForm(int tipo1, Produto pokemon, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umPokemon = "";
		if(tipo1 != FORM_INSERT) {
			umPokemon += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/pokemon/list/1\">Novo Pokemon</a></b></font></td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t</table>";
			umPokemon += "\t<br>";			
		}
		
		if(tipo1 == FORM_INSERT || tipo1 == FORM_UPDATE) {
			String action = "/pokemon/";
			String name, nome, buttonLabel;
			if (tipo1 == FORM_INSERT){
				action += "insert";
				name = "Inserir Pokemon";
				nome = "pikachu, braixen, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + pokemon.getNum();
				name = "Atualizar pokemon (num " + pokemon.getNum() + ")";
				nome = pokemon.getNome();
				buttonLabel = "Atualizar";
			}
			umPokemon += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umPokemon += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td>&nbsp;nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umPokemon += "\t\t\t<td>tipo: <input class=\"input--register\" type=\"text\" name=\"tipo\" value=\""+ pokemon.getTipo() +"\"></td>";
			umPokemon += "\t\t\t<td>nive: <input class=\"input--register\" type=\"text\" name=\"nive\" value=\""+ pokemon.getNive() +"\"></td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td>&nbsp;regiao: <input class=\"input--register\" type=\"text\" name=\"regiao\" value=\""+ pokemon.getRegiao() + "\"></td>";
			umPokemon += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t</table>";
			umPokemon += "\t</form>";		
		} else if (tipo1 == FORM_DETAIL){
			umPokemon += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Pokemon (num " + pokemon.getNum() + ")</b></font></td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td>&nbsp;nome: "+ pokemon.getNome() +"</td>";
			umPokemon += "\t\t\t<td>tipo: "+ pokemon.getTipo() +"</td>";
			umPokemon += "\t\t\t<td>nive: "+ pokemon.getNive() +"</td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t\t<tr>";
			umPokemon += "\t\t\t<td>&nbsp;regiao: "+ pokemon.getRegiao() + "</td>";
			umPokemon += "\t\t\t<td>&nbsp;</td>";
			umPokemon += "\t\t</tr>";
			umPokemon += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo1);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umPokemon);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Pokemons</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/pokemon/list/" + FORM_ORDERBY_NUM + "\"><b>num</b></a></td>\n" +
        		"\t<td><a href=\"/pokemon/list/" + FORM_ORDERBY_NOME + "\"><b>nome</b></a></td>\n" +
        		"\t<td><a href=\"/pokemon/list/" + FORM_ORDERBY_NIVE + "\"><b>nive</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Produto> pokemons;
		if (orderBy == FORM_ORDERBY_NUM) {                 	pokemons = produtoDAO.getOrderByNum();
		} else if (orderBy == FORM_ORDERBY_NOME) {			pokemons = produtoDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_NIVE) {			pokemons = produtoDAO.getOrderByNive();
		} else {											pokemons = produtoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Produto p : pokemons) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getNum() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getTipo() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pokemon/" + p.getNum() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pokemon/update/" + p.getNum() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteProduto('" + p.getNum() + "', '" + p.getNome() + "', '" + p.getTipo() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String tipo = request.queryParams("tipo");
		int nive = Integer.parseInt(request.queryParams("nive"));
		String regiao = request.queryParams("regiao");
		
		String resp = "";
		
		Produto pokemon = new Produto(-1, nome, tipo, regiao, nive);
		
		if(produtoDAO.insert(pokemon) == true) {
            resp = "Pokemon (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Pokemon (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int num = Integer.parseInt(request.params(":num"));		
		Produto pokemon = (Produto) produtoDAO.get(num);
		
		if (pokemon != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, pokemon, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pokemon " + num + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int num = Integer.parseInt(request.params(":num"));		
		Produto pokemon = (Produto) produtoDAO.get(num);
		
		if (pokemon != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, pokemon, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pokemon " + num + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int num = Integer.parseInt(request.params(":num"));
		Produto pokemon = produtoDAO.get(num);
        String resp = "";       

        if (pokemon != null) {
        	pokemon.setNome(request.queryParams("nome"));
        	pokemon.setTipo(request.queryParams("tipo"));
        	pokemon.setNive(Integer.parseInt(request.queryParams("nive")));
        	pokemon.setRegiao(request.queryParams("regiao"));
        	produtoDAO.update(pokemon);
        	response.status(200); // success
            resp = "Pokemon (num" + pokemon.getNum() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pokemon (num \" + pokemon.getNum() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int num = Integer.parseInt(request.params(":num"));
        Produto pokemon = produtoDAO.get(num);
        String resp = "";       

        if (pokemon != null) {
            produtoDAO.delete(num);
            response.status(200); // success
            resp = "Pokemon (" + num + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pokemon (" + num + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}