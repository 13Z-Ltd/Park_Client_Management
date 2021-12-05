package view;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Model;
import view.forms.ActivationForm;
import view.forms.AdminForm;
import view.forms.LoginForm;
import view.forms.MainJFrameForm;
import view.forms.DressingForm;

public class View {
	private Model model;

	private MainJFrameForm currentView;
	Thread currentViewThread;

	public View(Model model) { //, Controller controller) {
		this.model = model;
	}

	public void startViewThread(String viewName) {
		if (currentViewThread != null || currentView != null) {
			currentViewThread.interrupt();
			currentView.setVisible(false); //you can't see me!
			currentView.dispose();
		}
		
		if (viewName == "LoginForm") {
			currentView = new LoginForm();
			currentViewThread = new Thread((Runnable)currentView, "LoginFormThread");
			currentViewThread.start();
			currentView.setVisible(true);
		}
		if (viewName == "ActivationForm") {
			currentView = new ActivationForm(model);
			currentViewThread = new Thread((Runnable)currentView, "UserFormThread");
			currentViewThread.start();
			currentView.setVisible(true);
		}
		if (viewName == "DressingForm") {
			currentView = new DressingForm(model);
			currentViewThread = new Thread((Runnable)currentView, "ReceiverFormThread");
			currentViewThread.start();
			currentView.setVisible(true);
		}
		if (viewName == "AdminForm") {
			currentView = new AdminForm(model);
			currentViewThread = new Thread((Runnable)currentView, "AdminFormThread");
			currentViewThread.start();
			currentView.setVisible(true);
		}
		
		currentView.setResizable(false);
	}

	public MainJFrameForm getCurrentView() {
		return currentView;
	}

	public void setCurrentView(MainJFrameForm currentView) {
		this.currentView = currentView;
	}
	
	public int showExitDialog() {
		Object[] options = {"Igen", "Nem"};
		return (JOptionPane.showOptionDialog(currentView,
				"Biztosan be szeretné zárni az ablakot?", "Ablak bezárása?",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    new ImageIcon(getClass().getClassLoader().getResource("bird_question2.png")),     //do not use a custom Icon
			    options,  //the titles of buttons
			    options[0]));	
	}
	
	/*
	public void printException(String string, int fieldNumber) {
		System.out.println(string);
		
	}
	*/	
}
