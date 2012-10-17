package au.edu.uwa.csp.respreport;

import java.util.concurrent.TimeUnit;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class PatientActivity extends Activity {
	  
		private final String ERROR = "Error adding patient ";

		private String title;
		private String userName;
		private long patientId;
		private String password;
		private String firstName;
		private String lastName;
		
		@Override
	    public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_patient);
		    
		    Intent intent1 = getIntent();
		    userName = intent1.getStringExtra(Register.USER_NAME);
		    password = intent1.getStringExtra(Register.PASSWORD);
	    }
	   
	    public void addPatient(View view) {
			// setContentView(textView);
	    	EditText edt_title = (EditText) findViewById(R.id.title);
	    	title = edt_title.getText().toString();
	    	
	    	//get user name from input
	    	EditText edt_firstName = (EditText) findViewById(R.id.firstName);
	    	firstName = edt_firstName.getText().toString();
	    	
	    	//get password from input
	    	EditText edt_lastName = (EditText) findViewById(R.id.lastName);
	    	lastName = edt_lastName.getText().toString();
	    	
	    	SOAPTask task = new SOAPTask(PatientActivity.this,
					"AddPatient");
	    	
			task.addParam("userName", userName);
			task.addParam("password", password);
			task.addParam("title", title);
			task.addParam("firstName", firstName);
			task.addParam("lastName", lastName);
			task.parentActivity = PatientActivity.this;
			task.execute();
			
			String result = "";
			try {
				result = task.get(5, TimeUnit.SECONDS);
			} catch (Exception e) {
				result = ERROR;
				e.printStackTrace();
			}

			result+="";
			
			if(!result.equalsIgnoreCase("Error")){
				System.out.println("patientidis "+ result);
				
				patientId = Long.valueOf(result);
				 //store the patient info in sqlite database
				 PatientDataSource rds = new PatientDataSource(
						getApplicationContext());

				 rds.open();
				 Patient patient = rds.createPatient(userName, patientId, title, 
						 firstName, lastName);
				 rds.close();
				 
				 System.out.println("Patient Info:"+ patient.getReturnedID() + " "+patient.getFirstName()+" "+patient.getUserName());
				
				 //redirect to the login page
				 Intent intent = new Intent(PatientActivity.this, AuthUserActivity.class);
				 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 startActivity(intent);
			//	}else{
					alertDialog("Return from call: " + result);					
				//}		
			}
			
		}
	
	
    public void alertDialog(String msg)
	{
		AlertDialog.Builder altDialog= new AlertDialog.Builder(this);
    	altDialog.setMessage(msg); // here add your message
    	altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			// TODO Auto-generated method stub
    			
    		}});
			
    	altDialog.show();
	
	}
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_patient, menu);
        return true;
    }
}
