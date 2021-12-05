package ir.ui.se.mdserg.corerl.helper ; 

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class EclipseJavaProjects {
   public static List<IJavaProject> getJavaProjects() {
      List<IJavaProject> projectList = new LinkedList<IJavaProject>();
      try {
         IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
         IProject[] projects = workspaceRoot.getProjects();
         for(int i = 0; i < projects.length; i++) {
            IProject project = projects[i];
            if(project.isOpen() && project.hasNature(JavaCore.NATURE_ID)) {
               projectList.add(JavaCore.create(project));
            }
         }
      }
      catch(CoreException ce) {
         ce.printStackTrace();
      }
      return projectList;
   }
}