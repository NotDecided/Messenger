package WritingReadingXMLFiles;

import org.omg.CORBA.portable.InputStream;
import org.w3c.dom.*;//contains : Document , NodeList , Element , Text , Node  and Exceptions

import javax.xml.xpath.*;
import javax.xml.parsers.*;//contains DocumentBuilder DocumentBuilderFactory , SAX(Simple API for XML) parser 
import javax.xml.datatype.*;

import java.io.IOException;

import org.xml.sax.SAXException;







//using of JXPATH for reading and writing from / in XML file
public class JavaLessonFourtyFive
{
	
	
	public static void main(String[]args)
	{
		
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		
		domFactory.setNamespaceAware(true);//providing of namespaces for XML
		
		DocumentBuilder builder;
		
		Document doc = null;
		
		
		try
		{
			builder = domFactory.newDocumentBuilder();
			
			doc = builder.parse("./src/XMLLessons/XMLLessonNumberOne.xml");//parsing XML file from certain directory
			//in this case your XML file should always have defined <!DOCTYPE   >  statement			
			
			
		}
		catch(ParserConfigurationException e)
		{
			
			
		}
		catch(SAXException | IOException ex)
		{
			
		}
		
		XPath xPath = XPathFactory.newInstance().newXPath();//
		
		
		getNodeNameAndValue(doc,xPath);
		
	}
	
	private static void getNodeNameAndValue(Document docName,XPath xPath)
	{
		
		XPathExpression XPExpression;
		
		Object result = null;
		
		
		try
		{
		
			//to select the specific nodeset for pulling out the data we need to use the following   //text() - retrieves all text info defined in certain node
			XPExpression = xPath.compile("//show//name//text()");//specifies search for certain node
			
			//if we want to pull out certain element with a specific id , we do the following :   "//pathInsideOfXMLFile/nodeName[@id_code='certain_ID']//text()"  ;
			
			result = XPExpression.evaluate(docName, XPathConstants.NODESET);
			
			
			
			
		}
		catch(XPathException ex)
		{
			
			ex.printStackTrace();
			
		}
		
		
		NodeList nodes = (NodeList)result;
		
		System.out.println(nodes.item(0).getNodeValue());//retrieves data from any node
		
		for(int i=0 ; i<nodes.getLength(); i++)//creating for loop in order to list all nodes with certain parameters which are defined in XPathExpression
		{
			
			System.out.println(nodes.item(i).getParentNode().getNodeName() + " "+nodes.item(i).getNodeValue());
		}
		
		
	}
	
	
}



