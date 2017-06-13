package net.aionstudios.ndf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Winter Roberts
 *
 */
public class ANDFAssembler {
	
	/**
	 * Assembles the tree to proper formatting and writes it to file.
	 * 
	 * @param tree The ANDFTree to be assembled.
	 * @param andfPath The path of the file to assemble to. Should not include the ANDF extension!
	 * @return File operation success.
	 */
	public static boolean assemble(ANDFTree tree, String andfPath, int format){
		List<String> valued = new ArrayList<String>();
		for(ANDFNode n : tree.getChildren()){
			getValuedNodes(n, valued, format);
		}
		return output(valued, andfPath);
	}
	
	private static boolean output(List<String> valued, String andfPath) {
		PrintWriter writer;
		try {
			File f = new File(andfPath+".andf");
			f.getParentFile().mkdirs();
			f.createNewFile();
			File temp = File.createTempFile("temp_andf", null, f.getParentFile());
			writer = new PrintWriter(temp.toString(), "UTF-8");
			for(int i = valued.size()-1; i>=0; i--){
				writer.println(valued.get(i));
			}
			writer.close();
			Files.deleteIfExists(f.toPath());
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

	/**
	 * Creates or sets a node at the given path point with the given value.
	 * 
	 * @param tree The ANDFTree to add values in.
	 * @param fullPath The entire path of the node, including the node's name.
	 * @param value The value at this node.
	 */
	public static void setValueAtPath(ANDFTree tree, String fullPath, String value){
		if(fullPath!=null&&fullPath!=""){
			String[] pathPoint = fullPath.split("\\.", 2);
			ANDFNode n = tree.addChild(new ANDFNode(pathPoint[0], tree, null));
			if(pathPoint.length<2){
				n.setValue(value);
			} else {
				if(pathPoint[1]==null){
					pathPoint[1]="";
				}
				valPath(pathPoint[1], value, n);
			}
		}
		return;
	}
	
	private static void valPath(String pathLeft, String value, ANDFNode node){
		if(pathLeft!=null&&pathLeft!=""){
			String[] pathPoint = pathLeft.split("\\.", 2);
			ANDFNode n = node.addChild(new ANDFNode(pathPoint[0], node.getRoot(), node));
			if(pathPoint.length<2){
				n.setValue(value);
			} else {
				valPath(pathPoint[1], value, n);
			}
		}
		return;
	}
	
	/**
	 * Retrieves a node at a point if it exists.
	 * 
	 * @param tree The ANDFTree to get values from.
	 * @param fullPath The entire path of the node, including the node's name.
	 */
	public static ANDFNode getNodeAtPath(ANDFTree tree, String fullPath){
		if(fullPath!=null&&fullPath!=""){
			String[] pathPoint = fullPath.split("\\.", 2);
			ANDFNode n = tree.getChild(pathPoint[0]);
			if(pathPoint.length<2){
				return n;
			} else {
				if(pathPoint[1]==null){
					pathPoint[1]="";
				}
				return nodeAtPath(pathPoint[1], n);
			}
		}
		return null;
	}

	private static ANDFNode nodeAtPath(String pathLeft, ANDFNode node){
		if(pathLeft!=null&&pathLeft!=""){
			String[] pathPoint = pathLeft.split("\\.", 2);
			ANDFNode n = node.getChild(pathPoint[0]);
			if(pathPoint.length<2){
				return n;
			} else {
				return nodeAtPath(pathPoint[1], n);
			}
		}
		return null;
	}
	
	/**
	 * Removes a node at the given point if one exists.
	 * 
	 * @param tree The ANDFTree to remove the given node from.
	 * @param fullPath The entire path of the node, including the node's name.
	 * @return true if this node existed and was removed.
	 */
	public static boolean removeNodeAtPath(ANDFTree tree, String fullPath){
		ANDFNode rem = getNodeAtPath(tree, fullPath);
		if(rem!=null){
			if(rem.getParentNode()!=null){
				rem.getParentNode().removeChild(rem);
				return true;
			} else {
				rem.getRoot().removeChild(rem);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns whether or not a node with the given path exists in the given tree.
	 * 
	 * @param tree The ANDFTree to test in.
	 * @param fullPath The entire path of the node, including the node's name.
	 */
	public static boolean doesNodeExist(ANDFTree tree, String fullPath){
		if(fullPath!=null&&fullPath!=""){
			String[] pathPoint = fullPath.split("\\.", 2);
			for(ANDFNode n : tree.getChildren()){
				if(n.getNodePathPoint().equals(pathPoint[0])){
					if(pathPoint.length<2){
						return true;
					}
					if(pathPoint[1]==null){
						pathPoint[1]="";
					}
					return nodeChecker(pathPoint[1], n);
				}
			}
		} else {
			return true;
		}
		return false;
	}
	
	private static boolean nodeChecker(String pathLeft, ANDFNode node){
		if(pathLeft!=null&&pathLeft!=""){
			String[] pathPoint = pathLeft.split("\\.", 2);
			for(ANDFNode n : node.getChildren()){
				if(n.getNodePathPoint().equals(pathPoint[0])){
					if(pathPoint.length<2){
						return true;
					}
					return nodeChecker(pathPoint[1], n);
				}
			}
		} else {
			return true;
		}
		return false;
	}
	
	private static List<String> getValuedNodes(ANDFNode node, List<String> valued, int format){
		String compiled = "";
		if(format == 1) {
			for(int i = 0; i < node.getNodeDepth(); i++) {
				compiled += "  ";
			}
		}
		if(!node.isLeaf()){
			for(ANDFNode n : node.getChildren()){
				getValuedNodes(n, valued, format);
			}
		}
		if(node.getValue()!=""&&node.getValue()!=null&&format==0){
			valued.add(node.getFullPathToNode()+": "+node.getValue());
		} else if (format==1) {
			if(node.getValue()!=""&&node.getValue()!=null) {
				valued.add(compiled+node.getNodePathPoint()+": "+node.getValue());
			} else {
				valued.add(compiled+node.getNodePathPoint()+": ");
			}
		}
		return valued;
	}
}
