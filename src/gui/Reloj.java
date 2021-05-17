package gui;
import javax.swing.ImageIcon;
/**
 * Esta clase es la encargada de hacer lo necesario para brindar las imágenes del reloj a la gráfica.
 * @author Sergio Antozzi
 *
 */

public class Reloj {

	private long tiempoInicial;
	private ImagenReloj[] imaReloj;
	private int segU;
	private int segD;
	private int minU;
	private int minD;
	private int hrU;
	private int hrD;
	public Reloj() {
		this.tiempoInicial = 0;
		imaReloj = new ImagenReloj[7];

		for(int j= 0; j<7;j++) {
			imaReloj[j]= new ImagenReloj();
		}
		segU=0;
		segD=0;
		minU=0;
		minD=0;
		hrU=0;
		hrD=0;

	}
    /**
     * calcularTiempo
     * --------------
     * Este metodo recibe un entero de tipo long el tiempo en milisegundos
     * este dato es restado en al tiempo inicial y devuelve el resultado.
     * Tambien es el encargado de cargar las variables que luego van a ser
     * las encargadas de formar los números
     * @param i
     * @return i-tiempoInicial
     */
	public int calcularTiempo(long i) {
		long tiempoTranscurrido = i-tiempoInicial;
		int segundos;
		int minutos;
		int horas;
		segundos = (int)(tiempoTranscurrido/1000);
		minutos = segundos/60;
		horas= minutos/60;
		segundos= segundos%60;
		minutos= minutos%60;
		segU= segundos%10;
		segD =(segundos/10)%10;
		minU = minutos%10;
		minD = (minutos/10)%10;
		hrU = horas%10;
		hrD= (horas/10)%10;
		return (int)(tiempoTranscurrido/1000);
	}   
    
	//--Estos métodos son los encargados de retornar la imágen según el tiempo------------<
	public ImageIcon getImaHrD() {
		return imaReloj[0].getImagen(hrD);
	}
	public ImageIcon getImaHrU() {
		return imaReloj[1].getImagen(hrU);
	}
	public ImageIcon getImaMinD() {
		return imaReloj[2].getImagen(minD);
	}
	public ImageIcon getImaMinU() {
		return imaReloj[3].getImagen(minU);
	}
	public ImageIcon getImaSegD() {
		return imaReloj[4].getImagen(segD);
	}
	public ImageIcon getImaSegU() {
		return imaReloj[5].getImagen(segU);
	}
	public ImageIcon getImaDosPuntos() {
		return imaReloj[6].getImagen(10);
	}
	//>----------------------------------------------------------<
	
	
	//>---Getters y Setters--------------------------------------<
	public void setTiempoInicial(int tiempoInicial) {
		this.tiempoInicial = tiempoInicial;
	}

	public long getTiempoInicial() {
		return tiempoInicial;
	}

	public void setTiempoInicial(long tiempoInicial) {
		this.tiempoInicial = tiempoInicial;
	}
    //>----------------------------------------------------------------<
	
	//>---Sobre escribro el metodo toString para mostrar el tiempo-----<
	@Override
	public String toString() {
		return hrD+""+hrU+":"+minD+minU+":"+segD+segU;
		}
   
}
