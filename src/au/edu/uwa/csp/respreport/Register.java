package au.edu.uwa.csp.respreport;

import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import au.edu.uwa.csp.respreport.R;

public class Register extends Activity {

	public final static String USER_NAME = "com.edu.uwa.csp.respreport.RUSERNAME";
	public final static String PASSWORD = "com.edu.uwa.csp.respreport.RPASSWORD";
	private final String ERROR = "Error Registration failed ";

	private String userName;
	private String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	public void doRegister(View view) {
		// setContentView(textView);
		//get user name from input
		EditText edt_userName = (EditText) findViewById(R.id.reg_user_name);
		userName = edt_userName.getText().toString();

		//get password from input
		EditText edt_password = (EditText) findViewById(R.id.reg_password);
		password = edt_password.getText().toString();

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

		result+="";

		//if authentication succeeded, redirect to new activity
		if(result.equalsIgnoreCase("OK")){
			Intent intent = new Intent(Register.this, PatientActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(USER_NAME,userName);
			intent.putExtra(PASSWORD, password);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

}
