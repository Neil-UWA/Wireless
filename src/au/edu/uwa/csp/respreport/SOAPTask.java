package au.edu.uwa.csp.respreport;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

class SOAPTask extends AsyncTask<String, Void, String> {

	private String METHOD_NAME1;
	private String SOAP_ACTION2 = "http://tempuri.org/IService1/";
	private static String NAMESPACE = "http://tempuri.org/";
	private static String URL = "http://marge.csse.uwa.edu.au/RespServ/Service1.svc?wsdl";
	protected Activity parentActivity;
	public static String response;
	SoapObject request;

	public SOAPTask(Activity parentActivity, String method) {
		super();

		this.parentActivity = parentActivity;
		this.METHOD_NAME1 = method;
		this.SOAP_ACTION2 += method;
		//create request object here to allow outer method to set params
		request = new SoapObject(NAMESPACE, METHOD_NAME1);
		Log.d(SOAP_ACTION2, METHOD_NAME1);
	}

	public void addParam(String key, Object value) {
		request.addProperty(key, value);
	}

	@Override
	protected void onPreExecute() {

		// this.dialog = ProgressDialog.show(applicationContext, "Calling",
		// "Auth User...", true);

	}

	@Override
	protected void onPostExecute(final String result) {

		// this.dialog.dismiss();

		parentActivity.runOnUiThread(new Runnable() {
			public void run() {
				
			}
		});

	}

	@Override
	protected String doInBackground(String... params) {

		// Declare the version of the SOAP request
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;

		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			// this is the actual part that will call the webservice
			androidHttpTransport.call(SOAP_ACTION2, envelope);

			// Get the SoapResult from the envelope body.
			final SoapObject result = (SoapObject) envelope.bodyIn;


			if (result != null) {
				// Get the first property and change the label text
				Log.d("RESULT", result.toString());

				return result.getProperty(0).toString();

			} else {
				Toast.makeText(parentActivity.getApplicationContext(),
						"No Response", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}