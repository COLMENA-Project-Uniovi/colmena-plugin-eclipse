package es.uniovi.pulso.colmena.presentation.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import es.uniovi.pulso.colmena.activator.Activator;
import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceConstants;

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

public class ColmenaUserPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	private StringFieldEditor userPrefEditor;
	private Composite parent;

	public ColmenaUserPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Set your university ID");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		//obtain the parent
		parent = getFieldEditorParent();
		//creates a field
		userPrefEditor = new StringFieldEditor(PreferenceConstants.USER_NAME,
				"User ID: ", parent);
		//add the field
		addField(userPrefEditor);
	}

	@Override
	protected void checkState() {
		super.checkState();
		if (!isValid()) {
			return;
		} else if (!checkTextField()) {
			setErrorMessage("Some text fields are empty");
			setValid(false);
		} else {
			setErrorMessage(null);
			setValid(true);
		}
	}

	/**
	 * Check the value of text fields
	 * @return if the text field are empty or not
	 */
	private boolean checkTextField() {
		if (userPrefEditor.getStringValue().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getProperty().equals(FieldEditor.VALUE)) {
			if (event.getSource() == userPrefEditor) {
				checkState();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}