package es.uniovi.pulso.colmena.presentation.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * Class that represents an UI Window that displays alerts. Replaced by messageBox
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class AlertWindow {

	private String warningText;

	public AlertWindow(String warningText) {
		this.warningText = warningText;

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
	 * This method shows the dialog about warning happened
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void show() {

		Label label;
		Display display = Display.getDefault();
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.MIN);
		shell.setSize(394, 300);
		shell.setText("Warning");
		shell.setBounds(100, 100, 250, 110);

		// centered
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		shell.setLayout(null);

		label = new Label(shell, SWT.WRAP | SWT.CENTER);
		label.setBounds(21, 10, 202, 15);
		label.setText(warningText);

		Button buttonAccept = new Button(shell, SWT.CENTER);
		buttonAccept.setBounds(97, 47, 49, 25);
		buttonAccept.setText("Accept");

		buttonAccept.addSelectionListener(new SelectionAdapter() {
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
