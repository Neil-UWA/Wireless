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
		
		//Testing
		/*
		RespiratoryDataSource db = new RespiratoryDataSource(this);
		Log.d("Insert: ", "Inserting...");
		db.createRespiratory(100, 5, "10/10/2012 10:00");
		db.createRespiratory(101, 10, "10/10/2012 12:00");
		
		Log.d("Reading: ", "Reading All Contacts..");
		List<Respiratory> r = db.getAllRespiratorys();
		
		for(Respiratory i : r) {
			String log = "pID: " + i.getPatientID() + " ,Rate: "+ i.getRespiratoryRate()
					+ " Date: " + i.getDateMeasured();
			Log.d("Content: ", log);
		}
		
		PatientGraph gp = new PatientGraph();
		GraphicalView gpView = null;
		try {
			gpView = gp.GetView(this);
			Log.d("Success View", "Succeess View");
			System.out.println("Success");
		} catch (ParseException e) {
			Log.d("Error.. in View", "Error in View");
			e.printStackTrace();
		}
		
		LinearLayout gpLayout = (LinearLayout) findViewById(R.id.graphLayout);
		gpLayout.addView(gpView);
		*/
	}
	
	public void patientGraph(View view) {
		DateGraph dg = new DateGraph();
		Intent dgIntent = dg.getIntent(this);
		startActivity(dgIntent);
	}
	
	

}
