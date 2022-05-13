package es.uniovi.pulso.colmena.business.manager;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;

import es.uniovi.pulso.colmena.business.manager.listener.UpdateChangesManager;

/**
 * Class that Manages the subscription and unsubscription of the plugin in the
 * program
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class SubscribeManager {

	private static SubscribeManager manager;

	/**
	 * Private constructor (singleton)
	 */
	private SubscribeManager() {

	}

	/**
	 * Singleton
	 * 
	 * @return the single object
	 */
	public static SubscribeManager getInstance() {
		if (manager == null) {
			manager = new SubscribeManager();
		}
		return manager;
	}

	/**
	 * Subscribe the plugin in the system. It includes the listener.
	 */
	public void subscribePlugin() {
		UpdateChangesManager updateManager = UpdateChangesManager.getInstance();
		PluginManager pluginManager = PluginManager.getInstance();
		// enable the plugin
		pluginManager.setEnabled(true);
		// register the listener
		ResourcesPlugin.getWorkspace().addResourceChangeListener(updateManager,
				IResourceChangeEvent.POST_CHANGE);		
	}

	/**
	 * Unsubscribe the plugin including the listeners
	 */
	public void unsuscribePlugin() {
		UpdateChangesManager updateManager = UpdateChangesManager.getInstance();
		PluginManager pluginManager = PluginManager.getInstance();
		// stop the plugin
		pluginManager.setEnabled(false);
		// remove the listener
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(
				updateManager);
	}
}
