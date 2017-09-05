package br.com.carrowebservice.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import br.com.carrowebservice.service.CarroService;

public class CarroTest {
	
	private CarroService service = new CarroService();

	@Test
	public void testeListaDeCarros(){
		List<Carro> carros = service.getCarros();
		Carro carro = service.findByName("Tucker 1948").get(0);
		
		assertNotNull(carros);
		assertTrue(carros.size() > 0);
		assertEquals("Tucker 1948", carro.getNome());
		
	}
	
}
