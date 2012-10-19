package au.edu.uwa.csp.respreport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XMLParser {
	private static final String ns = null;
	
	public List parse(InputStream in) throws XmlPullParserException, IOException {
		try{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readDoc(parser);
		} finally{
			in.close();
		}
	}
	
	private List<Object> readDoc(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Object> patients = new ArrayList<Object>();
		
		parser.require(XmlPullParser.START_TAG, ns, "DocumentElement");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			
			String name = parser.getName();
			if (name.equals("Patient")) {
			//	patients.add(readPatients(parser));
			} else if (name.equals("PatientRespiratoryRate")) {
			//	patients.add(readPatientsRate(parser));
			}
		}
		
		return patients;
	}
	
	/*
	private Respiratory readPatientsRate(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "PatientRespiratoryRate");
		
		int patientId = 0;
		int patientRRId = 0;
		int resRate = 0;
		String date = null;
		
		while(parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			
			String name = parser.getName();
			if (name.equals("PatientRRID")) {
				patientRRId = readIntField(parser,"PatientRRID");
			} else if (name.equals("PatientID")) {
				patientId = readIntField(parser, "PatientID");
			} else if (name.equals("RespiratoryRate")) {
				resRate = readIntField(parser, "RespiratoryRate");
			} else if (name.equals("DateRRMeasured")){
				date = readStringField(parser, "DateRRMeasured");
			} 
		}
		
		return new Respiratory(patientRRId, patientId, resRate, date);
	}
	
	private Patient readPatients(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "Patient");
		
		int patientId = 0;
		String title = null;
		String firstName = null;
		String lastName = null;
		String userName = null;
		
		while(parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			
			String name = parser.getName();
			if (name.equals("Title")) {
				title = readStringField(parser,"Title");
			} else if (name.equals("FirstName")) {
				firstName = readStringField(parser, "FirstName");
			} else if (name.equals("LastName")) {
				lastName = readStringField(parser, "LastName");
			} else if (name.equals("UserName")){
				userName = readStringField(parser, "UserName");
			} else if (name.equals("PatientID")) {
				patientId = readIntField(parser, "PatientID");
			} 
		}
		
		//return new Patient(patientId, title, firstName, lastName, userName);
	}*/

	private int readIntField(XmlPullParser parser, String string) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, string);
		int iField = Integer.parseInt(readText(parser));
		parser.require(XmlPullParser.START_TAG, ns, string);
		return iField;
	}

	private String readStringField(XmlPullParser parser, String string) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, string);
		String sField = readText(parser);
		parser.require(XmlPullParser.START_TAG, ns, string);
		return sField;
	}

	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
 		return result;
	}

	
	
} 
