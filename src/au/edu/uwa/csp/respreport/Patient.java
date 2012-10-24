package au.edu.uwa.csp.respreport;

public class Patient {
	  private long id;
	
	  private long 	 returnedID; // the returned patientID from AddPatient web service
	  private String userName;
	  private String title;
	  private String firstName;
	  private String lastName;

	  public long getReturnedID(){
		  return returnedID;
	  }
	  
	  public void setReturnedID(long returnedID){
		  this.returnedID = returnedID;
	  }
	  
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }
	  
	  public String getUserName(){
		  return userName;
	  }
	  
	  public void setUserName(String userName){
		  this.userName = userName;
	  }
	  
	  public String getFirstName() {
	    return firstName;
	  }

	  public void setFirstName(String comment) {
	    this.firstName = comment;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return firstName + " " + lastName;
	  }

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
} 