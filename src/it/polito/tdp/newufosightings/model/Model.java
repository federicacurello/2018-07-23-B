package it.polito.tdp.newufosightings.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.Adiacenza;
import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	SimpleWeightedGraph<State, DefaultWeightedEdge> grafo;
	Map<String, State> map;
	NewUfoSightingsDAO dao;
	
	
	public Model() {
		map=new HashMap<String, State>();
		dao= new NewUfoSightingsDAO();
		dao.loadAllStates(map);
		
	}
	
	public String creaGrafo(int anno, int gg) {
		String string="";
		this.grafo= new SimpleWeightedGraph<State, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, map.values());
		for(Adiacenza a: dao.trovaArchi(anno, gg)) {
			Graphs.addEdgeWithVertices(grafo, map.get(a.getStato1()), map.get(a.getStato2()), a.getPeso());
		}
		System.out.println("Grafo con "+ grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+ " archi");
		for(State s: grafo.vertexSet() ) {
			
			string+= "La somma dei pesi degli archi adiacenti di "+s.toString()+": "+calcolaSommaPesi(s)+"\n";
		}
		return string;
	}

	private double calcolaSommaPesi(State s) {
		double somma=0;
		for(State t: Graphs.neighborListOf(grafo, s)) {
			
			somma=somma+grafo.getEdgeWeight(grafo.getEdge(s, t));
		}
		return somma;
	}
	public String simula(Integer anno, Integer gg, Integer giorno1, Integer giorno2) {
		Simulatore sim = new Simulatore();
		sim.init( anno , gg,  giorno1,  giorno2, grafo, map);
		return sim.run();
	}
}
