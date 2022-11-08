package a_2_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	static final int PORT = 8442;
	
	static private String leseNachricht(Socket s) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = br.read(buffer, 0, 200);
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}
	
	static public String erstelleSocketUndLeseNachricht() throws Exception {
		//DONE - Erstellen Sie einen ServerSocket der auf eine Verbindung wartet und eingehende Nachrichten liest.
		String serialisierteNachricht;
		try(ServerSocket serverSocket = new ServerSocket(PORT)) {
			Socket socket = serverSocket.accept();
			serialisierteNachricht = leseNachricht(socket);
		}
		return serialisierteNachricht;
	}
	
	static public BestellPosition unmarschalNachricht( String msg ) {
		//DONE - Implementieren Sie eine geeignete Deserialisierung der Nachricht hier, um die empfangenen Daten in ein Objekt
		//der Klasse BestellPosition zu transformieren.
		String[] values = msg.split("\t");
		BestellPosition bp = new BestellPosition();
		bp.material=values[0]; bp.preis=values[1]; bp.anzahl=Integer.parseInt(values[2]);
		return bp;
	}
	
	public static void bearbeiteBestellung(BestellPosition bp) {
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
