package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	private LocalDate time;
	private Flow flow;
	/**
	 * @param time
	 * @param flow
	 */
	public Event(LocalDate time, Flow flow) {
		super();
		this.time = time;
		this.flow = flow;
	}
	public LocalDate getTime() {
		return time;
	}
	public void setTime(LocalDate time) {
		this.time = time;
	}
	public Flow getFlow() {
		return flow;
	}
	public void setFlow(Flow flow) {
		this.flow = flow;
	}
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}

}
