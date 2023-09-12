package ie.itcarlow.CustomerEnvoice;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;

public class MainMenu {
	
	Connection con;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
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
	public MainMenu() {
		initialize();
		connection();
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 421, 401);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JButton customerBtn = new JButton("Customer Menu\r\n\r\n");
		customerBtn.setFont(new Font("Times New Roman", Font.BOLD, 16));
		customerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new CustomerRegistration();
				
			}
		});
		customerBtn.setBounds(83, 108, 220, 46);
		frame.getContentPane().add(customerBtn);
		
		JButton btnEmployeeMenu = new JButton("Employee Menu");
		btnEmployeeMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new EmployeeRegistration();
				
			}
		});
		btnEmployeeMenu.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnEmployeeMenu.setBounds(83, 164, 220, 46);
		frame.getContentPane().add(btnEmployeeMenu);
		
		JButton btnProductsMenu = new JButton("Products Menu");
		btnProductsMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Products();
				
			}
		});
		btnProductsMenu.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnProductsMenu.setBounds(83, 220, 220, 46);
		frame.getContentPane().add(btnProductsMenu);
		
		JButton btnBuyProducts = new JButton("Buy Products");
		btnBuyProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Invoice();
				
			}
		});
		btnBuyProducts.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnBuyProducts.setBounds(83, 276, 220, 46);
		frame.getContentPane().add(btnBuyProducts);
		
		JLabel lblNewLabel = new JLabel("Main Menu");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel.setBounds(141, 39, 119, 46);
		frame.getContentPane().add(lblNewLabel);
	}

}
