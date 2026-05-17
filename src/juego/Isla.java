package juego;

import entorno.Entorno;
import java.awt.Color;

public class Isla {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    
	public Isla(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = 20;
	}
	
    public void dibujar(Entorno e) {
        e.dibujarRectangulo(x, y, ancho, alto, 0, Color.GREEN);
        
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

}

