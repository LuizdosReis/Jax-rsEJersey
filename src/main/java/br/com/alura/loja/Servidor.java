
package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {
	
	private static HttpServer server;

	public static void main(String[] args) throws IOException {
		inicia();
		System.in.read();
		para();
	}

	public static void inicia(){
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
        URI uri = URI.create("http://localhost:8080/");
        server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
	}
	
	public static void para(){		
		server.stop();
	}

}
