package DisplayOperationsIncluded;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.text.NumberFormat;

import javax.swing.border.*;

public class JavaLessonTwentyTwo extends JFrame
{
	
	JButton button1;
	JLabel label1 , label2,label3;
	JTextField textField1 , textField2;
	JCheckBox dollarSign , commaSeparator;
	JRadioButton addNums,subtractNums,MultNums,divideNums;
	JSlider howManyTimes;
	double number1 ,number2 , totalCalc;
	
	public static void main(String[]args)
	{
		
		new JavaLessonTwentyTwo();
	}
	
	public JavaLessonTwentyTwo()
	{
		this.setSize(400,400);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		
		int Xpos = (dimension.width/2) - ( this.getWidth() /2);
		int Ypos = (dimension.height /2) -(this.getHeight() /2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(Xpos, Ypos);
		
		this.setTitle("Little Converter");
		JPanel thePanel = new JPanel();//creates a field on which all other objects will be placed 
		this.add(thePanel);
		
		
		ListenerForButtons buttonListener = new ListenerForButtons();
		
		button1 = new JButton("Calculate");
		button1.addActionListener(buttonListener);
		thePanel.add(button1);
		
		label1 = new JLabel("Number 1");
		thePanel.add(label1);
		
		textField1 = new JTextField("",5);
		
		thePanel.add(textField1);
		
		label2 = new JLabel("Number 2");
		thePanel.add(label2);
		
		textField2 = new JTextField("",5);
		thePanel.add(textField2);
		
		dollarSign = new JCheckBox("Dollars");
		commaSeparator = new JCheckBox("Commas");
		thePanel.add(dollarSign);
		thePanel.add(commaSeparator,true);
		
		addNums = new JRadioButton("Add");
		subtractNums = new JRadioButton("Subtract");
		MultNums = new JRadioButton("Divide");
		divideNums = new JRadioButton("Multiply");
		
		
		
		//creating a button group and adding all radio buttons into it
		ButtonGroup buttonGroup = new ButtonGroup();//ButtonGroup provides us an ability for switching to true
		// only 1 button from button group at the same time , which means that we can have only one button switched on 
		//very useful class(ButtonGroup)
		
		buttonGroup.add(addNums);
		buttonGroup.add(subtractNums);
		buttonGroup.add(divideNums);
		buttonGroup.add(MultNums);
		
		JPanel buttonGroupPanel = new JPanel();//creating separate panel for radio buttons
		
		Border buttonGroupBorder = BorderFactory.createTitledBorder("Opertaion");//creating the border for buttonGroupPanel
		
		
		buttonGroupPanel.setBorder(buttonGroupBorder);//applying a border to buttongroupPanel
		
		//adding all radio buttons to the buttonGroupPanel 
		buttonGroupPanel.add(addNums);
		buttonGroupPanel.add(subtractNums);
		buttonGroupPanel.add(divideNums);
		buttonGroupPanel.add(MultNums);
		
		addNums.setSelected(true);//setting addNums radio button to true as the default
		
		thePanel.add(buttonGroupPanel);//adding buttonGroupPanel to the main panel
		
		label3 = new JLabel("Preform How Many Times");
		
		ListenerForTheSlider sliderListener  = new ListenerForTheSlider();
		
		howManyTimes =new  JSlider(0,99,1);//defining of JSlider (minimumValue,maximumValue,initialValue);
		howManyTimes.setMinorTickSpacing(1);//creates a tiny sticks on slider as indicators
		howManyTimes.setMajorTickSpacing(10);//creates a larder sticks on slider as indicators
		howManyTimes.setPaintTicks(true);//it draws all ticks on the screen
		howManyTimes.setPaintLabels(true);//it draws all numbers underneath ticks
		howManyTimes.addChangeListener(sliderListener);//it means that it's going to alert me every single time the slider is changed
		
		thePanel.add(howManyTimes);
		
		
		
		this.setResizable(false);
		this.setVisible(true);
		
		textField1.requestFocus();
	}
	
	private class ListenerForButtons implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == button1)
			{
				try
				{
					number1 = Double.parseDouble(textField1.getText());//converting the string obtained from textField1 into the double value for the following dealing with number1(double)
					number2 = Double.parseDouble(textField2.getText());//converting the string obtained from textField2 into the double valur for the following dealing with number2(double)
				}
				catch(NumberFormatException exception)
				{
					//defining JOptionPane.showMessageDialog(nameOfTheClass,"Message","Title",JOptionPane.iconType(.ERROR_MESSAGE , .QUESTION_MASSAGE,PLAIN_MESSAGE));
					//which will pop up when we will catch an error
					JOptionPane.showMessageDialog(JavaLessonTwentyTwo.this,"Please Enter The Right Info","Error",JOptionPane.ERROR_MESSAGE);
					System.exit(0);//it closes the whole entire java app
					
				}
				if(addNums.isSelected())
				{
					totalCalc = addNumbers(number1 , number2 , howManyTimes.getValue());
				}
				else if(subtractNums.isSelected())
				{
					totalCalc = subtractNumbers(number1 , number2 ,howManyTimes.getValue());
				}
				else if(MultNums.isSelected())
				{
					totalCalc = multiplyNumbers(number1,number2,howManyTimes.getValue());
				}
				else if(divideNums.isSelected())
				{
					totalCalc = divideNumbers(number1 , number2,howManyTimes.getValue());
				}
			}
			
			if(dollarSign.isSelected())
			{
				NumberFormat numFormat = NumberFormat.getCurrencyInstance();
				JOptionPane.showMessageDialog(JavaLessonTwentyTwo.this,numFormat.format(totalCalc),"Solution",JOptionPane.WARNING_MESSAGE);
			}
			else if(commaSeparator.isSelected())
			{
				NumberFormat numFormat = NumberFormat.getNumberInstance();
				JOptionPane.showMessageDialog(JavaLessonTwentyTwo.this,numFormat.format(totalCalc),"Solution",JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(JavaLessonTwentyTwo.this,totalCalc,"Solution",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		
		
	}
	
	private class ListenerForTheSlider implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e) {
			
			if(e.getSource() == howManyTimes)
			{
				label3.setText("PerformHowManyTimes :" +howManyTimes.getValue());
				
			}
			
		}
		
		
	}
	
	public static double addNumbers(double number1 ,double number2 ,double howManyTimes)
	{
		double total = 0;
		total = (number1 + number2)*howManyTimes;
		return total;
		
		
		
	}
	public static double subtractNumbers(double number1 , double number2 , double howManyTimes)
	{
		
		double total = 0;
		total =(number1-number2);
		return total;
	}
	public static double multiplyNumbers(double number1 , double number2 , double howManyTimes)
	{
		double total = 0;
		total =(number1 *number2);
		return total;
				
	}
	public static double divideNumbers(double number1 , double number2 , double howManyTimes)
	{
		double total = 0;
		total = (number1/number2 );
		return total;
	}
}