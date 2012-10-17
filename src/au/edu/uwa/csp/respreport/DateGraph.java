package au.edu.uwa.csp.respreport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;

public class DateGraph {

	public Intent getIntent(Context context) {
		int[] y = {10, 30, 20, 10, 50, 90, 50, 70, 20, 60};
		String[] date = { "01/10/2012",
						"02/10/2012",
						"03/10/2012",
						"04/10/2012",
						"05/10/2012",
						"06/10/2012",
						"07/10/2012",
						"08/10/2012",
						"09/10/2012",
						"10/10/2012"
		};
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		TimeSeries series = new TimeSeries("Rate Graph");
		for(int i=0; i <y.length; i++){
			Date newDate = new Date();
			try {
				newDate = sdf.parse(date[i]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Date Error" + i);
				e.printStackTrace();
			}
			series.add(newDate, y[i]);
			System.out.println(newDate + " " + y[i]);
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.WHITE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		renderer.setLineWidth((float)1.5);
	
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setAxisTitleTextSize((float)18);
		
		//not moving
		mRenderer.setPanEnabled(true,false);
		
		
		//Background Color
		mRenderer.setBackgroundColor(Color.BLACK);
		mRenderer.setApplyBackgroundColor(true);
		
		//Set Labels
		mRenderer.setXTitle("Day");
		mRenderer.setYTitle("Rate");
		mRenderer.setLabelsColor(Color.RED);
		mRenderer.setLabelsTextSize((float)14);
		
		
		Intent intent = ChartFactory.getTimeChartIntent(context, dataset, mRenderer, "dd/MM/yyyy");
		return intent;
	}
}
