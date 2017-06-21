package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ClienteTest {
	
	private WebTarget target;

	@BeforeClass
	public static void levantaServidor(){
		Servidor.inicia();
	}
	
	@AfterClass
	public static void derrubaServidor(){
		Servidor.para();
	}
	
	@Before
	public void criandoAlvo() {
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		Client client = ClientBuilder.newClient(config);
		target = client.target("http://localhost:8080");
	}
	
	@Test
	public void testaQueBuscaOCarrinhoEsperado(){	
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
	public void testaQueTrazOProjetoEsperado(){
		Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
		Assert.assertEquals("Minha Loja", projeto.getNome());
	}
	
	@Test
	public void testaMetodoPostCarrinho(){
		Carrinho carrinho = new Carrinho();
		carrinho.setCidade("Santa Catarina");
		carrinho.setRua("Rua nossa senhora do rosario");
		carrinho.adiciona(new Produto(315L, "Notebook dell", 310.00, 1));
		
		Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
		
		Response response = target.path("/carrinhos").request().post(entity);
		
		Assert.assertEquals(201,response.getStatus());
		
		String location = response.getHeaderString("location");
				
		Client client = ClientBuilder.newClient();
		
		Carrinho carrinhoRetorno = client.target(location).request().get(Carrinho.class);;
		
		Assert.assertTrue(carrinho.equals(carrinhoRetorno));
	}
	
	@Test
	public void testaRemocaoDeProdutoNoCarrinho(){
		Response response = target.path("/carrinhos/1/produtos/6237").request().delete();
		
		Assert.assertEquals(200,response.getStatus());
		
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
				
		Produto esporte = new Produto(3467, "Jogo de esporte", 60, 2);
				
		Carrinho carrinhoParaComparacao = new Carrinho()
								.adiciona(esporte)
								.para("Rua Vergueiro 3185, 8 andar", "São Paulo")
								.setId(1l);
		
		Assert.assertEquals(carrinhoParaComparacao,carrinho);
	}
	
	@Test
	public void testaMetodoPostProjeto(){
		Projeto projeto = new Projeto("alura", 25, 2016);
		
		Entity<Projeto> entity = Entity.entity(projeto, MediaType.APPLICATION_XML);
		
		System.out.println(entity);
		Response response = target.path("/projetos").request().post(entity);
		
		Assert.assertEquals(201,response.getStatus());
	}
	
	@Test
	public void testaAlteracaoDeQuantidadeDoProdutoNoCarrinho(){
		Produto produto = new Produto(3467,"", 0,1);
		
		Entity<Produto> entity = Entity.entity(produto, MediaType.APPLICATION_XML);
		
		Response response = target.path("/carrinhos/1/produtos/3467/quantidade").request().put(entity);
		
		Assert.assertEquals(200,response.getStatus());
		
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
				
		Produto esporte = new Produto(3467, "Jogo de esporte", 60, 1);
				
		Carrinho carrinhoParaComparacao = new Carrinho()
								.adiciona(esporte)
								.para("Rua Vergueiro 3185, 8 andar", "São Paulo")
								.setId(1l);
		
		Assert.assertEquals(carrinhoParaComparacao,carrinho);
	}
	

}
