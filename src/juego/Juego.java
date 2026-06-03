package juego;


import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros

	private boolean mostrandoInicio; // pantalla de inicio atributo
	private Castillo castillo;
	private Entorno entorno;
	private Princesa elizabeth;
	//private Proyectil proyectil;
    private Isla[] islas;
	private Enemigo[] enemigos;
	private boolean juegoGanado; // boolean para pantalla ganadora
	private boolean juegoTerminado;
	private double respawnX; // para reiniciar a la princesa
	private double respawnY;
	private double velocidadMapa;
	private int enemigosEliminados;   // contador de enemigos eliminados
	private int puntuacion;           // sistema de puntos

	
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1280, 720);
		this.mostrandoInicio = true; //Pantalla de inicio
		elizabeth = new Princesa(640, 360, 20, 50, 10);
        this.islas = inicializarIslas();
		this.enemigos = new Enemigo[20];

		this.juegoGanado = false; // boolean para pantalla ganadora
		this.juegoTerminado = false;

		this.respawnX = entorno.ancho() / 2;
		this.respawnY = entorno.alto() / 2;

		this.enemigosEliminados = 0;
		this.puntuacion = 0;

		// Inicia el juego!
		this.entorno.iniciar();
	}
	
	private Isla[] inicializarIslas() {
		
		Isla[] misIslas1 = new Isla[50]; //Se toman este tamaños para tener más plataformas
	    int indice = 0;
		
		
        // Islas de piso (fijas) (Para que el jugador no caiga al inicio)
	    for (int i = 0; i < 16; i++) {
			misIslas1[indice] = new Isla(i * 250, entorno.alto() - 10, 200, i);
			if (i == 15) { //la  número 15 es la última isla de piso, colocamos el castillo sobre ella
				double x = misIslas1[indice].getX();
				double y = misIslas1[indice].getY() - misIslas1[indice].getAlto()/2; // Colocamos el castillo justo encima de la isla
				
				this.castillo = new Castillo(x, y, 160, 200);
				this.castillo.setY(this.castillo.getY() - this.castillo.getAlto()/2); // Ajustamos la posición del castillo para que esté sobre la isla
			}
	        indice++;
	    }


	    //GENERACIÓN POR "COLUMNAS" (Esto Evita la superposición entre las islas)

	    double avanceX = 25; // Empezamos después del piso inicial
	    double distanciaEntreColumnas = 250; 
	    
	    while (indice < misIslas1.length) {
	        // Decidimos cuántas islas habrá en esta coordenada X (2 o 3)
	        int cantidadEnEstaLinea = (int)(Math.random() * 2) + 2; // Da 2 o 3

	        for (int i = 0; i < cantidadEnEstaLinea; i++) {
	            if (indice < misIslas1.length) {
	                // Niveles de altura fijos para que no se superpongan verticalmente
	                // Nivel 0: 450px, Nivel 1: 300px, Nivel 2: 150px
	                double alturaFija = 450 - (i * 150); 
	                
	                // Agregamos una pequeña variación en X para que no sea una línea perfecta
	                double variacionX = (Math.random() * 50) - 10; 
	                
	                misIslas1[indice] = new Isla(avanceX + variacionX, alturaFija, 120, variacionX);
	                indice++;
	            }
	        }
	        
	        // Avanzamos en X para la siguiente "tanda" de islas
	        avanceX += distanciaEntreColumnas;
	    }
	    
	    return misIslas1;
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	
	
	public void tick()
	{
				// Implementación de pantalla de Inicio
		if (mostrandoInicio) {
		    // Configuramos el estilo del texto y el color 
		    this.entorno.cambiarFont("Arial Black", 60,new Color(0, 150,255)); 
		    // Escribimos el título en pantalla
		    this.entorno.escribirTexto("Super Elizabeth Sis", 350, 350);
		        
		    this.entorno.cambiarFont("Arial", 30, Color.GREEN);
		    this.entorno.escribirTexto("Presione ENTER para jugar", 480, 400);

		    // Detecta si el usuario presiona la tecla ENTER para cambiar el estado
		    if (this.entorno.estaPresionada(this.entorno.TECLA_ENTER)) {
		        this.mostrandoInicio = false;
		    }
		}
		else {
				if (this.juegoGanado) {// con esto creamos la pantalla de victoria
                this.entorno.colorFondo(Color.BLACK); // Pinta el fondo de negro
                
                // Configuramos la letra y el mensaje de victoria
                this.entorno.cambiarFont("Arial", 45, Color.WHITE);
                this.entorno.escribirTexto("¡Ganaste!", 500, 360);
                this.entorno.escribirTexto("Elizabeth libero a Mario!!!!", 350, 420); 
                
                return; // Corta el tick acá para congelar todo el juego al ganar
            }
				if (this.castillo != null) {
					this.castillo.dibujar(entorno);
					
					// Si Elizabeth roza el castillo, se activa la victoria
					if (elizabeth != null && this.castillo.princesaWin(elizabeth)) {
						this.juegoGanado = true;
					}
            }


				entorno.cambiarFont("Arial", 20, Color.GREEN);
				entorno.escribirTexto("Puntos: " + puntuacion, 400, 30);

				if (enemigosEliminados % 10 == 0 && enemigosEliminados > 0) {
				    entorno.cambiarFont("Arial", 20, Color.GREEN);
				    entorno.escribirTexto("¡Vida extra obtenida!", 800, 30);
				}

			this.entorno.colorFondo(new Color(128, 0, 128));
			//this.castillo.dibujar(entorno);
			
			
			if(elizabeth != null /* && juegoPerdido == false */) {
				elizabeth.dibujar(entorno);
				elizabeth.actualizarFisica(islas);
				elizabeth.dibujarVidas(entorno);
			}




			if(elizabeth != null /* && juegoPerdido == false */) {
				elizabeth.dibujar(entorno);
				//elizabeth.actualizarFisica(islas, entorno.alto());
				//elizabeth.dibujarVidas(entorno);
			}

			if (elizabeth != null) {
				if (elizabeth.bordeSuperior() > entorno.alto()) {
					//la princesa cayó al vacío, la reiniciamos al medio
					elizabeth.perderVida();
					//elizabeth = null;

					if (!elizabeth.estaViva()) {
						juegoTerminado = true;
						elizabeth = null; // La princesa desaparece al perder todas las vidas
					}
					else {
						elizabeth.reiniciarPosicion(respawnX, respawnY);
					}
				}
			}

			
			if(elizabeth!=null){
				if(elizabeth.getProyectil()!=null) {
					elizabeth.getProyectil().dibujar(entorno);			
				}
			}

			// Dibujar islas
			for (int i = 0; i < islas.length; i++) {
				if (islas[i] != null) { 
					// Cada isla sabe cómo dibujarse a sí misma
					islas[i].dibujar(entorno); 
				}
			}	

			
			
			this.renovarEnemigos();

			for (int i = 0; i < this.enemigos.length; i++) {
				if (this.enemigos[i] != null) {
					this.enemigos[i].actualizar(this.velocidadMapa);
					this.enemigos[i].dibujar(this.entorno);
				}
			}
			
			
			if (elizabeth != null) {

				for (int i = 0; i < enemigos.length; i++) {
					if (elizabeth != null && (elizabeth.colisionaPorAbajo(enemigos[i]) || elizabeth.colisionaPorArriba(enemigos[i]) || elizabeth.colisionaPorDerecha(enemigos[i]) || elizabeth.colisionaPorIzquierda(enemigos[i]))) {
						elizabeth.perderVida();
						enemigos[i] = null; // El enemigo desaparece al colisionar con la princesa

						
						if (!elizabeth.estaViva()) {
							juegoTerminado = true;
							elizabeth = null; // La princesa desaparece al perder todas las vidas
						} else {
							elizabeth.reiniciarPosicion(respawnX, respawnY); // reiniciamos la psoción de la princesa al medio si no se queda sin vidas
						}
						//break; // Salimos del ciclo para evitar múltiples colisiones en un solo tick
					}
				}
			
			
			
			
				// Movimiento IZQUIERDA
				if (elizabeth != null){
					if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) 
						&& elizabeth.getX() - elizabeth.getAncho()/2 > 0) {

						if (elizabeth.puedeMover(- elizabeth.getVelocidadX(), 0, islas)) {
							elizabeth.moverIzquierda();
						}
					}
				}
		
			

			

				double velocidad = 3;
					
					
				// Movimiento DERECHA
				if (elizabeth != null){
					this.velocidadMapa = 0;
					if (entorno.estaPresionada(entorno.TECLA_DERECHA)){ 
						double limiteMovimiento = entorno.ancho() * 0.55; // La princesa puede moverse libremente hasta el 55% del ancho de la pantalla
					// La princesa se mantiene en el centro, el mapa se desplaza a la izquierda

						if (elizabeth.getX() < limiteMovimiento && elizabeth.puedeMover(elizabeth.getVelocidadX(), 0, islas)) {
							elizabeth.moverDerecha();
						}
							
						else if(elizabeth.getX() >= limiteMovimiento && elizabeth.puedeMover(elizabeth.getVelocidadX(), 0, islas)) { // Solo mueve las islas si la princesa está más allá del centro de la pantalla
							if ((castillo.bordeDerecho() <= entorno.ancho())){

								if (elizabeth.bordeDerecho() < entorno.ancho()){
									elizabeth.moverDerecha();
								}
								else {
									elizabeth.setX(entorno.ancho() - elizabeth.getAncho()/2); // Evita que la princesa se salga por la derecha
								}
							}
							else {
								this.velocidadMapa = velocidad;
								this.castillo.mover(-velocidad); // El castillo se acerca solo si EXISTE
								for (int i = 0; i < islas.length; i++) {
									if (islas[i] != null) { // Solo mueve las islas si la princesa está más allá del centro de la pantalla
										islas[i].mover(-velocidad); //mueve cada isla en la dirección opuesta al movimiento de la princesa para simular desplazamiento del mapa
									}
								}
							}
						}
					}
				



				// Movimiento ARRIBA (salto)
				if (elizabeth != null) {
					if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && elizabeth.estaApoyado(islas)) { 
					// Solo puede saltar si está apoyada sobre una isla
							elizabeth.saltar();
					}
				}
				



				// disparo con botón izquierdo solo si no hay proyectil activo
				if (entorno.mousePresente() && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && elizabeth.getProyectil() == null) {
					elizabeth.disparar(entorno.mouseX(), entorno.mouseY());
				}
			}
				if (elizabeth != null){

					if (elizabeth.getProyectil() != null) {
					    elizabeth.getProyectil().mover();
					    elizabeth.getProyectil().dibujar(entorno);

					    for (int i = 0; i < enemigos.length; i++) {
					        if (elizabeth.getProyectil() != null) {
					            if (enemigos[i] != null && elizabeth.getProyectil().colisionaConEnemigo(enemigos[i])) {
					                enemigos[i] = null; // El enemigo desaparece al ser impactado
					                elizabeth.setProyectil(null); // El proyectil desaparece al impactar

					             
					                enemigosEliminados++;
					                puntuacion += 100; // suma puntos por enemigo

					                if (enemigosEliminados % 10 == 0) {
					                    elizabeth.agregarVida(1); // método que suma una vida
					                }
					            }
					        }
					    }

					    if (elizabeth.getProyectil() != null) {
					        if (elizabeth.getProyectil().estaFueraDePantalla(entorno)) {
					            elizabeth.setProyectil(null);
					        }
					    }
					}
				}
			}
		}
		


					if (elizabeth.getProyectil() != null) { //si no es null (está activo), lo movemos y dibujamos
						elizabeth.getProyectil().mover();
						elizabeth.getProyectil().dibujar(entorno);

						for (int i = 0; i < enemigos.length; i++) {
							if (elizabeth.getProyectil() != null) {
								if (enemigos[i] != null && elizabeth.getProyectil().colisionaConEnemigo(enemigos[i])) {
								enemigos[i] = null; // El enemigo desaparece al ser impactado
								//renovarEnemigos(); // Renovamos los enemigos para llenar el espacio del enemigo eliminado
							
								elizabeth.setProyectil(null); // El proyectil desaparece al impactar
								}
							}
						}

						if (elizabeth.getProyectil() != null){ // Primeros chequeamos que no se haya eliminado por colision
							if (elizabeth.getProyectil().estaFueraDePantalla(entorno)) { // si sale de la pantalla, lo eliminamos y se vuelve null
								elizabeth.setProyectil(null);
							}
						}
					}
				}
			

	
	    private void renovarEnemigos() {
        	for (int i = 0; i < this.enemigos.length; i++) {
            	if (this.enemigos[i] != null) {
                	double ex = this.enemigos[i].getX();
                	if (ex < -100 || ex > entorno.ancho() + 100) {
                    	this.enemigos[i] = null;
                	}
            	}
            	if (this.enemigos[i] == null && entorno.numeroDeTick() % 120 == i*15) {
                	this.enemigos[i] = crearEnemigoNuevo();
            	}
        	}
    	}

    	private Enemigo crearEnemigoNuevo() {
        	int direccion;
        	double x;
        	if (Math.random() < 0.5) {
            	direccion = 1;
            	x = -60;
        	} else {
            	direccion = -1;
            	x = entorno.ancho() + 60;
        	}
        	double y = buscarAlturaLibre();
        	return new Enemigo(x, y, 35, 35, 2.0, direccion);
    	}

    	private double buscarAlturaLibre() {
        	double y;
        	boolean mismaAltura;
        	do {
            	y = 60 + Math.random() * 400;
            	mismaAltura = false;
            	for (int i = 0; i < islas.length; i++) {
                	if (islas[i] != null) {
                    	if (Math.abs(y - islas[i].getY()) < 50) {
                        	mismaAltura = true;
                    	}
                	}
            	}
        	} while (mismaAltura);
        		return y;
    	}		
		
		// Procesamiento de un instante de tiempo
		// ...
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}

}

