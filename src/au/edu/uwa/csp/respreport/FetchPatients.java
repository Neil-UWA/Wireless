package au.edu.uwa.csp.respreport;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.util.Log;

public class FetchPatients {

	public static List<Patient> FetchPatientFromWebService(Activity act,
			String userName, String password) {
		SOAPTask task = new SOAPTask(act, "GetAllPatients");
		task.addParam("userName", userName);
		task.addParam("password", password);

		task.execute();
		String result = "";
		try {
			result = task.get(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Log.d("XML", result.toString());

		return ParsePatientXML(result);

	}

	private static List<Patient> ParsePatientXML(String xml) {
		List<Patient> plist = new ArrayList<Patient>();
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		InputSource is;
		Document dom = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			StringReader sr = new StringReader(xml);
			is = new InputSource(sr);
			dom = builder.parse(is);

			dom = builder.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}

		NodeList node_patients = dom.getElementsByTagName("Patient");

		for (int i = 0; i < node_patients.getLength(); i++) {
			Node elem = node_patients.item(i);
			NodeList patient_children = elem.getChildNodes();
			Patient patient = new Patient();
			for (int j = 0; j < patient_children.getLength(); j++) {
				Node pchild = patient_children.item(j);
				String name = pchild.getNodeName();
				if (name.equalsIgnoreCase("firstname")) {
					Log.d("FirstName", getElementValue(pchild));
					patient.setFirstName(getElementValue(pchild));
				} else if (name.equalsIgnoreCase("lastname")) {
					Log.d("LastName", getElementValue(pchild));
					patient.setLastName(getElementValue(pchild));

				} else if (name.equalsIgnoreCase("username")) {
					Log.d("Username", getElementValue(pchild));
					patient.setUserName(getElementValue(pchild));

				} else if (name.equalsIgnoreCase("title")) {
					Log.d("Title", getElementValue(pchild));
					patient.setTitle(getElementValue(pchild));

				} else if (name.equalsIgnoreCase("patientid")) {
					Log.d("PID", getElementValue(pchild));
					patient.setReturnedID(Long
							.parseLong(getElementValue(pchild)));

				}

			}

			plist.add(patient);
		}
		Log.d("Finished parsing", "Patients");
		return plist;
	}

	public static List<Respiratory> FetchRespiratoryFromWebService(
			Activity act, String userName, String password, long patientID) {
		SOAPTask task = new SOAPTask(act, "GetPatientRRByPatient");
		task.addParam("userName", userName);
		task.addParam("password", password);
		task.addParam("patientID", patientID);

		task.execute();
		String result = "";
		try {
			result = task.get(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Log.d("XML", result.toString());

		return ParseRespiratoryXML(result);

	}

	public static List<Respiratory> ParseRespiratoryXML(String xml) {
		List<Respiratory> rlist = new ArrayList<Respiratory>();

		
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		InputSource is;
		Document dom = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			StringReader sr = new StringReader(xml);
			is = new InputSource(sr);
			dom = builder.parse(is);

			dom = builder.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}

		NodeList node_respiratory = dom.getElementsByTagName("PatientRespiratoryRate");

		for (int i = 0; i < node_respiratory.getLength(); i++) {
			Node elem = node_respiratory.item(i);
			NodeList resp_child = elem.getChildNodes();
			Respiratory respiratory = new Respiratory();
			for (int j = 0; j < resp_child.getLength(); j++) {
				Node pchild = resp_child.item(j);
				String name = pchild.getNodeName();
				if (name.equalsIgnoreCase("PatientRRID")) {
					Log.d("PatientRRID", getElementValue(pchild));
					respiratory.setId(Long.parseLong(getElementValue(pchild)));
				} else if (name.equalsIgnoreCase("PatientID")) {
					Log.d("PatientID", getElementValue(pchild));
					respiratory.setPatientID(Long.parseLong(getElementValue(pchild)));

				} else if (name.equalsIgnoreCase("RespiratoryRate")) {
					Log.d("RespiratoryRate", getElementValue(pchild));
					respiratory.setRespiratoryRate(Integer.parseInt(getElementValue(pchild)));

				} else if (name.equalsIgnoreCase("DateRRMeasured")) {
					Log.d("DateRRMeasured", getElementValue(pchild));
					respiratory.setDateMeasured(getElementValue(pchild));

				} 

			}

			rlist.add(respiratory);
		}
		Log.d("Finished parsing", "Respiratory");
	
		
		return rlist;
	}

	public final static String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
}
