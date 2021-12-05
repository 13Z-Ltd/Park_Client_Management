package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class HintTextField extends JTextField {

	Font gainFont = new Font("SansSerif", Font.PLAIN, 28);
	Font lostFont = new Font("SansSerif", Font.PLAIN, 26);
	
	private String hint;

	public HintTextField(final String setupHint) {
		this.hint = setupHint;

		setText(hint);
		setFont(lostFont);
		setForeground(Color.LIGHT_GRAY);

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				setForeground(Color.BLACK);
				if (getText().equals(hint)) {
					setText("");
					setFont(gainFont);
				} else {
					setText(getText());
					setFont(gainFont);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().equals(hint) || getText().length() == 0) {
					setText(hint);
					setFont(lostFont);
					setForeground(Color.LIGHT_GRAY);
				} else {
					setText(getText());
					setFont(gainFont);
					setForeground(Color.BLACK);
				}
			}
		});
	}
	
	public void setToDefault() {
		setText(hint);
		setFont(lostFont);
		setForeground(Color.LIGHT_GRAY);
	}
	
	public String getRealText() {
		if (getText().equals(hint))
			return null;
		else 
			return getText();
	}
}