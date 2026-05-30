package juego;


import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros

	private boolean mostrandoInicio;

	private Castillo castillo;
	private Entorno entorno;
	private Princesa elizabeth;
	private Proyectil proyectil;
    private Isla[] islas;
	private Enemigo[] enemigos;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{

		this.mostrandoInicio = true;


		elizabeth = new Princesa(640, 360, 20, 50);
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1280, 720);
        this.islas = inicializarIslas();
		this.enemigos = new Enemigo[20];
		//this.castillo = new Castillo(300, 540, 150, 200);
		     
		proyectil = null; // no hay proyectil activo al inicio
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	private Isla[] inicializarIslas() {
		Isla[] misIslas = new Isla[10]; //10 islas
        
		Isla[] misIslas1 = new Isla[50]; // Aumentamos el tamaño para tener más plataformas
	    int indice = 0;
		
		
        // 1. Islas de piso (fijas) (Para que el jugador no caiga al inicio)
	    for (int i = 0; i < 15; i++) {
	        misIslas1[indice] = new Isla(i * 250, entorno.alto() - 10, 200, i);
				if (i == 9) { // Si es la última isla de piso, colocamos el castillo sobre ella
				double x = misIslas1[indice].getX();
				double y = misIslas1[indice].getY() - misIslas1[indice].getAlto()/2; // Colocamos el castillo justo encima de la isla
				
				this.castillo = new Castillo(x, y, 160, 200);
				this.castillo.setY(this.castillo.getY() - this.castillo.getAlto()/2); // Ajustamos la posición del castillo para que esté sobre la isla
			}
	        indice++;
	    }


	    // 2. GENERACIÓN POR "COLUMNAS" (Esto Evita la superposición)

	    // 2. GENERACIÓN POR "COLUMNAS" (Evita superposición)

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

		    if (mostrandoInicio) {
		        // Configuramos el estilo del texto [9]
		        this.entorno.cambiarFont("Arial Black", 60,new Color(0, 150,255)); 
		        // Escribimos el título en pantalla [6]
		        this.entorno.escribirTexto("Super Elizabeth Sis", 350, 350);
		        
		        this.entorno.cambiarFont("Arial", 25, Color.GREEN);
		        this.entorno.escribirTexto("Presione ENTER para jugar", 550, 400);

		        // Detectamos si el usuario presiona la tecla ENTER para cambiar el estado [7, 10]
		        if (this.entorno.estaPresionada(this.entorno.TECLA_ENTER)) {
		            this.mostrandoInicio = false;
		        }
		    } else {

		this.castillo.dibujar(entorno);
		elizabeth.dibujar(entorno);
		this.entorno.colorFondo(new Color(128, 0, 128));
		
		

		/*if(elizabeth != null) {
				elizabeth.dibujar(entorno);
			elizabeth.actualizarFisica(islas, entorno.alto());
			}*/
			 //física (gravedad y caída) con límite inferior de pantalla

		//if(elizabeth != null) {
			//	elizabeth.dibujar(entorno);
			//elizabeth.actualizarFisica(islas, entorno.alto());
			//}
			// física (gravedad y caída) con límite inferior de pantalla

		if (elizabeth != null) {
			if (elizabeth.bordeSuperior() > entorno.alto()) {
				//la princesa cayó al vacío, la reiniciamos al medio
				elizabeth = null;
			}
		}
		if(elizabeth==null) {
			elizabeth = new Princesa(entorno.ancho() / 2, entorno.alto() / 2, 20, 50);
			elizabeth.dibujar(entorno);
		}
		
		if(elizabeth.getProyectil()!=null) {
			elizabeth.getProyectil().dibujar(entorno);			
		}
	
		for (Isla isla : islas) {
	        if (isla != null) { 
	            // Cada isla sabe cómo dibujarse a sí misma
	            isla.dibujar(this.entorno); 
	            
	             //Aprovechamos el bucle para verificar si Elizabeth está apoyada
	             	//if (elizabeth.estaApoyadaEn(isla)) {
	             	//	elizabeth.detenerCaida(isla.getY());
	            }
	        }
		for (Isla Isla : islas) {
	        if (Isla != null) Isla.dibujar(entorno);
	    }		

	    // Movimiento IZQUIERDA
	    if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) 
	        && elizabeth.getX() - elizabeth.getAncho()/2 > 0) {

	        if (!elizabeth.colisionaPorIzquierda(islas)) {
	            elizabeth.moverIzquierda();
	        }
	    }
		
		double velocidad = 3;
			
			
		// Movimiento DERECHA
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)){ 
			double limiteMovimiento = entorno.ancho() * 0.55; // La princesa puede moverse libremente hasta el 55% del ancho de la pantalla
		// La princesa se mantiene en el centro, el mapa se desplaza a la izquierda

	       	if (elizabeth.getX() < limiteMovimiento && !elizabeth.colisionaPorDerecha(islas)) {
	           	elizabeth.moverDerecha();
			}
				
			else if(elizabeth.getX() >= limiteMovimiento && !elizabeth.colisionaPorDerecha(islas)) { // Solo mueve las islas si la princesa está más allá del centro de la pantalla
				if ((castillo.bordeDerecho() <= entorno.ancho())){

					if (elizabeth.bordeDerecho() < entorno.ancho()){
						elizabeth.moverDerecha();
					}
					else {
						elizabeth.setX(entorno.ancho() - elizabeth.getAncho()/2); // Evita que la princesa se salga por la derecha
					}
				}
				else {
					this.castillo.mover(-velocidad); // El castillo se acerca solo si EXISTE
					for (int i = 0; i < islas.length; i++) {
						if (islas[i] != null) { // Solo mueve las islas si la princesa está más allá del centro de la pantalla
							islas[i].mover(-velocidad); //mueve cada isla en la dirección opuesta al movimiento de la princesa para simular desplazamiento del mapa
						}
					}
				}

			}
		}


	    // Movimiento ABAJO (gravedad o tecla abajo)
	    if (entorno.estaPresionada(entorno.TECLA_ABAJO) 
	        && elizabeth.getY() + elizabeth.getAlto()/2 < entorno.alto()) {

	        if (!elizabeth.colisionaPorAbajo(islas)) {
	            elizabeth.moverAbajo();
	        }
	    }

	    // Movimiento ARRIBA (salto)
	    if (entorno.estaPresionada(entorno.TECLA_ARRIBA) 
	        && elizabeth.getY() - elizabeth.getAlto()/2 > 0) {

	        if (!elizabeth.colisionaPorArriba(islas)) {
	            elizabeth.saltar();
	        }
	    }

	    // Aplicar gravedad automática cuando no está sobre una isla
	    if (!elizabeth.colisionaPorAbajo(islas)) {
	        elizabeth.moverAbajo();
	    }
		this.renovarEnemigos();

		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] != null) {
				this.enemigos[i].actualizar(0);
				this.enemigos[i].dibujar(this.entorno);
			}
		}



	    //double velocidad = 3;

	    // AVANZAR HACIA LA DERECHA
    		//mapaDesplazamiento += velocidad;


	    // RETROCEDER (solo hasta el inicio)
	    /*if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
	        int mapaDesplazamiento= 0;
			if (mapaDesplazamiento > 0) {
	            // El mapa vuelve a la derecha porque aún no estamos en el inicio
	            for (Isla isla : islas) {
	                if (isla != null) isla.mover(velocidad);
	            }
			}
	    }*/
	    //}
	            //if (castillo != null) castillo.moverse(-velocidad);
	           // mapaDesplazamiento -= velocidad;
	       // } else {
	            // Si ya estamos en el inicio (mapaDesplazamiento == 0), 
	            // la princesa se mueve físicamente a la izquierda de la pantalla
	            //elizabeth.moverIzquierda(velocidad);
	        //}
	    //}
	    
	    
	    // disparo con botón izquierdo solo si no hay proyectil activo
		if (entorno.mousePresente() && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && elizabeth.getProyectil() == null) {
			elizabeth.disparar(entorno.mouseX(), entorno.mouseY());
			//double mouseX = entorno.mouseX();
			//double mouseX = entorno.mouseX();
			//double mouseY = entorno.mouseY();
			//proyectil = new Proyectil(elizabeth.getX(), elizabeth.getY(), 10, 10); //empieza en la misma x e y que la princesa
			//proyectil.dispararHacia(mouseX, mouseY); //dispara hacia la dirección del mouse
		}

		//movimiento del proyectil si es que hay uno activo
		if (elizabeth.getProyectil() != null) { //si no es null (está activo), lo movemos y dibujamos
			elizabeth.getProyectil().mover();
		}
		
		//colisiones y límites del proyectil
		if (elizabeth.getProyectil() != null && elizabeth.getProyectil().estaFueraDePantalla(entorno)) { // si sale de la pantalla, lo eliminamos y se vuelve null
			elizabeth.setProyectil(null);
		}

		}

		//if (elizabeth.getProyectil() != null && elizabeth.getProyectil().colisionaConEnemigo(enemigo)) { // si colisiona con algún enemigo, lo eliminamos y se vuelve null{
		//	elizabeth.setProyectil(null);
		//}

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

