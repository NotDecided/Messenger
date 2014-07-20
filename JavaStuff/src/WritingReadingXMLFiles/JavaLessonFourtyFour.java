package WritingReadingXMLFiles;


import javax.management.modelmbean.XMLParseException;
import javax.xml.*;

import org.xml.sax.*;
import org.w3c.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.stream.XMLStreamException;


//operating with XML files inside of java part 1
public class JavaLessonFourtyFour
{
	
	
	
	
	public static void main(String[]args)
	{
		
		
		Document xmlDoc  = getDocument("./src/XMLLessons/XMLLessonNumberOne.xml");
		
		System.out.println("ROOT NODE : "+xmlDoc.getDocumentElement().getNodeName());
		
		NodeList listOfShows = xmlDoc.getElementsByTagName("show");
		
		System.out.println("Number of elements : " + listOfShows.getLength());//the simplest way of getting the number of certain elements in the XML document 
		
		String elementName = "network";
		
		String attributeName ="country";
		
		getElementAndAttribute(listOfShows,elementName,attributeName);
		
	}
	
	private static Document getDocument(String pathToTheDocument)
	{
		try
		{
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setValidating(true);
			factory.setIgnoringElementContentWhitespace(true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			return builder.parse(new InputSource(pathToTheDocument));
						
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			
		}
		
		
		
		return null;
		
	}
	
	private static void getElementAndAttribute(NodeList listOfElements,String elementName , String elementAttribute)
	{
		
		try
		{
			
			 for(int i =0; i< listOfElements.getLength(); i++)
			 {
				 Node showNode = listOfElements.item(i);
				 
				 Element showElement = (Element)showNode;
				 
				 NodeList newtworkList = showElement.getElementsByTagName(elementName);
				 
				 Element networkElement = (Element)newtworkList.item(0);
				 
				 NodeList elementList = networkElement.getChildNodes();
				 
				 if(networkElement.hasAttribute(elementAttribute))
				 {
					 System.out.println(elementName + " : " + (elementList.item(0).getNodeValue().trim() + " has attribute " +networkElement.getAttribute(elementAttribute)));
				 }
				 
				 
				 
				 
			 }
			
		}
		catch(Exception ex)
		{
			
		}
		
	}
	
}