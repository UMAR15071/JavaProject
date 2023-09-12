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

public class CustomerRegistration{
	
	private String url; 
    private String username;
    private String password;
    private String query;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    


	private JFrame frame;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField dobField;
	private JTextField passwordField;
	private JTextField password2Field;
	private JTextField idField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerRegistration window = new CustomerRegistration();
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
	public CustomerRegistration() {
		connection(); 
		initialize();
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
	        pst = con.prepareStatement("SELECT `Customer_ID`, `First_Name`, `Last_Name`, `Date_of_Birth` FROM `customer` WHERE `Deleted_Customer`=0");
	        rs = pst.executeQuery();

	        // Get the number of columns in the result set
	        ResultSetMetaData metaData = rs.getMetaData();
	        int numColumns = metaData.getColumnCount();

	        // Create a DefaultTableModel with the column names
	        String[] columnNames = new String[numColumns];
	        for (int i = 0; i < numColumns; i++) {
	            columnNames[i] = metaData.getColumnName(i + 1);
	        }
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

	        // Add the rows to the table model
	        while (rs.next()) {
	            Object[] rowData = new Object[numColumns];
	            for (int i = 0; i < numColumns; i++) {
	                rowData[i] = rs.getObject(i + 1);
	            }
	            tableModel.addRow(rowData);
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
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 16));
		frame.setBounds(100, 100, 857, 594);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Register/Update/View Customer");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
		lblNewLabel.setBounds(284, 51, 342, 32);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Enter Details", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 118, 455, 328);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 80, 88, 25);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Last Name");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(25, 120, 88, 25);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Date Of Birth");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(25, 160, 88, 25);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Password");
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_2_1.setBounds(25, 200, 88, 25);
		panel.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("Confirm Password");
		lblNewLabel_1_2_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_2_2.setBounds(25, 240, 115, 25);
		panel.add(lblNewLabel_1_2_2);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(229, 80, 202, 19);
		panel.add(firstNameField);
		firstNameField.setColumns(10);
		
		lastNameField = new JTextField();
		lastNameField.setColumns(10);
		lastNameField.setBounds(229, 120, 202, 19);
		panel.add(lastNameField);
		
		dobField = new JTextField();
		dobField.setColumns(10);
		dobField.setBounds(229, 160, 202, 19);
		panel.add(dobField);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(229, 200, 202, 19);
		panel.add(passwordField);
		
		password2Field = new JPasswordField();
		password2Field.setColumns(10);
		password2Field.setBounds(229, 240, 202, 19);
		panel.add(password2Field);
		
		JButton submitButton = new JButton("Add");
		submitButton.setBackground(Color.GREEN);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String firstName,lastName,dateOfBirth,password,confirmPassword;
				
				firstName = firstNameField.getText();
				lastName = lastNameField.getText();
				dateOfBirth = "";
				
				if(!Pattern.matches("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", dobField.getText())) {
					
					JOptionPane.showMessageDialog(null,"please match the pattern for date (YYYY-MM-DD)");
					dobField.setText("");
					dobField.requestFocus();
					
				}
				else {
					dateOfBirth = dobField.getText();
				}
				
				password = "";
				confirmPassword = "";
				
				if(!Pattern.matches(passwordField.getText(),password2Field.getText())) {
					JOptionPane.showMessageDialog(null, "invalid password");
					passwordField.setText("");
					password2Field.setText("");
					passwordField.requestFocus();
					
				}
				else {
						password = passwordField.getText();
						confirmPassword = password2Field.getText();

				}
				
				if(firstName=="" || lastName=="" || dateOfBirth=="" || password=="" || confirmPassword=="") {
					JOptionPane.showMessageDialog(null, "Please fill all the fields");
				}
				else {
					
					try {
							
							query = "INSERT INTO `customer`(`First_Name`, `Last_Name`, `Date_of_Birth`, `Password`) values (?,?,?,?)";
					        PreparedStatement pst = con.prepareStatement(query);
	
					        pst.setString(1, firstName);
					        pst.setString(2, lastName);
					        pst.setString(3, dateOfBirth);
					        pst.setString(4, password);
	
					        int rowsInserted = pst.executeUpdate();
	
					        System.out.println(rowsInserted + " row(s) inserted.");
						
							JOptionPane.showMessageDialog(null, "Record has been added to customer table"); 
							viewTable();
							firstNameField.setText("");
							lastNameField.setText("");
							dobField.setText("");
							passwordField.setText("");
							password2Field.setText("");
							
							firstNameField.requestFocus();
							
						
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
				
				firstNameField.setText("");
				lastNameField.setText("");
				dobField.setText("");
				passwordField.setText("");
				password2Field.setText("");
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
					pst = con.prepareStatement("SELECT `Customer_ID`, `First_Name`, `Last_Name`, `Date_of_Birth`, `Password` FROM `customer` WHERE `Customer_ID`=? AND Deleted_Customer=0");
					pst.setString(1, id);
					rs = pst.executeQuery();
					
					if(rs.next()== true) {
						
						String firstname = rs.getString(2);
						String lastname = rs.getString(3);
						String dob = rs.getString(4);
						String password = rs.getString(5);
						
						firstNameField.setText(firstname);
						lastNameField.setText(lastname);
						dobField.setText(dob);
						passwordField.setText(password);
						password2Field.setText(password);
						
						
					}
					else {
						
						firstNameField.setText("");
						lastNameField.setText("");
						dobField.setText("");
						passwordField.setText("");
						password2Field.setText("");
						
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
					
					String firstName,lastName,dateOfBirth,password,confirmPassword;
					
					firstName = firstNameField.getText();
					lastName = lastNameField.getText();
					dateOfBirth = "";
					
					
					if(!Pattern.matches("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", dobField.getText())) {
						
						JOptionPane.showMessageDialog(null,"please match the pattern for date (YYYY-MM-DD)");
						dobField.setText("");
						dobField.requestFocus();
						
					}
					else {
						dateOfBirth = dobField.getText();
					}

					
					password = "";
					confirmPassword = "";
					
					if(!Pattern.matches(passwordField.getText(),password2Field.getText())) {
						JOptionPane.showMessageDialog(null, "invalid password");
						passwordField.setText("");
						password2Field.setText("");
						passwordField.requestFocus();
						
					}
					else {
							password = passwordField.getText();
							confirmPassword = password2Field.getText();

					}
					
					
					String id = idField.getText();
					
					if(firstName=="" || lastName=="" || dateOfBirth=="" || password=="" || confirmPassword=="") {
						JOptionPane.showMessageDialog(null, "Please fill all the fields");
					}
					else {
						
						try {
								
								query = "UPDATE `customer` SET `First_Name`=?,`Last_Name`=?,`Date_of_Birth`=?,`Password`=? WHERE `Customer_ID` = ?";
						        PreparedStatement pst = con.prepareStatement(query);
	
						        pst.setString(1, firstName);
						        pst.setString(2, lastName);
						        pst.setString(3, dateOfBirth);
						        pst.setString(4, password);
						        pst.setString(5, id);
	
						        int rowsInserted = pst.executeUpdate();
	
						        System.out.println(rowsInserted + " row(s) inserted.");
							
								JOptionPane.showMessageDialog(null, "Record has been updated"); 
								viewTable();
								firstNameField.setText("");
								lastNameField.setText("");
								dobField.setText("");
								passwordField.setText("");
								password2Field.setText("");
								
								firstNameField.requestFocus();
								
							
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
				
				String firstName,lastName,dateOfBirth,password,confirmPassword;
				
				firstName = firstNameField.getText();
				lastName = lastNameField.getText();
				dateOfBirth = dobField.getText();
				password = passwordField.getText();
				confirmPassword = password2Field.getText();
				String id = idField.getText();
				
				try {
						
						query = "UPDATE `customer` SET `Deleted_Customer`='1' WHERE `Customer_ID`=?";
				        PreparedStatement pst = con.prepareStatement(query);

				        
				        pst.setString(1, id);

				        int rowsInserted = pst.executeUpdate();

				        System.out.println(rowsInserted + " row(s) inserted.");
					
						JOptionPane.showMessageDialog(null, "Record has been deleted"); 
						viewTable();
						firstNameField.setText("");
						lastNameField.setText("");
						dobField.setText("");
						passwordField.setText("");
						password2Field.setText("");
						
						firstNameField.requestFocus();
						
					
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
		scrollPane.setBounds(488, 118, 327, 328);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

}
