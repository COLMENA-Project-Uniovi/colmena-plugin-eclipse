package es.uniovi.pulso.colmena.model.cache;

import es.uniovi.pulso.colmena.model.enumeration.NodeType;

/**
 * Class that represents a Variable in the Colmena Java Model.
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 *
 */
public class ColmenaVar extends ColmenaNode {
	//The start line of the var
	private int startLine;
	//The end line of the var (normally will be the same as startLine
	private int endLine;
	//The number of lines 
	private int numberOfLines;
	//Lengh of the var
	private int lenght;
	//Source of the var
	private String source;

	/**
	 * Constructor
	 * @param name The name of the var
	 * @param startLine The start line of the var
	 * @param endLine The endLine of the var
	 * @param numberOfLines The number of lines of the var
	 * @param lenght  The lenght of the var
	 * @param source The source of the var
	 */
	public ColmenaVar(String name, int startLine, int endLine,
			int numberOfLines, int lenght, String source) {
		super(name);
		this.startLine = startLine;
		this.endLine = endLine;
		this.numberOfLines = numberOfLines;
		this.lenght = lenght;
		this.source = source;
		super.setType(NodeType.VAR);
	}

	/* GETTERS, SETTERS, ETC */
	
	
	public int getStartLine() {
		return startLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public int getLenght() {
		return lenght;
	}

	public String getSource() {
		return source;
	}

	@Override
	public String toString() {
		String errors = super.toString();
		return "ColmenaVar\n\tname:" + getName() + "\n\tstartLine:" + startLine
				+ "\n\tendLine:" + endLine + "\n\tnumberOfLines:"
				+ numberOfLines + "\n\tlenght:" + lenght /*
														 * + "\n\tsource:" +
														 * source
														 */+ "\n\terrors: "
				+ errors;
	}

	@Override
	public boolean equals(ColmenaNode cn) {
		if (cn.getType() != NodeType.VAR) {
			return false;
		}
		ColmenaVar cv = ((ColmenaVar) cn);

		if (this.getName().equals(cv.getName())) {

			return true;
		}
		return false;
	}

}
