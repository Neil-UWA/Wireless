package au.edu.uwa.csp.respreport;

import java.text.ParseException;
import java.util.List;

import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


public class GraphingActivity extends Activity {
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphing);
		
		int intentInt;
		
		if(savedInstanceState != null){
			intentInt = savedInstanceState.getInt("PatientId");
		}else intentInt = -1;
		
		if(intentInt == -1) {
			Bundle extras = getIntent().getExtras();
			if(extras != null){
				intentInt = extras.getInt("PatientId");
			}
			else intentInt = -1;
		}
		
	
		getPatientGraphView(intentInt);
		//}
		//else{
		//	Intent intent = new Intent(getApplicationContext(),PatientsListGraphActivity.class);
		//	startActivity(intent);
		//}
	}
	
	public void getPatientGraphView(int patientId) {
		PatientGraph gp = new PatientGraph();
		
		GraphicalView gpView = gp.getView(this,patientId);
		
		LinearLayout gpLayout = (LinearLayout) findViewById(R.id.graphLayout);
		gpLayout.addView(gpView);
	}
	
	

}
