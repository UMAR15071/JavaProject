package ie.itcarlow.CustomerEnvoice;

import java.sql.*;
import java.util.regex.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;

public class EmployeeRegistration {
	
	private String[] gender= {"","male","female","prefer not to say"};
	private String url; 
    private String username;
    private String password;
    private String query;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    


	private JFrame frame;
	private JTextField nameField;
	private JTextField dobField;
	private JTextField emailField;
	private JTextField passwordField;
	private JTextField idField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeRegistration window = new EmployeeRegistration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeRegistration() {
		initialize();
		connection(); 
		viewTable();
	}
	
	public void connection() {
		
		try {
	        String url = "jdbc:mysql://localhost/project";
	        String username = "root";
	        String password = "";

	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection(url, username, password);
	        System.out.println("connection successful");

		}
		catch(SQLException e){
			
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void viewTable() {
	    try {
	        pst = con.prepareStatement("SELECT `Employee_id`, `Emplyee_Name`, `Date_of_Birth`, `Email`, `Gender` FROM `employees` WHERE `DeletedEmployee`=0");
	        rs = pst.executeQuery();

	        // Get the number of columns in the result set
	        ResultSetMetaData metaData = rs.getMetaData();//meta data gives all the additional information to a java program.
	        int numColumns = metaData.getColumnCount();//this number of columns is also retrieved with the help of meta data.

	        // Create a DefaultTableModel with the column names
	        String[] columnNames = new String[numColumns];
	        for (int i = 0; i < numColumns; i++) {
	            columnNames[i] = metaData.getColumnName(i + 1);
	        }
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

	        // Add the rows to the table model
	        while (rs.next()) {
	            String[] rowData = new String[numColumns];//taking data as String
	            for (int i = 0; i < numColumns; i++) {
	                rowData[i] = rs.getString(i + 1);
	            }
	            tableModel.addRow(rowData);//add row method adds the row in the table and place the data in that row
	        }

	        // Set the table model
	        table.setModel(tableModel);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 16));
		frame.setBounds(100, 100, 1056, 594);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Register/Update/View Employee");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
		lblNewLabel.setBounds(341, 51, 342, 32);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Enter Details", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 118, 455, 328);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Customer Name");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 80, 137, 25);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Date of Birth");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(25, 120, 88, 25);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("E-mail Adress");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(25, 160, 88, 25);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Password");
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_2_1.setBounds(25, 200, 88, 25);
		panel.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("gender");
		lblNewLabel_1_2_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_2_2.setBounds(25, 240, 115, 25);
		panel.add(lblNewLabel_1_2_2);
		
		nameField = new JTextField();
		nameField.setBounds(229, 80, 202, 19);
		panel.add(nameField);
		nameField.setColumns(10);
		
		dobField = new JTextField();
		dobField.setColumns(10);
		dobField.setBounds(229, 120, 202, 19);
		panel.add(dobField);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(229, 160, 202, 19);
		panel.add(emailField);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(229, 200, 202, 19);
		panel.add(passwordField);
		
		final JComboBox comboBox = new JComboBox(gender);
		comboBox.setEditable(true);
		comboBox.setBounds(229, 243, 202, 21);
		panel.add(comboBox);

		
		JButton submitButton = new JButton("Add");
		submitButton.setForeground(Color.BLACK);
		submitButton.setBackground(Color.GREEN);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name,password,selecteditem;
				String email="";
				String dateOfBirth="";
				
				name = nameField.getText();
				if(!Pattern.matches("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", dobField.getText())) {
					
					JOptionPane.showMessageDialog(null,"please match the pattern for date (YYYY-MM-DD)");
					dobField.setText("");
					dobField.requestFocus();
					
				}
				else {
					dateOfBirth = dobField.getText();
				}
				
		        if(!(Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z]+[.]{1}+[a-z]+$", emailField.getText()))) {
		        	
		        	JOptionPane.showMessageDialog(null, "invalid E-mail");
		        	emailField.setText("");
		        	emailField.requestFocus();
		        	
		        }
		        else {
					email = emailField.getText();
		        }
		        
				password = passwordField.getText();
				selecteditem = (String) comboBox.getSelectedItem();

				
				if(name=="" || dateOfBirth=="" || password=="" || selecteditem=="" || email=="") {
					
					JOptionPane.showMessageDialog(null, "Please fill all the fields");
					
				}
				else {
						try {
								
								query = "INSERT INTO `employees`(`Emplyee_Name`, `Date_of_Birth`, `Email`, `Password`, `Gender`) values (?,?,?,?,?)";
						        PreparedStatement pst = con.prepareStatement(query);
		
						        pst.setString(1, name);
						        pst.setString(2, dateOfBirth);
						        pst.setString(3, email);
						        
						        pst.setString(4, password);
						        pst.setString(5, selecteditem);
		
						        int rowsInserted = pst.executeUpdate();
		
						        System.out.println(rowsInserted + " row(s) inserted.");
							
								JOptionPane.showMessageDialog(null, "Record has been added to customer table"); 
								viewTable();
								nameField.setText("");
								dobField.setText("");
								emailField.setText("");
								passwordField.setText("");
								comboBox.setSelectedIndex(0);
								
								nameField.requestFocus();
								
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.print("error occured ");
							e1.printStackTrace();
						}
						
					}

				}
				
		});
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		submitButton.setBounds(229, 275, 85, 21);
		panel.add(submitButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setBackground(Color.CYAN);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nameField.setText("");
				dobField.setText("");
				emailField.setText("");
				passwordField.setText("");
				comboBox.setSelectedItem("");
				idField.setText("");
				idField.requestFocus();
				
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		clearButton.setBounds(331, 275, 85, 21);
		panel.add(clearButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 456, 455, 84);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("Customer ID");
		lblNewLabel_1_3.setBounds(44, 32, 147, 17);
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel_1.add(lblNewLabel_1_3);
		
		idField = new JTextField();
		idField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				
				String id = idField.getText();
				try {
					pst = con.prepareStatement("SELECT `Emplyee_Name`, `Date_of_Birth`, `Email`, `Password`, `Gender` FROM `employees` WHERE `DeletedEmployee`=0 AND `Employee_id`=?");
					pst.setString(1, id);
					rs = pst.executeQuery();
					
					if(rs.next()== true) {
						
						String name = rs.getString(1);
						String dob = rs.getString(2);
						String email = rs.getString(3);
						String password = rs.getString(4);
						String select = rs.getString(5);
						
						nameField.setText(name);
						dobField.setText(dob);
						emailField.setText(email);
						passwordField.setText(password);
						comboBox.setSelectedItem(select);
						
						
					}
					else {
						
						nameField.setText("");
						dobField.setText("");
						emailField.setText("");
						passwordField.setText("");
						comboBox.setSelectedIndex(0);
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		idField.setBounds(235, 32, 96, 19);
		idField.setColumns(10);
		panel_1.add(idField);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBackground(Color.ORANGE);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
					String firstName,dateOfBirth,password,selectedItem;
					
					String email="";
					
					firstName = nameField.getText();
					dateOfBirth = "";
			        if(!(Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z]+[.]{1}+[a-z]+$", emailField.getText()))) {
			        	
			        	JOptionPane.showMessageDialog(null, "invalid E-mail");
			        	emailField.setText("");
			        	emailField.requestFocus();
			        	
			        }
			        else {
						email = emailField.getText();
			        }
	
					password = passwordField.getText();
					selectedItem = (String) comboBox.getSelectedItem();
					String id = idField.getText();
					
					if(!Pattern.matches("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$"
							+ ""
							+ "", dobField.getText())) {
						
						JOptionPane.showMessageDialog(null,"please match the pattern for date (YYYY-MM-DD)");
						dobField.setText("");
						dobField.requestFocus();
						
					}
					else {
						dateOfBirth = dobField.getText();
					}

					
					if(firstName=="" || dateOfBirth=="" || password=="" || selectedItem=="" || email=="") {
						
						JOptionPane.showMessageDialog(null, "Please fill all the fields");
						
					}
					else {
						
						try {
								
								query = "UPDATE `employees` SET `Emplyee_Name`=?,`Date_of_Birth`=?,`Email`=?,`Password`=?,`Gender`=? WHERE `Employee_id`=?";
						        PreparedStatement pst = con.prepareStatement(query);
	
						        pst.setString(1, firstName);
						        pst.setString(2, dateOfBirth);
						        pst.setString(3, email);
						        pst.setString(4, password);
						        pst.setString(5, selectedItem);
						        pst.setString(6, id);
	
						        int rowsInserted = pst.executeUpdate();
	
						        System.out.println(rowsInserted + " row(s) inserted.");
							
								JOptionPane.showMessageDialog(null, "Record has been updated"); 
								viewTable();
								nameField.setText("");
								dobField.setText("");
								emailField.setText("");
								passwordField.setText("");
								comboBox.setSelectedIndex(0);
								
								nameField.requestFocus();
								
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.print("error occured ");
							e1.printStackTrace();
						}
					}			
				}	
		});
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		updateButton.setBounds(530, 487, 99, 32);
		frame.getContentPane().add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBackground(Color.RED);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String firstName,dateOfBirth,email,password,selectedItem;
				
				firstName = nameField.getText();
				dateOfBirth = emailField.getText();
				email = emailField.getText();
		        
				
				password = passwordField.getText();
				selectedItem = (String) comboBox.getSelectedItem();
				String id = idField.getText();
				
						try {
								
								query = "UPDATE `employees` SET `DeletedEmployee`='1' WHERE `Employee_id`=?";
						        PreparedStatement pst = con.prepareStatement(query);
		
						        
						        pst.setString(1, id);
		
						        int rowsInserted = pst.executeUpdate();
		
						        System.out.println(rowsInserted + " row(s) inserted.");
							
								JOptionPane.showMessageDialog(null, "Record has been deleted"); 
								viewTable();
								nameField.setText("");
								dobField.setText("");
								emailField.setText("");
								passwordField.setText("");
								comboBox.setSelectedIndex(0);
								
								nameField.requestFocus();
								
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.print("error occured ");
							e1.printStackTrace();
						}
					
				}
				
		});
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		deleteButton.setBounds(655, 487, 99, 32);
		frame.getContentPane().add(deleteButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(475, 118, 557, 328);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
