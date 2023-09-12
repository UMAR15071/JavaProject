package ie.itcarlow.CustomerEnvoice;
import java.sql.*;
import java.time.LocalDate;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Invoice {

	private JFrame frame;
	private JTextField cidField;
	private JTextField pidField;
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable customerTable;
	private JTable productTable;
	private JTextField customeridField;
	private JTextField fnameField;
	private JTextField lnameField;
	private JTextField dobField;
	private JTextField productIdField;
	private JTextField pnameField;
	private JTextField cnameField;
	private JTextField totalPriceField;
	private JTextField quantityField;
	private JTextField unitPriceField;
	JTextArea recieptField;
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Invoice window = new Invoice();
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
	public Invoice() {
		initialize();
		connection();
		viewCustomerTable();
		productViewTable();

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
	
	public void viewCustomerTable() {
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
	        
	        customerTable.setModel(tableModel);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void productViewTable() {
	    try {
	        pst = con.prepareStatement("SELECT `Product_id`, `Product_Name`,`Company`,`Quantity_in_Stock`,`Unit_Price` FROM `products` WHERE Deleted_Product=0 AND `Quantity_in_Stock`!=0");
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
	        productTable.setModel(tableModel);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void reciept() {
		
		String cid = customeridField.getText();
		String pid = productIdField.getText();
		String pname = pnameField.getText();
		String cname = cnameField.getText();
		String unitPrice = unitPriceField.getText();
		String quantity = quantityField.getText();
		String totalPrice = totalPriceField.getText();
		
		recieptField.setText(recieptField.getText() + "\n");
		recieptField.setText(recieptField.getText() + "----------------------------------------------------------------------------" + "\n");
		recieptField.setText(recieptField.getText() + "Customer Reciept"+ "\n");
		recieptField.setText(recieptField.getText() + "----------------------------------------------------------------------------"+ "\n");
		recieptField.setText(recieptField.getText() + "Customer id:		" + cid + "\n");
		recieptField.setText(recieptField.getText() + "Product id:		" + pid + "\n");
		recieptField.setText(recieptField.getText() + "Product Name:	" + cname +" "+ pname + "\n");
		recieptField.setText(recieptField.getText() + "Unit Price:		" + unitPrice +"\n");
		recieptField.setText(recieptField.getText() + "Quantity:		" + quantity + "\n");
		recieptField.setText(recieptField.getText() + "----------------------------------------------------------------------------"+"\n");
		recieptField.setText(recieptField.getText() + "Total Price:		"+totalPrice +"\n");
		recieptField.setText(recieptField.getText() + "----------------------------------------------------------------------------"+"\n");
		
	}





	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 16));
		frame.setBounds(100, 10, 1442, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "view Customers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 137, 520, 296);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 20, 500, 263);
		panel.add(scrollPane);
		
		customerTable = new JTable();
		scrollPane.setViewportView(customerTable);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "View Products", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(563, 137, 510, 290);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 21, 488, 259);
		panel_1.add(scrollPane_1);
		
		productTable = new JTable();
		scrollPane_1.setViewportView(productTable);
		
		JLabel lblNewLabel = new JLabel("Buy Products");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel.setBounds(513, 30, 136, 26);
		frame.getContentPane().add(lblNewLabel);
		
		cidField = new JTextField();
		cidField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				
				String id = cidField.getText();
				try {
					pst = con.prepareStatement("SELECT `Customer_ID`, `First_Name`, `Last_Name`, `Date_of_Birth` FROM `customer` WHERE `Customer_ID`=? AND Deleted_Customer=0");
					pst.setString(1, id);
					rs = pst.executeQuery();
					
					if(rs.next()== true) {
						
						String rId = rs.getString(1);
						String firstname = rs.getString(2);
						String lastname = rs.getString(3);
						String dob = rs.getString(4);
						
						customeridField.setText(rId);
						fnameField.setText(firstname);
						lnameField.setText(lastname);
						dobField.setText(dob);
						
						
					}
					else {
					
						customeridField.setText("");
						fnameField.setText("");
						lnameField.setText("");
						dobField.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				

			}
		});
		cidField.setBounds(400, 110, 96, 19);
		frame.getContentPane().add(cidField);
		cidField.setColumns(10);
		
		pidField = new JTextField();
		pidField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				
				
				String id = pidField.getText();
				String query = "SELECT `Product_id`, `Product_Name`,`Company`,`Unit_Price` FROM `products` WHERE `Product_id`= ? AND Deleted_Product=0";
				try {
					pst = con.prepareStatement(query);
					pst.setString(1, id);
					rs = pst.executeQuery();
					
					if(rs.next()== true) {
						
						String pid = rs.getString(1);
						String name = rs.getString(2);
						String company = rs.getString(3);
						String price = rs.getString(4);
						

						productIdField.setText(pid);
						pnameField.setText(name);
						cnameField.setText(company);
						unitPriceField.setText(price);
						
					}
					else {
						
						pnameField.setText("");
						cnameField.setText("");
						productIdField.setText("");
						unitPriceField.setText("");
					}

					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				
				
			}
		});
		pidField.setBounds(950, 110, 96, 19);
		frame.getContentPane().add(pidField);
		pidField.setColumns(10);
		
		JLabel cidLabel = new JLabel("Customer ID");
		cidLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		cidLabel.setBounds(250, 110, 113, 16);
		frame.getContentPane().add(cidLabel);
		
		JLabel pidLabel = new JLabel("Product ID");
		pidLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		pidLabel.setBounds(800, 110, 113, 16);
		frame.getContentPane().add(pidLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Customer Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 437, 520, 247);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		customeridField = new JTextField();
		customeridField.setEditable(false);
		customeridField.setBounds(300, 60, 147, 19);
		panel_2.add(customeridField);
		customeridField.setColumns(10);
		
		fnameField = new JTextField();
		fnameField.setEditable(false);
		fnameField.setColumns(10);
		fnameField.setBounds(300, 90, 147, 19);
		panel_2.add(fnameField);
		
		lnameField = new JTextField();
		lnameField.setEditable(false);
		lnameField.setColumns(10);
		lnameField.setBounds(300, 120, 147, 19);
		panel_2.add(lnameField);
		
		dobField = new JTextField();
		dobField.setEditable(false);
		dobField.setColumns(10);
		dobField.setBounds(300, 146, 150, 19);
		panel_2.add(dobField);
		
		JLabel lblNewLabel_1 = new JLabel("Customer ID");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1.setBounds(38, 60, 102, 16);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("First Name");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(38, 90, 125, 16);
		panel_2.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("date of Birth");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(38, 150, 102, 16);
		panel_2.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Last Name");
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_3.setBounds(38, 120, 102, 16);
		panel_2.add(lblNewLabel_1_3);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBorder(new TitledBorder(null, "Product details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2_1.setBounds(563, 437, 510, 247);
		frame.getContentPane().add(panel_2_1);
		panel_2_1.setLayout(null);
		
		productIdField = new JTextField();
		productIdField.setEditable(false);
		productIdField.setBounds(345, 60, 146, 19);
		productIdField.setColumns(10);
		panel_2_1.add(productIdField);
		
		pnameField = new JTextField();
		pnameField.setEditable(false);
		pnameField.setColumns(10);
		pnameField.setBounds(345, 90, 146, 19);
		panel_2_1.add(pnameField);
		
		cnameField = new JTextField();
		cnameField.setEditable(false);
		cnameField.setColumns(10);
		cnameField.setBounds(345, 120, 146, 19);
		panel_2_1.add(cnameField);
		
		totalPriceField = new JTextField();
		totalPriceField.setEditable(false);
		totalPriceField.setColumns(10);
		totalPriceField.setBounds(345, 208, 146, 19);
		panel_2_1.add(totalPriceField);
		
		JLabel lblNewLabel_1_4 = new JLabel("Product ID");
		lblNewLabel_1_4.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_4.setBounds(20, 60, 102, 16);
		panel_2_1.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Product Name");
		lblNewLabel_1_5.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_5.setBounds(20, 90, 102, 16);
		panel_2_1.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("Company Name");
		lblNewLabel_1_6.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_6.setBounds(20, 120, 121, 16);
		panel_2_1.add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1_7 = new JLabel("Total Price");
		lblNewLabel_1_7.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_7.setBounds(20, 208, 102, 16);
		panel_2_1.add(lblNewLabel_1_7);
		
		quantityField = new JTextField();
		quantityField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					
					int quantity = 0;
					double totalPrice = 0;
					if(quantityField.getText()!="") {
						
						try {
							quantity = Integer.parseInt(quantityField.getText());
							if(quantity <=0) {
								
								JOptionPane.showMessageDialog(null, "invalid Quantity");
								quantityField.setText("1");
							}
						}
						catch(NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Please enter the valid quantity in Quantity Field");
						}
					}
					
					double price = 0;
					price = Double.parseDouble(unitPriceField.getText());
					if(quantity != 0) {

						totalPrice = quantity*price;
						totalPriceField.setText(totalPrice+"");
					}
					
				}
			
		
			}
				
		});
		quantityField.setBounds(345, 150, 146, 19);
		panel_2_1.add(quantityField);
		quantityField.setColumns(10);
		quantityField.setText("1");
		
		JLabel quantityLabel = new JLabel("Quantity");
		quantityLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		quantityLabel.setBounds(20, 149, 85, 19);
		panel_2_1.add(quantityLabel);
		
		JLabel lblNewLabel_1_7_1 = new JLabel("Unit Price");
		lblNewLabel_1_7_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_7_1.setBounds(20, 183, 102, 16);
		panel_2_1.add(lblNewLabel_1_7_1);
		
		unitPriceField = new JTextField();
		unitPriceField.setEditable(false);
		unitPriceField.setColumns(10);
		unitPriceField.setBounds(345, 179, 146, 19);
		panel_2_1.add(unitPriceField);
		
		
		recieptField = new JTextArea();
		recieptField.setBounds(1097, 149, 321, 374);
		frame.getContentPane().add(recieptField);
		
		JButton btnNewButton_1 = new JButton("Print");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(1211, 553, 85, 21);
		frame.getContentPane().add(btnNewButton_1);

		
		JButton btnNewButton = new JButton("Buy Product");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String cid = customeridField.getText();
				String pid = productIdField.getText();
				int bQuantity = Integer.parseInt(quantityField.getText());
				double tPrice = Double.parseDouble(totalPriceField.getText());
				LocalDate date = LocalDate.now();
				int stockQuantity = 0;
				try {
					pst = con.prepareStatement("INSERT INTO `envoice`( `Customer_ID`, `Product_ID`, `Quantity_Bought`, `Total_Price`, `Date_of_Purchase`) VALUES (?,?,?,?,?)");
					
					pst.setString(1, cid);
					pst.setString(2, pid);
					pst.setInt(3, bQuantity);
					pst.setDouble(4, tPrice);
					pst.setObject(5, date);
					
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Thanks for buying our product");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					pst = con.prepareStatement("SELECT `Quantity_in_Stock` FROM `products` WHERE `Product_id`=?");
					pst.setString(1, pid);
					rs = pst.executeQuery();
					if(rs.next()==true) {
						stockQuantity = rs.getInt(1);
						stockQuantity = stockQuantity-bQuantity;
					}
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(stockQuantity == 0) {
					
					try {
						pst = con.prepareStatement("UPDATE `products` SET `Quantity_in_Stock`= ?, `Deleted_Product`=? WHERE `Product_id`=?");
						pst.setInt(1, stockQuantity);
						pst.setInt(2, 1);
						pst.setString(3,pid);
						pst.executeUpdate();
						productViewTable();
						reciept();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else {
					
					try {
						pst = con.prepareStatement("UPDATE `products` SET `Quantity_in_Stock`= ? WHERE `Product_id`=?");
						pst.setInt(1, stockQuantity);
						pst.setString(2,pid);
						pst.executeUpdate();
						productViewTable();
						reciept();
	
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});
		
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNewButton.setBounds(498, 704, 128, 21);
		frame.getContentPane().add(btnNewButton);

		
	}
	

	
	
}
