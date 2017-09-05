package br.com.carrowebservice.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.carrowebservice.dao.CarroDAO;
import br.com.carrowebservice.model.Carro;

public class CarroService {
	
	private CarroDAO db =  new CarroDAO();
	
	public List<Carro> getCarros(){
		try{
			List<Carro> carros = db.getCarros();
			return carros;
		} catch (SQLException e){
			e.printStackTrace();
			return new ArrayList<Carro>();
		}
	}
	
	public Carro getCarro(Long id){
		try{
			return db.getCarroById(id);
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	} 
	
	public boolean delete(Long id){
		try{
			return db.delete(id);
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean save(Carro carro){
		try{
			db.save(carro);
			return true;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Carro> findByName(String nome){
		try{
			return db.findByName(nome);
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Carro> findByTipo(String tipo){
		try{
			return db.findByTipo(tipo);
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

}
