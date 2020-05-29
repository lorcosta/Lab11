package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.FlowInformation;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {
	/**
	 * take all the river from the database and return them after putting 
	 * into a idMap
	 * @param rivers
	 * @return
	 */
	public List<River> getAllRivers(Map<Integer, River> idMapRivers) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> result = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				River r=new River(res.getInt("id"), res.getString("name"));
				idMapRivers.put(r.getId(),r);
				result.add(r);
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	public FlowInformation getFlowInformation(River r) {
		final String sql="SELECT MIN(day) as 'prima_data', MAX(day) as 'ultima_data', COUNT(*) as misurazioni, AVG(flow) as media " + 
				"FROM flow " + 
				"WHERE river=?";
		FlowInformation f = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			if(res.next()) {
				f=new FlowInformation(res.getDate("prima_data").toLocalDate(), 
						res.getDate("ultima_data").toLocalDate(), res.getInt("misurazioni"), 
						res.getDouble("media"));
				r.setFlowAvg(res.getDouble("media"));
			}

			conn.close();
			if(f!=null) {
				return f;
			}else 
				throw new RuntimeException("Errore nella ricerca delle informazioni");
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
	}
	public void fillFlowOfRiver(River toFill, Map<Integer,River> idMapRiver) {
		final String sql="SELECT river,day,flow " + 
				"FROM flow " + 
				"WHERE river=? ";
		River r=idMapRiver.get(toFill.getId());
		List<Flow> flows=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, toFill.getId());
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				flows.add(new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"),r));
			}

			conn.close();
			r.setFlows(flows);
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
}
