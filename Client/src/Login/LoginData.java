package Login;

import java.io.Serializable;

public class LoginData implements Serializable{

	private String password;
	private String username;
	
	
	public LoginData()
	{
		
	}
	
	public LoginData(String username , String password)
	{
		this.username = username;
		this.password = password;
	}
	
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return this.username;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPassword()
	{
		return this.password;
	}
	
	
}
