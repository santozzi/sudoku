package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import logica.Celda;
import logica.Nucleo;
import java.awt.FlowLayout;
/**
 * Esta clase es el entorno gráfico del Sudoku.
 * @author Sergio Antozzi
 *
 */

public class sudokuGrafica extends JFrame {
	//>--Estas constantes son los tres estados posibles de una celda----------<
	//---FIJO para los números iniciales que no son modificables--------------<
	private static int FIJO=0;
	//---OK para los números agregados sin error estos son modificables-------<
	private static int OK=1;
	//---ERROR para los números que con contengan por lo menos una colisión---<
	private static int ERROR=2;
	//>--Pongo todos los JPanel como atributos globales porque uso metodos para cada uno para que sea mas--
	//legible--<
	private JPanel contentPane;
	private JPanel[][] pnlCuadrante;
	private JLabel[][] casillas;
	private JPanel contenedor;
	private JPanel pnlOpciones;
	private JPanel pnlReloj;
	//>---Etiquetas del reloj------<
	private JLabel[] tiempo;
	//>---Etiqueta para mostrar a la celda auxiliar aux-----<
	private JLabel temporal;
	//>---Celda que le dice a la etiqueta temporal que número es-----<
	private Celda  aux;
	//>---Conexión con la lógica----<
	private Nucleo logica;
	//>---Reloj es una clase que hace todos los calculos para entregar la imagen correspondiente al tiempo
	//----transcurrido--------------<
	private Reloj reloj;
	//----Como es una matriz cuadrada la cantidad de cuadrantes es la misma que la cantidad de filas y 
	//----de columnas en el sudoku tradicional es 9 se configura desde la logica-----------------------<
	private  int cantCuadrantes;
	//----La cantidad de cuadrantes en una fila de un sudoku tradicional es 3, este valor lo utilizo para 
	//----los grid y para algunos for dobles----------<
	private  int cantCuadrantesXFila;
	//>---Datos de configuración---------------------<
	//>---Estos dos constantes son el esacio que hay entre las celdas ------------<
	private  static final int espacioEntreFilas=2;
	private static final int espacioEntreColumnas=2;
	//>---Esta constante es para la cantidad de celdas en blanco al iniciar el juego es un valor que
	//>---se envia a un metodo de la lógica------------------------<
	private static final int cantidadBlancos=30;
	//>---Es la clase encargada de enviar cada cierto tiempo una instrucción-------<
	private Timer tiempoT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sudokuGrafica frame = new sudokuGrafica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public sudokuGrafica() {
		//>---Inicializo variables-----<
		logica = new Nucleo();
		cantCuadrantes = logica.getCantCuadrantes();
		cantCuadrantesXFila= (int)Math.sqrt(cantCuadrantes);
		temporal = new JLabel();
		aux= new Celda(0,0,0,FIJO,cantCuadrantes);
		casillas = new JLabel[cantCuadrantes][cantCuadrantes];
		//>----------------------------------------------------<    

		//>---Configoro el Frame e inicio el panel del contenedor principal--------<
		setTitle("Proyecto N°2 - TDP 2020 - Sudoku - By Sergio Antozzi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 507);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//>------------------------------------------------------------------------<

		//>---Llamo a los metodos privados donde se encuentra cada panel-----------<
		pnlReloj();
		tablero();
		pnlAux();
		pnlOpciones();
		pnlIncio();
		//>------------------------------------------------------------------------<

	}
	/**
	 * tablero
	 * -------
	 * Este metodo se encarga de llenar de paneles, que luego serán los cuadrantes del 
	 * tablero, en un panel más grande.
	 */
	private void tablero() {
		//>---Inicializo el contenedor donde van los cuadrantes-------------<
		contenedor = new JPanel();
		contenedor.setBounds(10, 11, 458, 420);
		contentPane.setBackground(new Color(200,200,200));
		contenedor.setLayout(new GridLayout(cantCuadrantesXFila, cantCuadrantesXFila, espacioEntreFilas, espacioEntreColumnas));
		//>---Agrego los cuadrantes en forma de paneles dentro del contenedor---<
		pnlCuadrante = new JPanel[cantCuadrantesXFila][cantCuadrantesXFila];
		for(int i=0;i<cantCuadrantesXFila;i++) {
			for(int j=0;j<cantCuadrantesXFila;j++) {
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(cantCuadrantesXFila,cantCuadrantesXFila,espacioEntreFilas-1,espacioEntreColumnas-1));
				panel.setBorder(new LineBorder(new Color(0, 0, 0)));
				pnlCuadrante[i][j]=panel;
				contenedor.add(pnlCuadrante[i][j]);
			}
		}
		contentPane.add(contenedor);
	}

