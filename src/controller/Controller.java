package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Model;
import model.db.HibernateUtil;
import view.HintPasswordField;
import view.HintTextField;
import view.View;
import view.forms.LoginForm;

public class Controller implements ActionListener {

	private Model model;
	private View view;	
	private LoginForm loginForm;
	
	private HibernateUtil connectionUtil;
	private boolean availableDatabase;

	private ActivationFormController activationPanelController;
	private DressingFormController receiverFormController;
	private AdminFormController adminFormController;

	public Controller(Model model) {
		this.model = model;
		view = new View(model);
		
		connectionUtil = new HibernateUtil();
		availableDatabase = connectionUtil.makeConnection();
		checkDatabaseConnection();
		
		initailize();
	}

	private void checkDatabaseConnection() {
		if(!availableDatabase) {		
			JOptionPane.showMessageDialog(new JFrame(),
					"<html><h2><font color='red'>Kérem ellenõrizze, hogy az adatbázis elérhetõ-e!<br>"
					+ "Amennyiben nem nézze meg a szerver gép kábeleit majd indítsa újra!</font></h2></html>",
					"Hiba történt!",
				    JOptionPane.ERROR_MESSAGE,
				    new ImageIcon(getClass().getClassLoader().getResource("database_error.png")));
			
			//System.exit(0);
		}		
		setDatabaseIcon();
	}
	
	///!!!!!
	private void makeConnectionTo() {		
		//availableDatabase = connectionUtil.makeConnectionToNewIp("jdbc:postgresql://127.0.0.1:5432/KVJK");
		availableDatabase = connectionUtil.makeConnectionToNewIp("jdbc:postgresql://" + loginForm.getNewIPAddressTextField().getText() + ":5432/KVJK");
	}

	public void initailize() {		
		view.startViewThread("LoginForm");
		loginForm = ((LoginForm) view.getCurrentView());
		loginForm.getBtnLoginButton().addActionListener(this);
		loginForm.getNewIPAddressButton().addActionListener(this);
		
		setDatabaseIcon();		
	}
	
