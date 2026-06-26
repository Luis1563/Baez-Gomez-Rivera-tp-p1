package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Enemigo {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private int direccion; // 1 = Derecha, -1 = Izquierda
    private Image imagen;
    private Image imagenInvertida;
    private Item item; // Item que el enemigo puede soltar al morir

    // Constructor
    public Enemigo(double x, double y, double ancho, double alto, double velocidad, int direccion) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.direccion = direccion;
       // this.imagen = determinarImagen();
        this.imagen = Herramientas.cargarImagen("enemigo.png");
        this.imagenInvertida = Herramientas.cargarImagen("enemigoInvertido.png");
        this.item = new Item(this.x, this.y);
    }



    public void dibujar(Entorno e) {
        if (this.direccion == 1){
            e.dibujarImagen(this.imagen, x, y, 0, 0.08);
        }
        else{
            e.dibujarImagen(this.imagenInvertida, x, y, 0, 0.08);
        }
        //e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.BLUE);
    }

    
    public void mover() {
        this.x = this.x + (this.velocidad * this.direccion);
    }

    public void actualizar(double velocidadMapa) {
        this.mover();
        this.x = this.x - velocidadMapa;
        if (this.direccion == -1) {
            this.x = this.x + (velocidadMapa);
        }
    }

    public Item soltarItem(Entorno entorno) {
            Item item = new Item(this.x, this.y); // Creamos un nuevo item en la posición del enemigo
            return item;
    }


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

    
    public double getVelocidad() {
        return velocidad;
    }


    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }


    public double getX() { return x; }
    public int getDireccion(){return direccion;}
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
}