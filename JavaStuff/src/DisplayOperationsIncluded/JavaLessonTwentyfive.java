package DisplayOperationsIncluded;

import java.awt.*;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.tree.*;


//lesson about tree , most useful stuff in operating with PC
public class JavaLessonTwentyfive extends JFrame
{
	
	JButton button1 ;
	String outputString ="";
	JTree theTree;
	
	DefaultMutableTreeNode games, emails,documents,work;//demonstration trees which will represent 4 folders
	DefaultMutableTreeNode fileSystem = new DefaultMutableTreeNode("C drive");//its our root // 
	
	public static void main(String[]args)
	{
		
		new JavaLessonTwentyfive();
	}
	
	public JavaLessonTwentyfive()
	{
		this.setSize(400, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Small file explorer");
		this.setLocationRelativeTo(null);
		
		JPanel thePanel = new JPanel();
		this.add(thePanel);
		
		ListenerForButtons buttonListener = new ListenerForButtons();
		
		button1 = new JButton("Info");
		button1.setToolTipText("Represents all nformation about selected file");
		
		button1.addActionListener(buttonListener);
		thePanel.add(button1);
		
		theTree = new JTree(fileSystem);// in creating of JTree we should give as a parameter name of our DefaultMutableTreeNode 
		theTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);//defining of selection mode in tree
		theTree.setVisibleRowCount(10);
		
		documents = addAFile("docs",fileSystem);
		addAFile("Doc.txt",documents);
		addAFile("Doc2.txt",documents);
		
		emails = addAFile("emails",fileSystem);
		addAFile("email1.txt",emails);
		addAFile("email2.txt",emails);
		
		games = addAFile("games",fileSystem);
		addAFile("game1.exe",games);
		addAFile("game2.exe",games);
		
		work = addAFile("work",fileSystem);
		addAFile("proj1.pdf",work);
		addAFile("proj2.pdf",work);
		
		
		
		thePanel.add(theTree);
		
		
		
		JScrollPane scrollBar = new JScrollPane(theTree,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);//creates a scroll box with scroller
		Dimension dimension = scrollBar.getPreferredSize();
		dimension.width =200;
		scrollBar.setPreferredSize(dimension);
		thePanel.add(scrollBar);
		
		
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	public class ListenerForButtons implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == button1)
			{
				Object treeObj = theTree.getLastSelectedPathComponent();
				
				DefaultMutableTreeNode theFile = (DefaultMutableTreeNode) treeObj;//converting treeObj into DefaultMutableTreeNode
				
			    String treeNode = (String)theFile.getUserObject();//converting user object data into string
			    
			    outputString += "the Selected node : "+treeNode +"\n";
			    outputString += "number of children : "+theFile.getChildCount()+"\n";
			    outputString += "number of siblings : "+theFile.getSiblingCount()+"\n";
			    outputString += "Parent : " + theFile.getParent()+"\n";
			    outputString += "next node : "+theFile.getNextNode()+"\n";
			    outputString += "previous node : "+theFile.getPreviousNode()+"\n";
			    outputString += "\nChildren of Node\n";
				
			    for(Enumeration enumValue = theFile.children();enumValue.hasMoreElements();enumValue.nextElement())
			    {
			    	outputString += "Element : "+enumValue.nextElement() +"\n"; 
			    }
			    outputString += "\nPath from root\n" ;
			    TreeNode[] pathNodes = theFile.getPath();//it returns the array of individual nodes
			    for(TreeNode individualNode : pathNodes)
			    {
			    	outputString += individualNode +"\n";
			    }
			    
				
				JOptionPane.showMessageDialog(JavaLessonTwentyfive.this,outputString,"Info",JOptionPane.WARNING_MESSAGE);
				outputString ="";
			}
			
			
		}
		
	}
	
	public DefaultMutableTreeNode addAFile(String fileName , DefaultMutableTreeNode parentFolder)
	{
		
		DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(fileName);
		parentFolder.add(newFile);
		return newFile;
	}
}