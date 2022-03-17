package org.springframework.samples.petclinic.amazingco;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;

/**
 * Simple domain object representing a list of employees. Mostly here to be used for the
 * 'employees' {@link org.springframework.web.servlet.view.xml.MarshallingView}.
 *
 * @author Jason deMello
 */
@XmlRootElement
public class AmazingTreeNode<T> implements Cloneable {

	// This variable is checked before printing debug statements.
	//
	// TRUE => Print debug statements
	// FALSE => Do not print debug statements
	private static Boolean debug = false;

	// The height level of the current node in the tree.
	int height = 0;

	// This nodes's identity.
	String id = "";

	// The identity of this node's root.
	String rootId = "";

	// The identity of this node's parent.
	String parentId = "";

	// This node's list of children.
	LinkedList<AmazingTreeNode<T>> children = new LinkedList<>();

	/**
	 * Default constructor.
	 */
	<T> AmazingTreeNode() {
	}

	<T> AmazingTreeNode(String n) {
		height = 0;
		id = n;
		rootId = n;
		parentId = n;
	}

	/**
	 * Constructor taking as input all necessary primitive types to complete a clone, sans
	 * cloning the complex types.
	 * @param d The source debug setting for this tree.
	 * @param h The source height for thie tree
	 * @param id The identity of this node.
	 * @param rId The identity of the root of this tree
	 * @param pId The identity of the parent of this node.
	 */
	<T> AmazingTreeNode(int h, String id, String rId, String pId) {
		this.height = h;
		this.id = id;
		rootId = rId;
		this.parentId = pId;
	}

	/**
	 * This function turns debug statements ON.
	 **/
	public void setDebug() {
		debug = true;
	}

	/**
	 * This function turns debug statements OFF.
	 **/
	public void clearDebug() {
		debug = false;
	}

	/**
	 * This function checks the status of the debug flag.
	 **/
	public Boolean isDebugOn() {
		return (debug == true);
	}

	/**
	 * This function helps determine if the current node is the root node or not.
	 * @return True if the current node is the root, false if not.
	 */
	public boolean isRoot() {
		return (height == 0);
	}

	/**
	 * This function prints out some information of a 'this' node.
	 */
	public void printNode() {
		int numSpaces = height * 4;
		System.out.print("== ");
		for (int i = 0; i < numSpaces; i++) {
			System.out.print(" ");
		}
		System.out.println("id     : " + id);

		System.out.print("== ");
		for (int i = 0; i < numSpaces; i++) {
			System.out.print(" ");
		}
		System.out.println("height : " + height);

		System.out.print("== ");
		for (int i = 0; i < numSpaces; i++) {
			System.out.print(" ");
		}
		System.out.println("root   : " + rootId);

		System.out.print("== ");
		for (int i = 0; i < numSpaces; i++) {
			System.out.print(" ");
		}
		System.out.println("parent : " + parentId);
	}

	/**
	 * This function provides a consistent header for printing sub-nodes in a tree.
	 **/
	private void printSubNodeHeader() {
		System.out.print("== -----------------------------------------------------");
		System.out.println("-----------------------------------------------------");
	}

	/**
	 * This function provides a consistent header for printing trees.
	 **/
	private void printTreeHeader() {
		System.out.print("\n=======================================================");
		System.out.println("======================================================");
	}

	/**
	 * This function provides a consistent footer for printing trees.
	 **/
	private void printTreeFooter() {
		System.out.println("==");
	}

	/**
	 * This function performs a preorder print of all nodes in the tree starting from
	 * 'this'.
	 **/
	public void printTree() {
		if (height == 0) {
			printTreeHeader();
		}
		else {
			printSubNodeHeader();
		}

		// Print this node and its progeny depth-first in the family tree.
		this.printNode();
		if (!children.isEmpty()) {
			// Recursive case : Node does not meet search criteria =>
			// -- There's at least one child,
			// -- Print recursively
			for (int i = 0; i < children.size(); i++) {
				children.get(i).printTree();
			}
		}

		if (height == 0) {
			System.out.println("== ");
		}
	}

