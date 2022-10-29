package es.uniovi.pulso.colmena.business.manager;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import es.uniovi.pulso.colmena.business.manager.cache.ColmenaCache;
import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.model.cache.ColmenaTree;

/**
 * Class that manages the Cache system. This class was thought for calling from
 * PluginManager to recover and analize the different markers obtained from
 * eclipse.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class CacheManager {
	// The real cache, is a singleton pattern
	private ColmenaCache cache;
	private static CacheManager instance;

	/**
	 * Singleton
	 */
	private CacheManager() {
		this.cache = new ColmenaCache();
	}

	/**
	 * Singleton
	 * 
	 * @return the cache objetct
	 */
	public static CacheManager getInstance() {
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}

	/**
	 * Main Method of the class, that starts the operation about the cache.
	 * 
	 * @param resource
	 *            The local file where is launched the data recolection
	 * @param markerList
	 *            The list of errors recovered througth Ecipse
	 * @throws CoreException
	 *             For internal errors
	 * @throws JavaModelException
	 *             For errors in the java model
	 */
	public void addToCache(IResource resource, List<ColmenaMarker> markerList)
			throws CoreException, JavaModelException {
		// Check if is a java nature project (.java)
		if (resource.getProject().isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			// creates the resource about the file
			IJavaElement javaElement = JavaCore.create(resource);
			// creates a colmena tree about this compilation unit. Is like a
			// start an AST parser
			ColmenaTree tree = new ColmenaTree((ICompilationUnit) javaElement);
			
			// add to cache this new tree with all the errors (not processed yet).
			cache.addToCache(tree, markerList);
		}
	}
	
	/**
	 * Prints the cache
	 */
	public void printCache() {
		cache.printCache();
	}

	/**
	 * The list of errors after processing the cached or non cached errors.
	 * 
	 * @return the final list
	 */
	public List<ColmenaMarker> getFinalErrorList() {
		return cache.getFinalErrorList();
	}

}
