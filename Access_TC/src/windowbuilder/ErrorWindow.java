package windowbuilder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ErrorWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	
	public ErrorWindow(String msg) {//we receive the message to display
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 185);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane errorField = new JTextPane();
		errorField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		errorField.setOpaque(false);
		errorField.setEditable(false);
		errorField.setBounds(116, 43, 215, 57);
		contentPane.add(errorField);
		errorField.setText(msg);
		
		JLabel ErrorIcon = new JLabel("");
		ErrorIcon.setIcon(new ImageIcon(ErrorWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/Error.gif")));
		ErrorIcon.setBounds(63, 43, 38, 34);
		contentPane.add(ErrorIcon);
	}
}
