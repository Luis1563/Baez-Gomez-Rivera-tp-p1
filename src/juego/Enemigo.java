package juego;

import java.awt.Color;
import entorno.Entorno;

public class Enemigo {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private int direccion; // 1 = Derecha, -1 = Izquierda

    // Constructor
    public Enemigo(double x, double y, double ancho, double alto, double velocidad, int direccion) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.direccion = direccion;
    }

    
    public void dibujar(Entorno e) {
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.BLUE);
    }

    
    public void mover() {
        this.x = this.x + (this.velocidad * this.direccion);
    }

    public void actualizar(double velocidadMapa) {
        this.mover();
        this.x = this.x - velocidadMapa;
    }

    // public void esquivarIslas(Isla[] islas) {
    //     for (Isla isla : islas) {
    //         if (isla != null) {
    //             if (this.colisionaConIsla(isla)) {
    //                 this.direccion = this.direccion * -1; 
    //             }
    //         }
    //     }
    // }

    public boolean colisionaConIsla(Isla isla) {
        return this.bordeDerecho() >= isla.bordeIzquierdo() && 
               this.bordeIzquierdo() <= isla.bordeDerecho() && 
               this.bordeInferior() >= isla.bordeSuperior() && 
               this.bordeSuperior() <= isla.bordeInferior();
    }

    public boolean colisionaConPrincesa(Princesa p) {
        return this.bordeDerecho() >= p.bordeIzquierdo() && 
               this.bordeIzquierdo() <= p.bordeDerecho() && 
               this.bordeInferior() >= p.bordeSuperior() && 
               this.bordeSuperior() <= p.bordeInferior();
    }

  
    public double bordeDerecho() { return this.x + this.ancho / 2; }
    public double bordeIzquierdo() { return this.x - this.ancho / 2; }
    public double bordeInferior() { return this.y + this.alto / 2; }
    public double bordeSuperior() { return this.y - this.alto / 2; }
       
    public double getX() { return x; }
    public int getDireccion(){return direccion;}
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
}