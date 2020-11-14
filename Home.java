import java.lang.*;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener
{
	JPanel pn;
	JLabel lblPageName, imgLbl;
	JButton btnCheckBalance, btnCashOut, btnChangePin, btnStatement, btnBalanceTransfer, btnSignOut, btnAccounts;
	ImageIcon atmImg;
	Font hdrFont;
	String cardNo;
	
	public Home(String cardNo)
	{
		this.cardNo = cardNo;
		
		this.setTitle("SQUAD ATM SYSTEM HOMEPAGE");
		this.setSize(1250,650);
		this.setLocation(50,50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pn = new JPanel();
		pn.setLayout(null);
		
		hdrFont = new Font("Impact",Font.BOLD,30);
		lblPageName = new JLabel("WELCOME");
		
		btnCheckBalance = new JButton("Check Balance");
		btnCashOut = new JButton("Cash Out");
		btnChangePin = new JButton("Change PIN");
		btnStatement = new JButton("Check Statement");
		btnBalanceTransfer = new JButton("Transfer Balance");
		btnSignOut = new JButton("Exit");
		btnAccounts = new JButton("Check Accounts");
		
		atmImg = new ImageIcon("ATM.1920x1080.jpg");
		imgLbl = new JLabel(atmImg);
		
		lblPageName.setBounds(260,150,400,100);
		btnCheckBalance.setBounds(80,250,150,50);
		btnCashOut.setBounds(80,350,150,50);
		btnChangePin.setBounds(80,450,150,50);
		btnAccounts.setBounds(80,550,150,50);
		btnStatement.setBounds(380,250,150,50);
		btnBalanceTransfer.setBounds(380,350,150,50);
		btnSignOut.setBounds(380,450,150,50);
		imgLbl.setBounds(0,0,1250,650);
		
		lblPageName.setFont(hdrFont);
		//login.addActionListener(this);
		
		pn.add(lblPageName);
		pn.add(btnCheckBalance);
		pn.add(btnCashOut);
		pn.add(btnChangePin);
		pn.add(btnAccounts);
		pn.add(btnStatement);
		pn.add(btnBalanceTransfer);
		pn.add(btnSignOut);
		pn.add(imgLbl);
		
		btnSignOut.addActionListener(this);
		btnBalanceTransfer.addActionListener(this);
		btnCashOut.addActionListener(this);
		btnChangePin.addActionListener(this);
		btnAccounts.addActionListener(this);
		btnCheckBalance.addActionListener(this);
		btnStatement.addActionListener(this);
		
		this.add(pn);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(btnSignOut.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(btnBalanceTransfer.getText()))
		{
			Services sv = new Services(cardNo);
			
			sv.lblPageName.setText("Balance Transfer");
			sv.lblCheckBalance.setVisible(false);
			sv.lblAmount.setVisible(true);
			sv.lblPreviousPin.setVisible(false);
			sv.lblNewPin.setVisible(false);
			sv.lblRecipient.setVisible(true);
			sv.txtAmount.setVisible(true);
			sv.txtPreviousPin.setVisible(false);
			sv.txtNewPin.setVisible(false);
			sv.txtRecipient.setVisible(true);
			sv.btnCashOut.setVisible(false);
			sv.btnConfirmPin.setVisible(false);
			sv.btnConfirmTransaction.setVisible(true);
			sv.txtSearch.setVisible(false);
			sv.btnSearch.setVisible(false);
			sv.scroll.setVisible(false);
			sv.txtAccNo.setVisible(false);
			sv.btnDelAcc.setVisible(false);
			
			sv.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(btnCashOut.getText()))
		{
			Services sv = new Services(cardNo);
			
			sv.lblPageName.setText("CASH OUT");
			sv.lblCheckBalance.setVisible(false);
			sv.lblAmount.setVisible(true);
			sv.lblPreviousPin.setVisible(false);
			sv.lblNewPin.setVisible(false);
			sv.lblRecipient.setVisible(false);
			sv.txtAmount.setVisible(true);
			sv.txtPreviousPin.setVisible(false);
			sv.txtNewPin.setVisible(false);
			sv.txtRecipient.setVisible(false);
			sv.btnCashOut.setVisible(true);
			sv.btnConfirmPin.setVisible(false);
			sv.btnConfirmTransaction.setVisible(false);
			sv.txtSearch.setVisible(false);
			sv.btnSearch.setVisible(false);
			sv.scroll.setVisible(false);
			sv.txtAccNo.setVisible(false);
			sv.btnDelAcc.setVisible(false);
			
			sv.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(btnChangePin.getText()))
		{
			Services sv = new Services(cardNo);
			
			sv.lblPageName.setText("Change Account PIN");
			sv.lblCheckBalance.setVisible(false);
			sv.lblAmount.setVisible(false);
			sv.lblPreviousPin.setVisible(true);
			sv.lblNewPin.setVisible(true);
			sv.lblRecipient.setVisible(false);
			sv.txtAmount.setVisible(false);
			sv.txtPreviousPin.setVisible(true);
			sv.txtNewPin.setVisible(true);
			sv.txtRecipient.setVisible(false);
			sv.btnCashOut.setVisible(false);
			sv.btnConfirmPin.setVisible(true);
			sv.btnConfirmTransaction.setVisible(false);
			sv.txtSearch.setVisible(false);
			sv.btnSearch.setVisible(false);
			sv.scroll.setVisible(false);
			sv.txtAccNo.setVisible(false);
			sv.btnDelAcc.setVisible(false);
			
			sv.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(btnCheckBalance.getText()))
		{
			Services sv = new Services(cardNo);
			
			sv.lblPageName.setText("Your Current Balance");
			sv.lblCheckBalance.setVisible(true);
			sv.lblAmount.setVisible(false);
			sv.lblPreviousPin.setVisible(false);
			sv.lblNewPin.setVisible(false);
			sv.lblRecipient.setVisible(false);
			sv.txtAmount.setVisible(false);
			sv.txtPreviousPin.setVisible(false);
			sv.txtNewPin.setVisible(false);
			sv.txtRecipient.setVisible(false);
			sv.btnCashOut.setVisible(false);
			sv.btnConfirmPin.setVisible(false);
			sv.btnConfirmTransaction.setVisible(false);
			sv.txtSearch.setVisible(false);
			sv.btnSearch.setVisible(false);
			sv.scroll.setVisible(false);
			sv.txtAccNo.setVisible(false);
			sv.btnDelAcc.setVisible(false);
			
			sv.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(btnStatement.getText()))
		{
			Services sv = new Services(cardNo);
			
			sv.lblPageName.setText("Account Statement");
			sv.lblCheckBalance.setVisible(false);
			sv.lblAmount.setVisible(false);
			sv.lblPreviousPin.setVisible(false);
			sv.lblNewPin.setVisible(false);
			sv.lblRecipient.setVisible(false);
			sv.txtAmount.setVisible(false);
			sv.txtPreviousPin.setVisible(false);
			sv.txtNewPin.setVisible(false);
			sv.txtRecipient.setVisible(false);
			sv.btnCashOut.setVisible(false);
			sv.btnConfirmPin.setVisible(false);
			sv.btnConfirmTransaction.setVisible(false);
			sv.txtSearch.setVisible(false);
			sv.btnSearch.setVisible(false);
			sv.txtAccNo.setVisible(false);
			sv.btnDelAcc.setVisible(false);
			
			sv.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(btnAccounts.getText()))
		{
			Services sv = new Services(cardNo);
			
			sv.lblPageName.setText("Accounts List");
			sv.lblCheckBalance.setVisible(false);
			sv.lblAmount.setVisible(false);
			sv.lblPreviousPin.setVisible(false);
			sv.lblNewPin.setVisible(false);
			sv.lblRecipient.setVisible(false);
			sv.txtAmount.setVisible(false);
			sv.txtPreviousPin.setVisible(false);
			sv.txtNewPin.setVisible(false);
			sv.txtRecipient.setVisible(false);
			sv.btnCashOut.setVisible(false);
			sv.btnConfirmPin.setVisible(false);
			sv.btnConfirmTransaction.setVisible(false);
			sv.txtSearch.setVisible(true);
			sv.btnSearch.setVisible(true);
			sv.scroll.setVisible(false);
			sv.txtAccNo.setVisible(true);
			sv.btnDelAcc.setVisible(true);
			
			sv.setVisible(true);
			this.setVisible(false);
		}
	}
}