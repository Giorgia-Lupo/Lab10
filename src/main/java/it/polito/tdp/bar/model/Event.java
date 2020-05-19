package it.polito.tdp.bar.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		ARRIVO_GRUPPO_CLIENTI, TAVOLO_LIBERATO
	}
	
	private LocalTime time;
	private EventType type;	
	
	private int numPersone;
	private double tolleranza;
	private int numTavolo;
	
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
		numPersone=(int)((Math.random()*10)+1);
		tolleranza=Math.random();
		numTavolo = (int) (Math.random()*10);
		
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public int getNumPersone() {
		return numPersone;
	}

	public void setNumPersone(int numPersone) {
		this.numPersone = numPersone;
	}

	public void setType(EventType type) {
		this.type = type;
	}
	
	

	public double getTolleranza() {
		return tolleranza;
	}

	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}
	

	public int getNumTavolo() {
		return numTavolo;
	}

	public void setNumTavolo(int numTavolo) {
		this.numTavolo = numTavolo;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}

	@Override
	public int compareTo(Event other) {		
		return this.time.compareTo(other.time);
	}

}
