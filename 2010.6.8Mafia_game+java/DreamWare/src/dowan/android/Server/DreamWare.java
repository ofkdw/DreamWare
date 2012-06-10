package dowan.android.Server;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DreamWare extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.main); 
	
		 Button bt =  (Button)findViewById(R.id.main_start);
		 bt.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(DreamWare.this, SoketChat.class);
				startActivity(i);
			}
		});
	}

}
