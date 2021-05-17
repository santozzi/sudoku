package logica;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class Imagen {
   
    private int ancho;
    private int alto;
    private String nom;
    private ImageIcon imagen;
    private Map<Integer,String> ruta;
    
	
    public Imagen() {
		this.ruta = new HashMap<Integer, String>();
		this.ancho = 40;
		this.alto = 40;
	    imagen = new ImageIcon();
		
	    for(int i=0;i<=9;i++) {
			ruta.put(i,"/imagenes/"+i+".jpg");
		}
		
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
