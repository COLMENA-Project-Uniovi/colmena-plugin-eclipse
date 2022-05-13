package es.uniovi.pulso.colmena.model.cache;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Document;

/**
 * Class that represents a Colmena Tree. Is the abstract representation of a
 * java nature (.java) file. It is similar to an AST tree but based on Java
 * Model implementation (by Eclipse)
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaTree {
	// The global file of the Tree
	private ColmenaFile globalFile;
	// The list of vars in the Tree
	private List<ColmenaVar> vars;
	// The list of methods in the Tree
	private List<ColmenaMethod> methods;
	// The document that represents the Tree in JavaModel
	private Document document;
	// The compilation unit
	private ICompilationUnit unit;

	// The start lines and end lines of vars and methods
	private int startLineOfVars = 0;
	private int endLineOfVars = 0;
	private int startLineOfMethods = 0;
	private int endLineOfMethods = 0;

	/**
	 * Constructor. Initialize the process of builting a new tree.
	 * 
	 * @param unit
	 *            The unit where the procees will start
	 */
	public ColmenaTree(ICompilationUnit unit) {
		try {
			this.unit = unit;
			this.methods = new LinkedList<ColmenaMethod>();
			this.vars = new LinkedList<ColmenaVar>();
			this.document = new Document(unit.getSource());
			// start the parsing process
			initializeTree(unit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * First method in the building process
	 * 
	 * @param unit
	 *            The compilation unit
	 * @throws JavaModelException
	 */
	private void initializeTree(ICompilationUnit unit)
			throws JavaModelException {
		// We create a global file
		createGlobalFile(unit);
		// Then the tree Structure
		createTreeStructure();
		// And the assing the limit of the vars in the tree
		assignLimitVars();
	}

	/**
	 * Method that creates the structure of the file
	 * 
	 * @param unit
	 * @throws JavaModelException
	 */
	private void createGlobalFile(ICompilationUnit unit)
			throws JavaModelException {
		// extract name and the complete content (source), whit the number of
		// lines also
		String nameFile = unit.getElementName();
		String sourceFile = unit.getSource();
		int lines = document.getNumberOfLines();

		// The creation of the file
		this.globalFile = new ColmenaFile(nameFile, sourceFile, lines);

	}

	/**
	 * Method that fills the tree with vars and methods
	 * 
	 * @throws JavaModelException
	 */
	public void createTreeStructure() throws JavaModelException {
		// recover vars
		obtainVars();
		// then recover methods
		obtainMethods();
	}

	/**
	 * Method that stablish the limits in "variable zone" and "methods zone".
	 */
	private void assignLimitVars() {
		if (!vars.isEmpty()) {
			startLineOfVars = vars.get(0).getStartLine();
			endLineOfVars = vars.get(vars.size() - 1).getEndLine();
		}

		if (!methods.isEmpty()) {
			startLineOfMethods = methods.get(0).getStartLine();
			endLineOfMethods = methods.get(methods.size() - 1).getEndLine();
		}
	}

	/**
	 * Method that extract all the vars in the file (through JavaModel)
	 * 
	 * @throws JavaModelException
	 */
	private void obtainVars() throws JavaModelException {
		// Extract all types in the unit
		IType[] allTypes = unit.getAllTypes();
		// for each one
		for (IType type : allTypes) {
			// extract the variables
			IField[] fields = type.getFields();
			// for each var, we wrapp the field in our type
			for (IField field : fields) {
				// wrapping
				createColmenaVar(field);
			}
		}
	}

	/**
	 * Method that extracts all the methods in the file.
	 * 
	 * @throws JavaModelException
	 */
	private void obtainMethods() throws JavaModelException {
		// Extract all types in the file
		IType[] allTypes = unit.getAllTypes();
		// for each one
		for (IType type : allTypes) {
			// Extract the methods
			IMethod[] methods = type.getMethods();
			// for each var, we wrapp the field in our type
			for (IMethod method : methods) {
				// wrapping
				createColmenaMethod(method);
			}
		}
	}

	/**
	 * Method that creates a colmena var based on the var recovered from
	 * JavaModel
	 * 
	 * @param field
	 *            the original source
	 * @throws JavaModelException
	 */
	private void createColmenaVar(IField field) throws JavaModelException {
		// Extract the diferent paremeters
		String nameOfVar = field.getElementName();
		Document varDocument = new Document(field.getSource());
		int line = getNumberOfLine(field.getSource());
		int numberOfLines = varDocument.getNumberOfLines();
		int endLine = line + numberOfLines;
		endLine -= 1;
		int lenght = varDocument.getLength();

		// creaction of the var
		ColmenaVar cv = new ColmenaVar(nameOfVar, line, endLine, numberOfLines,
				lenght, field.getSource());

		// add to the general var list in the node
		vars.add(cv);
	}

	/**
	 * Method that creates the wrapper object for Methods.
	 * 
	 * @param method
	 *            The originial JavaModel IMethod source
	 * @throws JavaModelException
	 */
	private void createColmenaMethod(IMethod method) throws JavaModelException {
		// Extract the different paremeters
		Document methodDocument = new Document(method.getSource());
		String name = method.getElementName();
		int startLine = getNumberOfLine(method.getSource());
		int numberOfLines = methodDocument.getNumberOfLines();
		int endLine = startLine + numberOfLines;
		endLine -= 1;
		String signature = method.getSignature();
		String firstLine = method.getSource().split("\n")[0];
		int lenght = methodDocument.getLength();
		String source = method.getSource();

		// /Creation the Method
		ColmenaMethod cm = new ColmenaMethod(name, startLine, endLine,
				numberOfLines, signature, firstLine, lenght, source);

		// Add to the general method list in the node
		methods.add(cm);

	}

	/**
	 * Method that obtain the relative number line of each part (var or method)
	 * 
	 * @param source
	 *            The method source
	 * @return
	 */
	private int getNumberOfLine(String source) {
		// At first we caught all the content
		String generalSource = globalFile.getSource();
		// Obtain the first line of our method
		String firstLine = source.split("\n")[0];
		// split all the content by lines
		String[] partsOfGlobal = generalSource.split("\n");

		// we search the matches beetween ourt method first line and all the
		// lines in the file
		for (int i = 0; i < partsOfGlobal.length; i++) {
			if (partsOfGlobal[i].contains(firstLine)) {
				// when we find the matches, we replace (for more searchs in the
				// future)
				modifySourceToSearch(i);
				return i + 1;
			}
		}
		return -1;
	}

	/**
	 * Method that changes the matched line replacing it by a special string.
	 * This solves the problem of 2 methods with the same name vinculated to the
	 * same line on general file.
	 * 
	 * @param line
	 *            the number of line
	 */
	private void modifySourceToSearch(int line) {
		// We obtain the general source
		String generalSource = globalFile.getSource();
		// split that source
		String[] partsOfGlobal = generalSource.split("\n");
		// replace the line
		partsOfGlobal[line] = "(-----HERE WAS A LINE----)";
		// we rejoin all the parts in one new string
		String rejoined = "";
		for (int i = 0; i < partsOfGlobal.length; i++) {
			rejoined += partsOfGlobal[i] + "\n";
		}
		// we update the general source.
		globalFile.setSource(rejoined);
	}

	/**
	 * Method that gives the parts in the tree that have errors
	 * 
	 * @return The list of Nodes with errors
	 */
	public List<ColmenaNode> getPartsWithErrors() {
		// Create a new list
		List<ColmenaNode> partList = new LinkedList<ColmenaNode>();
		// If the global file has errors
		if (!globalFile.getErrorList().isEmpty()) {
			partList.add(globalFile);
		}
		// variebles
		for (ColmenaVar cv : vars) {
			if (!cv.getErrorList().isEmpty()) {
				partList.add(cv);
			}
		}
		// methods
		for (ColmenaMethod cm : methods) {
			if (!cm.getErrorList().isEmpty()) {
				partList.add(cm);
			}
		}
		return partList;
	}

	/* GETTERS, SETTERS, TOSTRING AND LIST MANAGE METHODS */

	public void addVar(ColmenaVar var) {
		vars.add(var);
	}

	public void addMethod(ColmenaMethod method) {
		methods.add(method);
	}

	public List<ColmenaVar> getVars() {
		return vars;
	}

	public void setVars(List<ColmenaVar> vars) {
		this.vars = vars;
	}

	public List<ColmenaMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<ColmenaMethod> methods) {
		this.methods = methods;
	}

	public int getStartLineOfVars() {
		return startLineOfVars;
	}

	public int getEndLineOfVars() {
		return endLineOfVars;
	}

	public int getStartLineOfMethods() {
		return startLineOfMethods;
	}

	public int getEndLineOfMethods() {
		return endLineOfMethods;
	}

	public ColmenaFile getGlobalFile() {
		return globalFile;
	}

	public String toString() {
		String str = "";
		System.out.println("Information of the tree");
		System.out.println("File in general");
		System.out.println(globalFile.toString());
		System.out.println("Variables");
		for (ColmenaVar cv : vars)
			System.out.println(cv.toString());
		System.out.println("Methods");
		for (ColmenaMethod cm : methods)
			System.out.println(cm.toString());
		return str;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((globalFile == null) ? 0 : globalFile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColmenaTree other = (ColmenaTree) obj;
		if (globalFile.getName() == null) {
			if (other.globalFile.getName() != null)
				return false;
		} else if (!globalFile.getName().equals(other.globalFile.getName()))
			return false;
		return true;
	}

}
