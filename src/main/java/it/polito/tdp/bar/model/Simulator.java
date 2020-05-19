 package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	//CODA DEGLI EVENTI, inizialmente vuota
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	//PARAMETRI DI SIMULAZIONE --> impostati dall'esterno (setter())
	private Map<Integer, Integer> tavoli = new TreeMap<>();
	private Duration T_IN = Duration.of(10, ChronoUnit.MINUTES);
	
	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(18, 00);
	
	private final int numEventi = 2000;
	
	//MODELLO DEL MONDO, numero di tavoli rimasti
	private Map<Integer, Integer> tavoliRimasti; //la chiave è il numero del tavolo
	
	//VALORI DA CALCOLARE --> da fornire all'esterno (getter())
	private int clienti;
	private int soddisfatti;
	private int insoddisfatti;
	
	//metodi per impostare i parametri
	public void setTavoli() {
		this.tavoli.put(10,2);
		this.tavoli.put(8,4);
		this.tavoli.put(6,4);
		this.tavoli.put(4,5);
	}
	
	public void setFrequenza() {
		double f = Math.random();
		int frequenza = (int) ((f*10)+1);
		this.T_IN = Duration.of(frequenza, ChronoUnit.MINUTES);
		
	}
	
	//metodi per restituire i risultati
	public int getClienti() {
		return clienti;
	}
	
	public int getSoddisfatti() {
		return soddisfatti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	//	SIMULAZIONE VERA E PROPRIA
	public void run() {
		
		//preparazione iniziale
		this.tavoliRimasti= this.tavoli;  //new TreeMap<>(this.tavoli);
			//System.out.println(tavoliRimasti);
		this.insoddisfatti=0;
		this.clienti=0;
		this.soddisfatti=0;
		
		//creo gli eventi
		this.queue.clear();
		LocalTime oraArrivoGruppo = this.oraApertura;
			//System.out.println(oraArrivoGruppo);
		
		do {
			Event e = new Event(oraArrivoGruppo, EventType.ARRIVO_GRUPPO_CLIENTI);
			this.queue.add(e);			
			oraArrivoGruppo = oraArrivoGruppo.plus(this.T_IN);	
				//System.out.println(e+" "+oraArrivoGruppo);
		}while(oraArrivoGruppo.isBefore(this.oraChiusura));		
		
		/*for(int i = 0; i<=numEventi; i++) {
			//this.setFrequenza();
			oraArrivoGruppo = oraArrivoGruppo.plus(this.T_IN);
			Event e = new Event(oraArrivoGruppo, EventType.ARRIVO_GRUPPO_CLIENTI);
			//e.setN();
			System.out.println(e+" "+oraArrivoGruppo+" "+this.T_IN);
			queue.add(e);
		}*/
		
		
		//esecuzione del ciclo di simulazione
		while(!this.queue.isEmpty()){
			Event e = this.queue.poll();
			ProcessEvent(e);
		}
	}

	private void ProcessEvent(Event e) {
		switch (e.getType()) {
			case ARRIVO_GRUPPO_CLIENTI:
				this.clienti+=e.getNumPersone();
				boolean esisteTavolo = false;
				
				for(Integer persPerTavolo : this.tavoli.keySet()) {
					
					double postiDisponibili = persPerTavolo/2; //almeno il 50%
					if(e.getNumPersone()>=postiDisponibili && e.getNumPersone()<=persPerTavolo && this.tavoliRimasti.get(persPerTavolo)!=0 && esisteTavolo==false) {
						esisteTavolo = true;
						//System.out.println(persPerTavolo+" "+postiDisponibili+" "+e.getNumPersone()+"\n");
						//Aggiorno il modello del mondo
						this.tavoliRimasti.replace(persPerTavolo, this.tavoliRimasti.get(persPerTavolo)-1);
						
						//Aggiorno i risultati							
						//this.clienti+=e.getNumPersone();
						this.soddisfatti+=e.getNumPersone();
						
						
						//Genera nuovi eventi
						Event nuovo = new Event(e.getTime().plus(this.durata()), EventType.TAVOLO_LIBERATO);
						nuovo.setNumTavolo(persPerTavolo);
						System.out.println(nuovo);
						this.queue.add(nuovo);		
						
					}
				}
				/*if(esisteTavolo=false) {
					double prob = Math.random();
					if(prob>=e.getTolleranza()) {
						this.soddisfatti+=e.getNumPersone();
						this.clienti+=e.getNumPersone();
					}else {
						this.clienti+=e.getNumPersone();
						this.insoddisfatti+=e.getNumPersone();
					}
				}*/		
				if(esisteTavolo==false) {
					if(calcolaTolleranza()==false) {
						this.insoddisfatti+=e.getNumPersone();
					}else {
						this.soddisfatti+=e.getNumPersone();
					}
				}


				//System.out.println(e.getNumPersone()+" "+ clienti);
				break;
			case TAVOLO_LIBERATO:
				Integer nTav = e.getNumTavolo();
				this.tavoliRimasti.replace(nTav, this.tavoliRimasti.get(nTav)+1);
				break;				
		}
	}
	
	public Duration durata() {		
		
		int b = 0;
		while(b<60) {
			double a = Math.random();
			b = (int)((a*100)+20);		
		}	
		//System.out.println(b);
	return Duration.of(b, ChronoUnit.MINUTES);		
	}	

	public boolean calcolaTolleranza() {
		
		int i = (int) (Math.random()*10);

		if(i==0) {
			return false;
		}else {
			return true;
		}
	}



	
}
