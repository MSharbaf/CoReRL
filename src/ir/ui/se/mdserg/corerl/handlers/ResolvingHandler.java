package ir.ui.se.mdserg.corerl.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import ir.ui.se.mdserg.corerl.wizards.*;

import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ResolvingHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		try {
			ResolvingWizard wizard = new ResolvingWizard() ; 
			wizard.setWindowTitle("Conflict Resolution Process");
			WizardDialog dialog = new WizardDialog(window.getShell(), wizard);

			if (dialog.open() == Window.OK) {
				// Doing something 
				return true ; 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	
		return null;
	}
}
