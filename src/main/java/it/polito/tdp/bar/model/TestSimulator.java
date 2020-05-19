package it.polito.tdp.bar.model;

public class TestSimulator {

	public static void main(String[] args) {

		Simulator sim = new Simulator();
		sim.setTavoli();
		sim.setFrequenza();
		
		sim.run();

		sim.durata();
		int clienti=sim.getClienti();
		int soddisfatti=sim.getSoddisfatti();
		int insodd=sim.getInsoddisfatti();
		
		System.out.println("Clienti: "+clienti+"\n");
		System.out.println(clienti+" "+soddisfatti+" "+insodd);
	}

}
