package a_2_3;

import java.io.Serializable;


public class BestellPosition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String material;
	public String preis;
	public int anzahl;
	
	@Override
	public String toString() {
		return "Material: " + material + "\nPreis: " + preis + "\nAnzahl: " +anzahl;
	}
}
