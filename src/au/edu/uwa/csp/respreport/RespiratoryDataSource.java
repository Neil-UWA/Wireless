	package au.edu.uwa.csp.respreport;

	import java.util.ArrayList;
	import java.util.List;

	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.SQLException;
	import android.database.sqlite.SQLiteDatabase;

public class RespiratoryDataSource {
	
	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_PATIENTID, MySQLiteHelper.COLUMN_RESPIRATORYRATE,
	      MySQLiteHelper.COLUMN_DATERRMEASURED };

	  public RespiratoryDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Respiratory createRespiratory(long patientID, int rRate,  String dateMeasured) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_PATIENTID, patientID);
	    values.put(MySQLiteHelper.COLUMN_RESPIRATORYRATE, rRate);
	    values.put(MySQLiteHelper.COLUMN_DATERRMEASURED, dateMeasured);

	    
	    long insertId = database.insert(MySQLiteHelper.TABLE_RESPIRATORY, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RESPIRATORY,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Respiratory newResp = cursorToRespiratory(cursor);
	    cursor.close();
	    return newResp;
	  }

	  public void deleteRespiratory(Respiratory resp) {
	    long id = resp.getId();
	    System.out.println("Respiratory deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_RESPIRATORY, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  
	  public List<Respiratory> getAllRespiratorys() {
	    List<Respiratory> respiratorys = new ArrayList<Respiratory>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RESPIRATORY,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Respiratory Respiratory = cursorToRespiratory(cursor);
	      respiratorys.add(Respiratory);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return respiratorys;
	  }

	  private Respiratory cursorToRespiratory(Cursor cursor) {
	    Respiratory Respiratory = new Respiratory();
	    Respiratory.setId(cursor.getLong(0));
	    Respiratory.setPatientID(cursor.getLong(1));
	    Respiratory.setRespiratoryRate(cursor.getInt(2));
	    Respiratory.setDateMeasured(cursor.getString(3));
	    return Respiratory;
	  }
	  
	  
	  
	  
	  
	  
	} 


