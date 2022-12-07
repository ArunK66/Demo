package sample;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class demo {

	private JFrame frame;
	private JTextField t1;
	private JTextField t2;
	private JTable table;
	private JTable table_1;
	private JButton btnNewButton_1;
	private JScrollPane scrollPane;
	private JButton btnNewButton_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo window = new demo();
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
	public demo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1290, 741);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		t1 = new JTextField();
		t1.setBounds(153, 136, 254, 28);
		frame.getContentPane().add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setBounds(170, 253, 254, 37);
		frame.getContentPane().add(t2);
		t2.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String n=t1.getText();
				String m=t2.getText();
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mrec", "root", "mysql");
					String query = "insert into student values('"+n+"', '"+m+"')";
					Statement sta = con.createStatement();
					sta.executeUpdate(query);
					
					con.close();
				}
				
				catch(Exception exception){
					
				}
				JOptionPane.showMessageDialog(btnNewButton, "Hello, "+n+"\nYour Marks:"+m);
			}
		});
		btnNewButton.setBounds(241, 425, 118, 21);
		frame.getContentPane().add(btnNewButton);
		
		table = new JTable();
		table.setBounds(670, 230, 1, 1);
		frame.getContentPane().add(table);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(562, 60, 349, 281);
		frame.getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		scrollPane.setViewportView(table_1);
		
		btnNewButton_1 = new JButton("Display Table");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mrec", "root", "mysql");
					String q1="Select * from student";
					Statement st=con1.createStatement();
					ResultSet rs=st.executeQuery(q1);
					ResultSetMetaData rsmd=(ResultSetMetaData) rs.getMetaData();
					DefaultTableModel model = (DefaultTableModel) table_1.getModel();
					int cols=rsmd.getColumnCount();
					String[] colName= new String[cols];
					for(int i=0; i<cols; i++)
						colName[i]=rsmd.getColumnName(i+1);
					model.setColumnIdentifiers(colName);
					String name, marks;
					while(rs.next()) {
						name=rs.getString(1);
						marks=rs.getString(2);
						String[] row = {name, marks};
						model.addRow(row);
					}
					st.close();
					con1.close();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(662, 425, 127, 21);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Clear Table");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_1.setModel(new DefaultTableModel());
			}
		});
		btnNewButton_2.setBounds(677, 487, 99, 21);
		frame.getContentPane().add(btnNewButton_2);
	}
}
