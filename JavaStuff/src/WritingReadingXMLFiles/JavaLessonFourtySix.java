package WritingReadingXMLFiles;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom2.*;
import org.jdom2.adapters.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class JavaLessonFourtySix
{
	
	public static void main(String[]args)
	{
		
		//WriteXML();
		EditXML();
		ReadXML();
		
	}
	
	private static void WriteXML()
	{
		
		try
		{
		
		
			
		//creating of new document instance as usually
		Document doc = new Document();
		
		
		
		//-----------------------------------------
		//---HERE--GOES--THE--FIRST--SHOW--NODE----
		//-----------------------------------------
		
		
		
		Element theRoot = new Element("tvshows");//creating the main element (the root element)
		doc.setRootElement(theRoot);//setting the root element for our Document object
		
		Element show = new Element("show");
		 
		Element name = new Element("name");
		
		name.setAttribute("show_id", "show_24");//an example of setting the attributes to the xml element , elementName.setAttribute("nameOfSpecicficID","ID_itself");
		
		name.addContent(new Text("Some pretty cool stuff goes here"));//an example of adding some info between desired tag nameOfDefindElement.addContent(new Text("some info"));
		
		Element network = new Element("network");
		
		network.setAttribute("country","USA");
		
		network.addContent(new Text("ABC"));
		
		
		//in order to add all this defined elements to our actual XML file we need to add each of this elements to Nodes
		//considering each element as a node we just add certain element to another
		
		//in this case our show's name and network should be inside of element show and our show node should be inside of our root node
		//just put them in the correct order like i did below
		
		show.addContent(name);//adding name to the show node
		show.addContent(network);//adding network to the show node
		
		theRoot.addContent(show);//adding the show node to the root node
		
		
		//------------------------------------------
		//---THE--END--OF--THE--FIRST--SHOW--NODE---
		//------------------------------------------
		
		//******************************************
		
		//-----------------------------------------
		//----HERE--GOES--SECOND--SHOW--NODE-------
		//-----------------------------------------
		
		Element show2 = new Element("show");
		 
		Element name2 = new Element("name");
		
		name2.setAttribute("show_id", "show_01");//an example of setting the attributes to the xml element , elementName.setAttribute("nameOfSpecicficID","ID_itself");
		
		name2.addContent(new Text("Some pretty cool stuff goes here ||EMINEM RULES"));//an example of adding some info between desired tag nameOfDefindElement.addContent(new Text("some info"));
		
		Element network2 = new Element("network");
		
		network2.setAttribute("country","USA");
		
		network2.addContent(new Text("MTV"));
		
		
		//in order to add all this defined elements to our actual XML file we need to add each of this elements to Nodes
		//considering each element as a node we just add certain element to another
		
		//in this case our show's name and network should be inside of element show and our show node should be inside of our root node
		//just put them in the correct order like i did below
		
		show2.addContent(name2);//adding name to the show2 node
		show2.addContent(network2);//adding network to the show2 node
		
		theRoot.addContent(show2);//adding the show2 node to the root node
		
		
		//------------------------------------------
		//---THE--END--OF--THE--SECOND--SHOW--NODE--
		//------------------------------------------
		
		
		
		
		//-------------------------------------------
		//-------HERE--GOES--THE--OUTPUTTING--PART---
		//-------------------------------------------
		
		
		
		XMLOutputter XMLOutput = new XMLOutputter(Format.getPrettyFormat());
		
		
		XMLOutput.output(doc, new FileOutputStream(new File("./src/XMLLessons/XMLLessonNumberOne.xml")));
		
		System.out.println("The information was written to your XML file");
		
		//-------------------------------------------
		//----THE--END--OF--THE--OUT--PUTTING--PART--
		//-------------------------------------------
		
		
		}
		catch(Exception ex)
		{
			
			
			ex.printStackTrace();
		}
		
		
	    
		
		
	}//THE END OF WriteXML() METHOD
	
	
	
	public static void ReadXML()
	{
		
		SAXBuilder saxBuilder = new SAXBuilder();
		
		try
		{
			
			Document readDoc = saxBuilder.build(new File("./src/XMLLessons/XMLLessonNumberOne.xml"));
			
			System.out.println("ROOT : " + readDoc.getRootElement());
			
			
			
			
			System.out.println("SHOW #2 : " +readDoc.getRootElement().getChild("show").getChildText("name"));
			
			System.out.println("SHOW #2 ID : " + readDoc.getRootElement().getChild("show").getChild("name").getAttributeValue("show_id"));
			
		
			
			Element root  = readDoc.getRootElement();
			
			for(Element currentElement:root.getChildren("show"))
			{
				
				System.out.println("Show Name  : " + currentElement.getChildText("name"));
				
				System.out.println("Show ID : " + currentElement.getChild("name").getAttributeValue("show_id"));
				
			}
			
			
			
			
			
		}
		catch(Exception ex)
		{
			
		}
		
		
		
		
	}//THE END OF ReadXML() METHOD
	
	public static void EditXML()
	{
		

		SAXBuilder builder = new SAXBuilder();
		
		try {

			Document editDocument = (Document)builder.build(new File("./src/XMLLessons/XMLLessonNumberOne.xml"));
			
			Element rootNode = editDocument.getRootElement();
			
			Element firstFileToEdit = rootNode.getChild("show").getChild("name");
			firstFileToEdit.getAttribute("show_id").setValue("1001");
			
			Element newElement = new Element("xPOS");
			newElement.setAttribute("X_pos", "123");
			
			rootNode.addContent(newElement);
			
			
			XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
			output.output(editDocument, new FileOutputStream(new File("./src/XMLLessons/XMLLessonNumberOne.xml")));
			
		}
		catch (JDOMException | IOException e) {
			
			e.printStackTrace();
			
			
		}
		
		
		
		
		
		
	}
	
}
