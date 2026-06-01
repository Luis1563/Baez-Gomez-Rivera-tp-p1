package juego;

import java.awt.Color;

import entorno.Entorno;

public class Princesa {
    
    private double x, y;
    private double ancho, alto; 
	private double velocidadY;
	private Proyectil proyectil;
	private boolean[] vidas;
	private int vidasRestantes;
    //private Image imagen;
    
    
	public Princesa(double x, double y, double ancho, double alto, int vidasIniciales) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.proyectil = null; // No hay proyectil activo al inicio
		//this.imagen = imagen;

		this.vidas = new boolean[vidasIniciales];
		for (int i = 0; i < vidasIniciales; i++) {
			this.vidas[i] = true;
		}
		this.vidasRestantes = vidasIniciales;
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
	
	
	// Métodos para detectar colisiones con enemigos
	public boolean colisionaPorIzquierda(Enemigo enemigo) {
		
		if (enemigo != null && bordeIzquierdo() <= enemigo.bordeDerecho() && bordeDerecho() > enemigo.bordeDerecho()) {
			if (bordeInferior() > enemigo.bordeSuperior() && bordeSuperior() < enemigo.bordeInferior()) {
				return true;				
			}
		}
		return false;
	}

	
	public boolean colisionaPorDerecha(Enemigo enemigo) {
		
		if (enemigo != null && bordeDerecho() >= enemigo.bordeIzquierdo() && bordeIzquierdo() < enemigo.bordeIzquierdo()) {
			if (bordeInferior() > enemigo.bordeSuperior() && bordeSuperior() < enemigo.bordeInferior()) {
				return true;
			}
		}
		return false;
	}

	public boolean colisionaPorAbajo(Enemigo enemigo) {
		
		if (enemigo != null && bordeInferior() >= enemigo.bordeSuperior() && bordeInferior() < enemigo.bordeInferior()) {
			if (bordeDerecho() > enemigo.bordeIzquierdo() && bordeIzquierdo() < enemigo.bordeDerecho()) {
				return true;
			}
		}
		return false;
	}


	public boolean colisionaPorArriba(Enemigo enemigo) {
		if (enemigo != null && bordeSuperior() <= enemigo.bordeInferior() && bordeSuperior() > enemigo.bordeSuperior()) {
			if (bordeDerecho() > enemigo.bordeIzquierdo() && bordeIzquierdo() < enemigo.bordeDerecho()) {
				return true;
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



	public void perderVida() {
		/* if (vidasRestantes <= 0) {
			return;
		}*/
		int indice = -1; // se usa para señalar el ultimo indice que estaba en true, para cambiarlo a false
		for (int i = vidas.length - 1; i >= 0; i--) {
			if (vidas[i] == true && indice == -1) {
				indice = i; // Guardamos el índice del corazón que se perdió
			}
		}
		if (indice != -1) {
			vidas[indice] = false; // Cambia el último corazón en true a false
			vidasRestantes--;
		}
	}

	public boolean estaViva() {
		return vidasRestantes > 0;
	}

	public void reiniciarPosicion(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidadY = 0;
	}

	public void dibujarVidas(Entorno e) {
		for (int i = 0; i < vidas.length; i++) {
			Color color;
			if (vidas[i] == true) {
				color = Color.RED;
			}
			else {
				color = Color.GRAY;
			}

			double x = 30 + i * 36; //36 porque el ancho del corazón es 30 y puse un espacio de 6 entre ellos
			double y = 30;
			e.dibujarRectangulo(x, y, 30, 30, 0, color);
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
