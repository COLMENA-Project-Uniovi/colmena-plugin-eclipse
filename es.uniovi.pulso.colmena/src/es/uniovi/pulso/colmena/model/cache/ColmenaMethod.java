package es.uniovi.pulso.colmena.model.cache;

import es.uniovi.pulso.colmena.model.enumeration.NodeType;

/**
 * Class that represents a Method in the Colmena Java Model
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaMethod extends ColmenaNode {
	// start line of the method
	private int startLine;
	// end line of the method
	private int endLine;
	// total number of lines of the method
	private int numberOfLines;

	// the signature of the method
	private String signature;
	// the first line of the method
	private String firstLine;

	// lenght of method
	private int lenght;
	// the source of the method (all the content)
	private String source;

	/**
	 * Constructor of the method
	 * @param name The name of the method
	 * @param startLine The start line of the method 
	 * @param endLine The end line of the method
	 * @param numberOfLines The number of lines of the method
	 * @param signature The signature of the method
	 * @param firstLine The first line of the method
	 * @param lenght The lenght of the method
	 * @param source The source of the method
	 */
	public ColmenaMethod(String name, int startLine, int endLine,
			int numberOfLines, String signature, String firstLine, int lenght,
			String source) {
		super(name);
		this.startLine = startLine;
		this.endLine = endLine;
		this.numberOfLines = numberOfLines;
		this.signature = signature;
		this.firstLine = firstLine;
		this.lenght = lenght;
		this.source = source;
		super.setType(NodeType.METHOD);
	}

	/* GETTERS, SETTERS, COMPARETO, TOSTRING */
	
	public int getStartLine() {
		return startLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public String getSignature() {
		return signature;
	}

	public String getFirstLine() {
		return firstLine;
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
		return "ColmenaMethod\n\tname:" + getName() + "\n\tstartLine:"
				+ startLine + "\n\tendLine:" + endLine + "\n\tnumberOfLines:"
				+ numberOfLines + "\n\tsignature:" + signature
				+ "\n\tfirstLine:" + firstLine + "\n\tlenght:" + lenght /*
																		 * +
																		 * "\n\tsource:"
																		 * +
																		 * source
																		 */
				+ "\n\terrors: " + errors;
	}

	@Override
	public boolean equals(ColmenaNode cn) {
		if (cn.getType() != NodeType.METHOD) {
			return false;
		}
		ColmenaMethod cm = ((ColmenaMethod) cn);

		if (this.getName().equals(cm.getName())) {

			return true;
		}
		return false;
	}

}
