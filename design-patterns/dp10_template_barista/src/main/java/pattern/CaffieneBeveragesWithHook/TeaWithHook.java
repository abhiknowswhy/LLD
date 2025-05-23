package pattern.CaffieneBeveragesWithHook;

import java.io.*;

import pattern.CaffeineBeverageWithHook;

public class TeaWithHook extends CaffeineBeverageWithHook {
	
	protected void brew() {
		System.out.println("Steeping the tea");
	}
	
	protected void addCondiments() {
		System.out.println("Adding Lemon");
	}
	
	public boolean customerWantsCondiments() {
		
		String answer = getUserInput();
		
		if (answer.toLowerCase().startsWith("y")) {
			return true;
		} else {
			return false;
		}
	}
	
	private String getUserInput() {
		// get the user's response
		String answer = null;
		
		System.out.print("Would you like lemon with your tea (y/n)? ");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			answer = in.readLine();
		} catch (IOException ioe) {
			System.err.println("IO error trying to read your answer");
		}
		if (answer == null) {
			return "no";
		}
		return answer;
	}
}
