package es.uniovi.pulso.colmena.business.manager.listener;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;

import es.uniovi.pulso.colmena.business.manager.visitor.ColmenaResourceVisitor;

/**
 * The Listener that was launched where a POST_CHANGED has detected by eclipe.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class UpdateChangesManager implements IResourceChangeListener {

	private static UpdateChangesManager manager;

	private UpdateChangesManager() {

	}

	/**
	 * The method called where a save operation (POST_CHANGED) was detected
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		// Creates a visitor for the IResource structure
		ColmenaResourceVisitor visitor = new ColmenaResourceVisitor();
		try {			
			// accept the visitor
			event.getDelta().accept(visitor);			
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Singleton
	 * 
	 * @return
	 */
	public static UpdateChangesManager getInstance() {
		if (manager == null) {
			manager = new UpdateChangesManager();
		}
		return manager;
	}

}