	/**
	 * This function performs a level order print of all nodes in the tree starting from
	 * 'this'.
	 */
	public void printTree_levelOrder() {
		// Level order traversal
		Queue<AmazingTreeNode<T>> queue = new LinkedList<>();
		queue.offer(this);
		while (!queue.isEmpty()) {
			int len = queue.size();
			for (int i = 0; i < len; i++) {
				AmazingTreeNode<T> node = queue.poll();
				assert node != null;

				if (node.height == 0) {
					printTreeHeader();
				}
				else {
					printSubNodeHeader();
				}

				// Print this node and its progeny breadth-first in the family tree.
				node.printNode();
				for (AmazingTreeNode<T> item : node.children) {
					queue.offer(item);
				}
			}
		}

		if (this.height == 0) {
			printTreeFooter();
		}
	}

	/**
	 * This function returns the node within the current tree identified by a given
	 * string.
	 * @param id The name of the node to retrieve.
	 * @return The node identified by the parameter 'id'.
	 */
	public AmazingTreeNode<T> get(String id) {
		String funcName = "[AmazingTreeNode<T>.get()]:: ";
		AmazingTreeNode<T> retval = null;

		if (this.id.equalsIgnoreCase(id)) {
			// Base case: Node found => return this node
			if (isDebugOn()) {
				System.out.println(funcName + "BASE CASE[FOUND]: " + this.id);
			}
			retval = this;
		}
		else if (!this.children.isEmpty()) {
			// Recursive case : Node does not meet search criteria =>
			// -- There's at least one child at this point,
			// -- Search children recursively
			if (isDebugOn()) {
				System.out.println(funcName + "RECURSIVE CASE[Entry]: Search children for " + id + " under " + this.id);
			}

			int i = 0;
			int ub = this.children.size();
			while ((retval == null) && (i < ub)) {
				retval = this.children.get(i).get(id);
				if (isDebugOn()) {
					System.out.print(funcName + "   retval[" + i + "]: " + this.children.get(i).id + " -> ");
					if (retval == null) {
						System.out.println("NOT FOUND");
					}
					else {
						System.out.println("FOUND");
					}
				}

				if ((retval != null) && (retval.id.equalsIgnoreCase(id))) {
					i = ub;
				}
				else {
					i++;
				}
			}
		}

		if (isDebugOn()) {
			System.out.println(funcName + "RETURN: retval " + ((retval == null) ? "null" : retval.id));
		}

		return retval;
	}

