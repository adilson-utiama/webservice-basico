package br.com.carrowebservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.carrowebservice.model.Carro;

public class CarroDAO {

	private Connection con;
		
	public Carro getCarroById(Long id) throws SQLException{
		con = new ConnectionFactory().getConnection();
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement("select * from carro where id=?");
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()){
				Carro carro = createCarro(resultSet);
				resultSet.close();
				return carro;
			}
			
		}finally {
			if(ps != null) ps.close();
			if(con != null) con.close();
		}
		return null;
	} 
	
	public List<Carro> findByName(String name) throws SQLException{
		List<Carro> carros = new ArrayList<>();
		con = new ConnectionFactory().getConnection();
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement("SELECT * FROM carro where lower(nome) like ?");
			ps.setString(1, "%" + name.toLowerCase() + "%");
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()){
				Carro carro = createCarro(resultSet);
				carros.add(carro);
			}
			resultSet.close();
		}finally {
			if(ps != null) ps.close();
			if(con != null) con.close();
		}
		return carros;
	}
	
	public List<Carro> findByTipo(String tipo) throws SQLException{
		List<Carro> carros = new ArrayList<>();
		con = new ConnectionFactory().getConnection();
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement("SELECT * FROM carro where tipo=?");
			ps.setString(1, tipo);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()){
				Carro carro = createCarro(resultSet);
				carros.add(carro);
			}
			resultSet.close();
		}finally {
			if(ps != null) ps.close();
			if(con != null) con.close();
		}
		return carros;
	}
	
	public List<Carro> getCarros() throws SQLException{
		List<Carro> carros = new ArrayList<>();
		con = new ConnectionFactory().getConnection();
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement("SELECT * FROM carro");
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()){
				Carro carro = createCarro(resultSet);
				carros.add(carro);
			}
			resultSet.close();
		}finally {
			if(ps != null) ps.close();
			if(con != null) con.close();
		}
		return carros;
	}
	
	public void save(Carro carro) throws SQLException{
		PreparedStatement ps = null;
		try{
			con = new ConnectionFactory().getConnection();
			if(carro.getId() == null){
				ps = con.prepareStatement("INSERT INTO carro (nome, descricao, url_foto, url_video, latitude, longitude, tipo) "
						+ "VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			} else {
				ps = con.prepareStatement("UPDATE carro SET nome=?, descricao=?, url_foto=?, url_video=?, latitude=?, longitude=?, tipo=? WHERE id=?");
			}
			ps.setString(1, carro.getNome());
			ps.setString(2, carro.getDesc());
			ps.setString(3, carro.getUrlFoto());
			ps.setString(4, carro.getUrlVideo());
			ps.setString(5, carro.getLatitude());
			ps.setString(6, carro.getLongitude());
			ps.setString(7, carro.getTipo());
			
			if(carro.getId() != null){
				ps.setLong(8, carro.getId());
			}
			int count = ps.executeUpdate();
			if(count == 0) throw new SQLException("Erro ao inserir carro");
			if(carro.getId() == null){
				Long id = getGeneratedId(ps);
				carro.setId(id);
			}
		} finally {
			if(ps != null) ps.close();
			if(con != null) con.close();
		}
	}
	
	public boolean delete(Long id) throws SQLException{
		PreparedStatement ps = null;
		try{
			con = new ConnectionFactory().getConnection();
			ps = con.prepareStatement("DELETE FROM carro WHERE id=?");
			ps.setLong(1, id);
			int count = ps.executeUpdate();
			boolean ok = count > 0;
			return ok;
		} finally {
			if(ps != null) ps.close();
			if(con != null) con.close();
		}
	}
	
	public static Long getGeneratedId(PreparedStatement ps) throws SQLException {
		ResultSet resultSet = ps.getGeneratedKeys();
		if(resultSet.next()){
			long id = resultSet.getLong(1);
			return id;
		}
		return 0L;
	}

	private Carro createCarro(ResultSet resultSet) throws SQLException {
		Carro carro = new Carro();
		carro.setId(resultSet.getLong("id"));
		carro.setNome(resultSet.getString("nome"));
		carro.setDesc(resultSet.getString("descricao"));
		carro.setUrlFoto(resultSet.getString("url_foto"));
		carro.setUrlVideo(resultSet.getString("url_video"));
		carro.setLatitude(resultSet.getString("latitude"));
		carro.setLongitude(resultSet.getString("longitude"));
		carro.setTipo(resultSet.getString("tipo"));
		return carro;
	}

	public static void main(String[] args) throws SQLException {
		CarroDAO carroDAO = new CarroDAO();
		List<Carro> carros = carroDAO.getCarros();
		System.out.println(carros);
	
	}
	
}
