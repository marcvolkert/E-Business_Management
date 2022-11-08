package ex_2_1;

public class BestellPosition {
	public String material;
	public String preis;
	public int anzahl;
	
	@Override
	public String toString() {
		return "Material: " + material + "\nPreis: " + preis + "\nAnzahl: " +anzahl;
	}
}
