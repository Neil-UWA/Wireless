package au.edu.uwa.csp.respreport;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

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
	public void goToLogin(View view){
		Intent intent = new Intent(this, AuthUserActivity.class);
		startActivity(intent);
	}

	
	public void goToRegister(View view) {
		Intent intent = new Intent(this, Register.class);
		startActivity(intent);

	}
	

	 public void patientGraph(View view) {
	    	Intent intent = new Intent(this, PatientsListGraphActivity.class);
	    	startActivity(intent);
		}

}
