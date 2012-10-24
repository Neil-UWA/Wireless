package au.edu.uwa.csp.respreport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatientsListGraphActivity extends Activity {

	private ListView patientListView;
	private ArrayAdapter<String> listAdapter;
	private ArrayList<Integer> patientsId = new ArrayList<Integer>();
	private static final String TAG = "PatientsList";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patients_list);

		// Find the list View
		patientListView = (ListView) findViewById(R.id.patientsListView);
		ArrayList<String> patientsList = getPatientsFromDatabase();

		// Create Array Adapter using the array list and bind the array with the
		// adapter.
		listAdapter = new ArrayAdapter<String>(this, R.layout.patient_row,
				patientsList);
		patientListView.setAdapter(listAdapter);
		patientListView.setOnItemClickListener(listViewClickHandler);

	}

	public ArrayList<String> getPatientsFromDatabase() {
		// String patient;
		ArrayList<String> patientsList = new ArrayList<String>();

		//PatientDataSource pds = new PatientDataSource(getApplicationContext());
		// List<Patient> pList = new ArrayList<Patient>();
		List<Patient> pList = FetchParseXML.FetchPatientFromWebService(
				PatientsListGraphActivity.this, AppFunctions.getUsername(),
				AppFunctions.getPassword());

		for (Patient p : pList) {
			if (!p.getTitle().equalsIgnoreCase("doctor")) {

			/*	int nResp = FetchParseXML.FetchRespiratoryFromWebService(
						PatientsListGraphActivity.this,
						AppFunctions.getUsername(), AppFunctions.getPassword(),
						p.getReturnedID()).size();
			*/
				//if (p.getUserName().equalsIgnoreCase("debugger")) {
					String patient = p.getTitle() + ". " + p.toString();
					patientsId.add((int) p.getReturnedID());
					patientsList.add(patient);
				//}
			}
		}

		return patientsList;
	}

	private OnItemClickListener listViewClickHandler = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			Log.d(TAG, "pos: " + position + " id: " + id);
			int patientId = patientsId.get((int) patientListView
					.getItemIdAtPosition(position));

			// Create new intent to call Graphing Activity class
			Intent intent = new Intent(getApplicationContext(),
					GraphingActivity.class);
			// Put patientId information in the intent to be passed over
			intent.putExtra("PatientId", patientId);
			startActivity(intent);

		}
	};

}
