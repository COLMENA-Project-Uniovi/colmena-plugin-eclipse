package es.uniovi.pulso.colmena.business.manager.source;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Document;

import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.model.cache.ColmenaFile;
import es.uniovi.pulso.colmena.model.cache.ColmenaMethod;

/**
 * Class that manages the Cache system. This class was thought for calling from
 * PluginManager to recover and analyze the different markers obtained from
 * eclipse.
 * 
 * @author Borja Rodríguez Lorenzo, PULSO Research Team, University of Oviedo
 * 
 */
public class SourceManager {

	// The list of methods in the Tree
	private List<ColmenaMethod> methods;

	// The global file
	private ColmenaFile globalFile;

	// The compilation unit
	@SuppressWarnings("unused")
	private ICompilationUnit unit;

	// The document that represents the Tree in JavaModel
	private Document document;

	/**
	 * Public constructor
	 * 
	 * @param unit
	 */
	public SourceManager(ICompilationUnit unit) {
		try {
			this.unit = unit;
			this.methods = new LinkedList<ColmenaMethod>();
			this.document = new Document(unit.getSource());

			createGlobalFile(unit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that creates the structure of the file
	 * 
	 * @param unit
	 * @throws JavaModelException
	 */
	private void createGlobalFile(ICompilationUnit unit) throws JavaModelException {
		// extract name and the complete content (source), whitf the number of
		// lines too
		String nameFile = unit.getElementName();
		String sourceFile = unit.getSource();
		int lines = document.getNumberOfLines();

		// The creation of the file
		this.globalFile = new ColmenaFile(nameFile, sourceFile, lines);

	}

	/**
	 * Method which searched the methods to find the marker code
	 * 
	 * @param resource, the document
	 * @param marker,   the marker
	 * @return the source code
	 * @throws JavaModelException
	 */
	public String obtainSourceCode(IResource resource, ColmenaMarker marker) throws JavaModelException {
		IJavaElement javaElement = JavaCore.create(resource);
		obtainMethods((ICompilationUnit) javaElement);

		return findMethod(marker);
	}

	/**
	 * Method which searched the methods to find the marker code 
	 * @param marker, the user marker
	 * @return the source code
	 */
	private String findMethod(ColmenaMarker marker) {
		for (ColmenaMethod method : methods) {
			if (Integer.parseInt(marker.getLineNumber()) < method.getEndLine()
					&& Integer.parseInt(marker.getLineNumber()) > method.getStartLine()) {
				return method.getSource();
			}
		}

		return "";
	}

	/**
	 * Method which creates the methods to fill the list
	 * 
	 * @param unit
	 * @throws JavaModelException
	 */
	public void obtainMethods(ICompilationUnit unit) throws JavaModelException {
		IType[] allTypes = unit.getAllTypes();
		for (IType type : allTypes) {

			IMethod[] methods = type.getMethods();
			for (IMethod method : methods) {
				createColmenaMethod(method);
			}
		}
	}

	/**
	 * Method that creates the wrapper object for Methods.
	 * 
	 * @param method The original JavaModel IMethod source
	 * @throws JavaModelException
	 */
	private void createColmenaMethod(IMethod method) throws JavaModelException {
		// Extract the different parameters
		Document methodDocument = new Document(method.getSource());
		String name = method.getElementName();
		String signature = method.getSignature();
		String firstLine = method.getSource().split("\n")[0];
		String source = method.getSource();

		int startLine = getNumberOfLine(method.getSource());
		int numberOfLines = methodDocument.getNumberOfLines();
		int endLine = startLine + numberOfLines - 1;
		int lenght = methodDocument.getLength();

		// /Creation the Method
		ColmenaMethod cm = new ColmenaMethod(name, startLine, endLine, numberOfLines, signature, firstLine, lenght,
				source);

		// Add to the general method list in the node
		methods.add(cm);

	}

	/**
	 * Method that obtain the relative number line of each part (var or method)
	 * 
	 * @param source The method source
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
	 * Method that changes the matched line replacing it by a special string. This
	 * solves the problem of 2 methods with the same name vinculated to the same
	 * line on general file.
	 * 
	 * @param line the number of line
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

}
