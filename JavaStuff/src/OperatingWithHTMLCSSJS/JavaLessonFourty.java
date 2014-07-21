package OperatingWithHTMLCSSJS;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.Border;
import javax.swing.event.HyperlinkListener;
import javax.swing.*;

import java.applet.*;



//1 simple example of using java.applet.*;  Java Applets
public class JavaLessonFourty extends JApplet
{
	
	
	JPanel thePanel;
	JPanel question1Panel , question2Panel,question3Panel,question4Panel;
	
	JButton result;
	JRadioButton extravertRadio,introvertRadio,sensorRadio,intuitiveRadio,feelerRadio,
	thinkerRadio,judgingRadio,perceivingRadio;
	
	JEditorPane yourReport;
	
	public void init()
	{
		this.setSize(675,870);
		
		thePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		question1Panel = new JPanel();
		question2Panel = new JPanel();
		question3Panel = new JPanel();
		question4Panel = new JPanel();
		
		Border border1 = BorderFactory.createTitledBorder("Do you prefer to work?");
		Border border2 = BorderFactory.createTitledBorder("Which is most important?");
		Border border3 = BorderFactory.createTitledBorder("Do you act on?");
		Border border4 = BorderFactory.createTitledBorder("Which do you prefer?");
		
		question1Panel.setBorder(border1);
		question2Panel.setBorder(border2);
		question3Panel.setBorder(border3);
		question4Panel.setBorder(border4);
		
		ButtonGroup group1 = new ButtonGroup();
		ButtonGroup group2 = new ButtonGroup();
		ButtonGroup group3 = new ButtonGroup();
		ButtonGroup group4 = new ButtonGroup();
		
		
		
		
		extravertRadio = new JRadioButton("In groups");
		introvertRadio = new JRadioButton("On your own");
		sensorRadio = new JRadioButton("The specifics");
		intuitiveRadio = new JRadioButton("The big picture");
		feelerRadio = new JRadioButton("What feels right");
		thinkerRadio = new JRadioButton("List of facts");
		judgingRadio = new JRadioButton("To plan");
		perceivingRadio = new JRadioButton("To adapt");
		
		extravertRadio.setSelected(true);
		sensorRadio.setSelected(true);
		feelerRadio.setSelected(true);
		judgingRadio.setSelected(true);
		
		question1Panel.add(extravertRadio);
		question1Panel.add(introvertRadio);
		question2Panel.add(sensorRadio);
		question2Panel.add(intuitiveRadio);
		question3Panel.add(feelerRadio);
		question3Panel.add(thinkerRadio);
		question4Panel.add(judgingRadio);
		question4Panel.add(perceivingRadio);
		
		group1.add(extravertRadio);
		group1.add(introvertRadio);
		
		group2.add(sensorRadio);
		group2.add(intuitiveRadio);
		
		group3.add(feelerRadio);
		group3.add(thinkerRadio);
		
		group4.add(judgingRadio);
		group4.add(perceivingRadio);
		
		thePanel.add(question1Panel);
		thePanel.add(question2Panel);
		thePanel.add(question3Panel);
		thePanel.add(question4Panel);
		
		
		result = new JButton("Get Result");
		
		GetResultListener buttonListener = new GetResultListener();
		result.addActionListener(buttonListener);
		thePanel.add(result);
		
		this.add(thePanel);
		this.setVisible(true);
		
		
	}
	
	private class GetResultListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String pageToOpen = "";
			
			String textToDisplay = "";
			
			if(e.getSource() == result)
			{
				
				if(extravertRadio.isSelected()) pageToOpen += "E";
				if(introvertRadio.isSelected()) pageToOpen += "D";
				
				if(sensorRadio.isSelected()) pageToOpen += "A";
				if(intuitiveRadio.isSelected()) pageToOpen += "U";
				
				if(feelerRadio.isSelected()) pageToOpen += "M";
				if(thinkerRadio.isSelected()) pageToOpen += "B";
				
				if(judgingRadio.isSelected()) pageToOpen += "D";
				if(perceivingRadio.isSelected()) pageToOpen += "S";
				
				thePanel.remove(question1Panel);
				thePanel.remove(question2Panel);
				thePanel.remove(question3Panel);
				thePanel.remove(question4Panel);
				
				yourReport = new JEditorPane("text/html","<html><div><h3>APPLETS</h3><a href=\"http://google.com\"><img src=\"http://upload.wikimedia.org/wikipedia/commons/8/8e/Solna_Brick_wall_Stretcher_bond_variation1.jpg\"</a></div></html>");
				
				yourReport.setEditable(false);
				
				yourReport.setSize(650, 825);
				
				JScrollPane scroller = new JScrollPane(yourReport,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scroller.setPreferredSize(new Dimension(675,870));
				
				thePanel.add(scroller);
				
				result.setVisible(false);
				
				thePanel.revalidate();//
				thePanel.repaint();//
				
				
			}
			
		}
		
		
		
	}
	
	
	
}


