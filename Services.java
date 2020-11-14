import java.lang.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Services extends JFrame implements ActionListener
{
	JPanel pn;
	JLabel lblPageName, lblCheckBalance, lblAmount, lblPreviousPin, lblNewPin, lblRecipient, imgLbl;
	JTextField txtAmount, txtPreviousPin, txtNewPin, txtRecipient, txtSearch, txtAccNo;
	JButton btnBack, btnCashOut, btnConfirmPin, btnConfirmTransaction, btnSearch, btnDelAcc;
	JTable table;
	JScrollPane scroll;

	ImageIcon atmImg;
	Font hdrFont;
	String cardNo;
	double cardBalance;
	boolean recipient = false;
	
	public Services(String cardNo)
	{
		this.cardNo = cardNo;
		
		System.out.println("This is from Services : " + cardNo);
		
		//recipientValidity();
		
		accBalance();
		
		this.setTitle("Service Page");
		this.setSize(1250,650);
		this.setLocation(50,50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pn = new JPanel();
		pn.setLayout(null);
		
		hdrFont = new Font("Impact",Font.BOLD,30);
		lblPageName = new JLabel("Service page");
		lblCheckBalance = new JLabel(String.valueOf(cardBalance) + " TK");
		lblAmount = new JLabel("Enter Amount To CashOut");
		lblPreviousPin = new JLabel("Enter Current Pin");
		lblNewPin = new JLabel("Enter New Pin");
		lblRecipient = new JLabel("Enter Recipient Number");
		
		txtAmount = new JTextField();
		txtPreviousPin = new JTextField();
		txtNewPin = new JTextField();
		txtRecipient = new JTextField();
		txtSearch = new JTextField();
		txtAccNo = new JTextField();
		
		btnBack = new JButton("Back To Main Menu");
		btnCashOut = new JButton("Confirm Cash Out");
		btnConfirmPin = new JButton("Confirm Pin");
		btnConfirmTransaction = new JButton("Confirm And Send Money");
		btnSearch = new JButton("Search");
		btnDelAcc = new JButton("Delete Account");
		
		atmImg = new ImageIcon("ATM.1920x1080.jpg");
		imgLbl = new JLabel(atmImg);
		
		lblPageName.setBounds(260,130,400,100);
		imgLbl.setBounds(0,0,1250,650);
		lblCheckBalance.setBounds(300,250,400,100);
		lblAmount.setBounds(170,300,200,30);
		txtAmount.setBounds(370,300,150,30);
		lblPreviousPin.setBounds(170,250,200,30);
		txtPreviousPin.setBounds(370,250,150,30);
		lblNewPin.setBounds(170,300,200,30);
		txtNewPin.setBounds(370,300,150,30);
		lblRecipient.setBounds(170,250,200,30);
		txtRecipient.setBounds(370,250,150,30);
		txtSearch.setBounds(50,210,150,30);
		txtAccNo.setBounds(280,210,150,30);
		btnBack.setBounds(350,480,150,50);
		btnCashOut.setBounds(190,480,150,50);
		btnConfirmPin.setBounds(190,480,150,50);
		btnConfirmTransaction.setBounds(190,480,150,50);
		btnSearch.setBounds(200,210,80,30);
		btnDelAcc.setBounds(430,210,80,30);
		
		lblPageName.setFont(hdrFont);
		lblCheckBalance.setFont(hdrFont);
		
		String query = "SELECT * FROM Transaction_Info WHERE Card_No='"+cardNo+"'";
		transactionTable(query);
		
		pn.add(lblPageName);
		pn.add(btnBack);
		pn.add(lblAmount);
		pn.add(lblCheckBalance);
		pn.add(lblPreviousPin);
		pn.add(lblNewPin);
		pn.add(lblRecipient);
		pn.add(txtAmount);
		pn.add(txtPreviousPin);
		pn.add(txtNewPin);
		pn.add(txtRecipient);
		pn.add(txtSearch);
		pn.add(txtAccNo);
		pn.add(btnCashOut);
		pn.add(btnConfirmPin);
		pn.add(btnConfirmTransaction);
		pn.add(btnSearch);
		pn.add(btnDelAcc);
		pn.add(imgLbl);
		
		btnBack.addActionListener(this);
		btnCashOut.addActionListener(this);
		btnConfirmPin.addActionListener(this);
		btnConfirmTransaction.addActionListener(this);
		btnSearch.addActionListener(this);
		btnDelAcc.addActionListener(this);
		
		this.add(pn);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(btnBack.getText()))
		{
			Home hm = new Home(cardNo);
			hm.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(btnCashOut.getText()))
		{
			cashOutOperation();
		}
		else if(text.equals(btnConfirmTransaction.getText()))
		{
			balanceTransfer();
		}
		else if(text.equals(btnSearch.getText()))
		{
			if(txtSearch.equals(""))
			{
				String query = "SELECT * FROM Customer_Info";
				transactionTable(query);
			}
			else
			{
				String query = "SELECT * FROM Customer_Info WHERE Card_No LIKE '"+txtSearch.getText()+"%' OR Name LIKE '"+txtSearch.getText()+"%'";
				transactionTable(query);
			}
		}
		else if(text.equals(btnDelAcc.getText()))
		{
			if(txtAccNo.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Enter a valid Card Number !","Error", JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				insertDB("Delete from Card_Info where Card_No = "+txtAccNo.getText()+"");
				insertDB("Delete from Customer_Info where Card_No = "+txtAccNo.getText()+"");
				insertDB("Delete from Account_Info where Card_No = "+txtAccNo.getText()+"");
				insertDB("Delete from Transaction_Info where Card_No = "+txtAccNo.getText()+"");
				JOptionPane.showMessageDialog(null, "Account Deletion Complete !","Success", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(text.equals(btnConfirmPin.getText()))
		{
			confirmPin();
		}
	}
	
	void accBalance()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select Current_Balance from Transaction_Info where Card_No = "+cardNo+" order by curr_date asc");
			
			boolean flag = false;
			
			while(rs.next())
			{
				flag = true;
				cardBalance = rs.getDouble("Current_Balance");
				System.out.println(String.valueOf(cardBalance));
			}
			if(!flag)
			{
				JOptionPane.showMessageDialog(this, "Data Coun=d=ldn't be Fetched");
			}
			
			stm.close();
			con.close();
		}
		catch(ClassNotFoundException cn)
		{
			System.out.println(cn.getMessage());
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Data Couldn't be Fetched");
			System.out.println(ex.getMessage());
		}
	}
	
	void cashOutOperation()
	{
		double amount = Double.parseDouble(txtAmount.getText());
		if(txtAmount.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please Enter An Amount");
		}
		else if( cardBalance <= 500 )
		{
			JOptionPane.showMessageDialog(this, "Not Enough Balance For Transaction");
		}
		else if((cardBalance - amount) <= 500 )
		{
			JOptionPane.showMessageDialog(this, "Not Enough Balance For Transaction");
		}
		else
		{
			int cNO = Integer.parseInt(cardNo);
			double currBalance = cardBalance - amount;
			int transactionNo = 1000104;
			String query = "insert into TRANSACTION_INFO values("+cNO+",0,"+amount+",0,"+currBalance+",sysdate,"+transactionNo+")";
			insertDB(query);
			JOptionPane.showMessageDialog(null, "CASH OUT completed !","Success", JOptionPane.WARNING_MESSAGE);
			Home hm = new Home(cardNo);
			hm.setVisible(true);
			this.setVisible(false);
		}
	}
	
	void balanceTransfer()
	{
		double amount = Double.parseDouble(txtAmount.getText());
		int recipientNumber = Integer.parseInt(txtRecipient.getText());
		
		if(txtAmount.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please Enter An Amount");
		}
		else if(txtAmount.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please Enter An Recipient Number");
		}
		else
		{
			recipientValidity();
		
			if(recipient == true)
			{
				JOptionPane.showMessageDialog(this, "Recipient Valid");
				
				if( cardBalance <= 500 )
				{
					JOptionPane.showMessageDialog(this, "Not Enough Balance For Transaction");
				}
				else if((cardBalance - amount) <= 500 )
				{
					JOptionPane.showMessageDialog(this, "Not Enough Balance For Transaction");
				}
				else
				{
					int cNO = Integer.parseInt(cardNo);
					double currBalance = cardBalance - amount;
					int transactionNo = 1000114;
					
					String query = "insert into TRANSACTION_INFO values("+cNO+",0,0,"+amount+","+currBalance+",sysdate,"+transactionNo+")";
					insertDB(query);
					
				//	String query = "insert into TRANSACTION_INFO values("+recipientNumber+","+amount+",0,0,"+currBalance+",sysdate,"+transactionNo+")";
				//	insertDB(query);
					JOptionPane.showMessageDialog(null, "Balance Transferred !","Success", JOptionPane.WARNING_MESSAGE);
					
					Home hm = new Home(cardNo);
					hm.setVisible(true);
					this.setVisible(false);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Recipient Number isn't Correct.","Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	void recipientValidity()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select card_no from Card_Info");
			
			boolean flag = false;
			
			while(rs.next())
			{
				flag = true;
				int recipientNo = rs.getInt("Card_No");
				int rNo = Integer.parseInt(txtRecipient.getText());
				if(rNo == recipientNo)
				{
					recipient = true;
					break;
				}
				else
				{
					recipient = false;
				}
			}
			if(!flag)
			{
				JOptionPane.showMessageDialog(this, "Data Coun=d=ldn't be Fetched");
			}
			
			stm.close();
			con.close();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Data Couldn't be Fetched");
			System.out.println(ex.getMessage());
		}
	}
	
	void insertDB(String query)
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(query);
			stm.close();
			con.close();
		}
		catch(ClassNotFoundException cn)
		{
			System.out.println(cn.getMessage());
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Data Couldn't be Written");
			System.out.println(ex.getMessage());
		}
	}
	
	void transactionTable(String query)
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int c = rsmd.getColumnCount();
			Vector column = new Vector(c);
			for(int i=1;i<=c;i++)
			{
				column.add(rsmd.getColumnName(i));
			}
			Vector data=new Vector();
			Vector row=new Vector();
			while(rs.next())
			{
				row=new Vector(c);
				for(int i=1;i<=c;i++)
				{
					row.add(rs.getString(i));
				}
				data.add(row);
			}
			table=new JTable(data,column);
			table.setEnabled(false);
			scroll=new JScrollPane(table);
			scroll.setBounds(50,250,550,200);
			pn.add(scroll);
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
	}
	void confirmPin()
	{
		String oldPIN = txtPreviousPin.getText();
		String newPIN = txtNewPin.getText();
		String prvPin;
		
		if(txtPreviousPin.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Empty Field");
		}
		else if(txtNewPin.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Empty Field");
		}
		else
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery("select Card_Pin from Card_Info where Card_No = "+cardNo+"");
				
				boolean flag = false;
				
				while(rs.next())
				{
					flag = true;
					prvPin = rs.getString("Card_Pin");
					if( prvPin.equals(oldPIN) )
					{
						String query = "update Card_Info set Card_Pin = '"+newPIN+"' where Card_No = "+cardNo+"";
						insertDB(query);
						JOptionPane.showMessageDialog(null, "The Password Had Been Restored !","Success", JOptionPane.WARNING_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The password didn't matched !","Error", JOptionPane.WARNING_MESSAGE);
					}
				}
				if(!flag)
				{
					JOptionPane.showMessageDialog(this, "Data Coun=d=ldn't be Fetched");
				}
				
				stm.close();
				con.close();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this, "Data Couldn't be Fetched");
				System.out.println(ex.getMessage());
			}
			
		}
	}
}