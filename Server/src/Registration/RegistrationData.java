package Registration;

import java.io.Serializable;

public class RegistrationData implements Serializable{

	
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String birthDate;
	
	
	
	public RegistrationData()
	{
		
	}
	
	public RegistrationData(String username ,String password , 
			              String email ,String firstName ,
			              String lastName ,String birthDate){
		
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		
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
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getEmail()
	{
		return this.email;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getLastName()
	{
		return this.lastName;
	}
	
	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
	}
	public String getBirthDate()
	{
		return this.birthDate;
	}
	
	
}
