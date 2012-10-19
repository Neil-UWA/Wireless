package au.edu.uwa.csp.respreport;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import au.edu.uwa.csp.respreport.R;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "au.edu.uwa.csp.respreport.MESSAGE";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/** Called when the user clicks the Send button */
	public void soapMethods(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, AuthUserActivity.class);

		startActivity(intent);
	}

	public void goToLogin(View view){
		Intent intent = new Intent(this, AuthUserActivity.class);
		startActivity(intent);
	}
	
	public void goToRegister(View view) {
		Intent intent = new Intent(this, Register.class);
		startActivity(intent);
	}
	
	public void writeDB(View view) {
		// Do something in response to button
		PatientDataSource pds = new PatientDataSource(this);
		pds.open();
		pds.createPatient("go", 1,  "Mr", "David", "Smith");
		AlertDialog.Builder altDialog= new AlertDialog.Builder(this);
		altDialog.setMessage("Wrote to DB"); // here add your message
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}});

		altDialog.show();

	}
	
	 public void patientGraph(View view) {
	    	Intent intent = new Intent(this, PatientsListGraphActivity.class);
	    	startActivity(intent);
		}

}
