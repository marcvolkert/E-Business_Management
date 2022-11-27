package org.example;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;

import java.time.Instant;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;


public class JobAgent extends Agent {

	int bearbeitungsdauer = 0;
	int budget;
	
	@SuppressWarnings("serial")
	protected void setup() {
		//Bearbeitungsdauer des Auftrags aus dem übergebenen Parameter lesen
		Object[] args = getArguments();
		if( args.length < 2 ) {
			System.out.println( "keine Bearbeitungsdauer für den Job angegeben" );
			this.doDelete();
			return;
		}
		try {
			bearbeitungsdauer = Integer.parseInt(args[0].toString());
			budget = Integer.parseInt(args[1].toString());
		} catch( Exception e) {
			System.out.println( "Bearbeitungsdauer und Budget eines Jobs müssen vom Typ Integer sein" );
			this.doDelete();
			return;
		}
		
		//Agenten vom Typ "maschine" vom Verzeichnisdienst abfragen
		//und im Array resourcenAgenten speichern
		AID[] maschinenAgenten = null;

		//TODO: implementieren Sie die Abfage des Agent-Directory-Service hier ...
		DFAgentDescription ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("maschine");
		ad.addServices(sd);
		try {
			DFAgentDescription[] results = DFService.search(this, ad);
			maschinenAgenten = new AID[results.length];
			for(int i = 0; i < results.length; i++) {
				maschinenAgenten[i] = results[i].getName();
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		//Nachricht (Call for Proposal - CFP) gemäß des Kontraktnetz-Protokolls
		//vorbereiten, die an alle gefundenen Agenten versendet wird.
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		assert maschinenAgenten != null;
		for (AID aid : maschinenAgenten) {
			msg.addReceiver(aid);
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);

			//TODO: fügen Sie der Nachricht den Inhalt (Content) hinzu ...
			msg.setContent(String.valueOf(bearbeitungsdauer));
		}

		//Antwortverhalten für die Nachricht als Subklasse der Klasse <ContractNetInitiator>
		//implementieren und registrieren.
		addBehaviour(new ContractNetInitiator(this, msg) {
			//Überschreiben der Funktion <handleAllResponses> um das Auswahlverhalten
			//des Auftragsagenten nach Erhalt aller Vertragsvorschläge zu implementieren
			@Override
			protected void handleAllResponses(Vector responses, Vector acceptances) {
				//Auswertung der Vorschläge und ermitteln des besten Vorschlags.
				//Gleichzeitig werden die Antworten an alle Resourcenagenten vorbereitet
				int fruehsteFertigstellungszeit = -1;
				ACLMessage accept = null;
				Enumeration e = responses.elements();
				while (e.hasMoreElements()) {
					ACLMessage msg = (ACLMessage) e.nextElement();
					if (msg.getPerformative() == ACLMessage.PROPOSE) {
						ACLMessage reply = msg.createReply();
						reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
						acceptances.addElement(reply);

						//TODO: implementieren Sie die Auswahl der Maschine hier ...
						//TODO: setzen die die Variable "accept" auf eine entsprechende Referenz ...
						//TODO: Irgendwas wird noch verdoppelt!?
						String[] parts = msg.getContent().split(";");
						int fertigstellungszeit = Integer.parseInt(parts[0]);
						int bearbeitungspauschale = Integer.parseInt(parts[1]);
						if (bearbeitungspauschale <= budget && (fertigstellungszeit < fruehsteFertigstellungszeit || fruehsteFertigstellungszeit < 0)) {
							fruehsteFertigstellungszeit = fertigstellungszeit;
							accept = reply;
						}
					}
				}
				//Akzeptieren des besten Angebotes vorbereiten und den entsprechenden
				//Ressourcenagenten durch eine Nachricht informieren.
				if (accept != null) {
					//TODO: erstellen Sie die Akzeptanznachricht
					accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					accept.setContent(String.valueOf(bearbeitungsdauer));
				}
			}} );
	  } 

}
