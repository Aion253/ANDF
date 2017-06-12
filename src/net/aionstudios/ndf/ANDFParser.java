package net.aionstudios.ndf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Winter Roberts
 *
 */
public class ANDFParser {
	
	//Aion Node Data Format - for simple, human readable, node based data storage
	
	/**
	 * Reads the file and builds a tree.
	 * 
	 * @param andfPath The path to the ANDF file. Shouldn't include the ANDF extension.
	 * @return The assembled ANDFTree.
	 */
	public static ANDFTree parse(String andfPath, int format){
		ANDFTree tree = new ANDFTree();
		andfPath = andfPath + ".andf";
		File f = new File(andfPath);
		f.getParentFile().mkdirs();
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(format==0) {
			try (BufferedReader br = new BufferedReader(new FileReader(andfPath.toString()))) {
			    for (String line; (line = br.readLine()) != null;) {
			        String[] datas = line.split(": ", 2);
			        System.out.println(line + " " + datas[0] + ": " + datas[1]);
			        ANDFAssembler.setValueAtPath(tree, datas[0], datas[1]);
			    }
			    br.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else if(format==1) {
			try (BufferedReader br = new BufferedReader(new FileReader(andfPath.toString()))) {
				List<String> nodes = new ArrayList<String>();
			    for (String line; (line = br.readLine()) != null;) {
			        String[] datas = line.split(": ", 2);
			        int n = datas[0].length() - datas[0].replace("  ", "").length();
			        while(nodes.size()<=n) {
			        	nodes.add("");
			        }
			        nodes.set(n, datas[0]);
			        String compiled = "";
			        if(datas[1].length() > 0) {
			        	for(int i = 0; i < n; i++) {
			        		compiled += nodes.get(i)+".";
			        	}
			        	compiled += datas[0];
			        	//TODO Some problems were occurring... This was a quick and messy fix, but it works!
			        	compiled = compiled.replace("..", ".");
			        	compiled = compiled.replace("...", ".");
			        	System.out.println(line + " " + compiled + ": " + datas[1]);
				        ANDFAssembler.setValueAtPath(tree, compiled, datas[1]);
			        }
			    }
			    br.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return tree;
	}
	
	/**
	 * Reads the file and builds a tree.
	 * 
	 * @param andfPath The path to the ANDF file. Shouldn't include the ANDF extension.
	 * @param t The tree to parse info into.
	 * @return The assembled ANDFTree.
	 */
	public static void parse(String andfPath, ANDFTree t, int format){
		andfPath = andfPath + ".andf";
		File f = new File(andfPath);
		f.getParentFile().mkdirs();
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(andfPath.toString()))) {
		    for (String line; (line = br.readLine()) != null;) {
		        String[] datas = line.split(": ", 2);
		        System.out.println(line + " " + datas[0] + ": " + datas[1]);
		        ANDFAssembler.setValueAtPath(t, datas[0], datas[1]);
		    }
		    br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
