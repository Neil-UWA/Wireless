package au.edu.uwa.csp.respreport;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;

public class PatientGraph {

	Activity parentActivity;

	public PatientGraph(Activity act) {
		// TODO Auto-generated constructor stub
		this.parentActivity = act;
	}

	public Intent getIntent(Context context) {

		int patientId = 5;
		String dateFormat = "dd/MM/yy hh:mm";

		XYMultipleSeriesDataset dataset = getDataset(context, patientId,
				dateFormat);
		XYMultipleSeriesRenderer mRenderer = getRenderer();

		Intent intent = ChartFactory.getTimeChartIntent(context, dataset,
				mRenderer, "dd/MM/yy hh:mm");
		return intent;
	}

	public GraphicalView getView(Context context, int patientId) {

		// int patientId = 5;
		String dateFormat = "dd/MM/yy hh:mm";

		XYMultipleSeriesDataset dataset = getDataset(context, patientId,
				dateFormat);
		XYMultipleSeriesRenderer mRenderer = getRenderer();

		/*
		 * String currDir = System.getProperty("user.dir");
		 * System.out.println(currDir);
		 * 
		 * File dir = new File("."); try { System.out.println("Curr Dir " +
		 * dir.getAbsolutePath() ); System.out.println("Curr Dir " +
		 * dir.getPath() ); System.out.println("Curr Dir " + dir.getName() ); }
		 * catch (Exception e) { System.out.println("Exception"); }
		 */

		return ChartFactory.getTimeChartView(context, dataset, mRenderer,
				"dd/MM/yy hh:mm");

	}

	private void writeToFile(String content, String filename) {
		FileWriter out = null;
		try {
			out = new FileWriter(filename);
			BufferedWriter writer = new BufferedWriter(out);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void checkParameter(XYMultipleSeriesDataset dataset,
			XYMultipleSeriesRenderer renderer) {
		if (dataset == null
				|| renderer == null
				|| dataset.getSeriesCount() != renderer
						.getSeriesRendererCount()) {
			throw new IllegalArgumentException(
					"Dataset and renderer should be not null and should have the same number of series");
		}
	}

	private XYMultipleSeriesDataset getDatasetEmpty(Context context,
			int patientId, String dateFormat) {
		TimeSeries series = new TimeSeries("Rate Graph");

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		series.add(new Date(), 0);

		dataset.addSeries(series);

		return dataset;

	}

	private XYMultipleSeriesDataset getDataset(Context context, int patientId,
			String dateFormat) {
		RespiratoryDataSource rds = new RespiratoryDataSource(context);

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		TimeSeries series = new TimeSeries("Rate Graph");
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		/*
		 * rds.open(); resList =
		 * rds.getRespiratoryByPatientIdSortByTime(patientId); rds.close();
		 */
		List<Patient> lPatient = FetchParseXML.FetchPatientFromWebService(
				parentActivity, AppFunctions.getUsername(),
				AppFunctions.getPassword());
		
		Log.d("fetchPatients", "Pgraph");

		Patient patient = null;
		for (Patient p : lPatient) {
			if (p.getReturnedID() == patientId) {
				patient = p;
				break;
			}
		}

		Log.d("Pgraph","found patient "+ patient.getUserName());
		List<Respiratory> resList = FetchParseXML.FetchRespiratoryFromWebService(
				parentActivity, AppFunctions.getUsername(),
				AppFunctions.getPassword(), patient.getReturnedID());
		
		Log.d("fetchResp", "Pgraph");

		if (resList.size() == 0) {
			Log.d("No dataset", "No Rate recorded before");
			dataset = getDatasetEmpty(context, patientId, dateFormat);
		} else {
			// Iterate Respiratory List
			for (int i = 0; i < resList.size(); i++) {
				Date newDate = new Date();

				String sDate = resList.get(i).getDateMeasured();
				int iRate = resList.get(i).getRespiratoryRate();

				try {
					newDate = sdf.parse(sDate);
				} catch (ParseException e) {
					System.out.println("Parsing Date Error!");
					e.printStackTrace();
				}

				series.add(newDate, iRate);
			}

			dataset.addSeries(series);

		}
		return dataset;
	}

	private XYMultipleSeriesRenderer getRenderer() {
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		// renderer.setColor(Color.rgb(135,206,250));
		renderer.setColor(Color.rgb(30, 144, 255));
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		renderer.setLineWidth((float) 1.5);
		// renderer.setFillBelowLine(true);
		// renderer.setFillBelowLineColor(Color.rgb(30,144,255));

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);

		// Customize mRenderer
		setChartSettings(mRenderer);

		return mRenderer;
	}

	private void setChartSettings(XYMultipleSeriesRenderer mRenderer) {
		mRenderer.setBackgroundColor(Color.BLACK);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setChartTitle("Respiration Rate");
		mRenderer.setChartTitleTextSize((float) 30);
		mRenderer.setXTitle("Day");
		mRenderer.setYTitle("Rate");
		mRenderer.setLabelsColor(Color.rgb(30, 144, 255));
		mRenderer.setLabelsTextSize((float) 14);
		mRenderer.setLegendTextSize((float) 20);
		mRenderer.setLegendHeight(50);
		mRenderer.setMargins(new int[] { 100, 50, 50, 50 });
		mRenderer.setXLabelsAlign(Align.CENTER);
		mRenderer.setYLabelsAlign(Align.CENTER);
		mRenderer.setPanEnabled(true, false);
		mRenderer.setAxisTitleTextSize((float) 18);

		mRenderer.setShowGridX(true);
		mRenderer.setShowGridY(false);
		mRenderer.setGridColor(Color.DKGRAY);

	}
}
