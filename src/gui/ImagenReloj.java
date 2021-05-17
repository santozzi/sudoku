package gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
/**
 * ImagenReloj
 * -----------
 * Esta clase no la iba a hacer, la idea era usar otra instancia de la clase Imagen, pero tenia
 * que cargar las rutas desde las Celdas y desde el reloj, asi que decidi usar clases diferentes
 * con las rutas ya cargadas. Y dejo público el método agregarRuta por si en un futuro el programa tiene
 * un panel de configuracion donde cambiar la ruta para agregar otro tipo de imágenes.
 *
 * @author Sergio Antozzi
 *
 */


public class ImagenReloj {
    private int ancho;
    private int alto;
    private String nom;
    private ImageIcon imagen;
    
    private Map<Integer,String> ruta;
    
	
    public ImagenReloj() {
		this.ruta = new HashMap<Integer, String>();
		this.ancho = 12;
		this.alto = 28;
	    imagen = new ImageIcon();
		
	    for(int i=0;i<=9;i++) {
			ruta.put(i,"/imagenes/reloj/"+i+".jpg");
		}
		ruta.put(10,"/imagenes/reloj/Dospuntos.jpg");
	}
	public int getAncho() { 
		return ancho;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	public int getAlto() { 
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public ImageIcon getImagen(int num) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.ruta.get(num)));
	   	this.imagen.setImage(imageIcon.getImage().getScaledInstance(ancho, alto,0));
	   	
		return imagen;
	}
	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}
	public void agregarRuta(int num, String ruta) {
		this.ruta.put(num,ruta);
	}
}
