package au.edu.uwa.csp.respreport;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import au.edu.uwa.csp.respreport.R;

public class AuthUserActivity extends Activity {

	Button btnConvert;

	public final static String USER_NAME = "com.edu.uwa.csp.respreport.AUTHUSERNAME";
	public final static String PASSWORD = "com.edu.uwa.csp.respreport.AUTHPASSWORD";
	public final static String PATIENT_ID = "com.edu.uwa.csp.respreport.MESSAGE";
	private final String ERROR = "Error Authentication failed ";

	private String userName;
	private String password;

	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth_user);
	}

	class Data {
		String namespace;
		String method;
	}

	public void login(View view) {
		// setContentView(textView);

		// get user name from input
		EditText edt_user_name = (EditText) findViewById(R.id.user_name);
		userName = edt_user_name.getText().toString();

		// get password from input
		EditText edt_password = (EditText) findViewById(R.id.password);
		password = edt_password.getText().toString();

		SOAPTask task = new SOAPTask(AuthUserActivity.this, "AuthUser");
		task.addParam("userName", userName);
		task.addParam("password", password);
		task.parentActivity = AuthUserActivity.this;
		task.execute();
		String result = "";
		try {
			result = task.get(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			result = ERROR;
			e.printStackTrace();
		}

		result += "";

		// if authentication succeeded, redirect to new activity
		if (result.equalsIgnoreCase("OK")) {
			PatientDataSource pds = new PatientDataSource(
					getApplicationContext());
			Patient patient = new Patient();
			pds.open();
			patient = pds.getPatient(userName);
			pds.close();
			// System.out.println("MyID="+patient.getReturnedID()+" "+patient.getUserName());

			// System.out.println("GO is +" + patient.getReturnedID());
			if (patient == null) {
				List<Patient> patients = FetchPatients
						.FetchPatientFromWebService(AuthUserActivity.this,
								userName, password);

				for (Patient patient1 : patients) {
					if (patient1.getUserName().equalsIgnoreCase(userName)) {
						patient = patient1;
						break;
					}
				}
			}
			// If it's a doctor, go to doctor view.
			if (patient != null) {
				if (patient.getTitle().equalsIgnoreCase("doctor")) {
					intent = new Intent(AuthUserActivity.this,
							PatientsListGraphActivity.class);
				} else {
					intent = new Intent(AuthUserActivity.this,
							BreathingActivity.class);
				}
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(USER_NAME, userName);
				intent.putExtra(PASSWORD, password);
				intent.putExtra(PATIENT_ID, patient.getReturnedID());

				startActivity(intent);
			} else
				AppFunctions.alertDialog(result, AuthUserActivity.this);
		}else  AppFunctions.alertDialog("User doesn't exist", AuthUserActivity.this);

	}

	// go to the register view
	public void goToRegister(View view) {
		Intent intent = new Intent(this, Register.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_auth_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
