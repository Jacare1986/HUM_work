package windowbuilder;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JTextField;

import humtmtc.*;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;

public class GUI {

	private JFrame frmTclistCreator;
	private JTextField M1Field;
	private JTextField M2Field;
	private JComboBox comboBox; //We need to make comboBox global in order to get its field anytime.
	private JTextField accesTimesField;
	private JTextField lightningField;
	private JFileChooser chooser = new JFileChooser();
	String AccesTimesPath;
	String LightningPath;
	String TCList_path;
	String Output_file_name;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmTclistCreator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTclistCreator = new JFrame();
		frmTclistCreator.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/javax/swing/plaf/basic/icons/JavaCup16.png")));
		frmTclistCreator.setTitle("TC_List Creator");
		frmTclistCreator.setBounds(100, 100, 450, 300);
		frmTclistCreator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.setBounds(179, 212, 86, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//When we press generate, we create the TCList				
				try {
					//1st we ask where do you want to save .ser file.
					JFileChooser chooser1 = new JFileChooser();
					chooser1.setDialogTitle("Save File");
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".ser", "ser");
					chooser1.setFileFilter(filter);
					if(chooser1.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						//Get directory path
						TCList_path=(chooser1.getCurrentDirectory().getPath())+"\\";
						Output_file_name=(chooser1.getSelectedFile().getName())+".ser";
						
						System.out.println(TCList_path);
						System.out.println(Output_file_name);
				    }				
					//We obtain the parameters selected in the window
					int M1 = Integer.parseInt(M1Field.getText());
					int M2 = Integer.parseInt(M2Field.getText());				
					int pass_case = Integer.parseInt((comboBox.getSelectedItem()).toString());//getSelectedItem returns an Object
					
					System.out.println("Pass Type to program: "+pass_case);
					
					//We read the Paths 
					AccesTimesPath=accesTimesField.getText();
					LightningPath=lightningField.getText();
					
					HUMDOpsTools.TCListCreator(M1,M2,pass_case,AccesTimesPath,LightningPath,TCList_path, Output_file_name);
					
				} catch (ClassNotFoundException | ParseException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		frmTclistCreator.getContentPane().setLayout(null);
		frmTclistCreator.getContentPane().add(btnNewButton);
		
	/*	JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Titulo");
		//Elegiremos archivos del directorio
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("Directorio: " + chooser.getCurrentDirectory());
			//Si no seleccionamos nada retornaremos No seleccion
			} else {
			System.out.println("No seleccion ");
			}
		*/
	    
		JLabel lblM = new JLabel("M1");
		lblM.setBounds(10, 38, 46, 14);
		frmTclistCreator.getContentPane().add(lblM);
		
		JLabel lblM_1 = new JLabel("M2");
		lblM_1.setBounds(10, 63, 46, 14);
		frmTclistCreator.getContentPane().add(lblM_1);
		
		M1Field = new JTextField();
		M1Field.setHorizontalAlignment(SwingConstants.TRAILING);
		M1Field.setText("0");
		M1Field.setBounds(42, 35, 86, 20);
		frmTclistCreator.getContentPane().add(M1Field);
		M1Field.setColumns(10);
		
		M2Field = new JTextField();
		M2Field.setHorizontalAlignment(SwingConstants.TRAILING);
		M2Field.setText("0");
		M2Field.setBounds(42, 63, 86, 20);
		frmTclistCreator.getContentPane().add(M2Field);
		M2Field.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("(sec)");
		lblNewLabel.setBounds(138, 38, 46, 14);
		frmTclistCreator.getContentPane().add(lblNewLabel);
		
		JLabel lblsec = new JLabel("(sec)");
		lblsec.setBounds(138, 63, 46, 14);
		frmTclistCreator.getContentPane().add(lblsec);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 21);
		frmTclistCreator.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JLabel lblSelectCaseType = new JLabel("Select Case Type");
		lblSelectCaseType.setBounds(194, 38, 104, 14);
		frmTclistCreator.getContentPane().add(lblSelectCaseType);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		comboBox.setBounds(308, 37, 96, 17);
		frmTclistCreator.getContentPane().add(comboBox);
		
		accesTimesField = new JTextField();
		accesTimesField.setBounds(300, 103, 86, 20);
		frmTclistCreator.getContentPane().add(accesTimesField);
		accesTimesField.setColumns(10);
		
		lightningField = new JTextField();
		lightningField.setBounds(300, 154, 86, 20);
		frmTclistCreator.getContentPane().add(lightningField);
		lightningField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Open");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
				    chooser.setFileFilter(filter);
				    if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				    	AccesTimesPath=chooser.getSelectedFile().getPath();
				    	accesTimesField.setText(AccesTimesPath);
				    }
			}
		});
		btnNewButton_1.setBounds(194, 102, 89, 23);
		frmTclistCreator.getContentPane().add(btnNewButton_1);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
				    chooser.setFileFilter(filter);
				    if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				    	LightningPath=chooser.getSelectedFile().getPath();
				    	lightningField.setText(LightningPath);
				    }
			}
		});
		btnOpen.setBounds(194, 153, 89, 23);
		frmTclistCreator.getContentPane().add(btnOpen);
		
		JLabel lblAccesTimeFile = new JLabel("Acces Time File");
		lblAccesTimeFile.setBounds(194, 87, 89, 14);
		frmTclistCreator.getContentPane().add(lblAccesTimeFile);
		
		JLabel lblLightningTimeFile = new JLabel("Lightning Time File");
		lblLightningTimeFile.setBounds(194, 136, 89, 14);
		frmTclistCreator.getContentPane().add(lblLightningTimeFile);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setBounds(300, 87, 46, 14);
		frmTclistCreator.getContentPane().add(lblPath);
		
		JLabel lblPath_1 = new JLabel("Path");
		lblPath_1.setBounds(300, 136, 46, 14);
		frmTclistCreator.getContentPane().add(lblPath_1);
		
		
	}
}
