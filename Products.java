package ie.itcarlow.CustomerEnvoice;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;

public class Products {

	private JFrame frame;
	private JTextField nameField;
	private JTextField cNameField;
	private JTextField quantityField;
	private JTextField priceField;
	private JLabel ProductName;
	private JLabel ComapnyName;
	private JLabel Quantity;
	private JLabel productPrice;
	PreparedStatement pst;
	Connection con;
	ResultSet rs;
	private JLabel lblNewLabel;
	private JTable table;
	private JTextField idField;
	private JScrollPane scrollPane;
	private JButton updateButton;
	private JButton deleteButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Products window = new Products();
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
	public Products() {
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
	        pst = con.prepareStatement("SELECT `Product_id`, `Product_Name`,`Company`,`Quantity_in_Stock`,`Unit_Price` FROM `products` WHERE Deleted_Product=0");
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
		frame.setBounds(100, 100, 1139, 518);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add Product", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 110, 534, 257);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		nameField = new JTextField();
		nameField.setBounds(349, 30, 170, 25);
		panel.add(nameField);
		nameField.setColumns(10);
		
		cNameField = new JTextField();
		cNameField.setColumns(10);
		cNameField.setBounds(349, 70, 170, 25);
		panel.add(cNameField);
		
		quantityField = new JTextField();
		quantityField.setColumns(10);
		quantityField.setBounds(349, 110, 170, 25);
		panel.add(quantityField);
		
		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(349, 150, 170, 25);
		panel.add(priceField);
		
		ProductName = new JLabel("Name");
		ProductName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		ProductName.setBounds(31, 29, 150, 25);
		panel.add(ProductName);
		
		ComapnyName = new JLabel("Company");
		ComapnyName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		ComapnyName.setBounds(31, 70, 150, 16);
		panel.add(ComapnyName);
		
		Quantity = new JLabel("Quantity in Stock");
		Quantity.setFont(new Font("Times New Roman", Font.BOLD, 16));
		Quantity.setBounds(31, 110, 150, 16);
		panel.add(Quantity);
		
		productPrice = new JLabel("Unit Price");
		productPrice.setFont(new Font("Times New Roman", Font.BOLD, 16));
		productPrice.setBounds(31, 150, 150, 16);
		panel.add(productPrice);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				
				String name = nameField.getText();
				String company = cNameField.getText();
				
				double price = 0;
				
				try {
					
					price = Double.parseDouble(priceField.getText());
					
				}
				catch(NumberFormatException nfe) {
					
					
					JOptionPane.showMessageDialog(null, "invalid data on Price field");
					priceField.setText("");
					priceField.requestFocus();

					
				}
				
				
				int quantity = 0;
				
				try {
					quantity = Integer.parseInt(quantityField.getText());
				}
				catch(NumberFormatException nfe){
					
					JOptionPane.showMessageDialog(null, "invalid data on Quantity field");
					quantityField.setText("");
					quantityField.requestFocus();
				}
	
				if(name == "" || company=="" || quantity<=0 || price<=0) {
					
					JOptionPane.showMessageDialog(null, "Please fill all the Fields");
					
				}
				else {
					
						String query = "INSERT INTO `products`(`Product_Name`, `Company`, `Quantity_in_Stock`, `Unit_Price`) VALUES (?,?,?,?)";
						try {
							pst = con.prepareStatement(query);
							
							pst.setString(1, name);
							pst.setString(2, company);
							pst.setInt(3, quantity);
							pst.setDouble(4, price);
							
							pst.executeUpdate();
							viewTable();
							
							JOptionPane.showMessageDialog(null, "Record has been added to Products table"); 
							
							nameField.setText("");
							cNameField.setText("");
							quantityField.setText("");
							priceField.setText("");
							
							nameField.requestFocus();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}

				}
				
		});
		addButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		addButton.setBounds(434, 199, 85, 21);
		panel.add(addButton);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nameField.setText("");
				cNameField.setText("");
				quantityField.setText("");
				priceField.setText("");
				
				nameField.requestFocus();

				
				
				
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNewButton.setBounds(349, 200, 85, 21);
		panel.add(btnNewButton);
		
		lblNewLabel = new JLabel("Add Products");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel.setBounds(363, 29, 134, 26);
		frame.getContentPane().add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(554, 110, 542, 257);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 393, 534, 78);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		idField = new JTextField();
		idField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				
				String id = idField.getText();
				String query = "SELECT `Product_Name`,`Company`,`Quantity_in_Stock`,`Unit_Price` FROM `products` WHERE `Product_id`= ? AND Deleted_Product=0";
				try {
					pst = con.prepareStatement(query);
					pst.setString(1, id);
					rs = pst.executeQuery();
					
					if(rs.next()== true) {
						
						String name = rs.getString(1);
						String company = rs.getString(2);
						String quantity = rs.getString(3);
						String price = rs.getString(4);
						
						nameField.setText(name);
						cNameField.setText(company);
						quantityField.setText(quantity);
						priceField.setText(price);
						
						
					}
					else {
						
						nameField.setText("");
						cNameField.setText("");
						quantityField.setText("");
						priceField.setText("");
						
					}

					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		idField.setBounds(241, 29, 136, 23);
		idField.setColumns(10);
		panel_1.add(idField);
		
		JLabel idLabel = new JLabel("Product ID");
		idLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		idLabel.setBounds(135, 29, 86, 25);
		panel_1.add(idLabel);
		
		updateButton = new JButton("Update");
		updateButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
					String name = nameField.getText();
					String company = cNameField.getText();

					int id = 0;
					
					try {
							id = Integer.parseInt(idField.getText());
					}
					catch(NumberFormatException nfe){
						
						JOptionPane.showMessageDialog(null, "invalid data on Quantity field");
						idField.setText("");
						idField.requestFocus();
					}

					
					double price = 0;
					
					try {
						
						price = Double.parseDouble(priceField.getText());
						
					}
					catch(NumberFormatException nfe) {
						
						
						JOptionPane.showMessageDialog(null, "invalid data on Price field");
						priceField.setText("");
						priceField.requestFocus();

						
					}
					
					
					int quantity = 0;
					
					try {
						quantity = Integer.parseInt(quantityField.getText());
					}
					catch(NumberFormatException nfe){
						
						JOptionPane.showMessageDialog(null, "invalid data on Quantity field");
						quantityField.setText("");
						quantityField.requestFocus();
					}
					
					if(name == "" || company=="" || quantity<=0 || price<=0) {
						
						JOptionPane.showMessageDialog(null, "Please fill all the Fields properly");
						
					}
					else {
						
							String query = "UPDATE `products` SET `Product_Name`=?,`Company`=?,`Quantity_in_Stock`=?,`Unit_Price`=? WHERE `Product_id`=?";
							try {
								pst = con.prepareStatement(query);
								
								pst.setString(1, name);
								pst.setString(2, company);
								pst.setInt(3, quantity);
								pst.setDouble(4, price);
								pst.setInt(5, id);
								
								pst.executeUpdate();
								viewTable();
								
								JOptionPane.showMessageDialog(null, "Record has been updated in customer table"); 
								
								nameField.setText("");
								cNameField.setText("");
								quantityField.setText("");
								priceField.setText("");
								idField.setText("");
								
								nameField.requestFocus();
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}

					}
		});
		updateButton.setBounds(653, 411, 95, 33);
		frame.getContentPane().add(updateButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id = idField.getText();
				
				try {
					pst = con.prepareStatement("UPDATE `products` SET `Deleted_Product`=1 WHERE `Product_id`=?");
					pst.setString(1, id);
					
					int rowDeleted = pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record has been deleted"); 
					viewTable();
					
					nameField.setText("");
					cNameField.setText("");
					quantityField.setText("");
					priceField.setText("");
					
					idField.requestFocus();
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		deleteButton.setBounds(793, 411, 95, 33);
		frame.getContentPane().add(deleteButton);
	}
}

