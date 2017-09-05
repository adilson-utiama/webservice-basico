package br.com.carrowebservice.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import br.com.carrowebservice.model.Carro;
import br.com.carrowebservice.model.ListaCarros;
import br.com.carrowebservice.model.Response;

public class JAXBUtil {

	private static JAXBUtil instance;
	private static JAXBContext context;

	public static JAXBUtil getInstance() {
		return instance;
	}

	static {
		try {
			//Informa ao JAXB que e para gerar XML destas clas
			context = JAXBContext.newInstance(Response.class,ListaCarros.class, Carro.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toXML(Object object) throws JAXBException {
		try {
			StringWriter writer = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(object, writer);
			String xml = writer.toString();
			return xml;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String toJSON(Object object) throws JAXBException {
		try {
			StringWriter writer = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			MappedNamespaceConvention convention = new MappedNamespaceConvention();
			MappedXMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(convention, writer);
			marshaller.marshal(object, xmlStreamWriter);
			String json = writer.toString();
			return json;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

}
