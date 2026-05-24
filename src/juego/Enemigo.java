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

    // Se dibuja en la pantalla
    public void dibujar(Entorno e) {
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.BLUE);
    }

    // Movimiento horizontal autónomo
    public void mover() {
        this.x = this.x + (this.velocidad * this.direccion);
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
}