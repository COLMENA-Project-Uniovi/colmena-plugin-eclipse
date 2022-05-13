package es.uniovi.pulso.colmena.presentation.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.wb.swt.ResourceManager;

import es.uniovi.pulso.colmena.activator.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ColmenaPreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage {

	public ColmenaPreferencesPage() {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Colmena Plugin for Eclipse General Preferences - Colmena 0.5");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite arg0) {
		Composite parent = arg0;
		noDefaultAndApplyButton();

		Label label = new Label(parent, SWT.HORIZONTAL);
		label.setImage(ResourceManager.getPluginImage(
				"es.uniovi.pulso.colmena", "icons/logo2.png"));

		label = new Label(parent, SWT.NONE);
		label.setText("PULSO Research Team");

		label = new Label(parent, SWT.NONE);
		label.setText("University Of Oviedo");

		label = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);

		label = new Label(parent, SWT.NONE);
		label.setText("Team Members");
		label.setFont(new Font(getShell().getDisplay(), "Arial", 10, SWT.BOLD));

		label = new Label(parent, SWT.NONE);
		label.setText("Carlos Fernandez Medina");
		label = new Label(parent, SWT.NONE);
		label.setText("Julia Vallina Garcia");
		label = new Label(parent, SWT.NONE);
		label.setText("Juan Ramon Perez Perez");
		label = new Label(parent, SWT.NONE);
		label.setText("Maria Del Puerto Paule Ruiz");
		label = new Label(parent, SWT.NONE);
		label.setText("Victor Manuel Alvarez Garcia");
		

		label = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		label = new Label(parent, SWT.NONE);
		label.setText("Official project page");
		label.setFont(new Font(getShell().getDisplay(), "Arial", 9, SWT.BOLD));

		label = new Label(parent, SWT.NONE);
		label.setText("www.pulso.uniovi.es/colmena2014");

		return parent;
	}

}