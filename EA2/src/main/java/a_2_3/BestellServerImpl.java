package a_2_3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


interface BestellServer {} //Ergänzen Sie die Schnittstellendefinition für das ServerObjekt...

public class BestellServerImpl implements BestellServer{
	
	

	public void bestelle( BestellPosition bp) throws RemoteException {
		System.out.println("Bestellung erhalten:\n###########");
		System.out.println( bp );
	}
	
	public static void main(String[] args) throws Exception {
		LocateRegistry.createRegistry( Registry.REGISTRY_PORT );

		//Registrieren Sie den BestellServer in der RMI-Registrierung so, dass ein Zugriff von Clients erfolgen kann. 
	    
	    

	 

	}
	
}
