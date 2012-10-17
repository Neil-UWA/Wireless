package au.edu.uwa.csp.respreport;

public class Respiratory {
	  private long id;
	  private long patientID;
	  private String dateMeasured;
	  private int respiratoryRate;

	  public int getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(int respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public String getDateMeasured() {
		return dateMeasured;
	}

	public void setDateMeasured(String dateMeasured) {
		this.dateMeasured = dateMeasured;
	}
}