	private void setDatabaseIcon() {
		if(loginForm != null) {
			if(availableDatabase) {
				loginForm.getAvailableDatabaseIcon().setIcon(new ImageIcon(getClass().getClassLoader().getResource("accept_database.png")));
				loginForm.getAvailableDatabaseIcon().setToolTipText("Adatbázis kapcsolat megfelelõ!");
				
				loginForm.getNewIPAddressLabel().setVisible(false);
				loginForm.getNewIPAddressTextField().setVisible(false);
				loginForm.getNewIPAddressButton().setVisible(false);
			}else {
				loginForm.getAvailableDatabaseIcon().setIcon(new ImageIcon(getClass().getClassLoader().getResource("misc_database.png")));
				loginForm.getAvailableDatabaseIcon().setToolTipText("Jelenleg nincs kapcsolat az adatbázissal!");
				
				loginForm.getNewIPAddressLabel().setVisible(true);
				loginForm.getNewIPAddressTextField().setVisible(true);
				loginForm.getNewIPAddressButton().setVisible(true);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
				
		if (e.getSource() == loginForm.getBtnLoginButton()) {
			checkDatabaseConnection();
			
			if(availableDatabase) {
				view.getCurrentView().hideExceptions();
				
				if(loginForm.isWantToRegister()) {
					registrationProcess();
				}else {
					loginProcess();
				}
			}else {
				view.getCurrentView().printExceptions("Az adatbázis jelenleg nem érhetõ el!", 2);
			}				
		}
		
		if (e.getSource() == loginForm.getNewIPAddressButton()) {
			makeConnectionTo();
			checkDatabaseConnection();
		}
	}

	private void registrationProcess() {
		LoginForm loginForm = (LoginForm) view.getCurrentView();
		String userName = ((HintTextField) loginForm.getUserNameTextField()).getRealText();
		String pass = ((HintPasswordField) loginForm.getPasswordField()).getRealText();
		String repeatPass = ((HintPasswordField) loginForm.getRepeatPasswordField()).getRealText();
		String firstName = ((HintTextField) loginForm.getFirstNameTextField()).getRealText();
		String lastName = ((HintTextField) loginForm.getLastNameTextField()).getRealText();

		if (userName == null || pass == null || repeatPass == null || firstName == null || lastName == null) {
			view.getCurrentView().printExceptions("Az összes mezõ kitöltése szükséges!", 0);
			return;
		}

		Boolean b1 = userName.length() > 2 && userName.length() <= 25;
		Boolean p1 = pass.length() > 3 && pass.length() <= 20;
		Boolean p2 = repeatPass.equals(pass);
		Boolean b2 = firstName.length() > 1 && firstName.length() <= 40;
		Boolean b3 = lastName.length() > 1 && lastName.length() <= 40;

		view.getCurrentView().hideExceptions();
		if (!b1) {
			if (userName.length() < 3)
				view.getCurrentView().printExceptions("Túl rövid felhasználói név (Min 3 karakter)!", 0);
			else
				view.getCurrentView().printExceptions("Túl hosszú felhasználói név (Max 25 karakter)!", 0);
		}
		if (!p1) {
			if (pass.length() < 4)
				view.getCurrentView().printExceptions("Túl rövid jelszó (Min 4 karakter)!", 1);
			else
				view.getCurrentView().printExceptions("Túl hosszú jelszó (Max 20 karakter)!", 1);
		}
		if (!p2) {
			view.getCurrentView().printExceptions("A megadott jelszavak nem egyeznek!", 2);
		}
		if (!b2) {
			if (lastName.length() < 2)
				view.getCurrentView().printExceptions("Túl rövid a megadott név (Min 2 karakter)!", 3);
			else
				view.getCurrentView().printExceptions("A név maximum 40 karakter lehet!", 3);
		}
		if (!b3) {
			if (firstName.length() < 2)
				view.getCurrentView().printExceptions("Túl rövid név (Min 2 karakter)!", 4);
			else
				view.getCurrentView().printExceptions("A név maximum 40 karakter lehet!", 4);
		}

		if (b1 && p1 && p2 && b2 && b3) {
			if(model.searchForExistingUser(userName)) {
				view.getCurrentView().printExceptions("Már létezik ilyen nevû felhasználó!", 0);
			}else {
				if(loginForm.isLoginAsDressing()) {
					model.createNewUser(userName, pass, firstName, lastName, "DRESSING");
				}else {
					model.createNewUser(userName, pass, firstName, lastName, "REGISTER");
				}
				
				((LoginForm) view.getCurrentView()).setRegisterView();
				((LoginForm) view.getCurrentView()).getLblForgetPassLabel().setText("Sikeres regisztráció!");
			}			
		}
	}

	private void loginProcess() {
		String userName = ((HintTextField) loginForm.getUserNameTextField()).getRealText();
		String pass = ((HintPasswordField) loginForm.getPasswordField()).getRealText();

		if (userName != null && pass != null) {
			if (userName.length() > 25 || pass.length() > 30) {
				if (userName.length() > 25) {
					view.getCurrentView().printExceptions("Túl hosszú felhasználói név!", 0);
				} else {
					view.getCurrentView().printExceptions("Túl hosszú jelszó!", 1);
				}
			} else {
				int loginCheck = model.authenticateUser(userName, pass);
				
				
				if(loginCheck == 0 || loginCheck == 1) {
					if (loginCheck == 0) {
						view.getCurrentView().printExceptions("Nincs ilyen felhasználó!", 0);
					}
					if (loginCheck == 1) {
						view.getCurrentView().printExceptions("Hibás jelszó! Kérem próbálja újra!", 1);
					}
				}else {
					if(loginForm.isLoginAsRegister()) {
						if(loginCheck == 10 || loginCheck == 111) {
							view.startViewThread("ActivationForm");
							setActivationPanelController(new ActivationFormController(view, model, this));
							model.createLoggedInUserActivity("REGISTER");
						}else {
							view.getCurrentView().printExceptions("Nem megfelelõ jogosultságú felhasználó!", 0);
							//model.setCurrentUser(null);			
							model.clearCurrentUser();
						}
					}
					
					if(loginForm.isLoginAsDressing()) {
						if(loginCheck == 11 || loginCheck == 111) {
							view.startViewThread("DressingForm");
							setReceiverFormController(new DressingFormController(view, model, this));
							model.createLoggedInUserActivity("DRESSING");
						}else {
							view.getCurrentView().printExceptions("Nem megfelelõ jogosultságú felhasználó!", 0);
							model.clearCurrentUser();	
						}
					}
					
					if(loginForm.isLoginAsAdmin()) {
						if(loginCheck == 111) {					
							view.startViewThread("AdminForm");
							adminFormController = new AdminFormController(view, model, this);
							model.createLoggedInUserActivity("ADMIN");
						}else {
							view.getCurrentView().printExceptions("A felhasználónak nincs Admin jogosultsága!", 0);
							model.clearCurrentUser();			
						}
					}
				}
			}
		}
	}

	/*
	private void exitProcess() {
		model.persistLoggedInUserActivity();
	}
	*/

	public ActivationFormController getActivationPanelController() {
		return activationPanelController;
	}

	public void setActivationPanelController(ActivationFormController controller) {
		this.activationPanelController = controller;
	}

	public DressingFormController getReceiverFormController() {
		return receiverFormController;
	}

	public void setReceiverFormController(DressingFormController controller) {
		this.receiverFormController = controller;
	}

	public AdminFormController getAdminFormController() {
		return adminFormController;
	}

	public void setAdminFormController(AdminFormController controller) {
		this.adminFormController = controller;
	}	
}