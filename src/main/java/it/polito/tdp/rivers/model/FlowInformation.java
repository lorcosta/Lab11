package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class FlowInformation {
	private LocalDate firstFlowDay;
	private LocalDate lastFlowDay;
	private Integer totMisurazioni;
	private double flowAvg;
	/**
	 * @param firstFlowDay
	 * @param lastFlowDay
	 * @param totMisurazioni
	 * @param flowAvg
	 */
	public FlowInformation(LocalDate firstFlowDay, LocalDate lastFlowDay, Integer totMisurazioni, double flowAvg) {
		super();
		this.firstFlowDay = firstFlowDay;
		this.lastFlowDay = lastFlowDay;
		this.totMisurazioni = totMisurazioni;
		this.flowAvg = flowAvg;
	}
	public LocalDate getFirstFlowDay() {
		return firstFlowDay;
	}
	public void setFirstFlowDay(LocalDate firstFlowDay) {
		this.firstFlowDay = firstFlowDay;
	}
	public LocalDate getLastFlowDay() {
		return lastFlowDay;
	}
	public void setLastFlowDay(LocalDate lastFlowDay) {
		this.lastFlowDay = lastFlowDay;
	}
	public Integer getTotMisurazioni() {
		return totMisurazioni;
	}
	public void setTotMisurazioni(Integer totMisurazioni) {
		this.totMisurazioni = totMisurazioni;
	}
	public double getFlowAvg() {
		return flowAvg;
	}
	public void setFlowAvg(double flowAvg) {
		this.flowAvg = flowAvg;
	}
	
	
	
}
