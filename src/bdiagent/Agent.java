package bdiagent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	public  Agent (String name, int startday, int endday){
		this.name = name;
		this.beliefs = new ArrayList<Belief> () ;
		this.desires = new ArrayList<Desire>();
		this.intentions = new ArrayList<Intention> ();
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
				System.out.println(arr[j]);
				System.out.println();
				try {
					JSONObject json = new JSONObject(arr[j]);
					//Buraya json dataların isminden anlayıp kalan objeyi ilgili classa koyucaz JSONObject java diye arattır görürsün functionlarını
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public String readInitialBelief() {
		File file = new File("belief.txt");
		try {
			Scanner sc = new Scanner(file);
			StringBuffer sb= new StringBuffer();
			while(sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
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
		return sb.toString();
		
	}

}
