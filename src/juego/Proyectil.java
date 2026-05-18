package juego;

import entorno.Entorno;
import java.awt.Color;

public class Proyectil {

        private double x, y;
    private double ancho, alto;
    private double velocidad;
    private double velocidadX, velocidadY;
    //private Image imagen;

    public Proyectil(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = 20; // Velocidad constante del proyectil
        this.velocidadX = 0;
        this.velocidadY = 0;
        //this.imagen = imagen;
    }

    public void dibujar(Entorno e) {
        e.dibujarRectangulo(x, y, ancho, alto, 0, Color.BLUE);
    }

    public void mover() {
        this.x += velocidadX; // Mover en la dirección calculada
        this.y += velocidadY; // Mover en la dirección calculada
    }

    public void dispararHacia(double destinoX, double destinoY) {
        double distanciaX = destinoX - this.x;
        double distanciaY = destinoY - this.y;
        double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY); //calculamos la hipotenusa para obteer la distancia entre el proyectil y el mouse
        if (distancia == 0) {
            velocidadX = velocidad; //  velocidad en la dirección horizontal
            velocidadY = 0;         // velocidad en la dirección vertical
        } else {
            velocidadX = velocidad * distanciaX / distancia; //20 constante * distanciaX (desde el proyectil al mouse) / distancia total
            velocidadY = velocidad * distanciaY / distancia;
        }
    }

    public boolean estaFuera(double anchoPantalla, double altoPantalla) {
        return x + this.ancho / 2 < 0 || x - this.ancho / 2 > anchoPantalla ||
            y + this.alto / 2 < 0 || y - this.alto / 2 > altoPantalla;
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

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidadY) {
        this.velocidad = velocidadY;
    }

}
