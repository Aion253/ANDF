package net.frostbite.unite.server;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.fusesource.jansi.AnsiConsole;

import net.frostbite.unite.util.Extras;
import net.frostbite.unite.Input;
import net.frostbite.unite.UniteHome;
import net.frostbite.unite.UniteVariables;

public class ServerScreen {
	
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
	
	  private static ExecutorService executor = Executors.newCachedThreadPool();
	private static Server current = null;
	static boolean showing = false;
	
	public static void startSS(int Server){
		if(!showing&&!UniteHome.showing){
			showing = true;
		current = UniteVariables.Servers.get(Server);
		String status = "";
		String ANSI_COL = "";
		int statuscode = 0;
		if(!new File(UniteVariables.opLocation+"/"+current.getDir()).exists()){
			status = "Missing!";
			ANSI_COL = YELLOW;
			statuscode=0;
		} else if (!new File(current.getLaunch()).exists()){
			status = "Not Setup";
			ANSI_COL = YELLOW;
			statuscode=1;
		} else if(current.isRunning()){
			status = "Online";
			ANSI_COL = GREEN;
			statuscode=2;
		} else {
			status = "Offline";
			ANSI_COL = RED;
			statuscode=3;
		}
		AnsiConsole.out.println(ANSI_CLS);
		AnsiConsole.out.println("");
		AnsiConsole.out.println("   "+Extras.equStringLengths(current.getName(), 20)+WHITE);
		AnsiConsole.out.println("");
		AnsiConsole.out.println("   Status: "+ANSI_COL+Extras.equStringLengths(status, 14)+WHITE+"   Directory: "+BLUE+current.getDir()+WHITE);
		AnsiConsole.out.println("");
		showOptions(statuscode, Server);
		}
	}
	
	private static void showOptions(int status, final int Server) {
		if(status == 0){
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [0] Remove Missing From Config");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [1] Return To Unite Home");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("Choice:");
			boolean inputProper = false;
			String input="";
			while(!inputProper){
				input=Input.waitForInput();
				if(Extras.isInteger(input)){
					if(Integer.parseInt(input)<4){
						inputProper=true;
					}
				}
			}
			int inputs = Integer.parseInt(input);
			showing = false;
			if(inputs == 0){
				AnsiConsole.out.println("Are you sure you want to remove this?");
				AnsiConsole.out.println("[Y/N]");
				String input1="";
				boolean inputProper1 = false;
				while(!inputProper1){
					input1=Input.waitForInput();
					if(input1.toUpperCase().equalsIgnoreCase("Y")||input1.toUpperCase().equalsIgnoreCase("N")){
						inputProper1=true;
					}
				}
				if(input1.toUpperCase().equalsIgnoreCase("Y")){
					UniteVariables.Servers.remove(Server);
					UniteVariables.config.removeAllFor("Server_");
					UniteVariables.config.writeData(false);
					UniteVariables.config.readData(false);
					for(int i = 0; i<UniteVariables.Servers.size();i++){
						Server s = UniteVariables.Servers.get(i);
						UniteVariables.config.addAttribute("Server_"+(i+1),s.getName()+"&"+s.getDir()+"&"+s.getLaunch().toString());
					}
					closeSS();
				}
				if(input1.toUpperCase().equalsIgnoreCase("N")){
					ServerScreen.startSS(Server);
				}
			} else if (inputs==1){
				closeSS();
			}
		} else if(status == 1){
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [0] Configure Server Settings");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [1] Return To Unite Home");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("Choice:");
			boolean inputProper = false;
			String input="";
			while(!inputProper){
				input=Input.waitForInput();
				if(Extras.isInteger(input)){
					if(Integer.parseInt(input)<2){
						inputProper=true;
					}
				}
			}
			int inputs = Integer.parseInt(input);
			showing = false;
			if(inputs == 0){
				ServerSettings.startSettings(Server);
			} else if (inputs==1){
				closeSS();
			}
		} else if(status == 2){
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [0] Show Console    - Use 'retunite' in console to hide");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [1] Kill Server");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [2] Return To Unite Home");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("Choice:");
			boolean inputProper = false;
			String input="";
			while(!inputProper){
				input=Input.waitForInput();
				if(Extras.isInteger(input)){
					if(Integer.parseInt(input)<2){
						inputProper=true;
					}
				}
			}
			int inputs = Integer.parseInt(input);
			showing = false;
			if(inputs == 0){
				UniteVariables.Servers.get(Server).showConsole();
			} else if (inputs==1){
				UniteVariables.Servers.get(Server).killServer();
			} else if (inputs==2){
				closeSS();
			}
		} else if(status == 3) {
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [0] Start Server    - Use 'retunite' in console to hide");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [1] Configure Server Settings");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("   [2] Return To Unite Home");
			AnsiConsole.out.println("");
			AnsiConsole.out.println("Choice:");
			boolean inputProper = false;
			String input="";
			while(!inputProper){
				input=Input.waitForInput();
				if(Extras.isInteger(input)){
					if(Integer.parseInt(input)<3){
						inputProper=true;
					}
				}
			}
			int inputs = Integer.parseInt(input);
			showing = false;
			if(inputs == 0){
						UniteVariables.Servers.get(Server).startServer();
				while(!UniteVariables.Servers.get(Server).show){
					
				}
				AnsiConsole.out().println("Showing Console...");
				while(UniteVariables.Servers.get(Server).show){
					
				}
				AnsiConsole.out().println("Hiding Console...");
				closeSS();
			} else if (inputs==1){
				ServerSettings.startSettings(Server);
			} else if (inputs==2){
				closeSS();
			}
		}
	}

	public static void closeSS(){
		UniteHome.showScreenMain();
	}

}
