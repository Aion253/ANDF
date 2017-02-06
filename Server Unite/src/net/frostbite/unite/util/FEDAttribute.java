package net.frostbite.unite.util;

public class FEDAttribute {

	private String attName = "";
	private String attEqu = "";
	
	public FEDAttribute(String Name, String Equals){
		this.attName = Name;
		this.attEqu = Equals;
	}

	public String getAttName() {
		return attName;
	}

	public void setAttName(String attName) {
		this.attName = attName;
	}

	public String getAttEqu() {
		return attEqu;
	}

	public void setAttEqu(String attEqu) {
		this.attEqu = attEqu;
	}
	
}
