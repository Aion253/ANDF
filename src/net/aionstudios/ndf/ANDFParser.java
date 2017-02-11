package net.aionstudios.ndf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
	public static ANDFTree parse(String andfPath){
		ANDFTree tree = new ANDFTree();
		andfPath = andfPath + ".andf";
		try (BufferedReader br = new BufferedReader(new FileReader(andfPath.toString()))) {
		    for (String line; (line = br.readLine()) != null;) {
		        String[] datas = line.split("=", 2);
		        System.out.println(line + " " + datas[0] + "=" + datas[1]);
		        ANDFAssembler.setValueAtPath(tree, datas[0], datas[1]);
		    }
		    br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return tree;
	}
	
	public static void parse(String andfPath, ANDFTree t){
		ANDFTree tree = new ANDFTree();
		andfPath = andfPath + ".andf";
		try (BufferedReader br = new BufferedReader(new FileReader(andfPath.toString()))) {
		    for (String line; (line = br.readLine()) != null;) {
		        String[] datas = line.split("=", 2);
		        System.out.println(line + " " + datas[0] + "=" + datas[1]);
		        ANDFAssembler.setValueAtPath(t, datas[0], datas[1]);
		    }
		    br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
