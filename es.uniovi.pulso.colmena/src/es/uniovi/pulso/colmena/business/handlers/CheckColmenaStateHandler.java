package es.uniovi.pulso.colmena.business.handlers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import es.uniovi.pulso.colmena.business.manager.PluginManager;

/**
 * Handler that controls the popup menu in the package explorer
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 * 
 */
public class CheckColmenaStateHandler extends
		WorkbenchWindowControlContribution {

	@Override
	protected Control createControl(Composite parent) {
		// Create a composite to place the label in
		Composite comp = new Composite(parent, SWT.NONE);

		// Give some room around the control
		FillLayout layout = new FillLayout();
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		comp.setLayout(layout);

		// Create a label for the trim.
		final Label ccCtrl = new Label(comp, SWT.BORDER | SWT.CENTER);
		ccCtrl.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WIDGET_LIGHT_SHADOW));

		ccCtrl.setText(" Colmena is " + getColmenaState()); 
		ccCtrl.setToolTipText("Colmena State"); 
				
		ccCtrl.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				ccCtrl.setText(" Colmena is " + getColmenaState()); 
			}
		});
		
		return comp;
	}
	
	private String getColmenaState() {
		if (PluginManager.getInstance().isEnabled())
			return "enabled";
		return "disabled";
	}

}
