package br.com.carrowebservice.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {

	private String status;
	private String msg;
	
	public Response() {}
	
	public static Response OK(String string) {
		Response response = new Response();
		response.setStatus("OK");
		response.setMsg(string);
		return response;
	}
	
	public static Response ERROR(String string) {
		Response response = new Response();
		response.setStatus("ERROR");
		response.setMsg(string);
		return response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
