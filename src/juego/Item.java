package juego;

import entorno.Entorno;
import java.awt.Color;
import entorno.Herramientas;
import java.awt.Image;

public class Item {
    private double x, y;
    private double ancho = 20;
    private double alto = 20;
    private Image imagen;

    //constructor
    public Item(double x, double y) {
        this.x = x;
        this.y = y;
        this.ancho = 20;
        this.alto = 20;
        this.imagen = Herramientas.cargarImagen("corazon.png");
    }

    public void dibujar(Entorno e) {
        //e.dibujarRectangulo(x, y, ancho, alto, 0, new Color(255, 0, 0));
        e.dibujarImagen(this.imagen, x, y, 0, 0.05);
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

}
