package a_2_3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
	public static void main(String[] args) throws Exception {
		
		BestellPosition bp = new BestellPosition();
		bp.material = "Stuhl";
		bp.preis = "302.91";
		bp.anzahl = 5;
		
		//Fragen Sie die Registrierung ab, um eine Referenz auf einen zuvor registrierten BestellServer zu erhalten.
		//Verwenden Sie die Referenz, um die Bestellung der Bestellposition durchzuführen. 

	}
}
