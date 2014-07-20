package DisplayOperationsIncluded;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.Calendar;



//all types of swings in Java
public class JavaLessonTwentyfour extends JFrame
{
	
	JButton button1 ;
	JSpinner spinner1,spinner2,spinner3,spinner4;
	String info;
	
	
	
	public static void main(String[]args)
	{
		new JavaLessonTwentyfour();
	}
	
	public JavaLessonTwentyfour()
	{
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setTitle("Different types of swings");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel thePanel = new JPanel();
		this.add(thePanel);
		
		
		ListenerForButtons buttonListener = new ListenerForButtons();
		button1 = new JButton("Info");
		button1.addActionListener(buttonListener);
		button1.setToolTipText("Shows all info about spinner values");
		thePanel.add(button1);
		
		ListenerForSpinners spinnerListener = new ListenerForSpinners();
		
		spinner1 = new JSpinner();
		thePanel.add(spinner1);
		
		spinner2 = new JSpinner(new SpinnerNumberModel(1,1,100,1));//defining of SpinnerNumberModel(initialNumber,minimumNumber,maximumNumber,numberOfEveryInvrement);
		thePanel.add(spinner2);
		
		String[] daysOfWeek = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
		
		spinner3 = new JSpinner(new SpinnerListModel(daysOfWeek));
		thePanel.add(spinner3);
		
		//setting of preferred size by using Dimension class very useful
		Dimension dimension = spinner3.getPreferredSize();
		dimension.width = 80;
		spinner3.setPreferredSize(dimension);
		
		Date todaysDate = new Date();//it automatically gives me todays day
		
		spinner4 = new JSpinner(new SpinnerDateModel(todaysDate,null,null,Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner4,"dd/MM/yy");
		spinner4.addChangeListener(spinnerListener);
		spinner4.setEditor(dateEditor);
		thePanel.add(spinner4);
		
		
		
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public class ListenerForButtons implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == button1)
			{
				
				info += "Spinner1 value = "+spinner1.getValue() +"\nSpinner2 value = "+spinner2.getValue() + "\nSpinner3 value = "+spinner3.getValue()+"\nSpinner4 value = " + spinner4.getValue() ;
			    JOptionPane.showMessageDialog(JavaLessonTwentyfour.this,info,"Short info about spinners",JOptionPane.WARNING_MESSAGE); 
			    info = "";
				
			}
		}
		
	}
	
	public class ListenerForSpinners implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e) {
			
			
		}
		
	}
	
}