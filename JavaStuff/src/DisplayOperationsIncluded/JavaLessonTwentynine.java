package DisplayOperationsIncluded;

import java.awt.*;
import javax.swing.*;
import java.io.*;



//using files inside of java , creating of file explorer
public class JavaLessonTwentynine extends JFrame
{
	
	static String filepath,parentDirectory;
	static File randomDirectory,randomFile,randomFile2;
	
	public static void main(String[]args)
	{
		
		randomDirectory = new File("D:/EclipseWorkspace/Java Training/FileOperations");//creating of the new file and setting the directory where to store it
		randomDirectory.mkdir();// .mkdir() method creates and stores certain folder on the Hard Drive
		
		randomFile = new File("D:/EclipseWorkspace/Java Training/FileOperations/random.txt");
		
		
		randomFile2 = new File("D:/EclipseWorkspace/Java Training/FileOperations/random3.txt");
		
		//all creating operations should be completed only by using try - catch(IOException e)  , 'cause it can should catch some errors if something will go wrong 
		try
		{
			randomFile.createNewFile();//this method allows us to create a certain file
			randomFile2.createNewFile();//this method allows us to create a certain file which we've  defined before 
			filepath = randomFile.getCanonicalPath();//getting the path where the object is stored
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		if(randomFile.exists())
		{
			System.out.println("File exists and is stored here : " + filepath);//getting the file path
			System.out.println("File is readable : " + randomFile.canRead());//gets info about the file
			System.out.println("File is writeable : " + randomFile.canWrite());//gets info about the file
			System.out.println("File location : " + filepath);//getting the file path again
			System.out.println("File name : " + randomFile.isFile());//getting information about the file either it's a file of not
			System.out.println("File is hidden : " + randomFile.isHidden()); //getting information about the file either it's hidden or not
			System.out.println("File last time when this file was modified in miliseconds : " + randomFile.lastModified());//getting information about the last time file was modified
			System.out.println("File Size : " + randomFile.length());//getting the size of file
			
			
			randomFile.renameTo(new File("D:/EclipseWorkspace/Java Training/FileOperations/random2.txt"));//that's basically how renameTo(new File("directory/fileName.fileFormat") method works
		    
	        
		
		}
		else
		{
			System.out.println("File doesn't exist");
		}
		
		randomFile2.delete();//deletes the certain file
		
		
		new JavaLessonTwentynine();
	}
	
	
	public JavaLessonTwentynine()
	{
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFileChooser fileChooser = new JFileChooser(randomDirectory);//defining of fileEplorer , new JFilechooser(desiredDirectoryPath);
		fileChooser.showOpenDialog(this);//opening dialog window for fileChooser 
		
		
	
		
	}
	
}