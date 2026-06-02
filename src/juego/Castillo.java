package juego;

import java.awt.Color;
import entorno.Entorno;

public class Castillo {
    private double x;
    private double y;
    private double ancho;
    private double alto;

    
    public Castillo(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        //this.imagen = Herramientas.cargarImagen("castillo.png");
    }

    // Se dibuja al final del nivel
    public void dibujar(Entorno e) {
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.YELLOW);
    }
    
    public void mover(double desplazamiento) {
    this.x += desplazamiento;
    }


    public boolean princesaWin(Princesa p) {
        return p.bordeDerecho() >= this.bordeIzquierdo() && 
            p.bordeIzquierdo() <= this.bordeDerecho() && 
            p.bordeInferior() >= this.bordeSuperior() && 
            p.bordeSuperior() <= this.bordeInferior();
    }


    // Métodos de los bordes (Sensores de colisión)
    public double bordeDerecho() {
        return this.x + this.ancho / 2;
    }

    public double bordeIzquierdo() {
        return this.x - this.ancho / 2;
    }

    public double bordeInferior() {
        return this.y + this.alto / 2;
    }

    public double bordeSuperior() {
        return this.y - this.alto / 2;
    }

    
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

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