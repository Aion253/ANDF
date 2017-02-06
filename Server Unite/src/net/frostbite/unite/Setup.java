package net.frostbite.unite;

import java.io.File;
import java.io.IOException;

import net.frostbite.unite.util.Extras;

public class Setup {
	
	public static boolean checkSetup(){
		if(new File(UniteVariables.opLocation.toString()+"/unite.cfg").exists()){
			return true;
		}
		return false;
	}
	
	public static boolean beginSetup(){
		if(UniteVariables.opLocation.list().length>1){
			System.out.println("Server Unite needs to setup in an empty directory!");
			System.out.println("Move Server Unite to an empty directory and retry.");
			return false;
		}
		File config = new File(UniteVariables.opLocation.toString()+"/unite.cfg");
		new File(UniteVariables.opLocation.toString()+"/servers/").mkdirs();
		try {
			config.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if(Extras.getSysOS()!="inv"){UniteVariables.config.addAttribute("OSType", Extras.getSysOS());UniteVariables.config.writeData(false);}
		else { System.out.println("Your current OS is not supported, please use Windows, Mac OSX or a Unix based system.");
		}
		System.out.println("Server Unite setup complete...");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
