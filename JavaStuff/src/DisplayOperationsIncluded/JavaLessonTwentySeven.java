package DisplayOperationsIncluded;

import javax.swing.*;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class JavaLessonTwentySeven extends JFrame
{
	
	JButton button1 , button2, button3,button4,button5,
	button6,button7,button8,button9,button0,buttonPlus,buttonMinus,resetButton;
	JTextField result;
	String input = "";
	String typeOfAction ="";
	int allInput =0 , firstNumber =0,secondNumber =0,sum =0;
	  
	
	public static void main(String[]args)
	{
		
		new JavaLessonTwentySeven();
		
	}
	
	public JavaLessonTwentySeven()
	{
		
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Calculator");
		
		JPanel thePanel = new JPanel();
		//example of setting of GridLayout  thePanel.setLayout(new GridLayout(0,3,2,2));// defining  of  GridLayout(numberOfRows(if n = 0 , creates as much as needed rows),NumberOfColumns,horizontalGaping,verticalGaping)
		thePanel.setLayout(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 1;//defines the X position for every single component
		gridConstraints.gridy = 1;//defines the Y position for every single component
		gridConstraints.gridwidth = 1;//defines how many rows a component is gonna take up
		gridConstraints.gridheight =1; //defines how many columns a component is gonna take up
		gridConstraints.weightx = 50;
		gridConstraints.weighty = 100;
		gridConstraints.insets = new Insets(5,5,5,5);//sets padding between components
		gridConstraints.anchor = GridBagConstraints.CENTER;
		gridConstraints.fill = GridBagConstraints.BOTH;//resizes the component both vertically and horizontally
		
		
		//setting up the text field
		result = new JTextField("0",20);//JTextField("text",chractersSize);
		Font font = new Font("Arial",Font.PLAIN,18);// defining of Font method - Font("fontName",fontType(bold/italic etc),charactersSize);
		result.setFont(font);//setting of the font
		
		
		this.add(thePanel);
		
		ListenerForTheButtons buttonListener = new ListenerForTheButtons();
		
		button1 = new JButton("1");
		button1.addActionListener(buttonListener);
		button2 = new JButton("2");
		button2.addActionListener(buttonListener);
		button3 = new JButton("3");
		button3.addActionListener(buttonListener);
		button4 = new JButton("4");
		button4.addActionListener(buttonListener);
		button5 = new JButton("5");
		button5.addActionListener(buttonListener);
		button6 = new JButton("6");
		button6.addActionListener(buttonListener);
		button7 = new JButton("7");
		button7.addActionListener(buttonListener);
		button8 = new JButton("8");
		button8.addActionListener(buttonListener);
		button9 = new JButton("9");
		button9.addActionListener(buttonListener);
		button0 = new JButton("0");
		button0.addActionListener(buttonListener);
		buttonMinus = new JButton("-");
		buttonMinus.addActionListener(buttonListener);
		buttonPlus = new JButton("+");
		buttonPlus.addActionListener(buttonListener);
		resetButton = new JButton("C");
		resetButton.addActionListener(buttonListener);
		
		
		thePanel.add(resetButton,gridConstraints);
		gridConstraints.gridwidth = 20;
		gridConstraints.gridx = 5;
		thePanel.add(result,gridConstraints);
		gridConstraints.gridwidth = 1;
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 2;
		thePanel.add(button1,gridConstraints);
		gridConstraints.gridwidth = 1;
		gridConstraints.gridx = 5;
		
		thePanel.add(button2,gridConstraints);
		
		gridConstraints.gridx = 9;
		
		thePanel.add(button3,gridConstraints);
		
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 3;
		thePanel.add(button4,gridConstraints);
		
		gridConstraints.gridx = 5;
		
		thePanel.add(button5,gridConstraints);
		
		gridConstraints.gridx = 9;
		
		thePanel.add(button6,gridConstraints);
		
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 4;
		thePanel.add(button7,gridConstraints);
		
		gridConstraints.gridx = 5;
		
		thePanel.add(button8,gridConstraints);
		
		gridConstraints.gridx = 9;
		
		thePanel.add(button9,gridConstraints);
		
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 5;
		thePanel.add(button0,gridConstraints);
		
		gridConstraints.gridx = 5;
		
		thePanel.add(buttonMinus,gridConstraints);
		
		gridConstraints.gridx = 9;
		
		thePanel.add(buttonPlus,gridConstraints);

		
		
		
		
		
		
		
		
		
		
		
		
		
		this.setVisible(true);
		this.setResizable(false);
	}
	
	private class ListenerForTheButtons implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
		
			
			if(e.getSource() ==button1)
			{
				
				input +=1;
			}
			else if(e.getSource() == button2)
			{
				
				input +=2;
			}
			else if(e.getSource() == button3)
			{
				input +=3;
			}
			else if(e.getSource() == button4)
			{
				input +=4;
			}
			else if(e.getSource() ==button5)
			{
				input +=5;
			}
			else if(e.getSource() == button6)
			{
				input += 6;
			}
			else if(e.getSource() == button7)
			{
				input +=7;
			}
			else if (e.getSource() ==button8)
			{
				input +=8;
			}
			else if(e.getSource() ==button9)
			{
			   input +=9;
			}
			else if(e.getSource() == button0)
			{
				input +=0;
			}
			
			if(e.getSource()==buttonPlus)
			{
			
				
				
				
				if(incrementing(Integer.parseInt(input)) == 0)
				{
			    
				
				
				}
				else
				{
				input = Integer.toString(incrementing(Integer.parseInt(input)));
				
				}
				
				typeOfAction = "incrementing";
				
				
			}
			if(e.getSource() == resetButton)
			{
				input ="0";
				allInput =0;
				firstNumber =0;
				secondNumber =0;
				sum =0;
			}
			
			result.setText(input);
			
		}
		private int incrementing(int Input)
		{
			
			if(firstNumber == 0)
			{
				firstNumber = Input;
				input = "";
				System.out.println("First Number: "+firstNumber);
				return 0;
			}
			else if(firstNumber != 0 && secondNumber ==0)
			{
				secondNumber = Input;
				input = "";
				System.out.println("Seconds number : "+secondNumber);
				return 0;
			}
			if(firstNumber !=0 && secondNumber != 0)
			{
				sum = firstNumber + secondNumber;
				
				return sum;
			}
			else
			{
				return 0;
			}
			
			
		}
		
	}
	
}