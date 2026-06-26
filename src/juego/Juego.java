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
	private int islasPiso;
    private Isla[] islas;
	private Enemigo[] enemigos;
	private Item[] items; // Item que puede soltar el enemigo y recoger la princesa
	private boolean juegoGanado; // boolean para pantalla ganadora
	private boolean juegoPerdido; // boolean para pantalla de derrota
	private double respawnX; // para reiniciar a la princesa
	private double respawnY;

	private Image imagenDerrota; // Imagen para la pantalla de derrota
	private Image imagenVictoria; // Imagen para la pantalla de victoria
	private Image portada; // Imagen para la pantalla de inicio
	private Image fondo; // Imagen de fondo del juego


	private double velocidadMapa;
	private int puntuacion;           // sistema de puntos
	private int proximaVidaExtra;
	private int ticksMensaje;
	private int duracionMensaje;

	
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "baez-gomez-rivera-tp-p1", 1280, 720);
		this.mostrandoInicio = true; //Pantalla de inicio
		elizabeth = new Princesa(200, 100, 30, 80, 10, entorno, 1);
		this.islasPiso = 15; // Esto va a determinar el largo del mapa
		this.islas = inicializarIslas(this.islasPiso);
		this.velocidadMapa = 3;
		this.enemigos = new Enemigo[20];
		//this.item = null; // El item comienza como null, se asignará cuando un enemigo muera
		this.items = new Item[5]; // Tamaño máximo de items en pantalla
		this.portada = Herramientas.cargarImagen("portada.png"); // Carga la imagen de portada para la pantalla de inicio
		this.fondo = Herramientas.cargarImagen("fondo.png"); // Carga la imagen de fondo del juego

		this.juegoGanado = false; // boolean para pantalla ganadora
		this.imagenDerrota = Herramientas.cargarImagen("derrota.png"); // Carga la imagen de derrota
		this.juegoPerdido = false;
		this.imagenVictoria = Herramientas.cargarImagen("victoria.png"); // Carga la imagen de victoria

		this.respawnX = entorno.ancho() / 2;
		this.respawnY = entorno.alto() / 2 + 50;


		this.puntuacion = 0;
		this.proximaVidaExtra = 100;
		this.ticksMensaje = 0;
		this.duracionMensaje = 200;

		// Inicia el juego!
		this.entorno.iniciar();
	}

	private void reiniciarJuego() {
		elizabeth = new Princesa(200, 100, 30, 80, 10, entorno, 1);
		this.islas = inicializarIslas(this.islasPiso);
		this.velocidadMapa = 3;
		this.enemigos = new Enemigo[20];
		//this.item = null;
		this.items = new Item[5]; // Tamaño máximo de items en pantalla
		this.juegoGanado = false;
		this.juegoPerdido = false;

		this.puntuacion = 0;
		this.proximaVidaExtra = 100;
		this.ticksMensaje = 0;
		this.duracionMensaje = 200;
	}

	private Isla[] inicializarIslas(int islasPisoParametro) {


		Isla[] islas = new Isla[500]; // cantidad maxima de islas que se pueden crear

		int islasPiso = islasPisoParametro;
		//double islasFlotantes = 30;

		// creamos todas las islas de piso
		for (int i = 0; i < islasPiso; i++){
			islas[i] = new Isla(i *250 , entorno.alto() - 10, 200, 20);
			
			// Accomodamos las islas para que solo se vean dentro de la pantalla
			islas[i].setX(islas[i].getX() + islas[i].getAncho()/2);
			//islas[i].setX(islas[i].bordeIzquierdo());
			
			if (i == islasPiso -1) { // Si es la última isla de piso, colocamos el castillo sobre ella
				double x = islas[i].getX();
				double y = islas[i].getY() - islas[i].getAlto() / 2; // Coloca el castillo justo encima de la isla
				
				this.castillo = new Castillo(x, y, 160, 200);
				this.castillo.setY(this.castillo.getY() - this.castillo.getAlto()/2);
			}
		}
		

		int indice = 1;
		for (int i = islasPiso; i < (islas.length); i++){
			double yIslas1 = 200;
			double yIslas2 = 350;
			double yIslas3 = 500;
			//double distanciaMinimaEnX = 100;

			double anchoIsla = Math.random() * 200;
			while (anchoIsla < 90){
				anchoIsla = Math.random() * 200;
			}
			double random = Math.random();
			if (random<0.33){
				islas[i] = new Isla(indice*280 , yIslas1, anchoIsla, 20);
				// Si la isla está sobre la ultima isla de piso, la eliminamos
				if (islas[i].bordeDerecho()>islas[islasPiso-1].bordeIzquierdo()){
					islas[i] = null;
				}
				if(islas[i] != null && islas[i-1]!= null){
					// Ponemos islas en la misma x que la isla anterior para ocupar más espacios
				if (Math.random() < 0.45 && islas[i-1] != islas[islasPiso-1] && islas[i-1].getY() != yIslas1){
					islas[i].setX(islas[i-1].getX());
					indice -= 1;
				}}
			}
			else if (random>0.66){
				islas[i] = new Isla(indice*260 , yIslas2, anchoIsla, 20);
				// Si la isla está sobre la ultima isla de piso, la eliminamos
				if (islas[i].bordeDerecho()>islas[islasPiso-1].bordeIzquierdo()){
					islas[i] = null;
				}
				if(islas[i] != null && islas[i-1]!= null){
					// Ponemos islas en la misma x que la isla anterior para ocupar más espacios
				if (Math.random() < 0.45 && islas[i-1] != islas[islasPiso-1] && islas[i-1].getY() != yIslas2){
					islas[i].setX(islas[i-1].getX());
					indice -= 1;
				}}
			}
			else{
				islas[i] = new Isla(indice*250  , yIslas3, anchoIsla, 20);
				// Si la isla está sobre la ultima isla de piso, la eliminamos
				if (islas[i].bordeDerecho()>islas[islasPiso-1].bordeIzquierdo()){
					islas[i] = null;
				}
				if(islas[i] != null && islas[i-1]!= null){
					// Ponemos islas en la misma x que la isla anterior para ocupar más espacios
					if (Math.random() < 0.45 && islas[i-1] != islas[islasPiso-1] && islas[i-1].getY() != yIslas3){
						islas[i].setX(islas[i-1].getX());
						indice -= 1;
					}
				}
			}
			indice+=1;
		}
		return islas;
	}

	
	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	*/
	
	
	public void tick()
	{
		// PANTALLA DE INICIO
		if (mostrandoInicio) {
			// Configuramos el estilo del texto y el color 
			this.entorno.colorFondo(new Color(252, 209,0)); // Pinta el fondo de negro
			this.entorno.dibujarImagen(this.portada, this.entorno.ancho() * 0.82, this.entorno.alto() / 2, 0, 1.2);
			this.entorno.cambiarFont("Akira Expanded", 60,new Color(0, 150,255)); 
			
			// Escribimos el título en pantalla
			this.entorno.escribirTexto("Super Elizabeth Sis", 40, 350);
			
			// Subtitulo
			this.entorno.cambiarFont("Poppins Medium", 30, Color.BLACK);
			this.entorno.escribirTexto("Presione ENTER para jugar", 40, 400);
			
			// Grupo
			this.entorno.cambiarFont("Poppins Black", 30, Color.WHITE);
			this.entorno.escribirTexto("Baez - Gomez - Rivera - TP - P1", 40, entorno.alto() - 30);
			
			// Detecta si el usuario presiona la tecla ENTER para cambiar el estado
			if (this.entorno.estaPresionada(this.entorno.TECLA_ENTER)) {
				this.mostrandoInicio = false;
			}
		}

		// PANTALLA DE VICTORIA
		else if (this.juegoGanado){
			// con esto creamos la pantalla de victoria
			this.entorno.colorFondo(Color.BLACK); // Pinta el fondo de negro
			
			// Configuramos la letra y el mensaje de victoria
			// Titulo
			this.entorno.cambiarFont("Akira Expanded", 60, Color.GREEN);
			this.entorno.escribirTexto("Ganaste", 40, 350);

			// Subtitulo
			this.entorno.cambiarFont("Poppins Medium", 30, Color.WHITE);
			this.entorno.escribirTexto("¡Elizabeth liberó a Mario!", 40, 400); 
			
			// Imagen
			this.entorno.dibujarRectangulo(this.entorno.ancho() / 2 + this.entorno.ancho() / 4, this.entorno.alto() / 2, this.entorno.ancho()/2, this.entorno.alto(), 0, new Color(0, 150,255));
			this.entorno.dibujarImagen(this.imagenVictoria, this.entorno.ancho() / 2 + this.entorno.ancho() / 4, this.entorno.alto() / 2, 0, 0.8);
			
			return; // Corta el tick acá para congelar todo el juego al ganar
		}

		// PANTALLA DE DERROTA
		else if (juegoPerdido) {
			// con esto creamos la pantalla de victoria
			this.entorno.colorFondo(Color.BLACK); // Pinta el fondo de negro

			// Titulo
			String mensajeDePerdida= "GAME OVER";
			this.entorno.cambiarFont("Akira Expanded", 60, Color.RED, 0);
			this.entorno.escribirTexto(mensajeDePerdida, 40, 350);
			
			// Subtitulo
			this.entorno.cambiarFont("Poppins Medium", 30, Color.WHITE);
			this.entorno.escribirTexto("Presione ENTER para reiniciar", 40, 400);
			
			// Imagen
			this.entorno.dibujarRectangulo(this.entorno.ancho() / 2 + this.entorno.ancho() / 4, this.entorno.alto() / 2, this.entorno.ancho()/2, this.entorno.alto(), 0, Color.RED);
			this.entorno.dibujarImagen(this.imagenDerrota, this.entorno.ancho() / 2 + this.entorno.ancho() / 4, this.entorno.alto() / 2 - 25, 0, 0.8);
			
			// Detecta si el usuario presiona la tecla ENTER para cambiar el estado
			if (this.entorno.estaPresionada(this.entorno.TECLA_ENTER)) {
				this.juegoPerdido = false; // Reiniciamos el estado de pérdida para permitir jugar de nuevo
				
				// Reiniciar el juego
				this.reiniciarJuego();
			}
		}

		//inicia el juego normal
		else{
			this.entorno.dibujarImagen(this.fondo, this.entorno.ancho() / 2, this.entorno.alto() / 2, 0, 0.70);
			
			
			// Dibujamos islas
			for (int i = 0; i < islas.length; i++) {
				if (islas[i] != null) { 
					islas[i].dibujar(entorno); 
				}
			}	
			
			// Dibujamos el castillo
			this.castillo.dibujar(entorno);
			
			// Dibujamos a la princesa
			elizabeth.dibujar(entorno);
			elizabeth.actualizarFisica(islas);
			elizabeth.dibujarVidas(entorno);
			
			// Dibujamos a los enemigos
			this.renovarEnemigos();
			for (int i = 0; i < this.enemigos.length; i++) {
				if (this.enemigos[i] != null) {
					this.enemigos[i].actualizar(0);
					this.enemigos[i].dibujar(this.entorno);
				}
			}
			
			// Dibujamos los items que sueltan los enemigos
			for (int i = 0; i < this.items.length; i++) {
				if (this.items[i] != null){
					this.items[i].dibujar(this.entorno);
					
					if (this.items[i].bordeDerecho() < 0){
						this.items[i] = null;
					}
				}
			}
			
			
			// Si hay un proyectil activo, se mueve y se dibuja
			if (elizabeth.getProyectil() != null) {
				elizabeth.getProyectil().mover();
				elizabeth.getProyectil().dibujar(entorno);
			}
			
			
			// Puntos texto
			entorno.cambiarFont("Poppins Medium", 25, Color.BLACK);
			entorno.escribirTexto("Puntos: " + puntuacion, 400, 35);
			
			// Vida extra
			if (this.puntuacion >= this.proximaVidaExtra){
				if (this.elizabeth.getVidasRestantes() < this.elizabeth.getVidasIniciales()){
					elizabeth.ganarVida();
					this.ticksMensaje = this.entorno.numeroDeTick() + this.duracionMensaje;
				}
				this.proximaVidaExtra += 100;
			}
			
			// Mensaje vida extra
			if (this.entorno.numeroDeTick() < this.ticksMensaje){
				entorno.cambiarFont("Poppins Medium", 20, Color.BLACK);
				entorno.escribirTexto("¡Vida Extra!", this.elizabeth.getX()-60, this.elizabeth.bordeSuperior() - 10);
			}
			
			
			// Si la princesa cae al vacío
			if (elizabeth.bordeSuperior() > entorno.alto()) {
				elizabeth.perderVida();
				
				// Si pierde todas las vidas, juego perdido
				if (!elizabeth.estaViva()) {
					elizabeth.setProyectil(null);
					elizabeth = null; // La princesa desaparece al perder todas las vidas
					juegoPerdido = true;
					return;
				}
				// Si sigue con vidas, reniciamos posicion inicial
				else {
					elizabeth.reiniciarPosicion(respawnX, respawnY);
				}
			}
			
			//---   COLISIONES DE LA PRINCESA  ---
			// Si Elizabeth roza el castillo, se activa la victoria
			if (elizabeth != null && this.castillo.princesaWin(elizabeth)) {
				this.juegoGanado = true;
			}

			// Si la princesa colisiona con un enemigo
			for (int i = 0; i < enemigos.length; i++) {
				if (elizabeth.colisionaPorAbajo(enemigos[i]) || elizabeth.colisionaPorArriba(enemigos[i]) || elizabeth.colisionaPorDerecha(enemigos[i]) || elizabeth.colisionaPorIzquierda(enemigos[i])) {
					elizabeth.perderVida();
					enemigos[i] = null; // El enemigo desaparece al colisionar con la princesa

					if (!elizabeth.estaViva()) {
						elizabeth.setProyectil(null);
						elizabeth = null; // La princesa desaparece al perder todas las vidas
						juegoPerdido = true;
						return;
					}
				}
			}
			
			// Si la princesa colisiona con un item
			for (int i = 0; i < this.items.length; i++){
				Item item = this.items[i];
				if ( item != null && (elizabeth.colisionaPorAbajo(item) || elizabeth.colisionaPorArriba(item) || elizabeth.colisionaPorDerecha(item) || elizabeth.colisionaPorIzquierda(item))) {
					this.items[i] = null; // El item desaparece al colisionar con la princesa
					// Solamente gana vida si le falta alguna
					if (this.elizabeth.getVidasRestantes() < this.elizabeth.getVidasIniciales()){
						elizabeth.ganarVida();
						this.ticksMensaje = this.entorno.numeroDeTick() + this.duracionMensaje;
					}
					else{
						puntuacion += 10;
					}
				}
			}




			//---   MOVIMIENTO DE LA PRINCESA   ---

			// Movimiento IZQUIERDA
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && elizabeth.getX() - elizabeth.getAncho()/2 > 0) {
				elizabeth.setDireccion(0);
				if (elizabeth.puedeMover(- elizabeth.getVelocidadX(), 0, islas)) {
					elizabeth.moverIzquierda();
				}
			}
				
			// Movimiento DERECHA
			if (entorno.estaPresionada(entorno.TECLA_DERECHA) && elizabeth.bordeDerecho() < entorno.ancho()) { 
				elizabeth.setDireccion(1);
				// Si la princesa se puede mover y no está en el limite de movimiento
				if (elizabeth.puedeMover(elizabeth.getVelocidadX(), 0, islas) && !elizabeth.estaEnlimiteMovimiento(elizabeth.getVelocidadX())){
					elizabeth.moverDerecha();
				}
				
				// Si la princesa se puede mover y está en el limite de movimiento
				// Si está en el limite la princesa "se movería a la velocidad del mapa" (se deja de mover en x)
				else if (elizabeth.puedeMover(elizabeth.getVelocidadX(), 0, islas) && elizabeth.estaEnlimiteMovimiento(elizabeth.getVelocidadX())){
					// Si el castillo no está dentro de la pantalla
					if ((castillo.bordeDerecho() > entorno.ancho())){
						// Movemos el castillo y los elementos en pantalla en esta parte para hacerlo solo cuando se aprieta la tecla derecha
						this.castillo.mover(-this.velocidadMapa);
						for (int i = 0; i < islas.length; i++) {
							if (islas[i] != null) { // Solo mueve las islas si la princesa está más allá del centro de la pantalla
									islas[i].mover(-this.velocidadMapa); //mueve cada isla en la dirección opuesta al movimiento de la princesa para simular desplazamiento del mapa
							}
						}
						
						// Movemos los items
						for (int i = 0; i < this.items.length; i++){
							if(this.items[i]!=null){
								items[i].mover(-this.velocidadMapa);
							}
						}

					}
					// Si el castillo está dentro de la pantalla
					else {
						elizabeth.moverDerecha();
					}
				}
			}

			// Movimiento ARRIBA (salto)
			if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && elizabeth.estaApoyado(islas)) { 
				// Solo puede saltar si está apoyada sobre una isla
				elizabeth.saltar();
			}




			//---   DISPARO Y PROYECTIL   ---
			// Disparo con Botón Izquierdo
			if (entorno.mousePresente() && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && elizabeth.getProyectil() == null) {
				elizabeth.disparar(entorno.mouseX(), entorno.mouseY());
			}

			// Colision de proyectil con enemigo
			if (elizabeth.getProyectil() != null) {
				for (int i = 0; i < enemigos.length; i++) {
					if (elizabeth.getProyectil().colisionaConEnemigo(enemigos[i])) {

						// Generamos el item si corresponde
						if (Math.random() < 0.3){
							boolean itemCreado = false;
							for(int j = 0; j < this.items.length; j++){
								if (!itemCreado && this.items[j] == null){
									this.items[j] = enemigos[i].soltarItem(entorno);
									this.items[j].dibujar(this.entorno);
									itemCreado = true;
								}
							}
						}

						puntuacion += 10;
						enemigos[i] = null; // El enemigo desaparece
						elizabeth.setProyectil(null); // El proyectil desaparece
						return; // Salimos del bucle
					}
				}
			}

			// Cuando el proyectil sale de pantalla
			if (elizabeth.getProyectil() != null){
				if (elizabeth.getProyectil().estaFueraDePantalla(entorno)) {
					elizabeth.setProyectil(null);
				}
			}
		}
	}


	//metodos
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
        	return new Enemigo(x, y, 45, 35, this.velocidadMapa,direccion);
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

