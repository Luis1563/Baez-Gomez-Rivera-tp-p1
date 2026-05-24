package juego;


import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Princesa elizabeth;
	private Proyectil proyectil;
    private Isla[] islas;
	private Enemigo[] enemigos;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		elizabeth = new Princesa(640, 360, 20, 50);
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1280, 720);
        this.islas = inicializarIslas();

		this.enemigos = new Enemigo[10]; 
    	this.enemigos = new Enemigo[6]; 
		for (int i = 0; i < this.enemigos.length; i++) {
			double xInicial;
			int direccionBicho; // Tipo int, igual que en tu constructor
			
			// Altura al azar en el cielo
			double yInicial = 80 + (Math.random() * 400); 
			
			if (i % 2 == 0) {
				// Los pares nacen en la IZQUIERDA y van a la DERECHA
				xInicial = -100 - (i * 200); 
				direccionBicho = 1; 
			} else {
				// Los impares nacen en la DERECHA y van a la IZQUIERDA
				xInicial = 1380 + (i * 200); 
				direccionBicho = -1; 
			}
			
			// Pasamos los 6 parámetros EXACTOS que te pide tu clase Enemigo:
			// x, y, ancho, alto, velocidad, direccion
			this.enemigos[i] = new Enemigo(xInicial, yInicial, 35, 35, 2.0, direccionBicho);
		}
		     
		proyectil = null; // no hay proyectil activo al inicio
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	private Isla[] inicializarIslas() {
		Isla[] misIslas = new Isla[10]; //10 islas
        
        // 1. Islas de piso (fijas) (Para que el jugador no caiga al inicio)
		Isla[] misIslas1 = new Isla[50]; // Aumentamos el tamaño para tener más plataformas
	    int indice = 0;
	    for (int i = 0; i < 15; i++) {
	        misIslas1[indice] = new Isla(i * 250, 580, 200, i);
	        indice++;
	    }

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
		elizabeth.dibujar(entorno);
		this.entorno.colorFondo(new Color(128, 0, 128));
		

		if(elizabeth != null) {
			elizabeth.dibujar(entorno);
			//elizabeth.actualizarFisica(islas, entorno.alto());
		}
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
		
		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] != null) {
				
				// 1. Avanzan según su propia dirección (si es 1 suma X, si es -1 resta X)
				double nuevaX = this.enemigos[i].getX() + (2.0 * this.enemigos[i].getDireccion());
				
				// 2. EFECTO SCROLL (Se arrastran con las teclas de tus compañeros)
				if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
					nuevaX -= 2; 
				}
				if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
					nuevaX += 2; 
				}
				
				// Guardamos la posición en el objeto
				this.enemigos[i].setX(nuevaX);
				
				// 3. Lo dibujamos
				this.enemigos[i].dibujar(this.entorno);
			}
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
	    if (entorno.estaPresionada(entorno.TECLA_DERECHA) 
	        && elizabeth.getX() + elizabeth.getAncho()/2 < entorno.ancho()) {

	        if (!elizabeth.colisionaPorDerecha(islas)) {
	            elizabeth.moverDerecha();
				if(elizabeth.getX() > entorno.ancho() / 2) { // Solo mueve las islas si la princesa está más allá del centro de la pantalla
					for (int i = 0; i < islas.length; i++) {
						if (islas[i] != null && elizabeth.getX() > entorno.ancho() / 2) { // Solo mueve las islas si la princesa está más allá del centro de la pantalla
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



	    //double velocidad = 3;

	    // AVANZAR HACIA LA DERECHA
	    //if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
	        // La princesa se mantiene en el centro, el mapa se desplaza a la izquierda
	        //for (Isla isla : islas) {
	            //if (isla != null) isla.mover(-velocidad);
	        //}
	       // if (castillo != null) castillo.moverse(velocidad); // El castillo se acerca
	      //  mapaDesplazamiento += velocidad; 
	   // }

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
		if (entorno.mousePresente() && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && proyectil == null) {
			double mouseX = entorno.mouseX();
			double mouseY = entorno.mouseY();
			proyectil = new Proyectil(elizabeth.getX(), elizabeth.getY(), 10, 10); //empieza en la misma x e y que la princesa
			proyectil.dispararHacia(mouseX, mouseY); //dispara hacia la dirección del mouse
		}

		if (proyectil != null) { //si no es null (está activo), lo movemos y dibujamos
			proyectil.mover();
			proyectil.dibujar(entorno);
			if (proyectil.estaFuera(entorno.ancho(), entorno.alto())) { // si sale de la pantalla, lo eliminamos y se vuelve null
				proyectil = null;
			}
		}


	    }
	
		
		// Procesamiento de un instante de tiempo
		// ...
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}

}

