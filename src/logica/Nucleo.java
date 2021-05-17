package logica;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Nucleo
 * ------
 * Esta lógica está basada en una matriz nxn siendo n >1
 * 
 * @author Sergio Antozzi
 *
 */
public class Nucleo {


	//El Sudoku tradicional es con N=9
	private final static int N=9;
	//>----Cantidad de cuadrantes que que tiene es sudoku es igual a N la agrego pora que sea más legible---<
	private int cantCuadrantes;
	//>----Cantidad de cuadrantes por fila.
	private int cantCuadrantesXFila;
	//>---Con estas variables controlo que no se agregen numeros repetidos----<
	private FilaCol[] filas ;
	private FilaCol[] columnas;
	private FilaCol[][] cuadrantes;
	//>-----------------------------------------------------------------------<

	//>---Si la solución de la matriz anterior es correcta se guarda en esta matriz--<
	//----Si la solución es inválida la misma tendrá el valor null.------------------<
	private Celda[][] tablero;

	//>--Estas constantes son los tres estados posibles de una celda----------<
	//---FIJO para los números iniciales que no son modificables--------------<
	private static int FIJO=0;
	//---OK para los números agregados sin error estos son modificables-------<
	private static int OK=1;
	//---ERROR para los números que con contengan por lo menos una colisión---<
	private static int ERROR=2;
	//>--Variable entera para contar la cantidad de elementos agregados-------<
	private int completado;
	//>--Variable para cargar las rutas de las imagenes de las celdas---------<
	private Imagen imagenCelda;
	//>--Logger para indicar si la solución es válida o no al administrador del programa----<
	private static Logger Logger;
	public Nucleo () {
		//inicializo las variables descriptas anteriormente
		cantCuadrantesXFila = (int)Math.sqrt(N);
		filas = new FilaCol[N];
		columnas= new FilaCol[N];
		cuadrantes= new FilaCol[cantCuadrantesXFila][cantCuadrantesXFila];
		tablero = new Celda[N][N];
		completado= 0;
		imagenCelda = new Imagen();
		cantCuadrantes=N;
		if(Logger==null) {
			Logger = Logger.getLogger(Nucleo.class.getName());
			Handler hnd = new ConsoleHandler();

			hnd.setLevel(Level.FINE);
			Logger.addHandler(hnd);
			Logger.setLevel(Level.FINE);
			Logger rootLogger = Logger.getParent();
			for(Handler h: rootLogger.getHandlers()) {
				h.setLevel(Level.OFF);
			}
		}
		inicializarSudoku();
		verificarMatriz();
	}

	private void inicializarSudoku() {
		//lleno los arreglos con nuevos FilaCol (es una colección de Celdas)
		for(int i=0;i<N;i++) {
			filas[i]=new FilaCol(N);
			columnas[i]=new FilaCol(N);
		}
		//lleno la con nuevos FilaCol (es una colección de Celdas)
		for(int i=0;i<cantCuadrantesXFila;i++) {
			for(int j=0;j<cantCuadrantesXFila;j++) {
				cuadrantes[i][j]=new FilaCol(N);
			}
		}
	}


	//>---Verifico si la matriz del archivo sea correcta y la agrego al tablero-------------------<	
	private void verificarMatriz() {
		boolean aRetornar= true;
		//>----ConsultaMatriz es una interfase que se encarga de contarse con la solucion----<
		/*>---Es inicializada con la clase ConsultaArchivoMatriz que se encarga de traer-----
		la matriz del arcivo soluciones.txt--------------------------------------------------<*/
		ConsultaMatriz consMat = new ConsultaArchivoMatriz("src/Archivos/soluciones.txt");
		//>---Convierto la posible solución a una matriz de enteros-------------------------<
		Integer[][] matriz = consMat.obtenerMatriz();

		//>---Verifica si la matriz tiene errores y agregandolos a la matriz de celdas-----<
		if(matriz.length==N) {
			for(int i=0;i<matriz.length&&aRetornar;i++) {
				for(int j=0;j<matriz[0].length&&aRetornar;j++) {
					Celda aVerificar = new Celda(i,j,matriz[i][j],FIJO,N);

					if(verificarCelda(aVerificar, new LinkedList<Celda>()))
						agregarCelda(aVerificar);
					else
						aRetornar=false;
				}
			}
		}else
			aRetornar=false;

		if(aRetornar) {
			Logger.fine("La matriz se ingreso correctamente");
		}else {
			Logger.warning("La matriz esta corrupta");
			tablero = null;
		}
		//>--------------------------------------------------------------------------------<
	}

