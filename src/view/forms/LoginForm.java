package view.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import view.HintPasswordField;
import view.HintTextField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginForm extends MainJFrameForm implements Runnable {
	// private Controller controller;
	private JPanel contentPane;
	private JPanel whitePanel;
	private HintTextField userNameTextField;
	private HintTextField firstNameTextField;
	private HintTextField lastNameTextField;
	private HintPasswordField passwordField;
	private HintPasswordField repeatPasswordField;

	private JButton btnLoginButton;

	private JLabel lblLoginLabel;
	private JLabel lblForgetPassLabel;
	private JLabel lblForgetPassLabel2;
	private JLabel userIconLabel;
	private JLabel scannerIconLabel;
	private JLabel adminIconLabel;
	private JLabel lblRegisterLabel;
	private JLabel lblHintLabel;
	private JLabel lblUserType;
	private Timer textDeleteTimer;
	private JLabel availableDatabaseIcon;
	private JLabel newIPAddressLabel;
	private JTextField newIPAddressTextField;
	private JButton newIPAddressButton;

	private List<JLabel> exceptionLabels;

	private boolean wantToRegister;
	private boolean loginAsRegister;
	private boolean loginAsDressing;
	private boolean loginAsAdmin;

	//private Color TRANSPARENT_RED = new Color(235, 48, 20, 225);
	//private Color TRANSPARENT_ORANGE = new Color(251, 182, 0, 225);
	private Color TRANSPARENT_GRASSGREEN = new Color(150, 169, 56, 225);
	private Color GRAPHITE = new Color(105, 105, 105);
	
	Font normalFont = new Font("SansSerif", Font.PLAIN, 18);
	Font activeFont = new Font("SansSerif", Font.PLAIN, 20);

	@Override
	public void run() {
		setVisible(true);
	}

	// Create the frame.
	@SuppressWarnings("unchecked")
	public LoginForm() {
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("gree_heron_logo.png")).getImage());		

		loginAsRegister = true;
		wantToRegister = loginAsDressing = loginAsAdmin = false;

		setSize(900, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(250, 250, 250, 0));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 250, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 148, 6));
		contentPane.add(panel);

		whitePanel = new JPanel();
		whitePanel.setBackground(new Color(245, 245, 245));
		whitePanel.setBounds(96, 11, 404, 778);
		whitePanel.setBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		whitePanel.addMouseListener(mouseHandler);
		whitePanel.addMouseMotionListener(mouseHandler);
		contentPane.add(whitePanel);
		whitePanel.setLayout(null);

		Border grayBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
		userNameTextField = new HintTextField("  Név");
		userNameTextField.setBounds(51, 155, 300, 70);
		userNameTextField.setBorder(grayBorder);
		whitePanel.add(userNameTextField);

		passwordField = new HintPasswordField("  Jelszó");
		passwordField.setColumns(10);
		passwordField.setBounds(51, 255, 300, 70);
		passwordField.setBorder(grayBorder);
		whitePanel.add(passwordField);

		lblLoginLabel = new JLabel("BELÉPÉS");
		lblLoginLabel.setForeground(GRAPHITE);
		lblLoginLabel.setFont(new Font("SansSerif", Font.BOLD, 39));
		lblLoginLabel.setBounds(51, 64, 294, 49);
		whitePanel.add(lblLoginLabel);

		lblForgetPassLabel = new JLabel("Elfelejtetted a jelszavad?");
		lblForgetPassLabel.setForeground(new Color(85, 107, 47));
		lblForgetPassLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblForgetPassLabel.setBounds(51, 437, 165, 21);
		lblForgetPassLabel.addMouseListener(loginFormMouseListener);
		whitePanel.add(lblForgetPassLabel);
		
		lblForgetPassLabel2 = new JLabel("");
		lblForgetPassLabel2.setForeground(Color.DARK_GRAY);
		lblForgetPassLabel2.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblForgetPassLabel2.setBounds(51, 465, 320, 40);
		lblForgetPassLabel2.addMouseListener(loginFormMouseListener);
		whitePanel.add(lblForgetPassLabel2);
		
		userIconLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("user_active.png")));
		userIconLabel.setBounds(255, 14, 40, 40);
		userIconLabel.addMouseListener(loginFormMouseListener);
		whitePanel.add(userIconLabel);

		scannerIconLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("scanner.png")));
		scannerIconLabel.setBounds(305, 17, 40, 40);
		scannerIconLabel.addMouseListener(loginFormMouseListener);
		whitePanel.add(scannerIconLabel);
		
		adminIconLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("admin.png")));
		adminIconLabel.setBounds(355, 16, 40, 40);
		adminIconLabel.addMouseListener(loginFormMouseListener);
		whitePanel.add(adminIconLabel);

		JPanel shadowPanel = new JPanel();
		shadowPanel.setBounds(91, 6, 414, 788);
		shadowPanel.setBackground(new Color(60, 60, 60, 150));
		contentPane.add(shadowPanel);
		JPanel shadowPanel2 = new JPanel();
		shadowPanel2.setBounds(88, 3, 420, 794);
		shadowPanel2.setBackground(new Color(75, 75, 75, 100));
		contentPane.add(shadowPanel2);
		JPanel shadowPanel_3 = new JPanel();
		shadowPanel_3.setBounds(85, 0, 426, 800);
		shadowPanel_3.setBackground(new Color(75, 75, 75, 50));
		contentPane.add(shadowPanel_3);

		btnLoginButton = new JButton("BELÉPÉS");
		btnLoginButton.setName("LOGIN");
		btnLoginButton.setForeground(SystemColor.control);
		btnLoginButton.setBackground(new Color(95, 95, 95));
		btnLoginButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnLoginButton.setBounds(114, 355, 237, 60);
		btnLoginButton.setFocusPainted(false);
		whitePanel.add(btnLoginButton);

		repeatPasswordField = new HintPasswordField("  Jelszó ismétlés");
		repeatPasswordField.setBounds(51, 355, 300, 70);
		whitePanel.add(repeatPasswordField);
		lastNameTextField = new HintTextField("  Vezetéknév");
		lastNameTextField.setBounds(51, 455, 300, 70);
		whitePanel.add(lastNameTextField);
		firstNameTextField = new HintTextField("  Keresztnév");
		firstNameTextField.setBounds(51, 555, 300, 70);
		whitePanel.add(firstNameTextField);

		lblHintLabel = new JLabel("");
		lblHintLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblHintLabel.setForeground(GRAPHITE);
		lblHintLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblHintLabel.setBounds(249, 59, 46, 21);
		whitePanel.add(lblHintLabel);

		lblUserType = new JLabel("kasszásként");
		lblUserType.setForeground(SystemColor.controlDkShadow);
		lblUserType.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblUserType.setBounds(52, 109, 288, 20);
		whitePanel.add(lblUserType);

		// Exception Labels
		exceptionLabels = new ArrayList<JLabel>();
		JLabel lblExceptionName = new JLabel("Error message");
		lblExceptionName.setBounds(52, 133, 300, 21);
		exceptionLabels.add(lblExceptionName);
		JLabel lblExceptionPass = new JLabel("Error password");
		lblExceptionPass.setBounds(52, 233, 265, 21);
		exceptionLabels.add(lblExceptionPass);
		JLabel lblExceptionRepeatPass = new JLabel("Error repeat password");
		lblExceptionRepeatPass.setBounds(52, 333, 265, 21);
		exceptionLabels.add(lblExceptionRepeatPass);
		JLabel lblExceptionFirstName = new JLabel("Error fist name");
		lblExceptionFirstName.setBounds(52, 433, 265, 21);
		exceptionLabels.add(lblExceptionFirstName);
		JLabel lblExceptionLastName = new JLabel("Error last name");
		lblExceptionLastName.setBounds(52, 533, 265, 21);
		exceptionLabels.add(lblExceptionLastName);
		hideExceptions();

		lblHintLabel.setVisible(false);
		repeatPasswordField.setVisible(false);
		firstNameTextField.setVisible(false);
		lastNameTextField.setVisible(false);

		JPanel redPanel = new JPanel();		
		redPanel.setBackground(TRANSPARENT_GRASSGREEN);
		redPanel.setBounds(10, 100, 890, 600);
		contentPane.add(redPanel);
		redPanel.setLayout(null);

		JLabel lblExitLabel = new JLabel("X");
		lblExitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblExitLabel.setForeground(Color.WHITE);
		lblExitLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		lblExitLabel.setBounds(860, 6, 25, 27);
		lblExitLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblExitLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblExitLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
			}
			@Override
			public void mouseClicked(MouseEvent e) {			}
		});
		redPanel.add(lblExitLabel);

		JLabel lblNewLabel_2 = new JLabel("...");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setForeground(SystemColor.control);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 38));
		lblNewLabel_2.setBounds(652, 90, 228, 57);
		redPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_2_2 = new JLabel("Kalandpark");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_2.setForeground(SystemColor.menu);
		lblNewLabel_2_2.setFont(new Font("SansSerif", Font.PLAIN, 38));
		lblNewLabel_2_2.setBounds(652, 143, 228, 57);
		redPanel.add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_2_1 = new JLabel("Jegykezelõ");
		lblNewLabel_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_2_1.setForeground(SystemColor.menu);
		lblNewLabel_2_2_1.setFont(new Font("SansSerif", Font.PLAIN, 38));
		lblNewLabel_2_2_1.setBounds(652, 211, 228, 57);
		redPanel.add(lblNewLabel_2_2_1);

		JLabel lblNewLabel_2_2_1_1 = new JLabel("Program");
		lblNewLabel_2_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_2_1_1.setForeground(SystemColor.menu);
		lblNewLabel_2_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 38));
		lblNewLabel_2_2_1_1.setBounds(652, 264, 228, 57);
		redPanel.add(lblNewLabel_2_2_1_1);

		JButton btnNewButton_1 = new JButton("...");
		btnNewButton_1.setName("...");
		btnNewButton_1.setForeground(SystemColor.control);
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnNewButton_1.setBounds(842, 572, 38, 17);
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setFocusPainted(false);
		redPanel.add(btnNewButton_1);
		
		availableDatabaseIcon = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("accept_database.png")));
		availableDatabaseIcon.setBounds(2, 735, 40, 40);
		availableDatabaseIcon.setToolTipText("Adatbázis kapcsolat megfelelõ!");
		//acceptDatabaseIcon.addMouseListener(loginFormMouseListener);
		whitePanel.add(availableDatabaseIcon);
		
		newIPAddressLabel = new JLabel("IP: ");
		newIPAddressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		newIPAddressLabel.setForeground(GRAPHITE);
		newIPAddressLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		newIPAddressLabel.setBounds(50, 740, 30, 25);
		newIPAddressLabel.setVisible(false);
		whitePanel.add(newIPAddressLabel);
		
		newIPAddressTextField = new JTextField("192.168.1.113");
		newIPAddressTextField.setHorizontalAlignment(SwingConstants.CENTER);
		newIPAddressTextField.setBounds(90, 740, 100, 25);
		newIPAddressTextField.setForeground(GRAPHITE);
		newIPAddressTextField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		newIPAddressTextField.setBorder(grayBorder);
		newIPAddressTextField.setToolTipText("Adja meg a szerver jelenlegi ip címét!");
		newIPAddressTextField.setVisible(false);
		whitePanel.add(newIPAddressTextField);
		newIPAddressTextField.setColumns(10);
		
		newIPAddressButton = new JButton("OK");
		newIPAddressButton.setForeground(SystemColor.control);
		newIPAddressButton.setFont(new Font("SansSerif", Font.BOLD, 13));		
		newIPAddressButton.setBackground(new Color(95, 95, 95));
		newIPAddressButton.setBounds(200, 740, 55, 25);
		newIPAddressButton.setVisible(false);
		whitePanel.add(newIPAddressButton);
		
		@SuppressWarnings("rawtypes")
		Map attributes = activeFont.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblRegisterLabel = new JLabel("Regisztrálás");
		lblRegisterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegisterLabel.setForeground(new Color(255, 255, 255));
		lblRegisterLabel.setFont(normalFont);
		lblRegisterLabel.setBounds(723, 422, 128, 27);
		lblRegisterLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {			}
			@Override
			public void mousePressed(MouseEvent e) {			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRegisterLabel.setFont(normalFont);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblRegisterLabel.setFont(activeFont.deriveFont(attributes));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// controller.registerLabelClicked();
				clearTextFields();
				hideExceptions();
				setRegisterView();
			}
		});

		redPanel.add(lblRegisterLabel);
	}

	public void setRegisterView() {
		loginAsRegister = true;
		loginAsDressing = loginAsAdmin = false;
		userIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("user_active.png")));				
		scannerIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("scanner.png")));
		adminIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("admin.png")));		
		
		if (!wantToRegister) {			
			//userIconLabel.setVisible(false);
			//scannerIconLabel.setVisible(false);
			adminIconLabel.setVisible(false);

			repeatPasswordField.setVisible(true);
			firstNameTextField.setVisible(true);
			lastNameTextField.setVisible(true);

			lblForgetPassLabel.setVisible(false);
			lblLoginLabel.setText("REGISZTRÁCIÓ");
			btnLoginButton.setText("Regisztrálás");
			btnLoginButton.setName("SIGN UP");
			btnLoginButton.setBounds(115, 671, 237, 60);
			lblRegisterLabel.setText("Bejelentkezés");
			lblUserType.setText("új kasszás felhasználó");
		} else {			
			//userIconLabel.setVisible(true);
			//scannerIconLabel.setVisible(true);
			adminIconLabel.setVisible(true);

			repeatPasswordField.setVisible(false);
			firstNameTextField.setVisible(false);
			lastNameTextField.setVisible(false);

			lblForgetPassLabel.setVisible(true);
			lblLoginLabel.setText("BELÉPÉS");
			btnLoginButton.setText("Belépés");
			btnLoginButton.setName("LOGIN");
			btnLoginButton.setBounds(115, 360, 237, 60);
			lblRegisterLabel.setText("Regisztrálás");
			lblUserType.setText("öltöztetõként");
		}

		wantToRegister = !wantToRegister;
	}

	@Override
	public void printExceptions(String exception, int lblNumber) {
		if (lblNumber >= 0 && lblNumber < exceptionLabels.size()) {
			JLabel currentJLabel = exceptionLabels.get(lblNumber);
			currentJLabel.setText(exception);
			currentJLabel.setVisible(true);
		}
	}

	@Override
	public void hideExceptions() {
		if (exceptionLabels.size() != 0) {
			for (JLabel exceptionLabel : exceptionLabels) {
				exceptionLabel.setForeground(new Color(139, 0, 0));
				exceptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				exceptionLabel.setVisible(false);
				whitePanel.add(exceptionLabel);
			}
		}
	}

	private void clearTextFields() {
		userNameTextField.setText("");
		passwordField.setToDefault();
		repeatPasswordField.setToDefault();
		firstNameTextField.setToDefault();
		lastNameTextField.setToDefault();
		userNameTextField.requestFocus();
	}

	public boolean isLoginAsRegister() {
		return loginAsRegister;
	}

	public boolean isLoginAsDressing() {
		return loginAsDressing;
	}

	public boolean isLoginAsAdmin() {
		return loginAsAdmin;
	}
	

	public JButton getBtnLoginButton() {
		return btnLoginButton;
	}
	
	public boolean isWantToRegister() {
		return wantToRegister;
	}

	public void setWantToRegister(boolean isNotRegistration) {
		this.wantToRegister = isNotRegistration;
	}

	public HintTextField getUserNameTextField() {
		return userNameTextField;
	}

	public void setUserNameTextField(HintTextField userNameTextField) {
		this.userNameTextField = userNameTextField;
	}

	public HintPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(HintPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public HintTextField getFirstNameTextField() {
		return firstNameTextField;
	}

	public void setFirstNameTextField(HintTextField firstNameTextField) {
		this.firstNameTextField = firstNameTextField;
	}

	public HintTextField getLastNameTextField() {
		return lastNameTextField;
	}

	public void setLastNameTextField(HintTextField lastNameTextField) {
		this.lastNameTextField = lastNameTextField;
	}

	public HintPasswordField getRepeatPasswordField() {
		return repeatPasswordField;
	}

	public void setRepeatPasswordField(HintPasswordField repeatPasswordField) {
		this.repeatPasswordField = repeatPasswordField;
	}

	public JLabel getLblForgetPassLabel() {
		return lblForgetPassLabel;
	}

	public void setLblForgetPassLabel(JLabel lblForgetPassLabel) {
		this.lblForgetPassLabel = lblForgetPassLabel;
	}

	public JLabel getLblRegisterLabel() {
		return lblRegisterLabel;
	}

	public void setLblRegisterLabel(JLabel lblRegisterLabel) {
		this.lblRegisterLabel = lblRegisterLabel;
	}
	
	public JLabel getAvailableDatabaseIcon() {
		return availableDatabaseIcon;
	}

	public JLabel getNewIPAddressLabel() {
		return newIPAddressLabel;
	}

	public JTextField getNewIPAddressTextField() {
		return newIPAddressTextField;
	}

	public JButton getNewIPAddressButton() {
		return newIPAddressButton;
	}



	MouseListener loginFormMouseListener = new MouseListener() {
		@Override
		public void mouseReleased(MouseEvent e) {		}
		@Override
		public void mousePressed(MouseEvent e) {			}
		@Override
		public void mouseExited(MouseEvent e) {
			lblHintLabel.setVisible(false);
			
			if(e.getSource() == lblForgetPassLabel) {
				lblForgetPassLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if(!wantToRegister) {			
				if (e.getSource() == userIconLabel) {
					lblHintLabel.setText("Register");
					lblHintLabel.setBounds(242, 60, 70, 21);
					lblHintLabel.setVisible(true);
				}
				if (e.getSource() == scannerIconLabel) {
					lblHintLabel.setText("Dressing");
					lblHintLabel.setBounds(290, 60, 75, 21);
					lblHintLabel.setVisible(true);
				}
				if (e.getSource() == adminIconLabel) {
					lblHintLabel.setText("Admin");
					lblHintLabel.setBounds(350, 60, 50, 21);
					lblHintLabel.setVisible(true);
				}
			}
		
			if(e.getSource() == lblForgetPassLabel) {
				lblForgetPassLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
			}			
		}
		@Override
		public void mouseClicked(MouseEvent e) {			
			if(e.getSource() == userIconLabel || e.getSource() == scannerIconLabel || e.getSource() == adminIconLabel) {
				userIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("user.png")));				
				scannerIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("scanner.png")));
				adminIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("admin.png")));
			}
			
			if (e.getSource() == userIconLabel) {
				userIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("user_active.png")));
				if(isWantToRegister())
					lblUserType.setText("új kasszás felhasználó");					
				else
					lblUserType.setText("kasszásként");
				
				loginAsRegister = true;
				loginAsDressing = loginAsAdmin = false;
			}
			if (e.getSource() == scannerIconLabel) {
				scannerIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("scanner_active.png")));
				if(isWantToRegister())
					lblUserType.setText("új öltöztetõ felhasználó");
				else					
					lblUserType.setText("öltöztetõként");
				
				loginAsDressing = true;
				loginAsRegister = loginAsAdmin = false;
			}
			if (e.getSource() == adminIconLabel) {
				adminIconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("admin_active.png")));
				lblUserType.setText("adminisztrátorként");
				loginAsAdmin = true;
				loginAsRegister = loginAsDressing = false;
			}
			
			if(e.getSource() == lblForgetPassLabel) {
				if(lblForgetPassLabel2.getText().length() == 0) {
					lblForgetPassLabel2.setText("<html>Adminisztrátor segítségével meg lehet változtatni az elfelejtett jelszavakat!</html>");
					
					textDeleteTimer = new Timer(20000, new ActionListener() {			
						@Override
						public void actionPerformed(ActionEvent e) {
							lblForgetPassLabel2.setText("");
							textDeleteTimer.stop();
						}
					});		
					textDeleteTimer.start();
				}else {					
					lblForgetPassLabel2.setText("");
				}
			}
		}		
	};

	MouseAdapter mouseHandler = new MouseAdapter() {
		private Point offset;

		protected boolean isWithinBorder(MouseEvent e) {
			Point p = e.getPoint();
			Component comp = whitePanel;
			return !(p.x < comp.getX() || p.y < comp.getY() || p.x > comp.getWidth() + comp.getX()
					|| p.y > comp.getHeight() + comp.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (isWithinBorder(e)) {
				Point pos = getLocationOnScreen();
				offset = new Point(e.getLocationOnScreen());
				offset.x -= pos.x;
				offset.y -= pos.y;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (isWithinBorder(e)) {
				e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
			if (offset != null) {
				Point pos = e.getLocationOnScreen();
				int x = pos.x - offset.x;
				int y = pos.y - offset.y;
				SwingUtilities.getWindowAncestor(e.getComponent()).setLocation(x, y);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			e.getComponent().setCursor(Cursor.getDefaultCursor());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	};	
}
