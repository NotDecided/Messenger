package Messaging;
import java.io.Serializable;


public  class Message implements Serializable {

	
	private String from;
	private String to;
	private String message;
	private int sender_id;
	private int receiver_id;
	private long sendingTime;

	
	public Message()
	{
		
	}
	
	public Message(String from , String to , String message)
	{
		this.from = from;
		this.to = to;
		this.message = message;
	}
	
	
	
	
	
	public void setFrom(String from)
	{
		this.from = from;
	}
	public String getFrom()
	{
		return this.from;
	}
	
	public void setTo(String to)
	{
		this.to = to;
	}
	public String getTo()
	{
		return this.to;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	public String getMessage()
	{
		return this.message;
	}
	
	public void setSenderID(int sender_id)
	{
		this.sender_id = sender_id;
	}
	public int getSenderID()
	{
		return this.sender_id;
	}
	
	public void setReceiverID(int receiver_id)
	{
		this.receiver_id = receiver_id;
	}
	public int getReceiverID()
	{
		return this.receiver_id;
	}
	
	public void setSendingTime(long sendingTime)
	{
		this.sendingTime = sendingTime;
	}
	public long getSendingTime()
	{
		return this.sendingTime;
	}
	
	
}
