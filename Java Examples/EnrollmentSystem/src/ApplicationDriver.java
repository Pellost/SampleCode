import resource.AppRunnable;

/*
 * This class encapsulates an enrollment system app, which displays
 * a simple forms and information displayer.
 */

public class ApplicationDriver {

	/*
	 * This method launches the app. the app does not make use of the command line
	 * arguments. The app does not terminates on its own.
	 */
	
	public static void main(String[] args){
		
		Runnable theRunnable = new AppRunnable();
		
		javax.swing.SwingUtilities.invokeLater(theRunnable);
	}
}
