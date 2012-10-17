package au.edu.uwa.csp.respreport;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	  public static final String TABLE_PATIENT = "patient";
	  public static final String TABLE_RESPIRATORY = "respiratory";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_USERNAME = "userName";
	  public static final String COLUMN_LASTNAME = "lastName";
	  public static final String COLUMN_TITLE = "title";
	  public static final String COLUMN_FIRSTNAME = "firstName";
	  public static final String COLUMN_PATIENTID = "patientID";
	  public static final String COLUMN_RETURNEDID = "returnedID";
	  public static final String COLUMN_RESPIRATORYRATE = "respiratoryRate";
	  public static final String COLUMN_DATERRMEASURED = "dateRRMeasured";
 
	  private static final String DATABASE_NAME = "patients.db";
	  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_PATIENT + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_USERNAME + " text not null, " + 
      COLUMN_RETURNEDID + " integer not null, "+ COLUMN_TITLE + " text not null, " + COLUMN_FIRSTNAME + " text not null, " +
	  COLUMN_LASTNAME + " text not null " + ");";

  private static final String DATABASE_CREATE2 = "create table "
	      + TABLE_RESPIRATORY + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_PATIENTID + " integer not null, " +
	      COLUMN_RESPIRATORYRATE + " integer not null, " + COLUMN_DATERRMEASURED + " text not null " + ");";
  
  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
    database.execSQL(DATABASE_CREATE2);
    
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPIRATORY);
    onCreate(db);
  }

} 