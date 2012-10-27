package au.edu.uwa.csp.respreport;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
/*
 * Activity to display the graph for each patient.
 * Call getPatientGraphView() function to display the graph.
 */
public class GraphingActivity extends Activity {
	
	int intentActivityInt;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphing);
		
		int intentInt;
		
		//Get the saved information about patientId
		if(savedInstanceState != null){
			intentInt = savedInstanceState.getInt("PatientId");
			intentActivityInt = savedInstanceState.getInt("Activity");
		}else {
			intentInt = -1;
			intentActivityInt = -1;
		}
		if(intentInt == -1) {
			Bundle extras = getIntent().getExtras();
			if(extras != null){
				intentInt = extras.getInt("PatientId");
			}
			else intentInt = (int) AppFunctions.getPatientID();
		}
		
		if(intentActivityInt == -1){
			Bundle extras = getIntent().getExtras();
			if(extras != null){
				intentActivityInt = extras.getInt("Activity");
				this.setTitle("Patient Graph");
			}
			
		}
	
		getPatientGraphView(intentInt);
	}
	
	/*
	 * Display the graph for each patient.
	 */
	public void getPatientGraphView(int patientId) {
		PatientGraph gp = new PatientGraph(GraphingActivity.this);

		GraphicalView gpView = gp.getView(this,patientId);		
		LinearLayout gpLayout = (LinearLayout) findViewById(R.id.graphLayout);
		//adding the view to the layout.
		gpLayout.addView(gpView);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    if(intentActivityInt != 0){
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.activity_patient, menu);
	    	
	    }
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		Intent intent = null;
	
	    switch (item.getItemId()) {
	        case R.id.menu_breathing_record:
	        	intent = new Intent(this, BreathingActivity.class);
	        	startActivity(intent);
	        	return true;
	        case R.id.menu_view_data:
	            //intent = new Intent(this, GraphingActivity.class);
	            //startActivity(intent);
	            return true;   
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	   
	}
	

}
