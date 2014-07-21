package OperatingWithHTMLCSSJS;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.*;
import javax.xml.ws.http.HTTPException;



public class HTMLreadingInJava extends JFrame implements HyperlinkListener,ActionListener
{
	
	
	
	
	public static void main(String[]args)
	{
		
		new HTMLreadingInJava();
		
	}
	
	
	
	String defaultURL;
	JTextField URLInput = new JTextField(25);
	JPanel toolPanel = new JPanel();
	JEditorPane htmlPage;
	
	public HTMLreadingInJava()
	{
		
		JFrame frame = new JFrame("Java Browser");
		
		String URLAdress = "http://google.com";
		this.defaultURL = URLAdress;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		URLInput.addActionListener(this);
		URLInput.setText(defaultURL);
		
		toolPanel.add(URLInput);
		
		frame.add(toolPanel,BorderLayout.NORTH);
		
		try
		{
		
			htmlPage = new JEditorPane(defaultURL);
			
			htmlPage.addHyperlinkListener(this);
			
			htmlPage.setEditable(false);
			
			JScrollPane scrollPane = new JScrollPane(htmlPage);
			
			frame.add(scrollPane,BorderLayout.CENTER);
			
		}
		catch(IOException e)
		{
			
			e.printStackTrace();
			
		}
		
		
		frame.setSize(1200,800);
		frame.setVisible(true);
		
		this.add(frame);
		
	}
	
	
	
	
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		
		if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
		{
			try
			{
				
				htmlPage.setPage(e.getURL());//returns the URL on which I've clicked 
				
				
			} 
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			}
			
			URLInput.setText(e.getURL().toExternalForm());//it creates a string representation for my URL(after it'll get this URL)
			
		}
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String pageURL = "";
		
		if(e.getSource() == URLInput)
		{
			if(URLInput.getText().contains("http://"))
			{
			pageURL = URLInput.getText(); 
			}
			else
			{
				pageURL = "http://" + URLInput.getText();
				URLInput.setText("http://"+URLInput.getText());
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Please Enter The Web Adress","Error",JOptionPane.ERROR_MESSAGE);
			
		}
		
		try 
		{
			htmlPage.setPage(new URL(pageURL));
			
			
		} 
		catch (IOException e1) {
			
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Please Use http://","Error",JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

	
	
	
	
}