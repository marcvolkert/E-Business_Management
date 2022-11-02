package a_2_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	static private String leseNachricht(Socket s) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = br.read(buffer, 0, 200);
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}
	
	static public String erstelleSocketUndLeseNachricht() throws Exception {
		//Erstellen Sie einen ServerSocket der auf eine Verbindung wartet und eingehende Nachrichten liest.
		return null;
	}
	
	static public BestellPosition unmarschalNachricht( String msg ) {
		//Implementieren Sie eine geeignete Deserialisierung der Nachricht hier, um die empfangenen Daten in ein Objekt 
		//der Klasse BestellPosition zu transformieren.
		return null;
	}
	
	public static void bearbeiteBestellung( BestellPosition bp) {
		System.out.println("Bestellung erhalten\n#######");
		System.out.println( bp );
	}
	
	public static void empfangeBestellung() throws Exception {
		String msg = erstelleSocketUndLeseNachricht();
		BestellPosition bp = unmarschalNachricht(msg);
		bearbeiteBestellung(bp);
	}
	

	public static void main(String[] args) throws Exception  {
		System.out.println("Server gestartet");
		
		while( true ) {
			empfangeBestellung();
		}
		
	
	}

}
