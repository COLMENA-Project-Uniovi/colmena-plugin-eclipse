package es.uniovi.pulso.colmena.model.cache;

import es.uniovi.pulso.colmena.model.enumeration.NodeType;

/**
 * Class that represents a file in the Colmena Java Model
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaFile extends ColmenaNode {
	// The source of the file
	private String source;
	// The original source
	private String originalSource;
	// number of lines
	private int lines;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            The name of the file
	 * @param source
	 *            The source of the file
	 * @param lines
	 *            The lines of the file
	 */
	public ColmenaFile(String name, String source, int lines) {
		super(name);
		this.source = source;
		this.originalSource = source;
		this.lines = lines;
		super.setType(NodeType.FILE);
	}

	/* GETTERS, SETTERS, TOSTRING.... */

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public String getOriginalSource() {
		return originalSource;
	}

	public String toString() {
		String errors = super.toString();
		return "ColmenaFile\n\tname:" + getName() /* + "\n\tsource:" + source */
				+ "\n\tlines:" + lines + "\n\terrors: " + errors;
	}

	@Override
	public boolean equals(ColmenaNode cn) {
		if (cn.getType() != NodeType.FILE) {
			return false;
		}
		ColmenaFile cf = (ColmenaFile) cn;
		if (this.getName().equals(cf.getName())) {
			return true;
		}

		return false;
	}

}
