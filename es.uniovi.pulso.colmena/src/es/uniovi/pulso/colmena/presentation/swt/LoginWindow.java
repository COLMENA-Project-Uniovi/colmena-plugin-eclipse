package es.uniovi.pulso.colmena.presentation.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import es.uniovi.pulso.colmena.business.manager.PluginManager;
import es.uniovi.pulso.colmena.business.manager.SubscribeManager;

/**
 * Class that represents an UI Window that displays the way to change the
 * current user id.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class LoginWindow {
	private Label label_1;

	public LoginWindow() {
		// It is necesary execute the UI changes into a Runnable class launched
		// by the asyncExec method. It is necesay because the interface only can
		// be modified by an UI-Thread and asyncExec forces it to happen
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				show();
			}
		});
	}

	/**
	 * This method shows the dialog about change/set the current owner user of
	 * Errors and Warnings.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void show() {

		Label label;
		final Text text;
		final Display display = Display.getDefault();
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.MIN);
		shell.setText("Set current user");
		shell.setBounds(100, 100, 250, 110);
		shell.setLayout(new GridLayout(4, false));
		// centered
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		label = new Label(shell, SWT.LEFT);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2,
				1));
		label.setText("Enter your university id");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		label_1 = new Label(shell, SWT.LEFT);
		label_1.setText("University ID:");
		text = new Text(shell, SWT.SINGLE | SWT.BORDER);
		text.setText(PluginManager.getInstance().getCurrentUserId());
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3,
				1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Button buttonAccept = new Button(shell, SWT.RIGHT_TO_LEFT);
		buttonAccept.setToolTipText("Change the user");
		buttonAccept.setText("Accept");

		Button buttonCancel = new Button(shell, SWT.CENTER);
		buttonCancel.setText("Cancel");

		buttonAccept.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// if plugin is not enabled runs it
				if (!PluginManager.getInstance().isEnabled()) {
					SubscribeManager.getInstance().subscribePlugin();
				}
				// change the user
				PluginManager.getInstance().changeCurrentUserName(
						text.getText());
				shell.dispose();
			}
		});

		buttonCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.dispose();
			}

		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		shell.dispose();
	}
}
