//package windowbuilder;
package org.humsat.demo.opstools.windowbuilder;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JFileChooser;

import java.awt.Toolkit;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon; 

import org.humsat.demo.opstools.humtmtc.HUMDOpsTools;

public class GUI {

	private static JFrame frmTclistCreator;
	private JTextField M1Field;
	private JTextField M2Field;
	private JTextField accesTimesField;
	private JTextField lightningField;
	private JFileChooser chooser = new JFileChooser();
	String AccesTimesPath;
	String LightningPath;
	String TCList_path;
	String Output_file_name;
	private JCheckBox case1checkbox;
	private JCheckBox case2checkbox;
	private JCheckBox case3checkbox;
	private JCheckBox case4checkbox;
	int M1,M2;
	boolean pass_selected= false;

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
	 * @throws ParseException 
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
		frmTclistCreator.setBounds(100, 100, 547, 415);
		frmTclistCreator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton generateButton = new JButton("Generate");
		generateButton.setBounds(238, 342, 86, 23);
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*When we press generate, we create the TCList
				 * 1st we check if we have a case type selected
				 * 2nd Opend a File Chooser to selected where to save our .ser file
				 * 3rd If the name of the file selected already exists, ask for overwriting.
				 */
				
				try {
					//1st: Case selected ==false ?								
					if((case1checkbox.isSelected()==false)&&(case2checkbox.isSelected()==false)&&(case3checkbox.isSelected()==false)&&(case4checkbox.isSelected()==false)){
						showError("Error! You must select a case type.","Error");
					}else{
						//2nd Open filechooser to save our output file.
						JFileChooser chooser1 = new JFileChooser();
						chooser1.setDialogTitle("Save File");
						FileNameExtensionFilter filter = new FileNameExtensionFilter(".ser", "ser");
						chooser1.setFileFilter(filter);
						if(chooser1.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {//we press save
							
							//Get directory path
							TCList_path=(chooser1.getCurrentDirectory().getPath())+File.separator;
							Output_file_name=(chooser1.getSelectedFile().getName());
							
							//Get M1 and M2 parameters
							M1 = Integer.parseInt(M1Field.getText());
							M2 = Integer.parseInt(M2Field.getText());
							
							//Get passes type
							int case_type1=0, case_type2=0,case_type3=0,case_type4=0;
							
							if(case1checkbox.isSelected()==true){
								case_type1=1;
							}if(case2checkbox.isSelected()==true){
								case_type2=2;
							}if(case3checkbox.isSelected()==true){
								case_type3=3;
							}if(case4checkbox.isSelected()==true){
								case_type4=4;
							};
																		
							System.out.println("Pass Type to program: 1 "+case1checkbox.isSelected()+ " 2 "+case2checkbox.isSelected()+" 3 "+case3checkbox.isSelected()+" 4 "+case4checkbox.isSelected());
							
							//Read the paths of the CSV Files 
							AccesTimesPath=accesTimesField.getText();
							LightningPath=lightningField.getText();
							
							//3rd Check if file already exists
							File f = new File(TCList_path+Output_file_name+".ser");
							if(f.isFile()){
								//The file already exists.Show a dialog box asking for overwriting
								Object[] options = {"Yes","No"};
								int n = JOptionPane.showOptionDialog(frmTclistCreator,"File already exists. Do you want to overwrite it?","Warning",
										JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[1]);
								if(n==0){//we have selected options [0] = YES
									HUMDOpsTools.TCListCreator(M1,M2,case_type1,case_type2,case_type3,case_type4,AccesTimesPath,LightningPath,TCList_path, Output_file_name);
								}
							}else{//the file doesn`t exist, so we create a TC_List
								HUMDOpsTools.TCListCreator(M1,M2,case_type1,case_type2,case_type3,case_type4,AccesTimesPath,LightningPath,TCList_path, Output_file_name);						
							}	
						}		
					}
				}catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("Error while executing.");
				}catch (ParseException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					showError("Error! Problem reading .csv files.","Error");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					showError("Error! Please open .csv Files.","Error");
				}catch (NumberFormatException e){
					//Message with error
					showError("Error! Please, type an integer valid margin in seconds.","Error");
				}
			}
		});
		frmTclistCreator.getContentPane().setLayout(null);
		frmTclistCreator.getContentPane().add(generateButton);
	    
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
		
		JLabel lblSelectCaseType = new JLabel("Select Case Type");
		lblSelectCaseType.setBounds(194, 38, 104, 14);
		frmTclistCreator.getContentPane().add(lblSelectCaseType);
		
		accesTimesField = new JTextField();
		accesTimesField.setBounds(391, 238, 86, 20);
		frmTclistCreator.getContentPane().add(accesTimesField);
		accesTimesField.setColumns(10);
		
		lightningField = new JTextField();
		lightningField.setBounds(391, 293, 86, 20);
		frmTclistCreator.getContentPane().add(lightningField);
		lightningField.setColumns(10);
		
		JButton accesButton = new JButton("Open");
		accesButton.setIcon(new ImageIcon(GUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		accesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
				    chooser.setFileFilter(filter);
				    if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				    	AccesTimesPath=chooser.getSelectedFile().getPath();
				    	accesTimesField.setText(AccesTimesPath);
				    }
			}
		});
		accesButton.setBounds(269, 237, 89, 23);
		frmTclistCreator.getContentPane().add(accesButton);
		
		JButton lightningButton = new JButton("Open");
		lightningButton.setIcon(new ImageIcon(GUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		lightningButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
				    chooser.setFileFilter(filter);
				    if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				    	LightningPath=chooser.getSelectedFile().getPath();
				    	lightningField.setText(LightningPath);
				    }
			}
		});
		lightningButton.setBounds(269, 292, 89, 23);
		frmTclistCreator.getContentPane().add(lightningButton);
		
		JLabel lblAccesTimeFile = new JLabel("Acces Time File");
		lblAccesTimeFile.setIcon(null);
		lblAccesTimeFile.setBounds(258, 212, 89, 14);
		frmTclistCreator.getContentPane().add(lblAccesTimeFile);
		
		JLabel lblLightningTimeFile = new JLabel("Lightning Time File");
		lblLightningTimeFile.setBounds(258, 271, 123, 14);
		frmTclistCreator.getContentPane().add(lblLightningTimeFile);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setBounds(391, 212, 46, 14);
		frmTclistCreator.getContentPane().add(lblPath);
		
		JLabel lblPath_1 = new JLabel("Path");
		lblPath_1.setBounds(391, 269, 46, 14);
		frmTclistCreator.getContentPane().add(lblPath_1);
		
		case1checkbox = new JCheckBox("Light");
		case1checkbox.setBounds(300, 34, 97, 23);
		frmTclistCreator.getContentPane().add(case1checkbox);
		
		case2checkbox = new JCheckBox("Eclipse");
		case2checkbox.setBounds(300, 59, 97, 23);
		frmTclistCreator.getContentPane().add(case2checkbox);
		
		case3checkbox = new JCheckBox("Light & Eclipse");
		case3checkbox.setBounds(300, 85, 137, 23);
		frmTclistCreator.getContentPane().add(case3checkbox);
		
		case4checkbox = new JCheckBox("Eclipse & Light");
		case4checkbox.setBounds(300, 111, 137, 23);
		frmTclistCreator.getContentPane().add(case4checkbox);
		
		JLabel imageLabel = new JLabel("New label");
		imageLabel.setBounds(10, 125, 238, 101);
		frmTclistCreator.getContentPane().add(imageLabel);
		
		//Get dimensions of imageLabel
		
		int h=imageLabel.getHeight();
		int w= imageLabel.getWidth();
		
		//ImageIcon image = new ImageIcon("images\\Esquema.jpg");//Load Image from the folder where 
		//ImageIcon image2=new ImageIcon(image.getImage().getScaledInstance(w, h, h));//Resize Image
		//imageLabel.setIcon(image2);
		
		URL imageurl = getClass().getResource("/images/Esquema.jpg");//Obtain url from the images package
		ImageIcon image = new ImageIcon(imageurl);
		ImageIcon image2 = new ImageIcon (image.getImage().getScaledInstance(w, h, h)); //Resize image
		imageLabel.setIcon(image2);
			
	}
	
	public static void showError(String msg, String error_type){
		Object[] obj={"OK"};
		JOptionPane.showOptionDialog(frmTclistCreator,msg,error_type,
				JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE,null,obj,obj[0]);		
	}
	public static void showWarning(String msg, String error_type){
		Object[] obj={"OK"};
		JOptionPane.showOptionDialog(frmTclistCreator,msg,error_type,
				JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,obj,obj[0]);		
	}
}
