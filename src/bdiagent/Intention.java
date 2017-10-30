package bdiagent;

import java.util.ArrayList;

public class Intention {
	int day;
	ArrayList<Event> events;
	public Intention(int day) {
		this.day=day;
		this.events = new ArrayList<Event>();
	}
	public void addEvent(Event event) {
		events.add(event);
	}
	public String toString() {
		String s="";
		s=s+"List of Intentions day " + day+ ":\n";
		for(int i =0;i<events.size();i++) {
			s=s+events.get(i).eventInfo()+"\n";
		}
		return s;
	}
}