	/**
	 * verificarCelda
	 * --------------
	 * Verifica si hay una celda repetida en una fila, representada por un arreglo.
	 * Verifica si hay una celda repetida en una columna, representada por un arreglo.
	 * Verifica si hay una celda repetida en un cuadrante, representada por un arreglo.
	 * @param c variable de tipo celda.
	 * @return verdadero si no hay colisiones en los 3 arreglos, falso de lo contrario.
	 */
	public boolean verificarCelda(Celda c,List<Celda> colisiones) {
		boolean celdaFila= false;
		boolean celdaColumna = false;
		boolean celdaCuadrante = false;




		//verifico si existen coliciones en las filas
		FilaCol nuevaFila;  
		nuevaFila= filas[c.getFila()];
		Celda colision= nuevaFila.verificar(c);
		if(colision==null) {
			celdaFila= true;
		}else {
			colisiones.add(colision.clone());
		}

		//verifico si existen coliciones en las columnas
		FilaCol nuevaColumna;  
		//System.out.println(c.getColumna());
		nuevaColumna= columnas[c.getColumna()];
		colision = nuevaColumna.verificar(c);
		if(colision==null) {
			celdaColumna= true;
		}else {

			colisiones.add(colision.clone());
		}

		//vrifico si existe coliciones en los cuadrantes
		FilaCol nuevoCuadrante;

		//coordenada del cuadrante

		nuevoCuadrante = cuadrantes[c.getCuadrante().getY()][c.getCuadrante().getX()];
		colision = nuevoCuadrante.verificar(c);
		if(colision==null) {
			celdaCuadrante= true;
		}else {

			colisiones.add(colision.clone());
		}

		return celdaFila&&celdaColumna&&celdaCuadrante;
	}


	/**
	 * agregarCelda
	 * ------------
	 * Agrega la celda al sistema de verificación y a la matriz de celdas.
	 * Se asume que ya fue verificada antes de agregar una nueva celda.
	 * @param c
	 * @return
	 */
	public void agregarCelda(Celda c) {
		//para agregar se asume que 
		filas[c.getFila()].agregar(c);
		columnas[c.getColumna()].agregar(c);
		cuadrantes[c.getCuadrante().getY()][c.getCuadrante().getX()].agregar(c);
		//c.setImagen(imagenCelda);
		tablero[c.getFila()][c.getColumna()]=c; 
		completado++;
	}
	/**
	 * modificarCelda
	 * ---------------
	 * Modifica una celda tanto del tablero como del verificador
	 * Se asume que ya fue verificado.
	 * @param c
	 */
	public void modificarCelda(Celda anterior,Celda aModificar) {
		//Celda anterior = tablero[aModificar.getFila()][aModificar.getColumna()];
		System.out.println(aModificar);
		if(aModificar.getRotulo()==0) {
			borrarDelVerificador(anterior);
			borrarDelTablero(anterior);
		}else {
			borrarDelVerificador(anterior);
			agregarCelda(aModificar);
		}

	}
	private void borrarDelTablero(Celda c) {
		tablero[c.getFila()][c.getColumna()].setEstado(OK);
		tablero[c.getFila()][c.getColumna()].setRotulo(0); 
		completado--;
	}
	private void borrarDelVerificador(Celda c) {
		filas[c.getFila()].eliminar(c);
		columnas[c.getColumna()].eliminar(c);
		cuadrantes[c.getCuadrante().getY()][c.getCuadrante().getX()].eliminar(c);

	}
	public void blanquear(int cantidad){
		List<Celda> celdasVacias = new LinkedList<Celda>();

		Random rand = new Random(); 
		int ranFil;
		int ranCol;

		int cantVacias=0;
		while(cantVacias<cantidad) {
			ranFil = rand.nextInt(N);
			ranCol = rand.nextInt(N);


			if(tablero[ranFil][ranCol].getRotulo()!=0) {

				borrarDelVerificador(tablero[ranFil][ranCol]);
				tablero[ranFil][ranCol].setEstado(OK);
				tablero[ranFil][ranCol].setRotulo(0); 
				completado--;
				cantVacias ++;
			}

		}


	}
	//Este metodo se ejecuta una vez se genero el tablero y se blanqueo
	//Tomo el tablero y lo agrego la logica para poder corroborar las celdas
	public boolean cargarjuego(int cantidad) {
		boolean solucionValida = tablero!=null;
		if(solucionValida) {

			blanquear(cantidad);
		}
		return solucionValida;
	}
	public boolean estaCompleto() {
		return completado==N*N;
	}
	public int getCantCuadrantes() {
		return cantCuadrantes;
	}

	public Celda getCelda(int i, int j) {
		return tablero[i][j];
	}

	public int getCompletado() {
		return completado;
	}


}
