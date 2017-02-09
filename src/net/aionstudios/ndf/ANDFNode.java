package net.aionstudios.ndf;

import java.util.ArrayList;
import java.util.List;

import net.aionstudios.ndf.util.Extras;

/**
 * 
 * @author Winter Roberts
 *
 */
public class ANDFNode {
	
	private List<ANDFNode> children;
	private String nodePathPoint;
	private ANDFTree root;
	private ANDFNode parentNode;
	//will not write data to or from node if the andfValue is "". or empty string
	private String andfValue = "";
	
	/**
	 * Creates a new ANDFNode without a value assigned.
	 * 
	 * @param path The full Node path to this node.
	 * @param rootNode The node at the root of this tree.
	 * @param parent The parent node of this node.
	 */
	public ANDFNode(String PathPoint, ANDFTree rootNode, ANDFNode parent){
		nodePathPoint = Extras.alphaNumHU(PathPoint);
		root = rootNode;
		parentNode = parent;
		if(parentNode!=null){
			parentNode.addChild(this);
		} else {
			root.addChild(this);
		}
		children = new ArrayList<ANDFNode>();
	}
	
	/**
	 * Creates a new ANDFNode with a value.
	 * 
	 * @param path The full Node path to this node.
	 * @param rootNode The node at the root of this tree.
	 * @param parent The parent node of this node.
	 * @param val A predefined value for the node.
	 */
	public ANDFNode(String PathPoint, ANDFTree rootNode, ANDFNode parent, String val){
		nodePathPoint = Extras.alphaNumHU(PathPoint);
		root = rootNode;
		parentNode = parent;
		if(parentNode!=null){
			parentNode.addChild(this);
		} else {
			root.addChild(this);
		}
		children = new ArrayList<ANDFNode>();
		andfValue = val;
	}
	
	/**
	 * Creates a new ANDFNode with a value and assumes the root via it's parent.
	 * 
	 * @param path The full Node path to this node.
	 * @param parent The parent node of this node.
	 * @param val A predefined value for the node.
	 */
	public ANDFNode(String PathPoint, ANDFNode parent, String val){
		nodePathPoint = Extras.alphaNumHU(PathPoint);
		parentNode = parent;
		if(parentNode!=null){
			root = parentNode.getRoot();
			parentNode.addChild(this);
		}
		children = new ArrayList<ANDFNode>();
		andfValue = val;
	}

	/**
	 * Creates a new ANDFNode without a value and assumes the root via it's parent.
	 * 
	 * @param path The full Node path to this node.
	 * @param parent The parent node of this node.
	 * @param val A predefined value for the node.
	 */
	public ANDFNode(String PathPoint, ANDFNode parent){
		nodePathPoint = Extras.alphaNumHU(PathPoint);
		parentNode = parent;
		if(parentNode!=null){
			root = parentNode.getRoot();
			parentNode.addChild(this);
		}
		children = new ArrayList<ANDFNode>();
	}
	
	/**
	 * @return A list of this nodes children (sub-nodes).
	 */
	public List<ANDFNode> getChildren() {
		return children;
	}
	
	/**
	 * Adds a new child to this node.
	 * 
	 * @param value The string to be decoded.
	 * @return A String.
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
	 * Returns a child with the given name if one exists.
	 * 
	 * @param pointName The node name to find.
	 * @returns The ANDFNode with the given name.
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
	 * Removes a child of this node.
	 * 
	 * @param node The node to be removed.
	 */
	public final void removeChild(ANDFNode node){
		children.remove(node);
	}

	/**
	 * @return The name of this node.
	 */
	public String getNodePathPoint() {
		return nodePathPoint;
	}
	
	/**
	 * Recursively moves through parents to assemble a full path
	 * 
	 * @return This nodes full path arrangement.
	 */
	public final String getFullPathToNode(){
		String point = getNodePathPoint();
		if(parentNode!=null){
			point = parentNode.getFullPathToNode()+"."+point;
		}
		return point;
	}

	/**
	 * @return The tree to which this node belongs.
	 */
	public ANDFTree getRoot() {
		return root;
	}
	
	/**
	 * @return The number of children (sub-nodes) of this node.
	 */
	public int degree(){
		return children.size();
	}
	
	/**
	 * @return The number of parents above this node.
	 */
	public final int depth(){
		String fnp = this.getFullPathToNode();
		return fnp.length() - fnp.replace(".", "").length();
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
	 * @return All children of this node's parent.
	 */
	public final List<ANDFNode> siblings(){
		if(parentNode == null){
			return root.getChildren();
		}
		return parentNode.getChildren();
	}

	/**
	 * @return The value of this node.
	 */
	public String getValue() {
		return andfValue;
	}

	/**
	 * Change the value of this node.
	 * 
	 * @param value This node's value.
	 */
	public void setValue(String andfValue) {
		this.andfValue = andfValue;
	}

	/**
	 * @return The parent node of this node, if it's parent is not root.
	 */
	public ANDFNode getParentNode() {
		return parentNode;
	}
	
}
