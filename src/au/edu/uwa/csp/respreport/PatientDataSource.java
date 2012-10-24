package au.edu.uwa.csp.respreport;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PatientDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_USERNAME, MySQLiteHelper.COLUMN_RETURNEDID,MySQLiteHelper.COLUMN_TITLE, 
      MySQLiteHelper.COLUMN_FIRSTNAME, MySQLiteHelper.COLUMN_LASTNAME };

  public PatientDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
}

  public Patient createPatient(String userName, long returnedID, String title, String firstName,
		  	String lastName) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_USERNAME, userName);
    values.put(MySQLiteHelper.COLUMN_RETURNEDID, returnedID);
    values.put(MySQLiteHelper.COLUMN_TITLE, title);
    values.put(MySQLiteHelper.COLUMN_FIRSTNAME, firstName);
    values.put(MySQLiteHelper.COLUMN_LASTNAME, lastName);
    
    long insertId = database.insert(MySQLiteHelper.TABLE_PATIENT, null,
        values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_PATIENT,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Patient newPatient = cursorToPatient(cursor);
    cursor.close();
    return newPatient;
  }

  // get the patient by using userName
  public Patient getPatient(String userName){
	  Cursor cursor = database.rawQuery("select * from "+ MySQLiteHelper.TABLE_PATIENT + " where " + 
			  MySQLiteHelper.COLUMN_USERNAME +" = ?" , new String[]{userName});
	  
	  if(cursor.getCount()!=0){
		  cursor.moveToFirst();
		  Patient patient = cursorToPatient(cursor);  
		  cursor.close();		 
		  return patient;
	  }
	  
	  return null;
  }
  
  public void deletePatient(Patient patient) {
    long id = patient.getId();
    System.out.println("Patient deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_PATIENT, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }
  
  public List<Patient> getAllPatients() {
    List<Patient> patients = new ArrayList<Patient>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_PATIENT,
        allColumns, null, null, null, null,null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Patient patient = cursorToPatient(cursor);
      patients.add(patient);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return patients;
  }

  private Patient cursorToPatient(Cursor cursor) {
    Patient patient = new Patient();
    
    patient.setId(cursor.getLong(0));
    patient.setUserName(cursor.getString(1));
    patient.setReturnedID(cursor.getLong(2));
    patient.setTitle(cursor.getString(3));
    patient.setFirstName(cursor.getString(4));
    patient.setLastName(cursor.getString(5));
    
    return patient;
  }
} 