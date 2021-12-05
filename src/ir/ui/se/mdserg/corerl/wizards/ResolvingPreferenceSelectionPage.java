package ir.ui.se.mdserg.corerl.wizards;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

import ir.ui.se.mdserg.e3mp.helper.Conflict;
import ir.ui.se.mdserg.e3mp.helper.ConflictManagement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;


public class ResolvingPreferenceSelectionPage extends WizardPage {
	public String path ; 
	private Composite container;
	
	private static Group grpModelProperties;
	
	public ConflictManagement CM ;

	
	public Button qBtn1, qBtn2, qBtn3, qBtn4, qBtn5 ; 
	public Text mText1, mText2, mText3, mText4, mText5 ; 
	
	public IProject selectedProject;
	public EmfModel tModel = null, modelV0, modelV1, modelV2, modelVm  ;
	public double p1, p2, p3, p4, p5 ; 
	
	
	public static List<Conflict> conflictList;
	private static List<List<Double>> qTable;
	private static List<List<Double>> actionReward;
	private static Integer NOEpisodes, NOSteps;
	private static List<EmfModel> VmCollection; 
	private static List<Double> VmCollectionReward;
	private static double epsilon = 0.8, gamma = 0.8, alpha = 0.9; 


    protected ResolvingPreferenceSelectionPage(String pageName) {
             super(pageName);
             setTitle("Preference Selection");
             setDescription("Please select your preferences then quantify their priorities");
    }
	
	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub	
		container = new Composite(parent, SWT.NULL);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayoutData(data);
		GridLayout layout = new GridLayout(2,false);
		container.setLayout(layout);
					
		

		grpModelProperties = new Group(container, SWT.NULL);
		GridData rddata = new GridData(GridData.FILL_BOTH);
		grpModelProperties.setText("Preferences options:");
		grpModelProperties.setLayout(new GridLayout(3,false));
		grpModelProperties.setLayoutData(rddata);
		
		
		
	    qBtn1 = new Button(grpModelProperties, SWT.CHECK);
	    qBtn1.setText("Reward improving Maintainability in the merged model") ;
	    qBtn1.setSelection(true);
	
		mText1 = new Text(grpModelProperties, SWT.SINGLE | SWT.BORDER);
		mText1.setText("20"); 
		GridData gd1 = new GridData(GridData.BEGINNING);
		gd1.grabExcessHorizontalSpace = true;
		gd1.horizontalAlignment = GridData.FILL;
		mText1.setLayoutData(gd1);
 
	    Label lbl1 = new Label(grpModelProperties,SWT.None);
		lbl1.setText("%");
 
		///////////////////////////////////////////////////////////
		
	    qBtn2 = new Button(grpModelProperties, SWT.CHECK);
	    qBtn2.setText("Reward improving Understandability in the merged model") ; 
	    qBtn2.setSelection(true);
	    
		mText2 = new Text(grpModelProperties, SWT.SINGLE | SWT.BORDER);
		mText2.setText("20");
		GridData gd2 = new GridData(GridData.BEGINNING);
		gd2.grabExcessHorizontalSpace = true;
		gd2.horizontalAlignment = GridData.FILL;
		mText2.setLayoutData(gd2);
	    
	    Label lbl2 = new Label(grpModelProperties,SWT.None);
		lbl2.setText("%");

		///////////////////////////////////////////////////////////////
		
	    qBtn3 = new Button(grpModelProperties, SWT.CHECK);
	    qBtn3.setText("Reward reducing Complexity in the merged model");
	    qBtn3.setSelection(true);
	    
		mText3 = new Text(grpModelProperties, SWT.SINGLE | SWT.BORDER);
		mText3.setText("20");
		GridData gd3 = new GridData(GridData.BEGINNING);
		gd3.grabExcessHorizontalSpace = true;
		gd3.horizontalAlignment = GridData.FILL;
		mText3.setLayoutData(gd3);
	    
	    Label lbl3 = new Label(grpModelProperties,SWT.None);
		lbl3.setText("%");

		///////////////////////////////////////////////////////////////
		
	    qBtn4 = new Button(grpModelProperties, SWT.CHECK);
	    qBtn4.setText("Reward improving Reusability in the merged model");
	    qBtn4.setSelection(true);
	    
		mText4 = new Text(grpModelProperties, SWT.SINGLE | SWT.BORDER);
		mText4.setText("20");
		GridData gd4 = new GridData(GridData.BEGINNING);
		gd4.grabExcessHorizontalSpace = true;
		gd4.horizontalAlignment = GridData.FILL;
		mText4.setLayoutData(gd4);
	    
	    Label lbl4 = new Label(grpModelProperties,SWT.None);
		lbl4.setText("%");

		///////////////////////////////////////////////////////////////
		
