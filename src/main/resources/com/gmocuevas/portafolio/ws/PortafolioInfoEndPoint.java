/**
 * 
 */
package com.gmocuevas.portafolio.ws;

/**
 * @author gcuevasf
 *
 */
public class PortafolioInfoEndPoint extends AbstractDomPayloadEndpoint {
	private Log logger = LogFactory.getLog(PortafolioInfoEndPoint.class);
	
	private IRequestProcessor procesor;
 
	/**
	 * Será inyectado por Spring
	 */
	public void setProcesor(IRequestProcessor procesor) {
		this.procesor = procesor;
		
	}
 
	/* 
	 * @see org.springframework.ws.server.endpoint.AbstractDomPayloadEndpoint#invokeInternal(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	protected Element invokeInternal(Element domRequest, Document document) throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Petición de consulta de libros");
		}
		
		PortafolioInfoRequest request = this.xmlToInfoRequest(domRequest);
		
		if (logger.isDebugEnabled()){
			logger.debug("PETICION: categoria=" + request.getCategoria() + ", nivel=" + request.getNivel());
		}
		
		PortafolioInfoResponse response    = procesor.process(request);
		
		if (logger.isDebugEnabled()){
			int numLibros = 0;
			if (response.getLibros() != null){
				numLibros = response.getLibros().size();
			}
			logger.debug("RESPUESTA: Número de libros: " + numLibros);
		}
		
		Element			  domResponse = this.responseToXml(response, document);
		
		return domResponse;
	}
	
	/**
	 * @param request Elemento XML <BooksInfoRequest ...>
	 * @return Genera una instancia de BooksInfoRequest a partir de un elemento xml
	 */
	private PortafolioInfoRequest xmlToInfoRequest(Element request){
		String categoria = request.getElementsByTagName("categoria").item(0).getFirstChild().getNodeValue();
		String nivel	 = request.getElementsByTagName("nivel").item(0).getFirstChild().getNodeValue();
		
		PortafolioInfoRequest portafolioInfoRequest = new PortafolioInfoRequest();
		
		portafolioInfoRequest.setCategoria(categoria);
		portafolioInfoRequest.setNivel(nivel);
		
		return PortafolioInfoRequest;
	}
 
	/**
	 * @param request Elemento XML <BooksInfoRequest ...>
	 * @return Genera una instancia de BooksInfoRequest a partir de un elemento xml
	 */
	private Element responseToXml(PortafolioInfoResponse response, Document document){
		Element		root	= document.createElementNS("http://www.gmocuevas.com/spring/ws/schemas", "PortafolioInfoResponse");
		List<Libro> libros  = response.getLibros();
		
		if (libros != null){
			Iterator<Libro> iteLibros = null;
			Libro			libro	  = null;
			Element			domLibro  = null;
			
			iteLibros = libros.iterator();
			while (iteLibros.hasNext()){
				libro	 = iteLibros.next();
				domLibro = this.bookToXml(document, libro);
				root.appendChild(domLibro);
			}
		}
		
		return root;
	}
 
	/**
	 * 
	 * @param document Document para construir el DOM
	 * @param libro	   Objeto a convertir a XML
	 * @return Devuelve el objeto libro en XML.
	 */
	private Element bookToXml(Document document, Libro libro){
		Element domLibro = document.createElement("libro");
		
		this.addHijo(document, domLibro, "editorial", libro.getEditorial());
		this.addHijo(document, domLibro, "titulo",	libro.getTitulo());
		this.addHijo(document, domLibro, "paginas",	String.valueOf(libro.getPaginas()));
		this.addHijo(document, domLibro, "precio",	String.valueOf(libro.getPrecio()));
		
		return domLibro;
	}
	
	/**
	 * Añade una nueva propiedad <tag>valor</tag> al elemento <padre>.
	 * @param document Para crear elementos
	 * @param padre	   La nueva propiedad será agregada a este elemento
	 * @param tag      Nombre de la propiedad
	 * @param valor	   Valor de la propiedad
	 */
	private void addHijo(Document document, Element padre, String tag, String valor){
		Element domNombre = document.createElement(tag);
		Text	domValor  = document.createTextNode(valor);
		
		domNombre.appendChild(domValor);
		padre.appendChild(domNombre);
	}
}
