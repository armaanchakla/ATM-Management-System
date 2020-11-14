import java.lang.*;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener
{
	JPanel pn;
	JLabel hdrLbl, CardNoLbl, pinLbl, imgLbl;
	JTextField CardNoTF ;
	JPasswordField pinTF;
	JButton login;
	ImageIcon atmImg;
	Font hdrFont;
	
	public Login()
	{
		this.setTitle("SQUAD ATM SYSTEM");
		this.setSize(1250,650);
		this.setLocation(50,50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pn = new JPanel();
		pn.setLayout(null);
		
		hdrFont = new Font("Impact",Font.BOLD,15);
		
		hdrLbl = new JLabel("Please Enter Information");
		CardNoLbl = new JLabel("Card Number : ");
		pinLbl = new JLabel("PIN : ");
		
		CardNoTF = new JTextField();
		pinTF = new JPasswordField();
		
		login = new JButton("Confirm !");
		
		atmImg = new ImageIcon("ATM.1920x1080.jpg");
		imgLbl = new JLabel(atmImg);
		
		hdrLbl.setBounds(190,250,200,50);
		CardNoLbl.setBounds(180,300,100,30);
		pinLbl.setBounds(180,350,100,30);
		CardNoTF.setBounds(280,300,100,30);
		pinTF.setBounds(280,350,100,30);
		login.setBounds(180,400,200,30);
		imgLbl.setBounds(0,0,1250,650);
		
		hdrLbl.setFont(hdrFont);
		
		login.addActionListener(this);
		
		pn.add(hdrLbl);
		pn.add(CardNoLbl);
		pn.add(CardNoTF);
		pn.add(pinLbl);
		pn.add(pinTF);
		pn.add(login);
		pn.add(imgLbl);
		
		this.add(pn);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(login.getText()))
		{
			verifyAccount();
		}
	}
	
	void verifyAccount()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select CARD_NO , CARD_PIN , CARD_TYPE , STATUS from Card_Info");
			
			boolean flag = false;
			
			while(rs.next())
			{
				int cNo = rs.getInt("CARD_NO");
				String cardPin = rs.getString("CARD_PIN");
				String cardType = rs.getString("CARD_TYPE");
				String status = rs.getString("STATUS");
				String cardNo = Integer.toString(cNo);
				System.out.println(cardNo);
				System.out.println(cardPin);
				System.out.println(cardType);
				System.out.println(status);
				if(cardNo.equals(CardNoTF.getText()) && cardPin.equals(pinTF.getText()))
				{
					flag = true;
					JOptionPane.showMessageDialog(null, "Login is Successfull","Success", JOptionPane.WARNING_MESSAGE);
					System.out.println(cardNo);
					Home hm = new Home(cardNo);
					
					if(status.equals("Admin"))
					{
						hm.btnAccounts.setVisible(true);
					}
					else
					{
						hm.btnAccounts.setVisible(false);
					}
					hm.setVisible(true);
					this.setVisible(false);
					//JOptionPane.showMessageDialog(null, "login success Message Dialog");
					//JOptionPane.showConfirmDialog(null, "login success Confirm Dialog");
					// JDialog.createDialog(null, "login success Create Dialog");
					break;
				}
				else if(CardNoTF.getText().equals(""))
				{
					flag = true;
					JOptionPane.showMessageDialog(null, "Card No Field is Blank","Error", JOptionPane.WARNING_MESSAGE);
					break;
				}
				else if(pinTF.getText().equals(""))
				{
					flag = true;
					JOptionPane.showMessageDialog(null, "Pin Field is Blank","Error", JOptionPane.WARNING_MESSAGE);
					break;
				}
			}
			if(!flag)
			{
				JOptionPane.showMessageDialog(this, "Invalid Credential");
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
			JOptionPane.showMessageDialog(this, "Invalid Credential");
			System.out.println(ex.getMessage());
		}
	}
	public static void main(String args[])
	{
		Login lg = new Login();
		lg.setVisible(true);
	}
	
}