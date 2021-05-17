package logica;
/**
 * ConsultaMatriz
 * -------------
 * Es una interface de recolección de matrices.
 * 
 * @author Sergio Antozzi
 *
 */
public interface ConsultaMatriz {
    /**
     * obtenerMatriz
     * -------------
     * recupera una matriz de enteros
     * @return Una matriz de enteros de cantidad de filas y columnas pre definidas
     */
	public Integer[][] obtenerMatriz();
	/**
	 * setFilas
	 * --------
	 * @param f cantidad de filas
	 * 
	 */
	public void setFilas(int f);
	/**
	 * setColumnas
	 * --------
	 * @param c cantidad de columnas
	 * 
	 */
	public void setColumnas(int c);
}
