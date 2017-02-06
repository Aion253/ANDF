package net.frostbite.unite.util;

public class Extras {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static String getSysOS(){
		if(isWindows()){return "win";}
		if(isMac()){return "mac";}
		if(isUnix()){return "nix";}
		return "inv";
	}
	
	public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
    
    public static String equStringLengths(String s, int i){
    	int length = s.length();
    	for(int i2 = length; i2<i; i2++){
    		s += " ";
    	}
    	return s;
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

}
