package net.frostbite.unite;

import java.io.File;

import org.fusesource.jansi.AnsiConsole;

import net.frostbite.unite.util.Extras;
import net.frostbite.unite.util.JansiGenerator;
import net.frostbite.unite.server.Server;
import net.frostbite.unite.server.ServerScreen;

public class UniteHome {
	
	  public static final String ANSI_CLS = "\033[2J\033[;H";
	  public static final String ANSI_HOME = "\u001b[H";
	  public static final String ANSI_BOLD = "\u001b[1m";
	  public static final String ANSI_AT55 = "\u001b[10;10H";
	  public static final String ANSI_REVERSEON = "\u001b[7m";
	  public static final String ANSI_NORMAL = "\u001b[0m";
	  public static final String ANSI_WHITEONBLUE = "\u001b[37;44m";
	  
	  public static final String BLACK = "\u001B[0;30m";
	  public static final String RED = "\u001B[0;31m";
	  public static final String GREEN = "\u001B[0;32m";
	  public static final String YELLOW = "\u001B[0;33m";
	  public static final String BLUE = "\u001B[0;34m";
	  public static final String MAGENTA = "\u001B[0;35m";
	  public static final String CYAN = "\u001B[0;36m";
	  public static final String WHITE = "\u001B[0;37m";

	public static void start(){
		AnsiConsole.systemInstall();
		AnsiConsole.out.println("");
		AnsiConsole.out.println("Server Unite Licensed Under "+GREEN+"The MIT License"+WHITE);
		AnsiConsole.out.println("Winter Roberts, 2016");
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showScreenMain();
	}
	
	public static boolean showing = false;
	
	public static void showScreenMain() {
		if(!showing){
			showing = true;
		clearScreen();
		printLogo();
		printOptions();
		}
	}

	private static void printOptions() {
		AnsiConsole.out.println(" "+JansiGenerator.genAddAnsiCode(0, 37, 42)+" "+WHITE+" = Online   "+JansiGenerator.genAddAnsiCode(0, 37, 41)+" "+WHITE+" = Offline   "+JansiGenerator.genAddAnsiCode(0, 37, 43)+" "+WHITE+" = Not Setup/Missing   q = Quit");
		if(UniteVariables.Servers.size()>0){
			int i = 0;
			for(Server s : UniteVariables.Servers){
				String ANSI_COL;
				if(!new File(UniteVariables.opLocation+"/"+s.getDir()).exists()){
					ANSI_COL = YELLOW;
				} else if (!new File(s.getLaunch()).exists()){
					ANSI_COL = YELLOW;
				} else if(s.isRunning()){
					ANSI_COL = GREEN;
				} else {
					ANSI_COL = RED;
				}
				AnsiConsole.out.println("");
				AnsiConsole.out.println(ANSI_COL+"   ["+i+"] "+Extras.equStringLengths(s.getName(), 20)+"    "+Extras.equStringLengths(s.getDir(), 20));
				i++;
			}
		} else {
			AnsiConsole.out.println(BLUE+"   No Server directories detected! Add some servers and restart Unite");
		}
		AnsiConsole.out.println("");
		AnsiConsole.out.println(WHITE+"Select a Server: "+BLUE);
		boolean inputProper = false;
		String input="";
		while(!inputProper){
			input=Input.waitForInput();
			if(Extras.isInteger(input)||input.toUpperCase().equalsIgnoreCase("Q")){
				if(input.toUpperCase().equalsIgnoreCase("Q")){
					showing = false;
					clearScreen();
					closeApp();
					inputProper=true;
				} else if(Integer.parseInt(input)<UniteVariables.Servers.size()){
					showing = false;
					ServerScreen.startSS(Integer.parseInt(input));
					inputProper=true;
				}
			}
		}
	}

	public static void printLogo(){
		AnsiConsole.out.println("");
		AnsiConsole.out.println(BLUE+" 8 8888      88 b.             8 8 8888 8888888 8888888888 8 888888888888 ");
		AnsiConsole.out.println(" 8 8888      88 888o.          8 8 8888       8 8888       8 8888         ");
		AnsiConsole.out.println(" 8 8888      88 Y88888o.       8 8 8888       8 8888       8 8888         ");
		AnsiConsole.out.println(" 8 8888      88 .`Y888888o.    8 8 8888       8 8888       8 8888         ");
		AnsiConsole.out.println(" 8 8888      88 8o. `Y888888o. 8 8 8888       8 8888       8 888888888888 ");
		AnsiConsole.out.println(" 8 8888      88 8`Y8o. `Y88888o8 8 8888       8 8888       8 8888         ");
		AnsiConsole.out.println(" 8 8888      88 8   `Y8o. `Y8888 8 8888       8 8888       8 8888         ");
		AnsiConsole.out.println(" ` 8888     ,8P 8      `Y8o. `Y8 8 8888       8 8888       8 8888         ");
		AnsiConsole.out.println("   8888   ,d8P  8         `Y8o.` 8 8888       8 8888       8 8888         ");
		AnsiConsole.out.println("    `Y88888P'   8            `Yo 8 8888       8 8888       8 888888888888 "+WHITE);
		AnsiConsole.out.println("");
	}
	
	public static void clearScreen(){
		AnsiConsole.out().print(ANSI_CLS);
	}
	
	public static void closeApp(){
		AnsiConsole.systemUninstall();
	}
	
	public static void printlnColor(String COLOR, String text){
		AnsiConsole.out().println(COLOR + text);
	}
	
}
