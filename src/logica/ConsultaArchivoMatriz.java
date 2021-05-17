package logica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
/**
 * ConsultaArchivoMatriz es la encargada de implementar la interface ConsultaMatriz
 * En esta implementación rescata números de un archivo de texto y los vuelca a una matriz de enteros.
 * 
 * @author Sergio Antozzi
 *
 */
public class ConsultaArchivoMatriz implements ConsultaMatriz {
	private String ruta;
	private File archivo;
	private int filas;
	private int columnas;


	public ConsultaArchivoMatriz(String ruta) {
	
		this.ruta = ruta;
		this.archivo = new File(ruta);
		this.filas=9;
		this.columnas=9;
	}

	@Override
	public Integer[][] obtenerMatriz() {
		//Defino e inicializo una nuevMatriz de enteros
		Integer[][] nuevaMatriz = new Integer[filas][columnas];

		//Conexion a archivo
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(ruta);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			//cantFilas será utilizado para bajar el cursor de la matriz a la linea de abajo. 
			int cantFilas=0;
            			
			while ((strLine = br.readLine()) != null)   {
				//quito los espacios de la linea capturada
				strLine = strLine.replaceAll("\\s", "");

				int tamLinea = strLine.length();
				//recorro la linea capturada y agrego el valor de cada caracter en la fila cantFilas
				for(int i=0;i<tamLinea;i++) {
					//suponiendo que el archivo de origen solo tiene valores enteros sin contar los espacios,
					//paso el caracter a un numero entero.
					nuevaMatriz[cantFilas][i]= (int)strLine.charAt(i)-48;
				}

				//de lo contrario, significa que estoy dentro de la matriz y termino de llenar una fila
				//por lo que incremento en uno contFilas.
				cantFilas++;

			}
     	//cierro el archivo
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nuevaMatriz;
	}

	@Override
	public void setFilas(int f) {
		this.filas=f;
	}

	@Override
	public void setColumnas(int c) {
		this.columnas=c;
	}
	

}


