package au.edu.uwa.csp.respreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PostLoginActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_login);

		// allow user to choose between recording breathing rate or viewing data
		// already recorded
		Button bResp = (Button) findViewById(R.id.buttonRecBreathing);
		bResp.setOnClickListener(this);
		Button bGraph = (Button) findViewById(R.id.buttonViewData);
		bGraph.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_post_login, menu);
		return true;
	}

	public void onClick(View v) {
		Intent intent = null;

		if (v.getId() == R.id.buttonRecBreathing) {
			intent = new Intent(PostLoginActivity.this, BreathingActivity.class);
		} else if (v.getId() == R.id.buttonViewData) {
			intent = new Intent(PostLoginActivity.this, GraphingActivity.class);
		}

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//starts the chosen activity
		startActivity(intent);
	}
}
