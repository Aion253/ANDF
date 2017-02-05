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
	public final List<ANDFNode> getChildren() {
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
		System.out.println(node);
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
	public final int degree(){
		return children.size();
	}
	
	/**
	 * @return If this node has no children.
	 */
	public final boolean isLeaf(){
		if(children.size() == 0){
			return true;
		}
		return false;
	}
	
}
