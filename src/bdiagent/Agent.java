package bdiagent;
import java.util.ArrayList;
public class Agent {
	String name;
	private ArrayList<Belief> beliefs;
	private ArrayList<Desire> desires;
	private ArrayList<Intention> intentions;
	public  Agent (String name){
		this.name = name;
		this.beliefs = new ArrayList<Belief> () ;
		this.desires = new ArrayList<Desire>();
		this.intentions = new ArrayList<Intention> ();
	}
	/**
	 * @return the beliefs
	 */
	public ArrayList<Belief> getBeliefs() {
		return beliefs;
	}
	/**
	 * @return the desires
	 */
	public ArrayList<Desire> getDesires() {
		return desires;
	}
	/**
	 * @return the intentions
	 */
	public ArrayList<Intention> getIntentions() {
		return intentions;
	}
	/**
	 * @param beliefs the beliefs to set
	 */
	public void setBeliefs(ArrayList<Belief> beliefs) {
		this.beliefs = beliefs;
	}
	/**
	 * @param desires the desires to set
	 */
	public void setDesires(ArrayList<Desire> desires) {
		this.desires = desires;
	}
	/**
	 * @param intentions the intentions to set
	 */
	public void setIntentions(ArrayList<Intention> intentions) {
		this.intentions = intentions;
	}
	public void addIntention(Intention inten){
		this.intentions.add(inten);
	}
	public void addBelief(Belief inten){
		this.beliefs.add(inten);
	}
	public void addDesire(Desire inten){
		this.desires.add(inten);
	}
	public void deleteIntention(Intention inten) {
		this.intentions.remove(intentions);
	}
	public void deleteBelief(Belief inten){
		this.beliefs.remove(inten);
	}
	public void deleteDesire(Desire inten){
		this.desires.remove(inten);
	}
	@Override
	public String toString(){
		return name+" has "+(beliefs.size())+" belief(s) "+(desires.size())+" desire(s) "+ (intentions.size()) +" intentions.";
		
	}

}
