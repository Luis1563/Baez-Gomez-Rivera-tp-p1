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
    public double getX() { return x; }
    public double getY() { return y; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }
    
}
