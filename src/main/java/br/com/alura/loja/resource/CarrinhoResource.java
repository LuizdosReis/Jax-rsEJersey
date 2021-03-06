package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

@Path("carrinhos")
public class CarrinhoResource {

	CarrinhoDAO dao = new CarrinhoDAO();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Carrinho busca(@PathParam("id") long id){
		Carrinho carrinho = dao.busca(id);
		return carrinho;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(Carrinho carrinho){
		dao.adiciona(carrinho);
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();
	}
	
	@DELETE
	@Path("{id}/produtos/{produtoId}")
	public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId){
		Carrinho carrinho = dao.busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
	}
	
	@PUT
	@Path("{id}/produtos/{produtoId}/quantidade")
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, Produto produto){
		Carrinho carrinho = dao.busca(id);
		carrinho.trocaQuantidade(produto);
		return Response.ok().build();
	}
	
	
}
