package bdiagent;
//import java.sql.DriverManager;

//import com.mysql.jdbc.Connection;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int startday = 15;
		int endday= 21;
		String name= "Agent2K";
		int type = 1;// ask to user at the beginning
		Thread agent = new Agent(name,startday,endday,type);
	}

	
}
