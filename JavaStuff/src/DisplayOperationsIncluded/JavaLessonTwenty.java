package DisplayOperationsIncluded;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class JavaLessonTwenty extends JFrame//class which will allow us to work with frames
{
	
	JButton button1;
	JTextArea textArea1;
	
	public static void main(String[]args)
	{
		
		new JavaLessonTwenty();//we are creating new constructor for this class in order to be allowed to use all references which belong to this class by using  this. 
		
	}
	public JavaLessonTwenty()
	{
		
		this.setSize(400,800);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();
		
		int Xpos = (dimension.width/2) - (this.getWidth()/2);
		int Ypos = (dimension.height /2) - (this.getHeight()/2);
	
		//this.setLocationRelativeTo(null);
		this.setLocation(Xpos,Ypos);
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//killing the process after hitting the close button
		this.setTitle("JAVA rules anyway");
		
		
		
		JPanel thePanel = new JPanel();//creates a panel which will be surrounding 
		this.add(thePanel);
		
		//creating of the label
		JLabel label1 = new JLabel("******* Some info ********");
		thePanel.add(label1);
		label1.setText("new text");
		label1.setToolTipText("Hint for this object(label)");//create a tip(hint) for certain object when your mouse will be over that object which is displayed on screen
		
		//creating of the button
		button1 = new JButton("Send");
		button1.setText("My Send request button");
		button1.setToolTipText("Hint (Tip) for htis button");	
		ListenerForButton buttonListener = new ListenerForButton();
		button1.addActionListener(buttonListener);
		thePanel.add(button1);//adding the button to the panel in order to display it
		
		//creating of the text field
		JTextField textField1 = new JTextField("Type here",15);
		textField1.setText("Type here again");
		textField1.setToolTipText("Hint(tip) for this text field");
		thePanel.add(textField1);
		
		//creating of flexible text area
		textArea1 = new JTextArea(15,20);//setting the size of text area
		
		textArea1.setText("a bunch of people always argue with each other "
				+ ", if we will consider this fact as a valuable fact and if we'll "
				+ "try to stop that we can help so many people from controvercys");
		textArea1.setLineWrap(true);//it skips the text to the next line if the text crossing the border 
		textArea1.setWrapStyleWord(true);//it prevents the word from splitting
		int NumberOfLines = textArea1.getLineCount();//it gets total number of lines
		
		textArea1.append("\nNum of lines : " + NumberOfLines);
		thePanel.add(textArea1);
		JScrollPane scrollBar1=new JScrollPane(textArea1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);//creating our scroll bar and setting its parameters
		thePanel.add(scrollBar1);
		
		
		textField1.requestFocus();//focusing on certain object
		
		
		
		this.setResizable(false);
		this.setVisible(true);
		
		
		
	}
	private class ListenerForButton implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == button1)
			{
				if(button1.getText() != "ha-ha")
				{
					button1.setText("ha-ha");
					textArea1.append("\nJava rules anyway");
				}
				else if(button1.getText() == "ha-ha")
				{
					button1.setText("click here");
					textArea1.append("\n and that's basically true!");
				}
			}
			
		}
		
	}
	
}