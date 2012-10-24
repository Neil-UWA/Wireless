package au.edu.uwa.csp.respreport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

//Class with values and functions acessible to all activities
public class AppFunctions {
	
	//user data to be able to keep calling webservice
	private static String username;
	private static String password;
	private static long patientID;

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		AppFunctions.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		AppFunctions.password = password;
	}

	public static long getPatientID() {
		return patientID;
	}

	public static void setPatientID(long patientID) {
		AppFunctions.patientID = patientID;
	}

	//creates dialog message with desired input
	public static void alertDialog(String msg, Context c) {
		AlertDialog.Builder altDialog = new AlertDialog.Builder(c);
		altDialog.setMessage(msg); // here add your message
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		altDialog.show();

	}
}
