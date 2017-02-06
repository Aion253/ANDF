package net.frostbite.unite.server;

import java.io.File;

import org.fusesource.jansi.AnsiConsole;

import net.frostbite.unite.util.Extras;
import net.frostbite.unite.Input;
import net.frostbite.unite.UniteVariables;

public class ServerSettings {
	
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
	
	  private static Server current = null;
	  
	public static void startSettings(int Server){
		current = UniteVariables.Servers.get(Server);
		AnsiConsole.out.println(ANSI_CLS);
		AnsiConsole.out.println("");
		AnsiConsole.out.println("   Settings - "+Extras.equStringLengths(current.getName(), 20)+WHITE);
		AnsiConsole.out.println("");
		AnsiConsole.out.println("   Select a Setting to Edit");
		AnsiConsole.out.println("");
		showOptions(Server);
	}
	
	private static void showOptions(int Server) {
		AnsiConsole.out.println("");
		AnsiConsole.out.println("   [0] " + Extras.equStringLengths("Name", 20)+current.getName());
		AnsiConsole.out.println("");
		AnsiConsole.out.println("   [1] " + Extras.equStringLengths("Server Directory", 20)+current.getDir());
		AnsiConsole.out.println("");
		AnsiConsole.out.println("   [2] " + Extras.equStringLengths("Launch File", 20)+current.getLaunch().toString());
		AnsiConsole.out.println("");
		AnsiConsole.out.println("   [3] " + Extras.equStringLengths("Back", 20));
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
		if(inputs == 0){
			AnsiConsole.out.println("Specify a name for the server: ");
			boolean inputProper1 = false;
			String input1="";
			while(!inputProper1){
				input1=Input.waitForInput();
				if(input1.toUpperCase().equalsIgnoreCase("UNNAMED SERVER")||input1.length()>0){
					inputProper1=true;
				}
			}
			UniteVariables.Servers.get(Server).setName(input1);
			UniteVariables.config.removeAllFor("Server_");
			for(int i = 0; i<UniteVariables.Servers.size();i++){
				Server s = UniteVariables.Servers.get(i);
				UniteVariables.config.addAttribute("Server_"+(i+1),s.getName()+"&"+s.getDir()+"&"+s.getLaunch().toString());
			}
			UniteVariables.config.writeData(false);
		} else if(inputs == 1){
			AnsiConsole.out.println("Specify a path below the servers/ directory: ");
			boolean inputProper1 = false;
			String input1="";
			while(!inputProper1){
				input1=Input.waitForInput();
				if(input1.length()>0&&new File(UniteVariables.opLocation+"/servers/"+input1).exists()){
					inputProper1=true;
				}
			}
			UniteVariables.Servers.get(Server).setDir("servers/"+input1);
			UniteVariables.config.removeAllFor("Server_");
			for(int i = 0; i<UniteVariables.Servers.size();i++){
				Server s = UniteVariables.Servers.get(i);
				UniteVariables.config.addAttribute("Server_"+(i+1),s.getName()+"&"+s.getDir()+"&"+s.getLaunch().toString());
			}
			UniteVariables.config.writeData(false);
		} else if(inputs == 2){
			AnsiConsole.out.println("Specify a path below "+UniteVariables.opLocation.toString().replace("\\", "/")+"/"+UniteVariables.Servers.get(Server).getDir()+"/");
			boolean inputProper1 = false;
			String input1="";
			while(!inputProper1){
				input1=Input.waitForInput();
				if(input1.toUpperCase().equalsIgnoreCase("NOT SETUP")||input1.length()>0&&new File(UniteVariables.opLocation+"/"+UniteVariables.Servers.get(Server).getDir()+"/"+input1).exists()){
					inputProper1=true;
				}
			}
			UniteVariables.Servers.get(Server).setLaunch(UniteVariables.opLocation.toString().replace("\\", "/")+"/"+UniteVariables.Servers.get(Server).getDir()+"/"+input1);
			UniteVariables.config.removeAllFor("Server_");
			for(int i = 0; i<UniteVariables.Servers.size();i++){
				Server s = UniteVariables.Servers.get(i);
				UniteVariables.config.addAttribute("Server_"+(i+1),s.getName()+"&"+s.getDir()+"&"+s.getLaunch().toString());
			}
			UniteVariables.config.writeData(false);
		} else if(inputs == 3){
			ServerScreen.startSS(Server);
		}
		ServerSettings.startSettings(Server);
	}

}
