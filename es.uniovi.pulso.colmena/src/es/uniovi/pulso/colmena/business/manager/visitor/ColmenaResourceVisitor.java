package es.uniovi.pulso.colmena.business.manager.visitor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;

import es.uniovi.pulso.colmena.business.manager.PluginManager;

/**
 * Class that visit all the nodes in the tree of changed resources.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaResourceVisitor implements IResourceDeltaVisitor {

	/**
	 * Method that visit the resource node. It is executed for all the resources
	 * changed.
	 */
	public boolean visit(IResourceDelta delta) {
		// check that the resource changed is one resource where the problem
		// markers were changed.
		/*if(delta.getResource().getName().endsWith(".java") && delta.getResource().getType() == IResourceDelta.CONTENT){
			
		}*/
		// controls if the type of resource is File type and no other type
		// like folder or workspace		
		if (delta.getResource().getType() == IResource.FILE && delta.getResource().getName().endsWith(".java")) {
			if (delta.getKind() == IResourceDelta.CHANGED) {
				if (delta.getFlags() == IResourceDelta.MARKERS) {
					// show data in the console
					printDelta(delta);							
					// runs the plugin core with this resource.
					PluginManager.getInstance().runPlugin(delta.getResource());
				} 
			}
		}
		
		return true;
	}

	/**
	 * Prints the change received
	 * 
	 * @param delta
	 *            the change received. the delta are linked with a change in the
	 *            file system.
	 */
	private void printDelta(IResourceDelta delta) {
		// prints different things. Some of them are commented for debug.

		// System.out.println("--------------------------");
		// System.out.println("Delta data");
		// System.out.println("\tType: " + delta.getKind());
		// System.out.println("\tFlags: " + delta.getFlags());
		// System.out.println("\tResource: " + delta.getResource());
		// System.out.println("\tFull Path: " + delta.getFullPath());
		// System.out.println("\tProject relative Path: "
		// + delta.getProjectRelativePath());

	}
	
}
