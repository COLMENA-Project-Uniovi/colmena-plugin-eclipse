package es.uniovi.pulso.colmena.business.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import es.uniovi.pulso.colmena.business.manager.PluginManager;

/**
 * Handler that controls the popup menu in the package explorer
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 * 
 */
public class ScanCompleteProjectHandler extends AbstractHandler {

	/**
	 * Execute the handler an pop-up the menu
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// obtain the selection
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);
		// obtain the selected element
		Object selectedElement = selection.getFirstElement();
		// compares with a java element
		if (selectedElement instanceof IJavaElement) {
			// prepares a new object IResource
			IResource projectSelected = ((IJavaElement) selectedElement)
					.getJavaProject().getProject();
			// runs the plugin with this new object
			PluginManager.getInstance().runPluginStatically(projectSelected);
			// shows an alert dialog
		} else {
			MessageDialog
					.openInformation(
							HandlerUtil.getActiveShell(event),
							"Information",
							"Please select a project resources, as project, package or source code file. Do not select other types of resources, as libraries or folders");
		}

		return null;
	}

}
