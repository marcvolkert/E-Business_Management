package ex_2_3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;


interface BestellServer extends Remote {
	public void bestelle(BestellPosition bp) throws RemoteException;
}

public class BestellServerImpl implements BestellServer {

	@Override
	public void bestelle(BestellPosition bp) throws RemoteException {
		System.out.println("Bestellung erhalten:\n###########");
		System.out.println( bp );
	}
	
	public static void main(String[] args) throws Exception {
		LocateRegistry.createRegistry( Registry.REGISTRY_PORT );

		//Registrieren Sie den BestellServer in der RMI-Registrierung so, dass ein Zugriff von Clients erfolgen kann.
		BestellServer stub = (BestellServer) UnicastRemoteObject.exportObject(new BestellServerImpl(), 0);
		RemoteServer.setLog(System.out);

		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("BestellServer", stub);

	}
	
}
