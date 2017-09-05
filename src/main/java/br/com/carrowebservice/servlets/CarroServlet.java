package br.com.carrowebservice.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.carrowebservice.model.Carro;
import br.com.carrowebservice.model.Response;
import br.com.carrowebservice.service.CarroService;
import br.com.carrowebservice.util.RegexUtil;
import br.com.carrowebservice.util.ServletUtil;

@WebServlet("/carros/*")
public class CarroServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private CarroService service = new CarroService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = req.getRequestURI();
		Long id = RegexUtil.matchId(requestURI);
				
//		List<Carro> carros = service.getCarros();
//		ListaCarros listaCarros = new ListaCarros();
//		listaCarros.setCarros(carros);
		
		if(id != null){
			Carro carro = service.getCarro(id);
			
			if(carro != null){
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(carro);
				ServletUtil.writeJSON(response, json);
			} else {
				response.sendError(404, "Carro não encontrado");
			}
		} else {
			List<Carro> carros = service.getCarros();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(carros);
			ServletUtil.writeJSON(response, json);
		}
		
		
		
//		try {
//			String xml = JAXBUtil.toJSON(listaCarros);
//			ServletUtil.writeJSON(response, xml);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		String json = gson.toJson(listaCarros);
//		
//		ServletUtil.writeJSON(response, json);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Carro carro = getCarroFromRequest(request);
		service.save(carro);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(carro);
		ServletUtil.writeJSON(response, json);
	}

		
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		Long id = RegexUtil.matchId(requestURI);
		if(id != null){
			service.delete(id);
			Response r = Response.OK("Carro excluido com sucesso");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(r);
			ServletUtil.writeJSON(response, json);
			//response.sendError(200, "Carro deletado com sucesso");
		} else {
			//URL invalida
			response.sendError(404, "URL invalida");
			
		}
	}
	
	
	private Carro getCarroFromRequest(HttpServletRequest request) {
		Carro carro = new Carro();
		String id = request.getParameter("id");
		if(id != null){
			carro = service.getCarro(Long.parseLong(id));
		}
		carro.setNome(request.getParameter("nome"));
		carro.setDesc(request.getParameter("descricao"));
		carro.setUrlFoto(request.getParameter("url_foto"));
		carro.setUrlVideo(request.getParameter("url_video"));
		carro.setLatitude(request.getParameter("latitude"));
		carro.setLongitude(request.getParameter("longitude"));
		carro.setTipo(request.getParameter("tipo"));
		
		return carro;
	}

}
