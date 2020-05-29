package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.PriorityQueue;

public class Simulatore {

		//CODA DEGLI EVENTI
		PriorityQueue<Event> coda;
		//PARAMETRI DELLA SIMULAZIONE
		private Double C;//quantità di acqua presente nel bacino di giorno in giorno
		private River river;//il river per cui è richiesta la simulazione
		private List<Flow> flows;//contiene tutti i flow per ogni giorno del fiume selezionato
		private Double k;//fattore di scala,frazione di un mese a flusso “medio” che il bacino potrà
		//contenere prima di riempirsi, ovvero il tempo che il bacino può sostenere un flusso medio prima di riempirsi
		
		//MODELLO DEL MONDO-> STATO DEL SISTEMA OGNI GIORNO
		private Double Q;//capacità totale del bacino
		private Double f_min;//flusso minimo garantito ogni giorno
		private Double prob=0.05;//probabilità che la richiesta f_out sia maggiore del normale

		//VALORI DA CALCOLARE 
		private Integer giorniInsoddisfacenti;
		private Double Cmed;//occupazione media del bacino
		private Integer giorniTotali;
		
		//SIMULAZIONE VERA E PROPRIA
		//METODI PER IMPOSTARE I PARAMETRI DELLA SIMULAZIONE
		public void init(River r, Double k) {
			//init value from user
			this.coda=new PriorityQueue<>();
			this.k=k;
			this.river=r;
			this.flows=r.getFlows();
			//init value of the world
			this.Q=k*river.getFlowAvg()*(60*60*24)*30;
			this.f_min=0.8*r.getFlowAvg()*(60*60*24);
			this.giorniTotali=0;
			this.giorniInsoddisfacenti=0;
			this.Cmed=0.0;
		
			//fill the queue
			for(Flow f:flows) {
				Event e=new Event(f.getDay(),f);
				coda.add(e);
			}
			//inizializzo il primo giorno
			this.C=this.Q/2;
			this.Cmed+=this.C;
			this.giorniTotali++;
		}
		public void run() {
			while(!this.coda.isEmpty()) {
				Event e=this.coda.poll();
				//System.out.println(e);
				processEvent(e);
			}
		}
		private void processEvent(Event e) {
			Double f_in=e.getFlow().getFlow()*(60*60*24);//il flusso in entrata di questo giorno
			Double f_out=this.f_min;
			this.C+=f_in;
			if(this.C>this.Q) {
				//TRACIMAZIONE
				this.C=this.Q;
				this.Cmed+=this.C;
			}
			Double random=Math.random();
			if(random<this.prob) {
				//i campi devono essere irrigati
				f_out=10*f_out;
			}
			this.C-=f_out;
			if(this.C<0) {
				//sono nel caso in cui f_in<f_out
				this.C=0.0;//ho finito l'acqua presente oggi nel bacino
				this.Cmed+=this.C;
				this.giorniInsoddisfacenti++;
				this.giorniTotali++;
			}else {
				//sono nel caso in cui f_in>f_out perciò l'acqua nel bacino oggi aumenta
				this.Cmed+=this.C;
				this.giorniTotali++;
			}
		}
		
		//METODI PER RESTITUIRE I VALORI DI OUTPUT
		public Integer getGiorniInsufficienti() {
			return this.giorniInsoddisfacenti;
		}
		 public Double getOccupazioneMedia() {
			 return (this.Cmed/this.giorniTotali);
		 }

}
