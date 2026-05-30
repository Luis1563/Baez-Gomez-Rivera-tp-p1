package juego;

import java.awt.Color;

import entorno.Entorno;

public class Princesa {
    
    private double x, y;
    private double ancho, alto; 
	private Proyectil proyectil;
    //private Image imagen;
    
    
	public Princesa(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.proyectil = null; // No hay proyectil activo al inicio
		//this.imagen = imagen;
	}

	public void dibujar(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
	}

	public void moverIzquierda() {
		this.x = this.x -5;
	}
	
	public void moverDerecha() {
		this.x = this.x +5;
	}
	
	public void saltar() { //moverArriba
		this.y=this.y-20;
	}
	
	public void moverAbajo() {
		this.y=this.y+2;
	}

	public void disparar(int mouseX, int mouseY) {
		double deltaX = mouseX - this.x;
		double deltaY = mouseY - this.y;
		this.proyectil = new Proyectil(this.x, this.y, 10, deltaX, deltaY); // Crea un nuevo proyectil en la posición de la princesa
	}

	public boolean colisionaPorIzquierda(Isla[] islas) {
		
		for (Isla isla : islas) {
			if (isla != null && bordeIzquierdo() <= isla.bordeDerecho() && bordeDerecho() > isla.bordeDerecho()) {
				if (bordeInferior() > isla.bordeSuperior() && bordeSuperior() < isla.bordeInferior()) {
					return true;				
				}
			}
		}
		return false;
	}
	
	public boolean colisionaPorDerecha(Isla[] islas) {
		
		for (Isla isla : islas) {
			if (isla != null && bordeDerecho() >= isla.bordeIzquierdo() && bordeIzquierdo() < isla.bordeIzquierdo()) {
				if (bordeInferior() > isla.bordeSuperior() && bordeSuperior() < isla.bordeInferior()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean colisionaPorAbajo(Isla[] islas) {
		
		for (Isla isla : islas) {
			if (isla != null && bordeInferior() >= isla.bordeSuperior() && bordeInferior() < isla.bordeInferior()) {
				if (bordeDerecho() > isla.bordeIzquierdo() && bordeIzquierdo() < isla.bordeDerecho()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean colisionaPorArriba(Isla[] islas) {
		for (Isla isla : islas) {
			if (isla != null && bordeSuperior() <= isla.bordeInferior() && bordeSuperior() > isla.bordeSuperior()) {
				if (bordeDerecho() > isla.bordeIzquierdo() && bordeIzquierdo() < isla.bordeDerecho()) {
					return true;
				}
			}
		}
		return false;
	}
	

	public double bordeDerecho() {
		return this.x+this.ancho/2;
	}
	public double bordeIzquierdo() {
		return this.x-this.ancho/2;
	}
	public double bordeInferior() {
		return this.y+this.alto/2;
	}
	public double bordeSuperior() {
		return this.y-this.alto/2;
	}



	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAncho() {
		return ancho;
	}
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}
	public double getAlto() {
		return alto;
	}
	public void setAlto(double alto) {
		this.alto = alto;
	}

	public Proyectil getProyectil() {
		return proyectil;
	}
	public void setProyectil(Proyectil proyectil) {
		this.proyectil = proyectil;
	}

}
