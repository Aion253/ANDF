package net.frostbite.unite.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.fusesource.jansi.AnsiConsole;

import net.frostbite.unite.Input;
import net.frostbite.unite.UniteHome;
import net.frostbite.unite.UniteVariables;

public class Server {
	
	public static final String ANSI_CLS = "\033[2J\033[;H";
	  public static final String BLACK = "\u001B[0;30m";
	  public static final String RED = "\u001B[0;31m";
	  public static final String GREEN = "\u001B[0;32m";
	  public static final String YELLOW = "\u001B[0;33m";
	  public static final String BLUE = "\u001B[0;34m";
	  public static final String MAGENTA = "\u001B[0;35m";
	  public static final String CYAN = "\u001B[0;36m";
	  public static final String WHITE = "\u001B[0;37m";
	private String name = "";
	private String dir = "";
	private boolean running = false;
	private String launch;
	public boolean show = true;
	public boolean located;
	
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
	public Server(String name, String dir, String launch, boolean located){
		this.name = name;
		this.dir = dir;
		this.launch = launch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getLaunch() {
		return launch;
	}

	public void setLaunch(String launch) {
		this.launch = launch;
	}
	
	public void killServer(){
		show=false;
		running = false;
		process.destroyForcibly();
		UniteVariables.config.readData(false);
		executor.shutdownNow();
		executor = Executors.newCachedThreadPool();
		UniteHome.showScreenMain();
	}
	
	Process process;
	
	OutputStream stdin;
	InputStream stderr;
	InputStream stdout;
	
	public void startServer(){
		AnsiConsole.out.println(ANSI_CLS);
		try {
			ProcessBuilder builder = new ProcessBuilder(launch);
			builder.directory(new File(new File(launch).getParent()));
			builder.redirectErrorStream(true);
			
			process = builder.start();
			
			stdin = process.getOutputStream ();
			stderr = process.getErrorStream ();
			stdout = process.getInputStream ();

			running = true;
			show = true;
			serverCon();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		running = false;
		UniteHome.showScreenMain();
	}
	
	private void serverCon() {
		BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
		Runnable r = new Runnable() {
			  public void run() {
		AnsiConsole.out.println(WHITE+ANSI_CLS);
		AnsiConsole.out.println("Use the 'retunite' command any time to return to Unite");
				while(running){
					if(show){
					String input=Input.waitForInput();
			    if (input.trim().equals("retunite")) {
			    	hideConsole();
			    } else {
			        try {
						writer.write(input);
					} catch (IOException e) {
						AnsiConsole.out.println(getName()+": Ran into an error!");
						hideConsole();
					}
			    }
			    try {
					writer.flush();
				} catch (IOException e) {
					AnsiConsole.out.println(getName()+": Ran into an error!");
					hideConsole();
				}
					}
				}
			}
		};
		
		executor.submit(r);
		
		while (running) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e1) {
				AnsiConsole.out.println(getName()+": Ran into an error!");
				hideConsole();
			}
		    while (line != null && ! line.trim().equals("--EOF--")) {
		    	try {
					line = reader.readLine();
					if(line==null&&!running){
						AnsiConsole.out.println(ANSI_CLS);
						AnsiConsole.out().println(BLUE+"[UNITE] "+GREEN+"The Server has stopped or crashed, "+CYAN+"returning to Unite...");
						killServer();
					}
					if(show){
					AnsiConsole.out.println(line);
					}
				} catch (IOException e) {
					AnsiConsole.out.println(getName()+": Ran into an error!");
					hideConsole();
				}
		    }
		}
		if(!running&&!show){
			killServer();
		}
	}

	public void showConsole(){
		for(int i = 0; i<UniteVariables.Servers.size();i++){
			Server s = UniteVariables.Servers.get(i);
			UniteVariables.config.addAttribute("Server_"+(i+1),s.getName()+"&"+s.getDir()+"&"+s.getLaunch().toString());
		}
		UniteVariables.config.writeData(false);
		UniteVariables.config.readData(false);
		show = true;
		if(!running){
			startServer();
		}
	}

	public void hideConsole(){
		if(running){
		show = false;
		AnsiConsole.out.println(ANSI_CLS);
		AnsiConsole.out.println(CYAN+"Returning to Unite..."+WHITE);
		UniteHome.showScreenMain();
		}
	}
	
	public boolean isRunning(){
		return running;
	}

}
