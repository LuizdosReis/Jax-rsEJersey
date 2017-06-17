package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.ProjetoDao;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {
	
	ProjetoDao dao = new ProjetoDao();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(){
		Projeto projeto = dao.busca(1L);
		return projeto.toXML();
	}

}