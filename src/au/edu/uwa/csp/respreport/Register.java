package au.edu.uwa.csp.respreport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import au.edu.uwa.csp.respreport.R;

public class Register extends Activity {

	public final static String USER_NAME = "com.edu.uwa.csp.respreport.RUSERNAME";
	public final static String PASSWORD = "com.edu.uwa.csp.respreport.RPASSWORD";
	private final String ERROR = "Error Registration failed ";

	private String title;
	private Spinner titleSp;
	private String userName;
	private long patientId;
	private String password;
	private String firstName;
	private String lastName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		addItemsOnSpinner();
	}

	public void addItemsOnSpinner() {
		titleSp = (Spinner) findViewById(R.id.title);
		List<String> list = new ArrayList<String>();
		
		list.add("Dr.");
		list.add("Mr.");
		list.add("Miss.");
		list.add("Mrs.");
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		titleSp.setAdapter(dataAdapter);
	}
	
		
	public void doRegister(View view) {
		// setContentView(textView);
		//get user name from input
		EditText edt_userName = (EditText) findViewById(R.id.reg_user_name);
		userName = edt_userName.getText().toString();

		//get password from input
		EditText edt_password = (EditText) findViewById(R.id.reg_password);
		password = edt_password.getText().toString();
		
		// setContentView(textView);
    	//EditText edt_title = (EditText) findViewById(R.id.title);
    	//title = edt_title.getText().toString();
		title = String.valueOf(titleSp.getSelectedItem());

    	
    	//get user name from input
    	EditText edt_firstName = (EditText) findViewById(R.id.firstName);
    	firstName = edt_firstName.getText().toString();
    	
    	//get password from input
    	EditText edt_lastName = (EditText) findViewById(R.id.lastName);
    	lastName = edt_lastName.getText().toString();
    	  	

		SOAPTask task = new SOAPTask(Register.this, "RegisterUser");
		task.addParam("userName", userName);
		task.addParam("password", password);
		task.parentActivity = Register.this;
		task.execute();
		
		String result = "";
		try {
			result = task.get(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			result = ERROR;
			e.printStackTrace();
		}

		if(result.equalsIgnoreCase("ok")){
			SOAPTask newTask = new SOAPTask(Register.this,
					"AddPatient");
	    	
			newTask.addParam("userName", userName);
			newTask.addParam("password", password);
			newTask.addParam("title", title);
			newTask.addParam("firstName", firstName);
			newTask.addParam("lastName", lastName);
			newTask.parentActivity = Register.this;
			newTask.execute();
			
			try {
				result = newTask.get(5, TimeUnit.SECONDS);
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
				 Intent intent = new Intent(Register.this, AuthUserActivity.class);
				 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 startActivity(intent);
			}	
		}else AppFunctions.alertDialog(result, Register.this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

}
