package net.frostbite.unite.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FED {
	
	Path file = null;
	static List<FEDAttribute> att = new ArrayList<FEDAttribute>();
	
	public FED(Path file){
		this.file = file;
	}
	
	public List<FEDAttribute> getAttribList(){
		return att;
	}
	
	public void addAttribute(String variable, String saveInfo){
		boolean added = false;
		for(int i = 0; i<att.size();i++){
			if(att.get(i).getAttName().equals(variable)){
				att.set(i, new FEDAttribute(variable, saveInfo));
				added = true;
				break;
			}
		}
		if (!added) {
			att.add(new FEDAttribute(variable, saveInfo));
		}
	}
	
	public void removeAttrib(String variable){
		for(int i = 0;i<att.size();i++){
			if(att.get(i).getAttName()==variable){
				att.remove(i);
				return;
			}
		}
	}
	
	public void removeAllFor(String variable){
		for(int i = 0;i<att.size();i++){
			if(att.get(i).getAttName().startsWith(variable)){
				att.get(i).setAttName("REMOVED");
			}
		}
	}
	
	public String getAttribute(String variable){
		for(int i = 0; i<att.size();i++){
			if(att.get(i).getAttName().equals(variable)){
				if (att.get(i).getAttName()==null){
					att.get(i).setAttEqu("");
				}
				return att.get(i).getAttEqu();
			}
		}
		return "";
	}
	
	public boolean writeData(boolean useEncoding){
		PrintWriter writer;
		try {
			File f = new File(file.toString());
			f.getParentFile().mkdirs();
			File temp = File.createTempFile("temp_fi", null, f.getParentFile());
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			writer = new PrintWriter(temp.toString(), "UTF-8");
		for(int i = 0; i<att.size();i++){
			if(useEncoding){
				try {
					if(att.get(i).getAttName()!="REMOVED"){
					writer.println(att.get(i).getAttName()+"="+Base64Utils.encode(att.get(i).getAttEqu()));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if(att.get(i).getAttName()!="REMOVED"){
				writer.println(att.get(i).getAttName()+"="+att.get(i).getAttEqu());
				}
			}
		}
		writer.close();
		PrintWriter w2 = new PrintWriter(f.toString(), "UTF-8");
		w2.print("");
		w2.close();
		Files.delete(f.toPath());
		temp.renameTo(f);
		return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean readData(boolean hasEncoding){
		try (BufferedReader br = new BufferedReader(new FileReader(file.toString()))) {
		    att.clear();
		    for (String line; (line = br.readLine()) != null;) {
		        String[] datas = line.split("=", 2);
		        if(hasEncoding){
		        	try {
		        		addAttribute(datas[0],Base64Utils.decode(datas[1]));
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
		        } else {
		        	addAttribute(datas[0],datas[1]);
		        }
		    }
		    br.close();
		    return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
