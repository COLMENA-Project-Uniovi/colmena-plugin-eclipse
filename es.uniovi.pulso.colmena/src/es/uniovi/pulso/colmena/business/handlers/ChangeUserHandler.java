package es.uniovi.pulso.colmena.business.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import es.uniovi.pulso.colmena.presentation.swt.LoginWindow;

/**
 * Handler that controls the menu option "set Colmena User". Changes the user in
 * the system.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 * 
 */
public class ChangeUserHandler extends AbstractHandler {

	/**
	 * Command method for the action. Only runs a UI class that allows change
	 * user.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// runs the window that allows us set the user.
		new LoginWindow();
		return null;
	}
}
