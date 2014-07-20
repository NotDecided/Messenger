package DisplayOperationsIncluded;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public class JavaLessonTwentythree extends JFrame
{
	
	JButton button1;
	String infoOnComponent = "";
	JList advancedList , usingDefaultListModel;//can be applied only on arrays because it lists all data from the array 
	
	DefaultListModel defaultList = new DefaultListModel();
	JScrollPane scrollBar;
	
	
	public static void main(String[]args)
	{
		
		new JavaLessonTwentythree();
		
	}
	
	public JavaLessonTwentythree()
	{
		this.setSize(400,800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Demonstration of JList and comparing it to DefaultListModel");
		
		JPanel thePanel = new JPanel();
		this.add(thePanel);
		
		ListenerForButtons buttonListener = new ListenerForButtons();
		button1 = new JButton("Click");
		button1.setToolTipText("Click here if you wanna see all items from the list");//tool tip(hint) for certain object , very useful if you wanna give some info to user about certain object
		button1.addActionListener(buttonListener);
		thePanel.add(button1);
		
		String[] listOfRandomStuff = {"How","Powerful","is","Java","?","JAva","Java","Java"};
		advancedList = new JList(listOfRandomStuff);
		advancedList.setFixedCellHeight(30);//the height and width here are defining in characters
		advancedList.setFixedCellWidth(150);
		
		advancedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		thePanel.add(advancedList);
		
		for(String i : listOfRandomStuff)
		{
			defaultList.addElement(i);
		}
		
		usingDefaultListModel = new JList(defaultList);
		usingDefaultListModel.setVisibleRowCount(4);
		usingDefaultListModel.setFixedCellHeight(30);
		usingDefaultListModel.setFixedCellWidth(150);
		
		thePanel.add(usingDefaultListModel);
		
		scrollBar = new JScrollPane(usingDefaultListModel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		thePanel.add(scrollBar);
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public class ListenerForButtons implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(defaultList.contains("JAva"))
			{
				infoOnComponent += "JAva is here\n";
			}
			if(!defaultList.isEmpty())
			{
				infoOnComponent +="isn't empty\n";
			}
			
			infoOnComponent += "Elements in the List : " + defaultList.size() +"\n";
			infoOnComponent += "Last element - "+defaultList.lastElement() + "\n";
			infoOnComponent += "First element - "+defaultList.firstElement() +"\n";
			
			defaultList.remove(0);
			defaultList.removeElement("Java");
			
			Object[] array = defaultList.toArray();
			for(Object word : array)
			{
				infoOnComponent += word +"\n";
			}
			
			JOptionPane.showMessageDialog(JavaLessonTwentythree.this, infoOnComponent,"Information",JOptionPane.INFORMATION_MESSAGE);
			
			infoOnComponent = "";
			
		}
		
	}
	
}