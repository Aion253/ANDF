package net.frostbite.unite.util;

public class JansiGenerator {
	
	private static int ATT = 0;
	private static int FORE = 37;
	private static int BACK = 40;
	
	public static int ATT_NORMAL = 0;
	public static int ATT_BOLD = 1;
	public static int ATT_UL = 4;
	public static int ATT_BLINK = 5;
	public static int ATT_REV = 7;
	public static int ATT_NO = 8;
	
	public static void setAttribute(int att){
		ATT = att;
	}
	
	public static int FORE_BLACK = 30;
	public static int FORE_RED = 31;
	public static int FORE_GREEN = 32;
	public static int FORE_YELLOW = 33;
	public static int FORE_BLUE = 34;
	public static int FORE_MAGENTA = 35;
	public static int FORE_CYAN = 36;
	public static int FORE_WHITE = 37;
	
	public static void setForeground(int fore){
		FORE = fore;
	}
	
	public static int BACK_BLACK = 40;
	public static int BACK_RED = 41;
	public static int BACK_GREEN = 42;
	public static int BACK_YELLOW = 43;
	public static int BACK_BLUE = 44;
	public static int BACK_MAGENTA = 45;
	public static int BACK_CYAN = 46;
	public static int BACK_WHITE = 47;
	
	public static String genAnsiCode(){
		return "\u001b["+ATT+";"+FORE+";"+BACK+"m";
	}
	
	public static void setBackground(int back){
		BACK = back;
	}
	
	public static String genAddAnsiCode(int att, int fore, int back){
		setAttribute(att);
		setForeground(fore);
		setBackground(back);
		return genAnsiCode();
	}

}
