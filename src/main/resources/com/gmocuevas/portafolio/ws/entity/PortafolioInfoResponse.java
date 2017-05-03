package com.gmocuevas.portafolio.ws.entity;

public class PortafolioInfoResponse {
		private java.util.ArrayList<Libro> libros;
		
		public BooksInfoResponse(){
			this.libros = new java.util.ArrayList<Libro>();
		}
	 
		public void addLibro(Libro libro){
			this.libros.add(libro);
		}
		
		public java.util.List<Libro> getLibros() {
			return this.libros;
		}
}