	    qBtn5 = new Button(grpModelProperties, SWT.CHECK);
	    qBtn5.setText("Reward the Compeletness of users modifications in the merged model");
	    qBtn5.setSelection(true);
	
		mText5 = new Text(grpModelProperties, SWT.SINGLE | SWT.BORDER);
		mText5.setText("20");
		GridData gd5 = new GridData(GridData.BEGINNING);
		gd5.grabExcessHorizontalSpace = true;
		gd5.horizontalAlignment = GridData.FILL;
		mText5.setLayoutData(gd5);

	    Label lbl5 = new Label(grpModelProperties,SWT.None);
		lbl5.setText("%");

		
		setControl(container);		
	}

	public void setSelectedProject(IProject project) {
		this.selectedProject = project;
	}
	
	public void onLoad(IProject selectedPrj, FileChooser MetaModelFC, FileChooser BaseModelFC, EmfModel modelV0, EmfModel modelV1, EmfModel modelV2, EmfModel modelVm)
	{
		this.selectedProject = selectedPrj ;
		this.path = selectedPrj.getLocation().toString() ;
	
		this.modelV0 = modelV0 ;
		this.modelV1 = modelV1 ;
		this.modelV2 = modelV2 ;
		this.modelVm = modelVm ;
	}
	
	
    /** @override */
    public IWizardPage getNextPage() {
        boolean isNextPressed = "nextPressed".equalsIgnoreCase(Thread.currentThread().getStackTrace()[2].getMethodName());
        if (isNextPressed) {
            boolean validatedNextPress = this.nextPressed();
            if (!validatedNextPress) {
                return this;
            }
        }
        return super.getNextPage();
    }
	   
	/**
	 * @see WizardDialog#nextPressed()
	 * @see WizardPage#getNextPage()
	 */
	 protected boolean nextPressed() {
		p1 = 0 ; p2 = 0 ; p3 = 0 ; p4 = 0 ; p5 = 0 ; 
		
		if(this.qBtn1.getSelection()==true)
			p1 = Double.parseDouble(this.mText1.getText().toString()) ;
		if(this.qBtn2.getSelection()==true)
			p2 = Double.parseDouble(this.mText2.getText().toString()) ;
		if(this.qBtn3.getSelection()==true)
			p3 = Double.parseDouble(this.mText3.getText().toString()) ;
		if(this.qBtn4.getSelection()==true)
			p4 = Double.parseDouble(this.mText4.getText().toString()) ;
		if(this.qBtn5.getSelection()==true)
			p5 = Double.parseDouble(this.mText5.getText().toString()) ;
		
		
		
		this.CM = new ConflictManagement() ; 
		
		conflictList = new ArrayList<Conflict>() ; 
		this.CM.ConflictDetection(modelV0, modelV1, modelV2, modelVm, conflictList) ;
	
		
		qTable = new ArrayList<List<Double>>(); 
	    for(int i = 0; i < conflictList.size(); i++)  {
	        qTable.add(new ArrayList<Double>());
	    }
	    
		this.CM.initQTable(qTable) ; 
		this.CM.initActionsList(actionReward) ;
		NOEpisodes = 10 ;
		NOSteps = (2*conflictList.size())+1 ;
		VmCollection = new ArrayList<EmfModel>();  
		VmCollectionReward = new ArrayList<Double>();
		
		while(NOEpisodes>0){
			double episodeReward = 0 ; 
			EmfModel Vmerged = modelVm;
			for (Conflict conflict : conflictList) {
				int conflictNum = identifyConflictNumber(conflict);
				while(NOSteps>0){
					int action = selectResolutionAction(conflictNum, this.CM.actionList);
					boolean result = false;
					try {
						result = this.CM.checkConflictResolution(Vmerged, conflictNum, action);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateQTable(conflictNum, action, result);
					NOSteps = NOSteps - 1 ; 
					if(result==true) {
						this.CM.applyAction(Vmerged, conflictNum, action);
						// Delete conflict from conflictList
						episodeReward +=  this.CM.evaluateReward(Vmerged, conflictNum, action, p1, p2, p3, p4, p5);  
						break ;
					}	
				}//End while
				NOSteps = (2*conflictList.size())+1 ;
			}// End for
			
			if(this.CM.isConsistant(Vmerged) && VmCollection.contains(Vmerged)!=true) {
				VmCollection.add(Vmerged) ; 
				VmCollectionReward.add(episodeReward) ; 
			}
			NOEpisodes = NOEpisodes - 1 ; 
			epsilon = 0.9*epsilon; 
		}//End while
		
		int index = 0 ;
		double rewardTemp = VmCollectionReward.get(0) ; 
		for(int i=1; i<VmCollectionReward.size(); i++) {
			if(VmCollectionReward.get(i) > rewardTemp) {
				index = i ; 
				rewardTemp = VmCollectionReward.get(i) ; 
			}
		}
		
		this.modelVm = VmCollection.get(index) ; 
		return true ;  
	 }
	
	public InMemoryEmfModel getUmlModel(String name, File file) throws Exception {
		ResourceSet set = new ResourceSetImpl();
		UMLResourcesUtil.init(set);
		set.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		set.getResourceFactoryRegistry().getExtensionToFactoryMap().
		         put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
	
		Resource r = set.getResource(URI.createFileURI(file.getAbsolutePath()), true);
		r.load(null);
		
		Collection<EPackage> ePackages = new ArrayList<EPackage>();
		for (Object ePackage : set.getPackageRegistry().values()) {
			ePackages.add((EPackage) ePackage);
		}
		return new InMemoryEmfModel(name, r, ePackages);
	}
		
	
	public InMemoryEmfModel getEcoreModel(String name, File file) throws Exception {
		ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
	
		Resource r = rs.getResource(URI.createFileURI(file.getAbsolutePath()), true);
		r.load(null);
		
		Collection<EPackage> ePackages = new ArrayList<EPackage>();
		for (Object ePackage : rs.getPackageRegistry().values()) {
			ePackages.add((EPackage) ePackage);
		}
		return new InMemoryEmfModel(name, r, ePackages);
	}
	
	public InMemoryEmfModel getInMemoryEmfModel(String name, File modelFile, String metamodelPath) throws IOException
	{
		ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl()) ; //XMIResourceFactoryImpl());
		Resource r = null;
		EPackage metamodel = null;
		
		java.net.URI javaURI = new File(metamodelPath).toURI() ; 
		
		r = rs.createResource(URI.createURI(javaURI.toString()));
		r.load(Collections.emptyMap());
		metamodel = (EPackage) r.getContents().get(0);

		rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl()) ;
				//XMIResourceFactoryImpl());
		
		Registry packageRegistry = rs.getPackageRegistry();
		EPackage pack = (EPackage) packageRegistry.get(EcorePackage.eINSTANCE.getNsURI());
		if (!(pack instanceof EcorePackage)) {
			packageRegistry.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		}
		
		rs.getPackageRegistry().put(metamodel.getNsURI(), metamodel);
		r = rs.createResource(URI.createURI(modelFile.toURI().toString()));
		r.load(Collections.emptyMap());
	
		InMemoryEmfModel model = new InMemoryEmfModel(name, r, metamodel);
		
		model.setMetamodelFile(URI.createFileURI(metamodelPath).toString());
		model.setMetamodelFileBased(true);

		return model ; 
	}
	
	protected EmfModel getEmfModel(String name, String model, 
			String metamodel, boolean readOnLoad, boolean storeOnDisposal) 
					throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new  EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,URI.createFileURI(metamodel).toString());
		properties.put(EmfModel.PROPERTY_MODEL_URI,URI.createFileURI(model).toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, 
				storeOnDisposal + "");
		emfModel.load(properties, (IRelativePathResolver) null);
		return emfModel;
	}

	private static void updateQTable(int conflictNum, int action, boolean result) {
		//Qnew(C1, A1) = Qcurrent(C1, A1) + 0.9*(A1reward + 0.8*Max{Q(Conflictnext, Actions)} - Qcurrent(C1, A1))
		double nextStateVal = 0 ;
		if((conflictNum+1) != conflictList.size()) {
			for(int i=0; i<qTable.get(conflictNum+1).size(); i++) {
				if(qTable.get(conflictNum+1).get(i)>nextStateVal)
					nextStateVal = qTable.get(conflictNum+1).get(i) ; 
			}
		}
		double value = qTable.get(conflictNum).get(action) + 
				alpha*(actionReward.get(conflictNum).get(action) + gamma*(nextStateVal) - qTable.get(conflictNum).get(action)) ;

		if(result==true)
			qTable.get(conflictNum).set(action, value);
		else
			qTable.get(conflictNum).set(action, -1*value);
	}


	private static int selectResolutionAction(int conflictNum, List<List<Boolean>> actionList) {
		Random r = new Random();
		double random = r.nextDouble();
		if(random <= epsilon) {
			Random r2 = new Random();
			int randomAction = r2.nextInt(actionReward.get(conflictNum).size()) + 0;
			return randomAction;
		}else {
			int action = 0 ; 
			for (int i=1; i<qTable.get(conflictNum).size(); i++){
				if(qTable.get(conflictNum).get(action)<qTable.get(conflictNum).get(i))
					action = i ; 
			}
			return action;
		}
	}
	
	private static int identifyConflictNumber(Conflict conflict) {
		int cIndex = -1 ; 
		for(int i=0; i<conflictList.size(); i++)
			if(conflictList.get(i).getConflictName().equals(conflict.getConflictName()))
				return i;  
		return cIndex;
	}
	
}
