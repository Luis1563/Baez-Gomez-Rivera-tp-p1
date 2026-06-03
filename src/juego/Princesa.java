package juego;

import java.awt.Color;

import entorno.Entorno;

public class Princesa {
    
    private double x, y;
    private double ancho, alto; 
	private double velocidadY;
	private double velocidadX;
	private double gravedad;
	private double velocidadMaximaDeCaida;
	private Proyectil proyectil;
	private boolean[] vidas;
	private int vidasRestantes;
    //private Image imagen;
    
    
	public Princesa(double x, double y, double ancho, double alto, int vidasIniciales) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;

		this.velocidadY = 0;
		this.velocidadX = 5;
		this.gravedad = 0.5; //gravedad para simular la caída
		this.velocidadMaximaDeCaida = 8; //velocidad máxima de caida

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
		this.x = this.x -(this.velocidadX);
	}
	
	public void moverDerecha() {
		this.x = this.x + this.velocidadX;
	}
	
	public void saltar() { //moverArriba
		//this.y=this.y-20; //velocidad de salto negativa (va hacia arriba)
		this.velocidadY = -20; //velocidad de salto negativa (va hacia arriba)
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

    // Detecta si está sobre una isla, esto es para decidir si puede saltar o no
    public boolean estaApoyado(Isla[] islas) {

        for (int i = 0; i < islas.length; i++) {
            Isla isla = islas[i];
            if (isla != null) {
                // Está apoyado si toca la parte superior de la isla (no desde abajo o dentro)
                if (this.bordeInferior() >= isla.bordeSuperior() && this.bordeInferior() <= isla.bordeSuperior() &&
                    this.bordeDerecho() > isla.bordeIzquierdo() && this.bordeIzquierdo() < isla.bordeDerecho() &&
                    this.velocidadY >= 0) { // Solo si no está subiendo (para evitar detectar colisiones al saltar desde abajo)
                    return true;
                }
            }
        }
        return false;
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


    // Comprueba si la princesa puede moverse por x e y antes de moverla, (colisiones con islas)
    public boolean puedeMover(double x, double y, Isla[] islas) { 
        // calculamos los bordes de la princesa después de moverse
		double izquierda = (this.x + x) - this.ancho / 2;
        double derecha = (this.x + x) + this.ancho / 2;
        double arriba = (this.y + y) - this.alto / 2;
        double abajo = (this.y + y) + this.alto / 2;

        for (int i = 0; i < islas.length; i++) {
            Isla isla = islas[i];
            if (isla != null) {
                //bordes de cada isla
                double iIzquierda = isla.bordeIzquierdo();
                double iDerecha = isla.bordeDerecho();
                double iSuperior = isla.bordeSuperior();
                double iInferior = isla.bordeInferior();

                // Si se superponen, no se puede mover
                if ((derecha <= iIzquierda || izquierda >= iDerecha || abajo <= iSuperior || arriba >= iInferior) == false) {
                    return false;
                }
            }
        }
        return true;
    }


	// Aplica gravedad y actualiza posición. altoPantalla es la parte superior de la pantalla para limitar el salto.
	public void actualizarFisica(Isla[] islas) {
		// Aplica gravedad
		velocidadY += gravedad; // velocidad + gravedad por cada tick (0,5 mas 0,5 cada tick, etc)
		if (velocidadY > velocidadMaximaDeCaida) { // cuando esa suma de velocidad + gravedad supera la velocidad máxima de caída
			velocidadY = velocidadMaximaDeCaida;   // se limita a esa velocidad máxima de caída para evitar que caiga demasiado rápido
		}

		// Calcula nueva posición en y
		double nuevaY = this.y + velocidadY; // cada vez que se actualiza la física, se suma la velocidadY a la posición y para simular la caida

		// Limitar borde superior de la pantalla para que no salga cuando salta
		double mitadAltoPrincesa = this.alto / 2; // el limite superior es la mitad del alto de la princesa, para que no se vea cortada al saltar. Si el borde superior de la princesa (y - alto/2) es menor que 0, significa que está intentando subir más allá del borde superior de la pantalla.
		// supongamos que el alto de la princesa es 50, entonces la mitad sería 25. Si el nuevoY es 20, el borde superior de la princesa sería -5, o sea que estaría por fuera
		
		if (nuevaY - mitadAltoPrincesa < 0) { // si el limite superior de la princesa está por encima del borde superior de la pantalla
			this.y = mitadAltoPrincesa; // se ajusta la posición y para que el borde superior de la princesa esté justo en el borde de la pantalla (y = 25)
			velocidadY = 0; //y se detiene la velocidad vertical para evitar que suba más allá del límite
		}

		// se aplica el movimiento vertical si no hay colisión
		else if (puedeMover(0, velocidadY, islas)) { 	// si no hay colision al moverse en y, 
			this.y = nuevaY;	// se actualiza la posición y sumando la velocidadY
		} else { //si hay colision
			velocidadY = 0; // se detiene la velocidad vertical
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

	

	public double getVelocidadY() {
		return velocidadY;
	}

	public void setVelocidadY(double velocidadY) {
		this.velocidadY = velocidadY;
	}

	public double getVelocidadX() {
		return velocidadX;
	}

	public void setVelocidadX(double velocidadX) {
		this.velocidadX = velocidadX;
	}

	public double getVelocidadMaximaDeCaida() {
		return velocidadMaximaDeCaida;
	}

	public void setVelocidadMaximaDeCaida(double velocidadMaximaDeCaida) {
		this.velocidadMaximaDeCaida = velocidadMaximaDeCaida;
	}

	public Proyectil getProyectil() {
		return proyectil;
	}
	public void setProyectil(Proyectil proyectil) {
		this.proyectil = proyectil;
	}

	public void agregarVida(int i) {
		// TODO Apéndice de método generado automáticamente
		
	}

	public String getVidas() {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

}
