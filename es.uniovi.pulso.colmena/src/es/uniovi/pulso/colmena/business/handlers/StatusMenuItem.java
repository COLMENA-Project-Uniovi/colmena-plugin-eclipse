package es.uniovi.pulso.colmena.business.handlers;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;
import org.osgi.framework.Bundle;

import es.uniovi.pulso.colmena.business.manager.PluginManager;

@SuppressWarnings("restriction")
public class StatusMenuItem extends CompoundContributionItem implements IWorkbenchContribution {  
	  
	private IServiceLocator mServiceLocator;  
	 private boolean status; 	  
	 private long mLastTimeStamp = 0; 
	  	 
	@Override  
	 protected IContributionItem[] getContributionItems() {
		 mLastTimeStamp = System.currentTimeMillis();  
		 status = PluginManager.getInstance().isEnabled();
		 String st = status?"enabled":"disabled";
	  
		 String icono = status?"icons/start.png":"icons/stop.png";	  
		 Bundle bundle = Platform.getBundle("es.uniovi.pulso.colmena");
		 URL fullPathString = BundleUtility.find(bundle, icono);
		 ImageDescriptor icon = ImageDescriptor.createFromURL(fullPathString);
		  
		 final CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(mServiceLocator, null, "es.uniovi.pulso.colmena.commands.Status",  
		    CommandContributionItem.STYLE_PUSH);  
		 contributionParameter.label = "Plugin status: " + st;  
		 contributionParameter.visibleEnabled = true;  
		 contributionParameter.icon = icon;
	  
		 return new IContributionItem[] { new CommandContributionItem(contributionParameter) };  
	 }  
	  
	 @Override  
	 public void initialize(final IServiceLocator serviceLocator) {
		 mServiceLocator = serviceLocator;  
	 }  
	  
	 @Override  
	 public boolean isDirty() {
		 return mLastTimeStamp < System.currentTimeMillis();  
	 }  
}  