	/**
	 * This function plucks the node within the current tree identified by a given string,
	 * assigns its children to its parent, and returns the node plucked.
	 * @param root Any node in the tree that serves as a root node for searching for other
	 * nodes from this instance.
	 * @param id The id of the node to retrieve.
	 * @return The node identified by the parameter 'id', sans children.
	 */
	public AmazingTreeNode<T> pluck(AmazingTreeNode<T> root, String id) {
		String funcName = "[AmazingTreeNode<T>.pluck()]:: ";
		AmazingTreeNode<T> retval = null;

		if (this.id.equalsIgnoreCase(id)) {
			// Base case: Node found => add new node to list of children and return
			if (isDebugOn()) {
				System.out.println(funcName + "BASE CASE[FOUND]: " + this.id);
				System.out.println(funcName + "   Number of Children: " + this.children.size());
			}

			// Append this.children to parent.children
			IntStream.range(0, this.children.size()).forEachOrdered(i -> {
				try {
					AmazingTreeNode<T> child = (AmazingTreeNode<T>) this.children.getFirst().clone();

					if (isDebugOn()) {
						System.out.println(funcName + "      [" + i + "]: " + child.id);
					}

					AmazingTreeNode<T> parent = root.get(this.parentId);
					child.height = parent.height + 1;
					child.rootId = parent.rootId;
					child.parentId = parent.parentId;
					parent.children.add(child);

					this.children.removeFirst();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			});

			retval = this;
		}
		else if (!this.children.isEmpty()) {
			// Recursive case : Node does not meet search criteria =>
			// -- There's at least one child at this point,
			// -- Search children recursively
			if (isDebugOn()) {
				System.out.println(funcName + "RECURSIVE CASE[Entry]: Search children for " + id + " under " + this.id);
			}

			int i = 0;
			int ub = this.children.size();
			while ((retval == null) && (i < ub)) {
				retval = this.children.get(i).pluck(root, id);
				if (isDebugOn()) {
					System.out.print(funcName + "   retval[" + i + "]: " + this.children.get(i).id + " -> ");
					if (retval == null) {
						System.out.println("NOT FOUND");
					}
					else {
						System.out.println("FOUND");
					}
				}

				if ((retval != null) && (this.id.equalsIgnoreCase(retval.parentId))) {
					this.children.remove(i);
					i = ub;
				}
				else {
					i++;
				}
			}
		}

		if (isDebugOn()) {
			System.out.println(funcName + "RETURN: retval " + ((retval == null) ? "null" : retval.id));
		}

		return retval;
	}

	/*
	 * This method returns a shallow copy of this AmazingTreeNode instance. (The elements
	 * themselves are not copied.)
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// Perform the shallow copy.
		AmazingTreeNode<T> dst = (AmazingTreeNode<T>) super.clone();

		// Prep for use by creating new objects within the destination clone.
		dst.children = new LinkedList<>();

		return dst;
	}

	/*
	 * This method returns a deep copy of this AmazingTreeNode instance. (The elements
	 * themselves are copied.)
	 *
	 * @param src The node from which the deep copy is performed.
	 *
	 * @param dst The node into which the deep copy goes.
	 *
	 * ASSUMPTION: The complex types for this instance are empty and already allocated.
	 */
	public Object copy(AmazingTreeNode<T> src) throws CloneNotSupportedException {
		AmazingTreeNode<T> dst = (AmazingTreeNode<T>) src.clone();
		if (src.children.isEmpty()) {
			return this;
		}
		else {
			IntStream.range(0, src.children.size()).forEachOrdered(i -> {
				try {
					dst.children.add((AmazingTreeNode<T>) src.copy(src.children.get(i)));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		return dst;
	}

	/**
	 * Insert a node into the tree. It is assumed that the 'this' pointer represents any
	 * given node in the tree, root or not, and that it is the intended parent of the
	 * child to insert.
	 * @param id The identity of the child node created.
	 */
	public void insert(String id) {
		AmazingTreeNode child = new AmazingTreeNode(this.height + 1, id, this.rootId, this.id);
		this.children.add(child);
	}

	/**
	 * Insert a node into the tree. It is assumed that the 'this' pointer represents any
	 * given node in the tree, root or not, and that it is the intended parent of the
	 * child to insert.
	 * @param n The child node to insert into the tree.
	 */
	public void insert(AmazingTreeNode n) {
		AmazingTreeNode child = new AmazingTreeNode(this.height + 1, n.id, this.rootId, this.id);
		this.children.add(child);
	}

	/**
	 * This function moves a node from one parent to another parent within the tree
	 * structure starting from root.
	 * @param childId The id of the child node being bandied about.
	 * @param from Input string denoting the id of the parent from which the child will be
	 * taken.
	 * @param to Input string denoting the id of the parent to which the child will be
	 * given.
	 *
	 * NOTE: Moves can only be performed by initiating searches for 'from' and 'to' nodes
	 * from the root.
	 */
	public void move(String childId, String from, String to) {
		String funcName = "[move(s, sFrom, sTo)]:: ";

		if (height != 0) {
			System.out.println(funcName + "!!! WARNING: Moves can only be performed by the root node !!!");
		}
		else {
			System.out.println(funcName + "sFrom: " + from);
			AmazingTreeNode<T> fromNode = get(from);

			System.out.println(funcName + "sTo: " + to);
			AmazingTreeNode<T> toNode = get(to);

			System.out.println(funcName + "ch: " + childId);
			AmazingTreeNode<T> childNode = fromNode.pluck(this, childId);

			toNode.insert(childNode);
		}
	}

	/*
	 * This function places tree nodes into a list in preorder form. TODO[JHD
	 * (3/16/2022)]: This doesn't work. Find out why.
	 */
	public LinkedList<AmazingTreeNode<T>> toList(AmazingTreeNode<T> node) {
		LinkedList<AmazingTreeNode<T>> list = new LinkedList<>();
		System.out.println("**********************************************************************");
		System.out.println("** toList():: " + node.id);
		if (node.children.isEmpty()) {
			list.add(node);
		}
		else {
			IntStream.range(0, node.children.size()).forEachOrdered(i -> {
				list.addAll(children.get(i).toList(children.get(i)));
			});
		}

		return list;

	}

}
