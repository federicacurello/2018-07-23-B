package it.polito.tdp.newufosightings.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import it.polito.tdp.newufosightings.model.Evento.TipoEvento;
import it.polito.tdp.newufosightings.model.Evento;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings(int anno) {
		String sql = "SELECT * FROM sighting " + 
				"WHERE YEAR(DATETIME)=? " + 
				"ORDER BY datetime";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<State> loadAllStates(Map<String, State> map) {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
				map.put(state.getId(), state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public List<Adiacenza> trovaArchi(int anno, int gg) {
		String sql = "SELECT n.state1 AS stato1, n.state2 AS stato2, COUNT(*) AS cnt, s1.DATETIME AS DATE1, s2.DATETIME AS DATE2 " + 
				"FROM neighbor AS n, sighting AS s1, sighting AS s2 " + 
				"WHERE n.state1=s1.state AND n.state2= s2.state AND " + 
				"YEAR(s1.DATETIME)=? AND YEAR(s2.DATETIME)=? " + 
				"AND day(DATEDIFF(s1.DATETIME, s2.DATETIME))<? " + 
				"AND n.state1>n.state2 " + 
				"GROUP BY n.state1, n.state2";
		List<Adiacenza> result = new ArrayList<Adiacenza>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setInt(3, gg);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Adiacenza a = new Adiacenza(rs.getString("stato1"), rs.getString("stato2"), rs.getInt("cnt"));
				result.add(a);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public List<Evento> trovaEventi(int anno, int gg, Map<String, State> map) {
		String sql = "SELECT n.state1 AS stato1, n.state2 AS stato2, COUNT(*) AS cnt, s1.DATETIME AS DATE1, s2.DATETIME AS DATE2 " + 
				"FROM neighbor AS n, sighting AS s1, sighting AS s2 " + 
				"WHERE n.state1=s1.state AND n.state2= s2.state AND " + 
				"YEAR(s1.DATETIME)=? AND YEAR(s2.DATETIME)=? " + 
				"AND day(DATEDIFF(s1.DATETIME, s2.DATETIME))<? " + 
				"AND n.state1>n.state2 " + 
				"GROUP BY n.state1, n.state2";
		List<Evento> result = new ArrayList<Evento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setInt(3, gg);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Evento e = new Evento(TipoEvento.INCREMENTA, rs.getTimestamp("DATA1").toLocalDateTime(), map.get(rs.getString("stato1")));
				result.add(e);
				Evento e2 = new Evento(TipoEvento.INCREMENTA, rs.getTimestamp("DATA2").toLocalDateTime(), map.get(rs.getString("stato2")));
				result.add(e2);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
