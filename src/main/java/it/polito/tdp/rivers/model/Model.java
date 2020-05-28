package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	Simulatore simulatore=new Simulatore();
	RiversDAO dao= new RiversDAO();
	Map<Integer,River> idMapRiver=new HashMap<>();
	
	public List<River> getAllRivers(){
		return dao.getAllRivers(idMapRiver);
	}
	public FlowInformation getFlowInformation(River r) {
		return dao.getFlowInformation(r);
	}
	public void fillFlowOfRiver(River r) {
		dao.fillFlowOfRiver(r, idMapRiver);
	}
	public void simula(River r,Double k) {
		simulatore.init(r,k);
		simulatore.run();
	}
	
	public Integer getGiorniInsufficienti() {
		return simulatore.getGiorniInsufficienti();
	}
	public Double getOccupazioneMedia() {
		return simulatore.getOccupazioneMedia();
	}
}
