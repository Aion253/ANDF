package net.frostbite.unite;

import java.io.File;

import net.frostbite.unite.util.FEDAttribute;
import net.frostbite.unite.server.Server;

public class Startup {
	
	//██╗   ██╗███╗   ██╗██╗████████╗███████╗
	//██║   ██║████╗  ██║██║╚══██╔══╝██╔════╝
	//██║   ██║██╔██╗ ██║██║   ██║   █████╗  
	//██║   ██║██║╚██╗██║██║   ██║   ██╔══╝  
	//╚██████╔╝██║ ╚████║██║   ██║   ███████╗
	// ╚═════╝ ╚═╝  ╚═══╝╚═╝   ╚═╝   ╚══════╝

	public static void main(String[] args) {
		UniteVariables.init();
		if(!Setup.checkSetup()){Setup.beginSetup();}else{
		System.out.println("Starting In: "+UniteVariables.opLocation);
		UniteVariables.config.readData(false);
		for(FEDAttribute a : UniteVariables.config.getAttribList()){
			if(a.getAttName().startsWith("Server_")){
				String[] sa = a.getAttEqu().split("&");
				String s2t = "Added";
				boolean located = false;
				if(new File(sa[2]).exists()){
					s2t = "Added and Located";
					located = true;
				}
				UniteVariables.Servers.add(new Server(sa[0],sa[1],sa[2],located));
				System.out.println(s2t+" "+a.getAttName()+": "+sa[0]+" - "+sa[1]);
			}
		}
		if(new File(UniteVariables.opLocation.toString()+"/servers/").list().length>0){
			for(File dirs : new File(UniteVariables.opLocation.toString()+"/servers/").listFiles()){
				System.out.println(dirs.toString());
				boolean newDir = true;
				for(Server s : UniteVariables.Servers){
					File sdir = new File(UniteVariables.opLocation.toString()+"/"+s.getDir());
					if(sdir.equals(dirs)){
						newDir = false;
					}
				}
				if(newDir){
					String dirString = dirs.toString().replace("\\", "/");
					String runDir = UniteVariables.opLocation.toString().replace("\\", "/")+"/";
					UniteVariables.Servers.add(new Server("UNNAMED SERVER",dirString.replace(runDir, ""),"NOT SETUP",true));
					System.out.println("Found new Server: "+""+" - "+""+" - Needs To Be Setup");
				}
			}
		}
		for(int i = 0; i<UniteVariables.Servers.size();i++){
			Server s = UniteVariables.Servers.get(i);
			UniteVariables.config.addAttribute("Server_"+(i+1),s.getName()+"&"+s.getDir()+"&"+s.getLaunch().toString());
		}
		UniteVariables.config.writeData(false);
		UniteVariables.config.readData(false);
		UniteHome.start();
	}
	}
}
