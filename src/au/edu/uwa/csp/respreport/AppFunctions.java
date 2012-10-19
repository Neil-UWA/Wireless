package au.edu.uwa.csp.respreport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AppFunctions {

	public static void alertDialog(String msg, Context c) {
		AlertDialog.Builder altDialog = new AlertDialog.Builder(c);
		altDialog.setMessage(msg); // here add your message
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		altDialog.show();

	}
}
