package au.edu.uwa.csp.respreport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BreathingActivity extends Activity {
	private String userName;
	private String password;
	private long 	patientID;
	private final String ERROR = "Error Uploading Respiratory Rate ";

	
	Chronometer mChronometer;
	LinearLayout layout;
	EditText respBox;
	Button sendResp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breathing);

		mChronometer = (Chronometer) findViewById(R.id.chronometer1);
		// Set the initial value
		mChronometer.setText("00:00");
		mChronometer.setOnChronometerTickListener(mTickListener);

		Button startButton = (Button) findViewById(R.id.buttonStartResp);
		startButton.setText("Start");
		startButton.setOnClickListener(mStartListener);

		Button stopButton = (Button) findViewById(R.id.buttonStopResp);
		stopButton.setText("Stop");
		stopButton.setOnClickListener(mStopListener);

		Button resetButton = (Button) findViewById(R.id.buttonResetResp);
		resetButton.setText("Reset");
		resetButton.setOnClickListener(mResetListener);

		respBox = (EditText) findViewById(R.id.editTextResp);
		sendResp = (Button) findViewById(R.id.buttonSendResp);
		respBox.setHint("Waiting for timer..");

		sendResp.setEnabled(false);
		respBox.setEnabled(false);
		
		Intent intent =  getIntent();
		userName = intent.getStringExtra(AuthUserActivity.USER_NAME);
		password = intent.getStringExtra(AuthUserActivity.PASSWORD);
		patientID = intent.getLongExtra(AuthUserActivity.PATIENT_ID, 1);

		System.out.println("GO is +" + patientID);
	}

	private void showElapsedTime() {
		long elapsedMillis = SystemClock.elapsedRealtime()
				- mChronometer.getBase();
		/*
		 * Toast.makeText(BreathingActivity.this,
		 * 
		 * "Elapsed milliseconds: " + elapsedMillis, Toast.LENGTH_SHORT)
		 * .show();
		 */
	}

	View.OnClickListener mStartListener = new OnClickListener() {
		public void onClick(View v) {
			int stoppedMilliseconds = 0;

			String chronoText = mChronometer.getText().toString();
			String array[] = chronoText.split(":");
			if (array.length == 2) {
				stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
						+ Integer.parseInt(array[1]) * 1000;
			} else if (array.length == 3) {
				stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60
						* 1000 + Integer.parseInt(array[1]) * 60 * 1000
						+ Integer.parseInt(array[2]) * 1000;
			}

			mChronometer.setBase(SystemClock.elapsedRealtime()
					- stoppedMilliseconds);
			mChronometer.start();
		}
	};

	View.OnClickListener mStopListener = new OnClickListener() {
		public void onClick(View v) {
			mChronometer.stop();
			showElapsedTime();
		}
	};

	View.OnClickListener mResetListener = new OnClickListener() {
		public void onClick(View v) {
			respBox.setHint("Waiting for timer..");
			respBox.setText("");

			sendResp.setEnabled(false);
			respBox.setEnabled(false);
			
			mChronometer.setBase(SystemClock.elapsedRealtime());
			showElapsedTime();

		}
	};
	OnChronometerTickListener mTickListener = new OnChronometerTickListener() {

		public void onChronometerTick(Chronometer chronometer) {
			if ("00:05".equals(chronometer.getText())) {
				Toast.makeText(getApplicationContext(), "Time's up",
						Toast.LENGTH_LONG).show();

				((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(500);

				// create resp rate box
				createRespBox();
				chronometer.stop();

			}

		}
	};

	public void createRespBox() {

		respBox.setInputType(InputType.TYPE_CLASS_NUMBER);
		respBox.setHint("Enter respiratory rate");
		respBox.setTextColor(Color.RED);

		sendResp.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				//TODO when a new patient is created, store its patient id for use below
				//patientID = patient.getId();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				String dateMeasured = sdf.format(new Date());
				int respRate = 0 + Integer.parseInt(respBox.getText()
						.toString());
				// store in database
				RespiratoryDataSource rds = new RespiratoryDataSource(
						getApplicationContext());

				rds.open();
				rds.createRespiratory(patientID, respRate, dateMeasured);
				rds.close();
				System.out.println("Id is = "+ patientID);
				Log.d("Resp rate:", respBox.getText().toString() + " at "
						+ dateMeasured);

				// upload to web
				SOAPTask task = new SOAPTask(BreathingActivity.this,
						"AddPatientRR");
				task.addParam("userName", userName);
				task.addParam("password", password);
				task.addParam("patientID", patientID);
				task.addParam("respRate", respRate);
				task.addParam("dateRRMeasured", dateMeasured);
				task.parentActivity = BreathingActivity.this;
				task.execute();
				String result = "";
				try {
					result = task.get(5, TimeUnit.SECONDS);
				} catch (Exception e) {
					result = ERROR;
					e.printStackTrace();
				}

				result+="";
				if(result.equalsIgnoreCase("ok"))
					alertDialog(result);
				else alertDialog(ERROR + result);

			
			}
		});
		respBox.setEnabled(true);
		sendResp.setEnabled(true);

	}
	public void alertDialog(String msg) {
		AlertDialog.Builder altDialog = new AlertDialog.Builder(this);
		altDialog.setMessage(msg); // here add your message
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		altDialog.show();

	}
}