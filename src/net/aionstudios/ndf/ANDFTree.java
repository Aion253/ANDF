package net.aionstudios.ndf;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Winter Roberts
 *
 */
public class ANDFTree {
	
	private List<ANDFNode> children;
	
	/**
	 * Creates a new ANDFTree.
	 */
	public ANDFTree(){
		children = new ArrayList<ANDFNode>();
	}

	/**
	 * @return The top-level nodes.
	 */
	public List<ANDFNode> getChildren() {
		return children;
	}
	
	/**
	 * Adds a new top-level node.
	 * 
	 * @param node The node to add.
	 */
	public final ANDFNode addChild(ANDFNode node){
		for(ANDFNode n : children){
			if(node.getNodePathPoint().equals(n.getNodePathPoint())){
				return n;
			}
		}
		children.add(node);
		return node;
	}
	
	/**
	 * Returns a child with the given name if one exists
	 * 
	 * @param pointName The node name to find.
	 */
	public final ANDFNode getChild(String pointName){
		for(ANDFNode n : children){
			if(pointName.equals(n.getNodePathPoint())){
				return n;
			}
		}
		return null;
	}
	
	/**
	 * Removes a top-level node.
	 * 
	 * @param node The node to be removed.
	 */
	public final void removeChild(ANDFNode node){
		children.remove(node);
	}
	
	/**
	 * @return 0 always, why do I even bother
	 */
	public final int depth(){
		return 0;
	}
	
	/**
	 * @return The number of top-level nodes.
	 */
	public int degree(){
		return children.size();
	}
	
	/**
	 * @return If this node has no children.
	 */
	public boolean isLeaf(){
		if(children.size() == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * @param path A path within the tree.
	 * 
	 * @return The value at the specified path.
	 */
	public String getValueAtPath(String path){
		if(ANDFAssembler.doesNodeExist(this, path)) {
			return ANDFAssembler.getNodeAtPath(this, path).getValue();
		}
		return "";
	}
	
	/**
	 * Changes or creates a node's value at the specified path within this tree.
	 * 
	 * @param path A path within the tree.
	 * @param value The value to be inserted.
	 */
	public void setValueAtPath(String path, String value){
		ANDFAssembler.setValueAtPath(this, path, value);
	}
	
	/**
	 * Removes the node at the specified path within this tree.
	 * 
	 * @param path A path within the tree.
	 */
	public void removeAtPath(String path){
		ANDFAssembler.removeNodeAtPath(this, path);
	}
	
	/**
	 * @param path A path within the tree.
	 * 
	 * @return True if a node exists at the specified path within this tree.
	 */
	public boolean doesNodeExist(String path){
		return ANDFAssembler.doesNodeExist(this, path);
	}
	
	/**
	 * Loads the ANDF data from the specified file.
	 * 
	 * @param andfFile The file tom be loaded from.
	 */
	public void parseFrom(String andfFile, int format){
		ANDFParser.parse(andfFile, this, format);
	}
	
	/**
	 * Saves this tree to the given file.
	 * 
	 * @param andfFile The file to save to.
	 */
	public void assembleTo(String andfFile, int format){
		ANDFAssembler.assemble(this, andfFile, format);
	}
	
	/**
	 * Empty's the tree.
	 */
	public void emptyTree(){
		children = new ArrayList<ANDFNode>();
	}
	
}
