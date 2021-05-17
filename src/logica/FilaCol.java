package logica;
/**
 * FilaCol
 * -------
 * Es una colección de Celdas, fue creada para el sistema de verificacion de la clase Nucleo.
 * @author Sergio Antozzi
 *
 */
public class FilaCol {
   private int numero;
   private Celda[] filaCol;


public FilaCol(int cantCel) { 
	filaCol = new Celda[cantCel];
	for(int i=0;i<filaCol.length;i++)
		filaCol[i]=null;
}

/**
 * agregar recibe un valor ya verificado y lo agrega al arreglo.
 * @param cel
 */
public void agregar(Celda cel) {
	 filaCol[cel.getRotulo()-1]=cel;
}

public void eliminar(Celda cel) {
	filaCol[cel.getRotulo()-1]=null;
}
/**
 * verificar
 * 
 * @param cel, celda a verificar
 * @return devuelve true si el valor que hay en ese lugar del arreglo es null
 */
public Celda verificar(Celda cel) {
	Celda retorno = null;
 
	  int posicion = cel.getRotulo()-1;    
	  if(filaCol[posicion]!=null)
		 retorno = filaCol[posicion];
	return retorno;
}

public Celda getCelda(int c) {

	return filaCol[c-1];
}
public String toString() {
	String respuesta="";
	for(int i=0;i<9;i++) {
		respuesta += filaCol[i]+" ";
	}
	
	
	return respuesta;
}
}
