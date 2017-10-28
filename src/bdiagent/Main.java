package bdiagent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



//import java.sql.DriverManager;

//import com.mysql.jdbc.Connection;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Agent generic = new Agent("Agent1");
		ArrayList<ArrayList<String>> beliefs = beliefReader("belief.txt");
		System.out.println(beliefs);
	}
	public static ArrayList<ArrayList<String>> beliefReader(String name){
		try {
			Scanner sc = new Scanner(new File(name));
			ArrayList<String> single = new ArrayList<String>();
			ArrayList<ArrayList<String>> list = new ArrayList<>();
			while(sc.hasNext()){
				String line = sc.nextLine();
				if (line=="!!!"){
					list.add(single);
					single.removeAll(single);
				}else{
					single.add(line);
				}
			}
			sc.close();
			return list;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
