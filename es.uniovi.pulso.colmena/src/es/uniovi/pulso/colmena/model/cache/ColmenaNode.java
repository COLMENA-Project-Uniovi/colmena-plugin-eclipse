package es.uniovi.pulso.colmena.model.cache;

import java.util.LinkedList;
import java.util.List;

import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.model.enumeration.NodeType;

/**
 * Class that represents an abstract element of the Colmena Tree. Is the parent of ColmenaVar, ColmenaFile or ColmenaMethod
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 *
 */
public abstract class ColmenaNode {
	//Name of the node
	private String name;
	//The error list linked to this node.
	private List<ColmenaMarker> errorList;
	//The node type (FILE, VAR OR METHOD)
	private NodeType type;

	/**
	 * Constructor base
	 * @param name The name of the node
	 */
	public ColmenaNode(String name) {
		this.errorList = new LinkedList<ColmenaMarker>();
		this.name = name;
	}

	/**
	 * Method that adds one error to the node
	 * @param error
	 */
	public void addError(ColmenaMarker error) {
		errorList.add(error);
	}

	/**
	 * Method that removes one error to the list
	 * @param error
	 */
	public void removeError(String error) {
		errorList.remove(error);
	}

	/* GETTER, SETTER, ETC */
	
	public String toString() {
		return errorList.toString();
	}

	public List<ColmenaMarker> getErrorList() {
		return errorList;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public abstract boolean equals(ColmenaNode cn);
}
