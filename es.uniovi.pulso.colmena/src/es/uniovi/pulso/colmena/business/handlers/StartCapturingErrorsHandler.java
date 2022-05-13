package es.uniovi.pulso.colmena.business.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import es.uniovi.pulso.colmena.business.manager.PluginManager;
import es.uniovi.pulso.colmena.business.manager.SubscribeManager;

/**
 * Handler that controls the menu option "Start Capturing Errors". Start or
 * re-start the capturing operations
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 * 
 */
public class StartCapturingErrorsHandler extends AbstractHandler {

	/**
	 * Command method for the action. Register the event. If the process is
	 * already started, shows a dialog
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {		
		// check if plugin is enabled
		if (PluginManager.getInstance().isEnabled()) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information",
					"Colmena is already started and ready for capture errors");
		} else {
											
			// subscribe the plugin
			SubscribeManager.getInstance().subscribePlugin();
		}
		return null;
	}
	
	@Override
	public boolean isEnabled(){
		return !(PluginManager.getInstance().isEnabled());		
	}
}
