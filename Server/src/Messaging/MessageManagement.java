package Messaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import DataBase.DataBase;
import ServerAndConnectionHandler.ServerSide;

public class MessageManagement {

	
	
	public void sendDirectMessage(Message messageData)
	{
	
		String sent_from = messageData.getFrom();
		int sender_ID = messageData.getSenderID();
		String sent_to = messageData.getTo();
		int receiver_ID = messageData.getReceiverID();
		String messageText = messageData.getMessage();
		long sendingTime = messageData.getSendingTime();
		
		System.out.println("Message " + messageText + " | FROM : " + 
		                    sent_from + " | TO : "+ sent_to);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		ServerSide.displayMessage("From : " + sent_from);
		ServerSide.displayMessage("To : " + sent_to);
		
		if(sender_ID != receiver_ID)
		{
			ServerSide.directMessage(sender_ID ,receiver_ID ,messageText ,
					                    "MESSAGE" ,sent_from ,sdf.format(sendingTime));
			
			DataBase.newMessage(sender_ID ,receiver_ID ,messageText ,sendingTime ,127001);
		}
		
		messageData = null;
	}
	
	public void deleteMessageFromHistory(int sent_from ,int sent_to ,String messageText ,long sendingTime)
	{
		DataBase.deleteTheMessage(sent_from ,messageText ,sent_to ,sendingTime);
	}
	
	public void getChatHistory(int sender_ID ,int receiver_ID)
	{
		ServerSide.directMessage(sender_ID ,sender_ID ,"/CHAT HISTORY IS SENDING" ,"QUERY" ,"SERVER" ,getTime());
		ServerSide.sendChatHistory(sender_ID ,DataBase.getMessageHistory(sender_ID ,receiver_ID));
		System.out.println("SERVER / MESSAGE HISTORY SIDE ="+DataBase.getMessageHistory(sender_ID ,receiver_ID).length);
		
	}
	
	
	
	
	public static String getTime()
	{
		
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		
		String time = hours+":"+minutes+":"+seconds;
		
		return time;
		
	}
	
}
