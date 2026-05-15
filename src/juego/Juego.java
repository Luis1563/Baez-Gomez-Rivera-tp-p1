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

    private Isla[] islas;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1280, 720);
        this.islas = inicializarIslas();
        
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	private Isla[] inicializarIslas() {
		Isla[] misIslas = new Isla[20]; // Ejemplo con 10 islas
        
        // 1. Islas de piso (fijas) (Para que el jugador no caiga al inicio)
		Isla[] misIslas1 = new Isla[20]; // Aumentamos el tamaño para tener más plataformas
	    int indice = 0;
	    for (int i = 0; i < 5; i++) {
	        misIslas1[indice] = new Isla(i * 250, 580, 200, i);
	        indice++;
	    }

	    // 2. GENERACIÓN POR "COLUMNAS" (Evita superposición)
	    double avanceX = 600; // Empezamos después del piso inicial
	    double distanciaEntreColumnas = 300; 
	    
	    while (indice < misIslas1.length) {
	        // Decidimos cuántas islas habrá en esta coordenada X (2 o 3)
	        int cantidadEnEstaLinea = (int)(Math.random() * 2) + 2; // Da 2 o 3

	        for (int i = 0; i < cantidadEnEstaLinea; i++) {
	            if (indice < misIslas1.length) {
	                // Niveles de altura fijos para que no se superpongan verticalmente
	                // Nivel 0: 450px, Nivel 1: 300px, Nivel 2: 150px
	                double alturaFija = 450 - (i * 150); 
	                
	                // Agregamos una pequeña variación en X para que no sea una línea perfecta
	                double variacionX = (Math.random() * 50) - 25; 
	                
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
	
		for (Isla isla : islas) {
	        if (isla != null) { 
	            // Cada isla sabe cómo dibujarse a sí misma
	            isla.dibujar(this.entorno); 
	            
	            // Aprovechamos el bucle para verificar si Elizabeth está apoyada
	           // if (elizabeth.estaApoyadaEn(isla)) {
	               // elizabeth.detenerCaida(isla.getY());
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
