package es.uniovi.pulso.colmena.business.manager.cache;

import java.util.LinkedList;
import java.util.List;

import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.model.cache.ColmenaMethod;
import es.uniovi.pulso.colmena.model.cache.ColmenaNode;
import es.uniovi.pulso.colmena.model.cache.ColmenaTree;
import es.uniovi.pulso.colmena.model.cache.ColmenaVar;

/**
 * Class that Manages the global cache for errors
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaCache {

	// The list for the different trees (for each file)
	private List<ColmenaTree> treeList;
	// The final error list
	private List<ColmenaMarker> finalErrorList;

	/**
	 * Constructor
	 */
	public ColmenaCache() {
		this.treeList = new LinkedList<ColmenaTree>();
		this.finalErrorList = new LinkedList<ColmenaMarker>();
	}

	/**
	 * Method that adds in a specific tree a list of markers
	 * 
	 * @param currentTree
	 *            The current representation (tree) of the file
	 * @param markerList
	 *            The list of errors recovered
	 */
	public void addToCache(ColmenaTree currentTree,
			List<ColmenaMarker> markerList) {
		// reset the final error list
		finalErrorList.clear();

		// System.out.println("me estoy preparando para añadir a la cache");

		// making the relations between the markers and the tree
		synchronizeErrorsWithTree(markerList, currentTree);

		// System.out.println("Ya hemos asignado las variables al arbol " +
		// currentTree.getGlobalFile().getName());
		// System.out.println("Buscamos en la cache");
		// check if the list of trees contains the current tree (if is cached)
		if (treeList.contains(currentTree)) {
			// System.out.println("Hay un arbol en la cache para "
			// +currentTree.getGlobalFile().getName());
			// if the tree already exists, check the specific parts
			checkErrorsInCache(currentTree);
		} else {
			// System.out.println("Era la primera vez, as� que a�adimos todos los errores");
			// in the other hand, add all the markers to the list
			finalErrorList.addAll(markerList);
		}
		// finally, add the current tree to the list
		treeList.add(currentTree);
	}

	/**
	 * Method that checks the errors in the cache, comparing the current tree
	 * with the cached tree
	 * 
	 * @param currentTree
	 *            the current representation of the file
	 */
	private void checkErrorsInCache(ColmenaTree currentTree) {
		// System.out.println("\tVamos a hacer un chequeo en la cahe, para el arbol actual");
		// extract the name of the file
		String nameOfFile = currentTree.getGlobalFile().getName();
		// search the tree in the cache
		ColmenaTree cacheTree = searchTree(nameOfFile);
		// compare the trees
		// System.out.println("\tComparamos arboles");
		compareTrees(cacheTree, currentTree);
	}

	/**
	 * Method that compares two trees. One of them is the cached tree and the
	 * other one is the new registered tree
	 * 
	 * @param cacheTree
	 *            The cached representation (old one)
	 * @param currentTree
	 *            The new representation
	 */
	private void compareTrees(ColmenaTree cacheTree, ColmenaTree currentTree) {
		// Extract the nodes of the tree wich contain errors
		List<ColmenaNode> cacheNodesWithErrros = cacheTree.getPartsWithErrors();
		List<ColmenaNode> currentNodesWithErrors = currentTree.getPartsWithErrors();

		// Search the common parts between the list of errors
		searchCommonParts(cacheNodesWithErrros, currentNodesWithErrors);
		
		// Remove of the cache the cached tree, allowing save the new tree
		removeFromCache(cacheTree);

	}

	/**
	 * Method that search the common parts between the list of errors with
	 * nodes.
	 * 
	 * @param cacheNodesWithErrros
	 *            cached nodes with errors
	 * @param currentNodesWithErrors
	 *            new nodes with errors
	 */
	private void searchCommonParts(List<ColmenaNode> cacheNodesWithErrros, List<ColmenaNode> currentNodesWithErrors) {
		// prepares a buffer based on cached nodes
		List<ColmenaNode> bufferList = new LinkedList<ColmenaNode>(
				cacheNodesWithErrros);

		// for each node in the current nodes package
		for (ColmenaNode cn : currentNodesWithErrors) {
			// boolean flag
			boolean finded = false;
			// search this node in the "cached" list
			for (ColmenaNode ccn : bufferList) {
				// compare, if are equals
				if (cn.equals(ccn)) {
					// flag
					finded = true;
					// cause they're equals, we have found the same node, so we
					// have to check if the errors in the node are the same
					this.finalErrorList.addAll(compareErrors(cn, ccn));
					// delete the node found because it is not necessary (avoid extra comparison)
					bufferList.remove(ccn);
					// stop the search process
					break;
				}
			}
			// if we have not found a node in the cached list equal than the new one
			if (!finded) {
				// add to the final list all erros in this current node error list
				this.finalErrorList.addAll(cn.getErrorList());
			}
		}
	}

	/**
	 * Method that compares two specific nodes of the tree
	 * 
	 * @param cn
	 *            the current node
	 * @param ccn
	 *            the cached node
	 * @return a List with the new error markers
	 */
	private List<ColmenaMarker> compareErrors(ColmenaNode cn, ColmenaNode ccn) {
		// obtain the list of errors
		List<ColmenaMarker> currentErrors = cn.getErrorList();
		List<ColmenaMarker> cacheErrors = ccn.getErrorList();
		// a buffer error list based on current errors
		List<ColmenaMarker> newErrors = new LinkedList<ColmenaMarker>(currentErrors);

		// for the current errors
		for (int i = 0; i < currentErrors.size(); i++) {
			// boolean flag
			boolean alreadyExists = false;
			// extract the current marker
			ColmenaMarker currentMarker = currentErrors.get(i);
			// search the marker in the cached list
			for (int j = 0; j < cacheErrors.size() && !alreadyExists; j++) {
				ColmenaMarker cacheMarker = cacheErrors.get(j);
				// compare the markers
				if (currentMarker.equals(cacheMarker)) {
					// if they're equals, we remove the marker of the final
					// list, because it is already in the cache
					newErrors.remove(currentMarker);
					// activate the flag
					alreadyExists = true;
				}
			}
		}
		return newErrors;
	}

	/**
	 * Method that searches a tree in the tree list
	 * 
	 * @param nameOfFile
	 *            name of the file that is represented by a tree
	 * @return the tree or null if there isn't a tree with this filename
	 */
	private ColmenaTree searchTree(String nameOfFile) {
		// search in all of trees
		for (ColmenaTree ct : treeList)
			if (ct.getGlobalFile().getName().equals(nameOfFile))
				return ct;
		
		return null;
	}

	/**
	 * Method that links the error list with a tree
	 * 
	 * @param errorList
	 *            the list of errors
	 * @param tree
	 *            the current tree that represents the file where we have the
	 *            errors
	 */
	private void synchronizeErrorsWithTree(List<ColmenaMarker> errorList, ColmenaTree tree) {

		// search for each marker
		for (ColmenaMarker cm : errorList) {
			// boolean flag
			boolean added = false;
			
			// obtain the line of error
			int lineOfError = Integer.parseInt(cm.getLineNumber());

			// check where is it placed, vars, method or the whole file
			if (lineOfError >= tree.getStartLineOfVars() && lineOfError <= tree.getEndLineOfVars()) {
				added = searchErrorInVars(cm, tree);
			}

			if (!added && lineOfError >= tree.getStartLineOfMethods() && lineOfError <= tree.getEndLineOfMethods()) {
				added = searchErrorInMethods(cm, tree);
			}

			if (!added) {
				addErrorInWholeFile(cm, tree);
			}

		}
	}

	/**
	 * Method that adds an error marker in the global file and not in a variable
	 * node or method node
	 * 
	 * @param cm
	 *            the marker to link
	 * @param tree
	 *            the tree that represents a file
	 */
	private void addErrorInWholeFile(ColmenaMarker cm, ColmenaTree tree) {
		tree.getGlobalFile().addError(cm);
	}

	/**
	 * Method that search the var node where an error will be linked
	 * 
	 * @param cm
	 *            the marker
	 * @param tree
	 *            the current tree
	 * @return if the error was linked or not
	 */
	private boolean searchErrorInVars(ColmenaMarker cm, ColmenaTree tree) {
		// extract the line number
		int lineNumber = Integer.parseInt(cm.getLineNumber());

		// for each var node
		for (ColmenaVar cv : tree.getVars()) {

			// if the line number of error was located in this node
			if (lineNumber >= cv.getStartLine() && lineNumber <= cv.getEndLine()) {
				// add to this node the error
				cv.addError(cm);
				
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that search the var node where the error will be placed
	 * 
	 * @param cm
	 *            the error marker
	 * @param tree
	 *            the current tree
	 * @return if the marker was located or not
	 */
	private boolean searchErrorInMethods(ColmenaMarker cm, ColmenaTree tree) {
		// extract the line number
		int lineNumber = Integer.parseInt(cm.getLineNumber());
		// for each method node
		for (ColmenaMethod cme : tree.getMethods()) {
			
			// check if the line number of marker is inside the node
			if (lineNumber >= cme.getStartLine() && lineNumber <= cme.getEndLine()) {
				// add the error with this node
				cme.addError(cm);
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that removes a tree from the list
	 * 
	 * @param tree
	 *            the tree we have to delete
	 */
	public void removeFromCache(ColmenaTree tree) {
		treeList.remove(tree);
	}

	/* GETTERS AND PRINTERS */

	public void printCache() {
		for (ColmenaTree ct : treeList) {
			System.out.println(ct);
		}
	}

	public List<ColmenaMarker> getFinalErrorList() {
		return finalErrorList;
	}

}