	/**
	 * pnlInicio
	 * ---------
	 * En este panal se encuentra el botón que va a llenar el tablero y activar el reloj.
	 */
	private void pnlIncio() {
		JPanel pnlInicio = new JPanel();
		pnlInicio.setBounds(470, 11, 159, 51);
		contentPane.add(pnlInicio);
		pnlInicio.setLayout(null);

		JButton btnInicio = new JButton("Comencemos!!");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//>---Cargo con celdas los paneles, si la solucion del archivo es valida, ---<
				if(logica.cargarjuego(cantidadBlancos)) {
					for(int i=0;i<cantCuadrantes;i++) {
						for(int j=0;j<cantCuadrantes;j++) {
							Celda celdaBuscada = logica.getCelda(i, j);
							casillas[i][j] = new JLabel(celdaBuscada.getIcono());
							if(celdaBuscada.getEstado()==FIJO)
								casillas[i][j].setBorder(new LineBorder(new Color(0,0,0)));
							else
								casillas[i][j].setBorder(new LineBorder(new Color(200,200,200)));
							//Cuando suelto el click
							casillas[i][j].addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									if(celdaBuscada.getEstado()!=FIJO) {
										verificar(celdaBuscada);
										if(logica.estaCompleto()) {
											tiempoT.cancel();
											JOptionPane.showMessageDialog(null, "Ganaste !!!!!!, tu tiempo fue: "+reloj.toString());
										}
									}	
								}
							});
							pnlCuadrante[celdaBuscada.getCuadrante().getX()][celdaBuscada.getCuadrante().getY()].add(casillas[i][j]);
							pnlCuadrante[celdaBuscada.getCuadrante().getX()][celdaBuscada.getCuadrante().getY()].updateUI();
						}
					}
				}
				btnInicio.setEnabled(false);
				reloj();
			}
		});
		btnInicio.setBounds(0, 0, 159, 51);
		pnlInicio.add(btnInicio);
	}

	/**
	 * pnlAux
	 * ------
	 * Panel donde se encuentra la etiqueta auxiliar que deriva de presionar sobre una etiqueta
	 * del panel de opciones. Por defecto tiene la etiqueta vacia.
	 */
	private void pnlAux() {
		JPanel pnlAux = new JPanel();
		temporal.setIcon(aux.getIcono());
		pnlAux.setBounds(470, 125, 159, 55);
		pnlAux.add(temporal);
		pnlAux.setBackground(new Color(200,200,200));
		contentPane.add(pnlAux);
	}
	/**
	 * pnlOpciones
	 * -----------
	 * En este panel se encuentran las celdas para elegir, al hacer click sobre una etiqueta,
	 * esta pasa a la etiqueta auxiliar y luego al hacer click en una etiqueta que no estén fija
	 * en el tablero, se cambia por la etiqueta auxiliar.
	 */
	private void pnlOpciones() {
		pnlOpciones = new JPanel();
		pnlOpciones.setBounds(470, 191, 159, 240);
		pnlOpciones.setLayout(new GridLayout(cantCuadrantesXFila+1, cantCuadrantesXFila+1, 3, 3));
		for(int i=0; i<10;i++) {
			Celda c= new Celda(0,0,i,FIJO,cantCuadrantes);
			JLabel casilla = new JLabel(c.getIcono());  
			pnlOpciones.add(casilla);
			casilla.setBorder(new LineBorder(new Color(0,0,0)));
			casilla.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					aux.setRotulo(c.getRotulo());
					temporal.setIcon(c.getIcono());
					pnlOpciones.updateUI();
				}
			});
		} 
		contentPane.add(pnlOpciones);
	}

	/**
	 * reloj
	 * -----
	 * Este método es el encargado de preguntarle a la clase Reloj qué imágenes debe poner en el 
	 * pnlReloj, las agrega y refresca el pnlReloj cada segundo.
	 */
	private void reloj() {
		tiempoT = new Timer();
		reloj.setTiempoInicial(new Date().getTime());
		TimerTask tarea = new TimerTask() {
			@Override
			public void run() {
				reloj.calcularTiempo(new Date().getTime());
				tiempo[0].setIcon(reloj.getImaHrD());
				tiempo[1].setIcon(reloj.getImaHrU());
				tiempo[2].setIcon(reloj.getImaDosPuntos());
				tiempo[3].setIcon(reloj.getImaMinD());
				tiempo[4].setIcon(reloj.getImaMinU());
				tiempo[5].setIcon(reloj.getImaDosPuntos());
				tiempo[6].setIcon(reloj.getImaSegD());
				tiempo[7].setIcon(reloj.getImaSegU());
				pnlReloj.updateUI();
			}
		};
		tiempoT.schedule(tarea,0, 1000);
	}


	/**
	 * pnlReloj
	 * --------
	 * Inicia el panel donde se encuentra el reloj y agrega imágenes al reloj antes de iniciar
	 * el juego.
	 */
	private void pnlReloj() {
		reloj = new Reloj();
		pnlReloj = new JPanel();
		pnlReloj.setBounds(470, 73, 159, 51);
		pnlReloj.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		tiempo = new JLabel[8];	
		for(int i=0;i<8;i++) {
			tiempo[i] = new JLabel();
			pnlReloj.add(tiempo[i]);
		}
		//>---Estado inicial del reloj antes de presionar el boton de comencemos----<
		tiempo[0].setIcon(reloj.getImaHrD());
		tiempo[1].setIcon(reloj.getImaHrU());
		tiempo[2].setIcon(reloj.getImaDosPuntos());
		tiempo[3].setIcon(reloj.getImaMinD());
		tiempo[4].setIcon(reloj.getImaMinU());
		tiempo[5].setIcon(reloj.getImaDosPuntos());
		tiempo[6].setIcon(reloj.getImaSegD());
		tiempo[7].setIcon(reloj.getImaSegU());
		contentPane.add(pnlReloj);
	}

	/**
	 * verificar
	 * ---------
	 * Le pregunta a la lógica si la celda ingredada tiene colisiones y de no tenerlas la
	 * agrega al al tablero y al sistema de verificación de la lógica.
	 * 
	 * @param cel celda a verificar
	 */
	private void verificar(Celda cel) {
		List<Celda> colisiones = new LinkedList<Celda>();
		boolean verificado = false;
		if(cel.getRotulo()==0&&aux.getRotulo()!=0) {
			cel.setRotulo(aux.getRotulo());
			casillas[cel.getFila()][cel.getColumna()].setIcon(cel.getIcono());
			verificado=logica.verificarCelda(cel,colisiones);

			if(verificado) {
				logica.agregarCelda(cel);
			}else {
				aviso(0,cel,colisiones);
			}
		}else if(cel.getRotulo()!=0&&aux.getRotulo()==0) {
			aux.setFila(cel.getFila());
			aux.setColumna(cel.getColumna()); 
			casillas[cel.getFila()][cel.getColumna()].setIcon(aux.getIcono());
			logica.modificarCelda(cel,aux);
		}else if(cel.getRotulo()!=0&&aux.getRotulo()!=0) {
			aux.setFila(cel.getFila());
			aux.setColumna(cel.getColumna()); 
			
			casillas[cel.getFila()][cel.getColumna()].setIcon(aux.getIcono());
			
			verificado=logica.verificarCelda(aux,colisiones);
			if(verificado) {
				logica.modificarCelda(cel,aux);
			}else {
				aviso(cel.getRotulo(),aux,colisiones);
			}
		}
	}

	/**
	 * Aviso
	 * -----
	 * Muestra donde están las colisiones y muestra un cartal, luego de precionar aceptar en el cartel
	 * devuelve las celdas modificadas a su estado anterior
	 * @param anterior parametro de tipo entero señalando el rotulo que estaba antes del cambio
	 * @param cel parametro de tipo Celda que indica la celda en donde hice click
	 * @param colisiones lista donde se encuentran todas las colisiones de celdas.
	 */
	private void aviso(int anterior,Celda cel,List<Celda> colisiones) {
		casillas[cel.getFila()][cel.getColumna()].setBorder(new LineBorder(new Color(200,0,0)));
		for(Celda colision : colisiones) {
			casillas[colision.getFila()][colision.getColumna()].setBorder(new LineBorder(new Color(200,0,0)));
		}
		JOptionPane.showMessageDialog(null, "No, ahí no se puede");
		casillas[cel.getFila()][cel.getColumna()].setBorder(new LineBorder(new Color(200,200,200)));
		for(Celda colision : colisiones) {
			if(colision.getEstado()==FIJO)
				casillas[colision.getFila()][colision.getColumna()].setBorder(new LineBorder(new Color(0,0,0)));
			else
				casillas[colision.getFila()][colision.getColumna()].setBorder(new LineBorder(new Color(200,200,200)));
		}
		cel.setRotulo(anterior);
		casillas[cel.getFila()][cel.getColumna()].setIcon(cel.getIcono());
	}

}
