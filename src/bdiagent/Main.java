package bdiagent;



//import java.sql.DriverManager;

//import com.mysql.jdbc.Connection;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int startday = 15;
		int endday= 15;
		String name= "Agent2K";
		Thread agent = new Agent(name,startday,endday);
	}

	
}
