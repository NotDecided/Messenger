package DisplayOperationsIncluded;


import java.awt.*;

import javax.management.InstanceNotFoundException;
import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class SimpleNoteWriterReader extends JFrame
{
	
	
	JButton createNote,openNote,deleteNote;
	JTextField fileNameCreating,fileNameSearching;
	JTextArea NoteCreating,NoteReading;
	JLabel createName,readName,createArea,readArea;
	JButton editButton ;
	
	public static void main(String[]args)
	{
		
		new SimpleNoteWriterReader();
		
		
	}
	
	public SimpleNoteWriterReader()
	{
		this.setSize(new Dimension(600,400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Note Writer/Reader");
		this.setLocationRelativeTo(null);
		
		JPanel thePanel = new JPanel();
		thePanel.setLayout(null);
		
		ListenerForButtons buttonListener = new ListenerForButtons();
		
		
		createNote = new JButton("Create");
		createNote.setToolTipText("Press \"Create\" button when you are ready to save your note");
		createNote.addActionListener(buttonListener);
		thePanel.add(createNote);
		openNote = new JButton("Open");
		openNote.setToolTipText("Press \"Open\" button when you've put desired note name in required field ");
		openNote.addActionListener(buttonListener);
		fileNameCreating = new JTextField();
		fileNameCreating.setPreferredSize(new Dimension(200,20));
		thePanel.add(fileNameCreating);
		fileNameSearching = new JTextField();
		fileNameSearching.setPreferredSize(new Dimension(200,20));
		thePanel.add(fileNameSearching);
		NoteCreating = new JTextArea(5,20);
		NoteCreating.setWrapStyleWord(true);
		NoteCreating.setLineWrap(true);
		thePanel.add(NoteCreating);
		NoteReading = new JTextArea(5,20);
		NoteReading.setWrapStyleWord(true);
		NoteReading.setLineWrap(true);
		thePanel.add(NoteReading);
		thePanel.add(openNote);
		createName = new JLabel("Name Of The New File");
		thePanel.add(createName);
		readName = new JLabel("Name Of The Existing File");
		thePanel.add(readName);
		createArea = new JLabel("Write Your Note Below");
		thePanel.add(createArea);
		readArea = new JLabel("Read And Make Your Changes Below");
		thePanel.add(readArea);
		
		editButton = new JButton("Edit");
		editButton.setToolTipText("Press \"Edit\" when you've made some changes in existing note and want to save them");
		editButton.addActionListener(buttonListener);
		thePanel.add(editButton);
		deleteNote = new JButton("Delete");
		deleteNote.setToolTipText("Press \"Delete\" when you've put file name in the proper field and ready to delete it");
		deleteNote.addActionListener(buttonListener);
		thePanel.add(deleteNote);
		
		Insets insets = thePanel.getInsets();
		Dimension size = createNote.getPreferredSize();
		createNote.setBounds(500 +insets.left, 30 + insets.top, size.width, size.height);
		size = openNote.getPreferredSize();
		openNote.setBounds(500+insets.left,230+insets.top,size.width,size.height);
		size = editButton.getPreferredSize();
		editButton.setBounds(500+insets.left, 270+insets.top, size.width, size.height);
		size = deleteNote.getPreferredSize();
		deleteNote.setBounds(500+insets.left,300+insets.top,size.width,size.height);
		size = fileNameCreating.getPreferredSize();
		fileNameCreating.setBounds(180+insets.left,20+insets.top,size.width,size.height);
		size = fileNameSearching.getPreferredSize();
		fileNameSearching.setBounds(180+insets.left,200+insets.top,size.width,size.height);
		size = NoteCreating.getPreferredSize();
		NoteCreating.setBounds(170+insets.left,50+insets.top,size.width,size.height);
		size = NoteReading.getPreferredSize();
		NoteReading.setBounds(170+insets.left,50+insets.top,size.width,size.height);
		size = createName.getPreferredSize();
		createName.setBounds(215+insets.left,insets.top,size.width,size.height);
		size = readName.getPreferredSize();
		readName.setBounds(210+insets.left,175 +insets.top,size.width,size.height);
		size = createArea.getPreferredSize();
		createArea.setBounds(215+insets.left,40+insets.top,size.width,size.height);
		size = readArea.getPreferredSize();
		readArea.setBounds(175+insets.left,220+insets.top,size.width,size.height);
		
		JScrollPane scrollBar = new JScrollPane(NoteCreating,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		size =scrollBar.getPreferredSize();
		scrollBar.setBounds(170+insets.left,60+insets.top,size.width,size.height);
		thePanel.add(scrollBar);
		
		JScrollPane scrollBar2 = new JScrollPane(NoteReading,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		size = scrollBar2.getPreferredSize();
		scrollBar2.setBounds(170+insets.left,240+insets.top,size.width,size.height);
		thePanel.add(scrollBar2);
		
		
		this.add(thePanel);

		
		
		this.setResizable(false);
		this.setVisible(true);
		
	}
	private class ListenerForButtons implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()== createNote)
			{
				createNote();
			}
			else if(e.getSource() == openNote)
			{
				readNote();
			}
			else if(e.getSource() ==editButton)
			{
				editNote();
			}
			else if(e.getSource() ==deleteNote)
			{
				deleteNote();
			}
		}
		
	}
	
	private void createNote()
	{
		String fileName = fileNameCreating.getText();
		
		File newFile= new File(fileName+".txt");
	
		
		try
		{
			newFile.createNewFile();
			
			
			
			PrintWriter fileinput = new PrintWriter(
					new BufferedWriter(
							new FileWriter(
									newFile)));
			
			fileinput.println(NoteCreating.getText());
			fileinput.close();
			fileNameCreating.setText("");
			NoteCreating.setText("");
			String message = "File "+newFile.getName() +" was created and stored at "+newFile.getCanonicalPath();
			JOptionPane.showMessageDialog(SimpleNoteWriterReader.this,message , "Created Successfully", JOptionPane.INFORMATION_MESSAGE);
			
		}
		catch(IOException e)
		{
			System.out.println("An IO/Error ocurred");
		}
		
		
		
		
	}
	private void readNote()
	{
		String fileName = fileNameSearching.getText();
		File newFile = new File(fileName+".txt");
		
		try
		{
			BufferedReader readTheNote = new BufferedReader(
					new FileReader(newFile));
			String readingOfNote = readTheNote.readLine();
			NoteReading.setText(readingOfNote);
			
			
		}
		catch(IOException e)
		{
			
		}
		/*
		catch(FileNotFoundException e)
		{
			System.out.println("File wasn't found :(");
			System.exit(0);
		}*/
		
		
		
	}
	private void editNote()
	{
		String fileName = fileNameSearching.getText();
		File newFile = new File(fileName +".txt");
		
		try
		{
			PrintWriter editing = new PrintWriter(
					new BufferedWriter(new FileWriter(newFile)));
			
			editing.println(NoteReading.getText());
			editing.close();
			
		}
		catch (IOException e)
		{
			System.out.println("An IOException error ocurred");
		}
		
		
		
	}
	
	private void deleteNote()
	{

			String fileName = fileNameSearching.getText();
			File fileToDelete = new File(fileName +".txt");
			fileToDelete.delete();
			fileNameSearching.setText("");
		    NoteReading.setText("");
		    String message = "File "+fileToDelete.getName() +" was successfully deleted ";
			JOptionPane.showMessageDialog(SimpleNoteWriterReader.this, message, "Deleted",JOptionPane.INFORMATION_MESSAGE);
	}
	
	
}