package view.forms;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class MainJFrameForm extends JFrame implements Runnable{

	public void printExceptions(String exception, int lblNumber) {}
	public abstract void hideExceptions();
}
