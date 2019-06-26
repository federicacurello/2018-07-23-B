package it.polito.tdp.newufosightings.model;

import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Evento.TipoEvento;

public class Simulatore {
	
	
	private int giorno1;
	private int giorno2;
	private SimpleWeightedGraph<State, DefaultWeightedEdge> grafo;
	private PriorityQueue<Evento> queue;
	private Map<State, Integer> output;
	private Map<State, Integer> defcon;
	private NewUfoSightingsDAO dao;

	public void init(int anno ,int gg, int giorno1, int giorno2, SimpleWeightedGraph<State, DefaultWeightedEdge> grafo, Map<String, State> map) {
		this.giorno1=giorno1;
		this.giorno2=giorno2;
		dao= new NewUfoSightingsDAO();
		this.grafo=grafo;
		for(State s: grafo.vertexSet()) {
			defcon.put(s, 5);
			output.put(s, 0);
			
		}
		for(Evento e : dao.trovaEventi(anno, gg, map)) {
			if(!queue.contains(e))
				queue.add(e);
			
		}
		
	}
	public String run() {
		Evento e;
		while((e = queue.poll()) != null) {
			switch(e.getTipo()) {
				
				case INCREMENTA:
					System.out.println("NUOVO AVVISTAMENTO! " + e.getData()+" "+e.getStato());
					if(defcon.get(e.getStato())==2) {
						
						queue.add(new Evento(TipoEvento.EMERGENZA, e.getData(), e.getStato()));
					}
					else {
						defcon.put(e.getStato(), defcon.get(e.getStato())-1);
						
						queue.add(new Evento(TipoEvento.DECREMENTA, e.getData(), e.getStato()));
					}
					break;
				case DECREMENTA:
					System.out.println("aumento di 1! " + e.getData()+" "+e.getStato());
					defcon.put(e.getStato(), defcon.get(e.getStato())+1);
					break;
					
				case EMERGENZA:
					System.out.println("NUOVA EMERGENZA! " + e.getData()+" "+e.getStato());
					for(Evento ev:queue) {
						if(ev.getData().isAfter(e.getData())&& ev.getData().isBefore(e.getData().plusDays(giorno2))) {
							queue.remove(ev);
						}
					}
					output.put(e.getStato(), output.get(e.getStato())+1);
					queue.add(new Evento(TipoEvento.FINE, e.getData().plusDays(giorno2), e.getStato()));
					break;
					
				case FINE:
					System.out.println("FINE EMERGENZA! " + e.getData()+" "+e.getStato());
					output.put(e.getStato(), 5);
					break;
			} 
			
		}
		return output.toString();
		
	}
}
