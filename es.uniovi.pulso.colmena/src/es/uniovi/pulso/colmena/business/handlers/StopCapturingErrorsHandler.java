package es.uniovi.pulso.colmena.business.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import es.uniovi.pulso.colmena.business.manager.PluginManager;
import es.uniovi.pulso.colmena.business.manager.SubscribeManager;

/**
 * Handler that controls the menu option "Stop Capturing errors". Stop the
 * capture errors operation
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 * 
 */
public class StopCapturingErrorsHandler extends AbstractHandler {

	/**
	 * Command method for the action. UnRegister the event. If the process is
	 * already stopped, shows a dialog
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// check if plugin is enabled
		if (!PluginManager.getInstance().isEnabled()) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "The plugin is already stopped.");
		} else {						
			// unsuscribe the plugin
			SubscribeManager.getInstance().unsuscribePlugin();
		}
		return null;
	}
	
	@Override
	public boolean isEnabled(){
		return (PluginManager.getInstance().isEnabled());		
	}
}
