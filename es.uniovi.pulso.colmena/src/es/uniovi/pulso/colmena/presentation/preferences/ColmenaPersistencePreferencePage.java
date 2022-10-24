package es.uniovi.pulso.colmena.presentation.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
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

public class ColmenaPersistencePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private BooleanFieldEditor databasePrefEditor;
	private BooleanFieldEditor requestPrefEditor;
	private BooleanFieldEditor filePrefEditor;
	private BooleanFieldEditor ftpPrefEditor;
	
	private StringFieldEditor databaseHostName;
	private StringFieldEditor dataBaseName;
	private StringFieldEditor databaseUserName;
	private StringFieldEditor databaseUserPassword;

	private StringFieldEditor folderName;
	private StringFieldEditor fileName;
	
	private StringFieldEditor markersEndpoint;
	private StringFieldEditor compilationsEndpoint;
	
	private StringFieldEditor ftpHostName;
	private StringFieldEditor ftpHostPort;
	private StringFieldEditor ftpBaseName;
	private StringFieldEditor ftpUserName;
	private StringFieldEditor ftpUserPassword;
	private RadioGroupFieldEditor ftpSaving;
	private StringFieldEditor ftpMinutes;

	private Composite parent;

	public ColmenaPersistencePreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		parent = getFieldEditorParent();
		
		// Request's fields
		requestPrefEditor = new BooleanFieldEditor(
				PreferenceConstants.COLMENA_POST,
				"Send POST request to the endpoints", parent);
		
		markersEndpoint = new StringFieldEditor(
				PreferenceConstants.COLMENA_MARKERS_ENDPOINT, "Markers endpoint : ", parent);
		
		compilationsEndpoint = new StringFieldEditor(
				PreferenceConstants.COLMENA_COMPILATIONS_ENDPOINT, "Compilations endpoint : ", parent);
		
		addField(requestPrefEditor);
		addField(markersEndpoint);
		addField(compilationsEndpoint);
		
		
		// Database's fields
		databasePrefEditor = new BooleanFieldEditor(
				PreferenceConstants.COLMENA_STORE_IN_DB,
				"Store the data in a MySql database", parent);
		databaseHostName = new StringFieldEditor(
				PreferenceConstants.DATABASE_HOST_NAME, "Host Name : ", parent);

		dataBaseName = new StringFieldEditor(PreferenceConstants.DATABASE_NAME,
				"Database Name : ", parent);

		databaseUserName = new StringFieldEditor(
				PreferenceConstants.DATABASE_USER_NAME, "User Name : ", parent);

		databaseUserPassword = new StringFieldEditor(
				PreferenceConstants.DATABASE_USER_PASS, "User Password : ",
				parent);

		databaseUserPassword.getTextControl(parent).setEchoChar('*');

		addField(databasePrefEditor);
		addField(databaseHostName);
		addField(dataBaseName);
		addField(databaseUserName);
		addField(databaseUserPassword);

		
		// FTP fields
		filePrefEditor = new BooleanFieldEditor(
				PreferenceConstants.COLMENA_STORE_IN_FILE,
				"Store the data in a txt file", parent);

		addField(filePrefEditor);
		folderName = new StringFieldEditor(PreferenceConstants.FOLDER_NAME,
				"Folder name : ", parent);
		addField(folderName);
		fileName = new StringFieldEditor(PreferenceConstants.FILE_NAME,
				"File name : ", parent);
		addField(fileName);
		
		// FTP preferences
		ftpPrefEditor = new BooleanFieldEditor(
				PreferenceConstants.COLMENA_STORE_IN_FTP,
				"Store the data with a FTP conection", parent);
		ftpHostName = new StringFieldEditor(
				PreferenceConstants.FTP_HOST_NAME, "Host Name : ", parent);

		ftpHostPort = new StringFieldEditor(
				PreferenceConstants.FTP_HOST_PORT, "Host Port: ", parent);

		ftpBaseName = new StringFieldEditor(PreferenceConstants.FTP_NAME,
				"FTP Folder : ", parent);

		ftpUserName = new StringFieldEditor(
				PreferenceConstants.FTP_USER_NAME, "User Name : ", parent);

		ftpUserPassword = new StringFieldEditor(
				PreferenceConstants.FTP_USER_PASS, "User Password : ",
				parent);
		
		ftpSaving = new RadioGroupFieldEditor(PreferenceConstants.FTP_SAVING,
		        "When would you like to save in FTP:", 1,
		        new String[][] { { "&After n minutes", "minutes" },
		            { "After saving n times", "times" } }, parent);
		
		ftpMinutes = new StringFieldEditor(
				PreferenceConstants.FTP_N_SAVING, "Number of minutes o times : ", parent);

		ftpUserPassword.getTextControl(parent).setEchoChar('*');

		addField(ftpPrefEditor);
		addField(ftpHostName);
		addField(ftpHostPort);
		addField(ftpBaseName);
		addField(ftpUserName);
		addField(ftpUserPassword);
		addField(ftpSaving);
		addField(ftpMinutes);
		
	}

	@Override
	protected Control createContents(Composite parent) {
		Label infoLabel = new Label(parent, SWT.WRAP);
		infoLabel.setFont(new Font(getShell().getDisplay(), "Arial", 9,
				SWT.BOLD));
		infoLabel.setText("Database connection details.");
		infoLabel = new Label(parent, SWT.WRAP);
		infoLabel
				.setText("Please fill the fields with the values of your database. ");
		infoLabel = new Label(parent, SWT.WRAP);
		infoLabel
				.setText("The database must be located in the port number 80. ");

		infoLabel = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		infoLabel = new Label(parent, SWT.WRAP);
		infoLabel.setText("");

		infoLabel = new Label(parent, SWT.WRAP);
		infoLabel.setFont(new Font(getShell().getDisplay(), "Arial", 9,
				SWT.BOLD));
		infoLabel.setText("Select types of persistence");
		return super.createContents(parent);
	}

	public void init(IWorkbench workbench) {
	}

	@Override
	protected void checkState() {
		super.checkState();
		if (!isValid())
			return;
		if (!databasePrefEditor.getBooleanValue()) {
			enableDatabaseFields(false);
		} else {
			enableDatabaseFields(true);
		}

		if (!filePrefEditor.getBooleanValue()) {
			enableFileFields(false);
		} else {
			enableFileFields(true);
		}
		
		if (!ftpPrefEditor.getBooleanValue()) {
			enableFtpFields(false);
		} else {
			enableFtpFields(true);
		}

		if (!checkCheckBoxes()) {
			setErrorMessage("Must have at least one type of persistence");
			setValid(false);
		} else{
			if (!checkDatabaseField() && databasePrefEditor.getBooleanValue()) {		
				setErrorMessage("Some database text fields are empty");
				setValid(false);
			} else {
				if (!checkFtpField() && ftpPrefEditor.getBooleanValue()) {		
					setErrorMessage("Some ftp text fields are empty");
					setValid(false);
				} else {
					setErrorMessage(null);
					setValid(true);
				}
			}
		}
	}

	private void enableDatabaseFields(boolean value) {
		databaseHostName.setEnabled(value, parent);
		// databaseHostPort.setEnabled(value, parent);
		dataBaseName.setEnabled(value, parent);
		databaseUserName.setEnabled(value, parent);
		databaseUserPassword.setEnabled(value, parent);

	}

	private void enableFileFields(boolean value) {
		folderName.setEnabled(value, parent);
		fileName.setEnabled(value, parent);
	}
	
	private void enableFtpFields(boolean value) {
		ftpHostName.setEnabled(value, parent);
		ftpHostPort.setEnabled(value, parent);
		ftpBaseName.setEnabled(value, parent);
		ftpUserName.setEnabled(value, parent);
		ftpUserPassword.setEnabled(value, parent);
		ftpSaving.setEnabled(value, parent);
		ftpMinutes.setEnabled(value, parent);
	}

	private boolean checkCheckBoxes() {
		if (!databasePrefEditor.getBooleanValue()
				&& !filePrefEditor.getBooleanValue()
					&& !ftpPrefEditor.getBooleanValue()
						&&!requestPrefEditor.getBooleanValue()) {
			return false;
		}
		return true;
	}

	private boolean checkDatabaseField() {
		if (databaseHostName.getStringValue().isEmpty()
				|| dataBaseName.getStringValue().isEmpty()
				|| databaseUserName.getStringValue().isEmpty()
				|| databaseUserPassword.getStringValue().isEmpty()
				|| folderName.getStringValue().isEmpty()
				|| fileName.getStringValue().isEmpty()) {
			return false;
		}
		return true;
	}
	
	private boolean checkFtpField() {
		if (ftpHostName.getStringValue().isEmpty()
				|| ftpHostPort.getStringValue().isEmpty()
				|| ftpBaseName.getStringValue().isEmpty()
				|| ftpUserName.getStringValue().isEmpty()
				|| ftpUserPassword.getStringValue().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getProperty().equals(FieldEditor.VALUE)) {
			if (event.getSource() == databasePrefEditor
					|| event.getSource() == filePrefEditor
					|| event.getSource() == ftpPrefEditor
					|| event.getSource() == databaseHostName
					// || event.getSource() == databaseHostPort
					|| event.getSource() == dataBaseName
					|| event.getSource() == databaseUserName
					|| event.getSource() == databaseUserPassword
					|| event.getSource() == folderName
					|| event.getSource() == fileName) {
				checkState();
			}
		}
	}

}