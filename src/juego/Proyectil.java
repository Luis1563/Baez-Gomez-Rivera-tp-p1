package juego;

import entorno.Entorno;
import java.awt.Color;
import entorno.Herramientas;
import java.awt.Image;

public class Proyectil {

    private double x, y;
    private double radio;
    private double dx, dy;
    private double velocidad;
    private Image imagen;

    public Proyectil(double x, double y, double radio, double deltaX, double deltaY) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.dx = deltaX;
        this.dy = deltaY;

        this.velocidad = 20; // Velocidad constante del proyectil
        double distancia= Math.sqrt(deltaX*deltaX + deltaY*deltaY); // Calculamos la distancia total entre el proyectil y el mouse para normalizar el vector de dirección
        this.dx = (this.dx / distancia) * this.velocidad; // Normalizar el vector de dirección
        this.dy = (this.dy / distancia) * this.velocidad; // Normalizar el vector de dirección
        this.imagen = Herramientas.cargarImagen("proyectil.gif");
    }

    public void dibujar(Entorno e) {
        e.dibujarImagen(this.imagen, x, y, 0, 0.2);
        //e.dibujarCirculo(x, y, radio*2, Color.BLUE);
    }

    public void mover() {
        this.x += dx; // Mover en la dirección calculada
        this.y += dy; // Mover en la dirección calculada
    }

    public boolean estaFueraDePantalla(Entorno entorno) {
        if(this.x+this.radio <0 || this.x-this.radio >entorno.ancho() || this.y+this.radio <0 || this.y-this.radio >entorno.alto()) {
			return true;
		}else {
			return false;
		}
    }


    public boolean colisionaConEnemigo(Enemigo enemigo) {
		if(enemigo==null) {
			return false;
		}
	
		double xCercano = Math.max(enemigo.bordeIzquierdo(), Math.min(this.x, enemigo.bordeDerecho()));
		double yCercano = Math.max(enemigo.bordeSuperior(), Math.min(this.y, enemigo.bordeInferior()));
		
		
		double alto= yCercano - this.y;
		double ancho= xCercano - this.x;
		double distancia = (int) Math.sqrt( Math.pow(alto, 2) + Math.pow(ancho, 2));
		
		if(distancia <= (this.radio)) {
			return true;
		}else {
			return false;
		}		
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

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidadY) {
        this.velocidad = velocidadY;
    }

}
