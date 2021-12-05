package ir.ui.se.mdserg.corerl.wizards;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.jface.wizard.Wizard;


public class ResolvingWizard extends Wizard {
	public IProject selectedProject ; 
	ResolvingModelSelectionFinishPage reslvingModelSelectionFinishPage;
	ResolvingPreferenceSelectionPage resolvingPreferenceSelectionPage; 
	ResolvingModelSelectionPage resolvingModelSelectionPage;
	ProjectSelectionWizardPage projectSelectionPage ;  
    public MatchTrace matchTraces ; 
	public EmfModel bModel = null ; 
	public EmfModel modelV1 = null ; 
	public EmfModel modelV2 = null ; 
	public EmfModel tModel = null ; 
	

    public void addPages() {
    		projectSelectionPage = new ProjectSelectionWizardPage("Project Selection"); 
    		addPage(projectSelectionPage) ; 
    		
    		resolvingModelSelectionPage = new ResolvingModelSelectionPage("Model Selection");
    		projectSelectionPage.ResolvingSelectionPage = resolvingModelSelectionPage ; 
    		addPage(resolvingModelSelectionPage);
    		   		
    		resolvingPreferenceSelectionPage = new ResolvingPreferenceSelectionPage("Preference Selection");
    		resolvingModelSelectionPage.ResolvingPreferenceSelectionPage  = resolvingPreferenceSelectionPage ; 
    		addPage(resolvingPreferenceSelectionPage);   
    		
    		reslvingModelSelectionFinishPage = new ResolvingModelSelectionFinishPage("Finish Resolution Process"); 
    		addPage(reslvingModelSelectionFinishPage);
    }
    
	public boolean canFinish()
	{
		if(getContainer().getCurrentPage() == projectSelectionPage || getContainer().getCurrentPage() == resolvingModelSelectionPage 
				|| getContainer().getCurrentPage() == resolvingPreferenceSelectionPage)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
			System.out.println("Finish") ; 		    
			return true ; 
	}	
}