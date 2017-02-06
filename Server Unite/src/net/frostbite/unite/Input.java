package net.frostbite.unite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
	
	public static String waitForInput(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = null;
        try {
			s = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return s;
	}

}
