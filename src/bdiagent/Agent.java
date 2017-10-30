package bdiagent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

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
	private double desireThreshold=0.4;
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
		//run();
		new Thread(this).start();
	}
	
	public void run() {
		for(int i=sday;i<=eday;i++) {
			String belief;
			System.out.println();
			System.out.println(name+" wakes up at Day "+i);
			System.out.println();
			if (i==sday) {
				belief=readInitialBelief();
			}
			else {
				belief = getForEachDay(i);
			}
			if(belief.isEmpty()) {
				continue;
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
							System.out.println(comparison.toString());
						}else if(json.has("relation")) {
							Relation relation = new Relation(json.getJSONObject("relation"));
							relations.add(relation);
							System.out.println(relation.toString());
						}else if(json.has("change")) {
							JSONObject change = json.getJSONObject("change");
							if(change.getString("type").equalsIgnoreCase("update-event")){
								int number= getEventID(change.getString("explanation"));
								if(number!=-1) {
									System.out.print(events.get(number).toString()+" and change its day to ");
									events.get(number).setDate(change.getInt("time"));
									System.out.println(events.get(number).day);
								}
							}else if(change.getString("type").equalsIgnoreCase("drop-event")) {
								int number= getEventID(change.getString("explanation"));
								if(number!=-1) {
									System.out.println(events.get(number).toString()+" is removed.");
									events.remove(number);
								}
							}

						}
					}
					catch (Exception e) {
						// TODO: handle exception
						System.err.println("json cannot be parsed");
					}
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
			Desire desire = new Desire(i);
			for(int d=i;d<=eday;d++) {
				for(int e=0;e<events.size();e++) {
					if(events.get(e).day==d && events.get(e).period.equalsIgnoreCase("Day")) {
						if(events.get(e).getTotal()>this.desireThreshold)
							desire.addEvent(events.get(e));
					}
				}
				for(int e=0;e<events.size();e++) {
					if(events.get(e).day==d && events.get(e).period.equalsIgnoreCase("Night")) {
						if(events.get(e).getTotal()>this.desireThreshold)
							desire.addEvent(events.get(e));
					}
				}
			}
			desires.add(desire);
			System.out.println();
			System.out.println(desire.toString());
			
			
			
			Intention intention = new Intention(i);
			for(int d=i;d<=eday;d++) {
				Event ev = null;
				Event ev2 = null;
				double temp=0;
				for(int e=0;e<events.size();e++) {
					if(events.get(e).day==d && events.get(e).period.equalsIgnoreCase("Day")) {
						if(events.get(e).getTotal()>temp) {
							desire.addEvent(events.get(e));
							ev=events.get(e);
							temp=events.get(e).getTotal();
						}
					}
				}
				if(ev!=null)
					intention.addEvent(ev);
				temp=0;
				for(int e=0;e<events.size();e++) {
					if(events.get(e).day==d && events.get(e).period.equalsIgnoreCase("Night")) {
						if(events.get(e).getTotal()>temp) {
							desire.addEvent(events.get(e));
							ev2=events.get(e);
							temp=events.get(e).getTotal();
						}
					}
				}
				if(ev2!=null)
					intention.addEvent(ev2);
			}
			intentions.add(intention);
			System.out.println();
			System.out.println(intention.toString());
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
	public String getForEachDay(int day)  {
		File file = new File("day"+day+".txt");
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
		event.setBase(importance);
		
	}
	
	public void  relationExecuter(Event event,Relation r) {
		if(event.eventType.equalsIgnoreCase(r.first) && event.period.equalsIgnoreCase(r.firstperiod)){
			for (Event event2 : events) {
				if(event2.eventType.equalsIgnoreCase(r.second) && event2.period.equalsIgnoreCase(r.secondperiod)){
					if (event2.day==event.day+r.time){
						
							event2.setAdditional(event.getBase());
							//System.out.println("oluyor mu "+event2.toString());
							
						
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
						event.setAdditional(c.amount); 
						//System.out.println("update geldi hanım"+event.toString());
						break;
					}
				}
			}
		}
	}

}
