/**
 * 
 */
package com.gmocuevas.portafolio.ws;

/**
 * @author gcuevasf
 *
 */
public interface IRequestProcessor {

	public PortafolioInfoResponse process(PortafolioInfoRequest request);
	PortafolioInfoResponse response = new PortafolioInfoResponse();
		Libro libro = null;
		
		for (int i = 0; i < 5; i++){
			libro = new Libro();
			
			libro.setTitulo("Titulo libro " + i);
			libro.setEditorial("Editorial libro " + i);
			libro.setPaginas(100 + i);
			libro.setPrecio(50 + i);
			
			response.addLibro(libro);			
		}
		
		return response;
	}
}
