package net.frostbite.unite;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import net.frostbite.unite.util.FED;
import net.frostbite.unite.server.Server;

public class UniteVariables {
	
	public static FED config = null;
	public static List<Server> Servers = new ArrayList<Server>();
	public static File opLocation = null;

	public static void init(){
		config = new FED(Paths.get(new File("./unite.cfg").getPath()));
		String opL = new File("./unite.cfg").getAbsoluteFile().getParent().toString();
		if(opL.endsWith("."))
		{
			opL = opL.substring(0,opL.length() - 1);
		}
		opLocation = new File(opL);
		
	}
	
}
