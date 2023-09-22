package app;

import static spark.Spark.*;
import service.ProdutoService;


public class Aplicacao {
	
	private static ProdutoService produtoService = new ProdutoService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/pokemon/insert", (request, response) -> produtoService.insert(request, response));

        get("/pokemon/:num", (request, response) -> produtoService.get(request, response));
        
        get("/pokemon/list/:orderby", (request, response) -> produtoService.getAll(request, response));

        get("/pokemon/update/:num", (request, response) -> produtoService.getToUpdate(request, response));
        
        post("/pokemon/update/:num", (request, response) -> produtoService.update(request, response));
           
        get("/pokemon/delete/:num", (request, response) -> produtoService.delete(request, response));

             
    }
}