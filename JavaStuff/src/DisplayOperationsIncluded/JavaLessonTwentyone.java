package DisplayOperationsIncluded;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import java.awt.Event.*;//
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;



// GUI
public class JavaLessonTwentyone extends JFrame // so it can create frames and operate with them
{
	
	JButton button1;
	JTextField textField1;
	JTextArea textArea1;
	int buttonClicked;
	
	public static void main(String[]args)
	{
		
		new JavaLessonTwentyone();
		
		
	}
	
	public JavaLessonTwentyone()
    {
	
		this.setSize(400,800);
		
		
		Toolkit tk  = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();
		
		
		int Xpos = (dimension.width/2) - (this.getWidth()/2);
		int Ypos = (dimension.height/2) -(this.getHeight()/2);
		
		this.setLocation(Xpos,Ypos);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JavaLessonTwentyone");
		
		JPanel thePanel = new JPanel();
		this.add(thePanel);
		
		button1 = new JButton("My button");
        ListenerForButton Buttonlistener = new ListenerForButton();
		button1.addActionListener(Buttonlistener);//here we are simply adding action listener for our button
        
		thePanel.add(button1);
		
		textField1 = new JTextField(" ",15);
		ListenForKeys keyListener = new ListenForKeys();
		textField1.addKeyListener(keyListener);
	    thePanel.add(textField1);
		
	    textArea1 = new JTextArea(15,20);
	 
	    textArea1.setWrapStyleWord(true);
	    textArea1.setLineWrap(true);
	 
	    thePanel.add(textArea1);
	    textArea1.setText("");
	    JScrollPane scrollBar= new JScrollPane(textArea1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    thePanel.add(scrollBar);//adding a scroll bar
	    
	
		
	    ListenerForWindow windowListener = new ListenerForWindow();
	    ListenerForMouse mouseListener = new ListenerForMouse();
	    ListenerForFocus focusListener = new ListenerForFocus();
	    this.addFocusListener(focusListener);
	    
	    this.addWindowListener(windowListener);
		
		this.setResizable(false);//preventing from resize
		this.setVisible(true);//sets visibility to true in order to display it
		
		
    }
	private class ListenerForButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			if(e.getSource() == button1)
			{
				buttonClicked++;
				textArea1.append("Button clicked : "+buttonClicked +"times\n");
				
				
			}
		}

		
	}
	private class ListenForKeys implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent e) {
			
			textArea1.append("Key hit : "+e.getKeyChar() + "\n");
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class ListenerForWindow implements WindowListener
	{

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			textArea1.append("Window is active");
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			System.out.println("Windows is minimized");
			textArea1.append("Window is minimized");
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			textArea1.append("Window is created");
			
		}
		
	}
	private class ListenerForMouse implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class ListenerForFocus implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e) {
			textArea1.append("You are focused on " +e.getSource());
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			textArea1.append("You are no longer focused on " +e.getSource());
			
		}
		
	}
	
}