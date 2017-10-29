package bdiagent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.json.*;

public class Agent extends Thread {
	private String name;
	private int sday;
	private int eday;
	private ArrayList<Belief> beliefs;
	private ArrayList<Desire> desires;
	private ArrayList<Intention> intentions;
	private ArrayList<Event> events;
	private ArrayList<Comparison> comparisons;
	private ArrayList<Relation> relations;
	private double[][] list = {{0.1,0.3,0.6,0.9},{0.1,0.3,0.5,0.8}};
	private int type;
	public  Agent (String name, int startday, int endday,int type){
		this.type = type;
		this.name = name;
		this.beliefs = new ArrayList<Belief> ();
		this.desires = new ArrayList<Desire>();
		this.intentions = new ArrayList<Intention> ();
		this.events = new ArrayList<Event>();
		this.comparisons = new ArrayList<Comparison>();
		this.relations = new ArrayList<Relation>();
		this.sday=startday;
		this.eday=endday;
		new Thread(this).start();
		
	}
	
	public void run() {
		for(int i=sday;i<=eday;i++) {
			String belief;
			if (i==sday) {
				belief=readInitialBelief();
			}
			else {
				belief = getBeliefFromConsole();
			}
			String[] arr=belief.split("!!!");
			for(int j=0;j<arr.length;j++) {
				//System.out.println(arr[j]);
				try {
					JSONObject json = new JSONObject(arr[j]);

					try {

						if(json.has("event-schedule")){
							Event event = new Event(json.getJSONObject("event-schedule"));
							events.add(event);
							calculateImportance(event);
							System.out.println(event.toString());
						}else if(json.has("comparison")){
							Comparison comparison = new Comparison(json.getJSONObject("comparison"));
							comparisons.add(comparison);
						}else if(json.has("relation")) {
							Relation relation = new Relation(json.getJSONObject("relation"));
							relations.add(relation);
						}else if(json.has("change")) {
							JSONObject change = json.getJSONObject("change");
							if(change.getString("type").equalsIgnoreCase("update-event")){
								int number= getEventID(change.getString("explanation"));
								if(number!=-1) {
									events.get(number).setDate(change.getInt("time"));
								}
							}else if(change.getString("type").equalsIgnoreCase("drop-event")) {
								int number= getEventID(change.getString("explanation"));
								if(number!=-1) {
									events.remove(number);
								}
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.err.println("json cannot be parsed");
					}

					//Buraya json dataların isminden anlayıp kalan objeyi ilgili classa koyucaz JSONObject java diye arattır görürsün functionlarını
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (Event event : events) {
				for (Relation r : relations) {
					relationExecuter(event,r);
				}
				for (Comparison c : comparisons) {
					comparisonExecuter(event,c);
				}
				
			}
		}
	}
	public int getEventID(String a) {
		for (int i = 0; i < events.size(); i++) {
			if(a.equalsIgnoreCase(events.get(i).getExplanation())) {
				return i;
			}
		}
		return -1;
	}
	public String readInitialBelief() {
		File file = new File("belief.txt");
		try {
			Scanner sc = new Scanner(file);
			StringBuffer sb= new StringBuffer();
			while(sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
			sc.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "File Not Found";
		}
	}
	public String getBeliefFromConsole() {
		Scanner sc = new Scanner(System.in);
		StringBuffer sb= new StringBuffer();
		while(sc.hasNextLine()) {
			sb.append(sc.nextLine());
		}
		sc.close();
		return sb.toString();
	}
	public void calculateImportance(Event event) {
		double importance = 0;
		Random random = new Random();
		if(event.type.equalsIgnoreCase("Stay")){
			if(event.period.equalsIgnoreCase("day")){
				importance = random.nextDouble()*list[type][1];
			}else if (event.period.equalsIgnoreCase("night")){
				importance = random.nextDouble()*list[type][0];
			}
		}else if(event.type.equalsIgnoreCase("Work")){
			if(event.eventType.equalsIgnoreCase("Talk-Speaker")){
				importance = 1.5;
			}else{
				importance = random.nextDouble()*(1-list[type][3])+list[type][3];
			}
		}else if (event.type.equalsIgnoreCase("Entertainment")){
			importance = random.nextDouble()*(list[type][2]-list[type][1])+list[type][1];;
		}
		event.setImportance(importance);
		
	}
	public boolean checkNextDay(String name,int day) {
		for (Event event : events) {
			if(event.day == day || event.day == -1){
				if (event.eventType.equalsIgnoreCase(name)){
					
					return true;
				}
			}
		}
		return false;
	}
	public void  relationExecuter(Event event,Relation r) {
		if(event.eventType.equalsIgnoreCase(r.first)){
			for (Event event2 : events) {
				if(event2.eventType.equalsIgnoreCase(r.second)){
					if (event2.day==event.day+r.time){
						if(event2.period.equalsIgnoreCase(r.period)){
							event2.importance += event.importance;
							System.out.println("oluyor mu "+event2.toString());
						}
					}
				}
			}
		}
	}
	public void  comparisonExecuter(Event event,Comparison c) {
		if(event.eventType.equalsIgnoreCase(c.first)){
			for (Event event2 : events) {
				if(event2.eventType.equalsIgnoreCase(c.second)){
					if(event.day==event2.day&&event.period.equalsIgnoreCase(event2.period)){
						event.importance += c.amount;
						System.out.println("update geldi hanım"+event.toString());
					}
				}
			}
		}
	}

}
