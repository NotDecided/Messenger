package DisplayOperationsIncluded;

import javax.swing.*;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;


public class JavaLessonTwentysix extends JFrame
{
	
	JButton button1 , button2 , button3,button4,button5;
	
	public static void main(String[]args)
	{
		
		new JavaLessonTwentysix();
	}
	
	public JavaLessonTwentysix()
	{
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(" ");
		this.setLocationRelativeTo(null);
		
		
		/*
		 * 
		 * // FLOW LAYOUT //
		
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,100));//defines the position for layout FlowLayout(FlowLayout.LEFT(.LEFT,.RIGHT,.CENTER),horizontalGapping ,VerticalGapping)
		
		button1 = new JButton("Click");
	    button2 = new JButton("Click");
	    button3 = new JButton("Click");
	    button4 = new JButton("Click");
		thePanel.add(button1);
		thePanel.add(button2);
		thePanel.add(button3);
		thePanel.add(button4);
		
		this.add(thePanel);
		*/
		
		
		
		
		/*
		 * 
		 * //Border layout
		 
		 
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new BorderLayout());
		
		button1 = new JButton("Click");
	    button2 = new JButton("Click");
	    button3 = new JButton("Click");
	    button4 = new JButton("Click");	
	    button5 = new JButton("Click");
		
	    thePanel.add(button1,BorderLayout.EAST);
	    thePanel.add(button2,BorderLayout.WEST);
	    thePanel.add(button3,BorderLayout.SOUTH);
	    thePanel.add(button4,BorderLayout.NORTH);
	    thePanel.add(button5,BorderLayout.CENTER);
		
		this.add(thePanel);
		//*/
		
		
		//USING OF BOX METHOD
		Box theBox = Box.createHorizontalBox();
		
		button1 = new JButton("Click");
	    button2 = new JButton("Click");
	    button3 = new JButton("Click");
	    button4 = new JButton("Click");	
	    button5 = new JButton("Click");
	    
	    //theBox.add(Box.createHorizontalStrut(10));//you can separate elements in box by using this method but it's not a perfect method
	    
	    //instead you can use glue method
	    
	    //theBox.add(Box.createHorizontalGlue());//moves the body maximally to right side
	    
	    
	    //theBox.add(Box.createRigidArea(new Dimension(30,50)));//or we can manually set the gaping by using .createRigidArea(new Dimension(Xpos,Ypos)) method
	    
	    theBox.add(button1);
		
		//theBox.add(Box.createHorizontalGlue());
		
		//theBox.add(Box.createHorizontalStrut(10));//you can separate elements in box by using this method but it's not a perfect method
	    
	    //theBox.add(Box.createRigidArea(new Dimension(30,50)));//or we can manually set the gaping by using .createRigidArea(new Dimension(Xpos,Ypos)) method
	    
		theBox.add(button2);
	
		//theBox.add(Box.createHorizontalGlue());
		
		//theBox.add(Box.createHorizontalStrut(10));//you can separate elements in box by using this method but it's not a perfect method
		
		//theBox.add(Box.createRigidArea(new Dimension(30,50)));//or we can manually set the gaping by using .createRigidArea(new Dimension(Xpos,Ypos)) method
		
		theBox.add(button3);
	
		//theBox.add(Box.createHorizontalGlue());
		
		//theBox.add(Box.createHorizontalStrut(10));//you can separate elements in box by using this method but it's not a perfect method
		
		//theBox.add(Box.createRigidArea(new Dimension(30,50)));//or we can manually set the gaping by using .createRigidArea(new Dimension(Xpos,Ypos)) method
		
		theBox.add(button4);
		
		//theBox.add(Box.createHorizontalStrut(10));//you can separate elements in box by using this method but it's not a perfect method
		
		//theBox.add(Box.createHorizontalGlue());
		
		//theBox.add(Box.createRigidArea(new Dimension(30,50)));//or we can manually set the gaping by using .createRigidArea(new Dimension(Xpos,Ypos)) method
		
		theBox.add(button5);
		
	    this.add(theBox);
	    
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
}