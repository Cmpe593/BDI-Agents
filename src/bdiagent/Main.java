package bdiagent;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//This is for test forking
        //this is what korhan does
		getConnection();
	}
	public static Connection getConnection() throws Exception{
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1/CMPE593_BDI";
			String username= "root";
			String password= "";
			//Class.forName(driver);
			Connection con = (Connection)DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return con;
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
