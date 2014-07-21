package WritingReadingXMLFiles;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdom2.input.*;
import org.jdom2.output.*;
import org.jdom2.*;

import DisplayOperationsIncluded.SimpleNoteWriterReader;



public class JavaXMLNoteCreator  
{
	
	
	
	JButton createButton,readButton;
	JTextField createTextField,readTextField;
	JTextArea createTextArea,readTextArea;
	
	
	public static void main(String[]args)
	{

		JFrame frame = new JFrame();
		createXMLFile();
		new JavaXMLNoteCreator(frame);

		
	}
	

    public static void createXMLFile()
    {
    	
    	File create = new File("./src/WritingReadingXMLFiles/NoteStorage.xml");
    	if(!create.exists())
    	{
    	
    	try
    	{
    		
    		Document doc = new Document();
    		
    		
    		Element rootElement = new Element("notes");
    		
    		doc.setRootElement(rootElement);
    		
    		
    		XMLOutputter output = new XMLOutputter(Format.getCompactFormat());
    		output.output(doc, new FileOutputStream(create));
    		
    		
    	
    		
    		
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	System.out.println("XMLFile is created");
    	}
    	else
    	{
    		System.out.println("XMLFile exists");
    	}
    	
    }
	public JavaXMLNoteCreator(JFrame frame) 
	{
		
		

		frame.setTitle("Advanced Note Creator");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(800,400);
		
		frame.setVisible(true);
		
		frame.setResizable(false);
		
		
        JPanel thePanel = new JPanel();
		
		
		
        ListenerForButtons buttonListener = new ListenerForButtons();
        
		
		createTextField = new JTextField();
		createTextField.setPreferredSize(new Dimension(250,30));
		createTextField.setFont(new Font("Arial",Font.BOLD,25));
		thePanel.add(createTextField);
		
		createButton = new JButton("Create");
		createButton.setPreferredSize(new Dimension(95,30));
		createButton.addActionListener(buttonListener);
		thePanel.add(createButton);
		
		readTextField = new JTextField();
		readTextField.setPreferredSize(new Dimension(250,30));
		readTextField.setFont(new Font("Arial",Font.BOLD,25));
		thePanel.add(readTextField);
		
		readButton = new JButton("Read");
		readButton.setPreferredSize(new Dimension(95,30));
		readButton.addActionListener(buttonListener);
		thePanel.add(readButton);
		
		createTextArea = new JTextArea(15,24);
		createTextArea.setWrapStyleWord(true);
		createTextArea.setLineWrap(true);
		//createTextArea.setPreferredSize(new Dimension(350,300));
		createTextArea.setFont(new Font("Arial",Font.PLAIN,18));
		thePanel.add(createTextArea);
		
		readTextArea = new JTextArea(15,24);
		readTextArea.setLineWrap(true);
		readTextArea.setWrapStyleWord(true);
		//readTextArea.setPreferredSize(new Dimension(350,300));
		readTextArea.setFont(new Font("Arial",Font.PLAIN,18));
		thePanel.add(readTextArea);		
		
		JScrollPane scroller1 = new JScrollPane(createTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		thePanel.add(scroller1);
		JScrollPane scroller2 = new JScrollPane(readTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		thePanel.add(scroller2);
		
		
		frame.setLocationRelativeTo(null);
		
		frame.add(thePanel);
		
		
	}


	private class ListenerForButtons implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == createButton)
			{
				String cNoteName = createTextField.getText();
				String cNoteText = createTextArea.getText();
				CreateNote(cNoteName,cNoteText);
				
			}
			else if(e.getSource() == readButton)
			{
				String rNoteName = readTextField.getText();
				ReadNote(rNoteName);
				
			}
			
		}
	
		
		
	}
	
	
	public void CreateNote(String nameOfTheNote, String noteText)
	{
		
		
		
		try
		{
			
		SAXBuilder builder = new SAXBuilder();
		File XMLStorage = new File("./src/WritingReadingXMLFiles/NoteStorage.xml");
		Document createNewNoteDoc = (Document)builder.build(XMLStorage);
		
		Element theRootElement = createNewNoteDoc.getRootElement();
		
		
		if(theRootElement.getChild(nameOfTheNote) == null)
		{
		Element note = new Element(nameOfTheNote);
		//note.setAttribute(, nameOfTheNote);
		note.setContent(new Text(noteText));
		
		theRootElement.addContent(note);//adding element(node) to the root node
		
		JOptionPane.showMessageDialog(null,"The Note " +nameOfTheNote +" Was Stored Into "+ XMLStorage.getName(),"Saving Completed",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
		
			JOptionPane.showMessageDialog(null,"The File With The Following Name Exists \n Try Another One","Error",JOptionPane.ERROR_MESSAGE);
			createTextField.setText("");
			createTextArea.setText("");
		}
		
		XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
		xmlWriter.output(createNewNoteDoc, new FileOutputStream(XMLStorage));
		
		
		
		
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		
	}
	public void ReadNote(String nameOfTheNote)
	{
		
		try
		{
			
			SAXBuilder builder = new SAXBuilder();
			Document readDoc = (Document)builder.build(new File("./src/WritingReadingXMLFiles/NoteStorage.xml"));
			
			Element rootElement = readDoc.detachRootElement();
			
		    
			
			if(rootElement.getChild(nameOfTheNote) != null)
			{
			readTextArea.setText(rootElement.getChildText(nameOfTheNote));
			}
			else
			{
				ArrayList list = new ArrayList();
				for(Element noty:rootElement.getChildren())
				{
		    		System.out.println(noty.getName());//lists all notes (all note names ) which are stored into out XML file
		    	    list.add(noty.getName());
				}
			 JOptionPane.showMessageDialog(null,"The Note With The Name " + nameOfTheNote+ " Doesn't Exist \n Choose One Of The Following Notes \n"+list,"Error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		
		
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	
	
}