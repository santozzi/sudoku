package logica;

import javax.swing.ImageIcon;
/**
 * Está clase es la encargada de representar a una celda, esta celda tiene una imágen, sabe en qué
 * fila está en qué columna está, cual es el numero que contiene, qué tiepo de celda (estado) y en
 * qué cuadrante se encuentra.
 *  
 * @author Sergio Antozzi
 *
 */
public class Celda {
	private static int FIJO=0;
	private static int OK=1;
	private static int ERROR=2;
	private int fila;
	private int columna;
	private Imagen imagen;
	private int rotulo;
	private int estado;
	//>--Esta variables es la que le dice a la celda cuantas filas y columnas va a tener----<
	//>--está para poder calcular en qué cuadrante de la matriz se encuentra----------------<
	private int n;

	public Celda(int fila, int columna, int rotulo,int estado,int n) {
		this.n= n;
		this.fila = fila;
		this.columna = columna;
		this.rotulo = rotulo;
		
		this.estado= estado;
		

	}
    /**
     * getCuadrante
     * ------------
     * Calcula en qué cuadrante se encuentra la celda.
     * @return Devuelve una coordenada de tipo XY.
     */
	public XY getCuadrante() {
	
		return new XY(fila/(int)Math.sqrt(n),columna/(int)Math.sqrt(n));
	}

	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	
	public Imagen getImagen() {
		return imagen;
	}
	
	public ImageIcon getIcono() {
		
		return (new Imagen()).getImagen(rotulo);
	}



	public void setImagen(Imagen imagen) {
		this.imagen = imagen;

	}
	public int getRotulo() {
		return rotulo;

	}
	public void setRotulo(int rotulo) {
		this.rotulo = rotulo;
	
			//imagen.setNom(rotulo+"");

	}

	public String toString() {
		return "["+fila+","+columna+"] = "+rotulo+" cuadrante= "+getCuadrante();
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Celda clone() {
		return new Celda(fila,columna,rotulo,estado,n);
	}


}
