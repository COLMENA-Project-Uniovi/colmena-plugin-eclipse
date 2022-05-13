package es.uniovi.pulso.colmena.activator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import es.uniovi.pulso.colmena.business.manager.PluginManager;
import es.uniovi.pulso.colmena.business.manager.SubscribeManager;
import es.uniovi.pulso.colmena.business.manager.persistence.DataBaseManager;
import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin implements IStartup {

	// The plug-in ID
	public static final String PLUGIN_ID = "es.uniovi.pulso.colmena"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {		
		// close the connection to mysql via jdbc
		if (new PreferenceManager().isActiveDatabasePersistence()) {
			DataBaseManager.getInstance().closeConnection();
		}
		// send to the ftp when stopping the plugin
		if (new PreferenceManager().isActiveFtpPersistence()) {			
			PluginManager.getInstance().saveFtp();
		}
		plugin = null;
		super.stop(context);		
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	public void earlyStartup() {
		if (!PluginManager.getInstance().isEnabled()) {
			// subscribe the plugin
			SubscribeManager.getInstance().subscribePlugin();
		}
		
	}
}
