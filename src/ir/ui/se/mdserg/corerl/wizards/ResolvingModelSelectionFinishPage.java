package ir.ui.se.mdserg.corerl.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;


public class ResolvingModelSelectionFinishPage extends WizardPage {
	public String path ; 
	private Composite container;
	public IProject selectedProject;


    protected ResolvingModelSelectionFinishPage(String pageName) {
             super(pageName);
             setTitle("Resolution Process Finished Successfully!");
             setDescription("The Merged model saved in the project directory ...");
    }
	
	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		
		container = new Composite(parent, SWT.NULL);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayoutData(data);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
			
		setControl(container);		
	}

	public void setSelectedProject(IProject project) {
		this.selectedProject = project;
	}
	
}
