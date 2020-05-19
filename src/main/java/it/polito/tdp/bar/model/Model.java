package it.polito.tdp.bar.model;

public class Model {

	Simulator sim = new Simulator();
	
	public void risolvi() {
		sim.run();
	}
	
	public int getClienti() {
		return sim.getClienti();
	}
	
	public int getInsoddisfatti() {
		return sim.getInsoddisfatti();
	}
	
	public int getSoddisfatti() {
		return sim.getSoddisfatti();
	}
}
