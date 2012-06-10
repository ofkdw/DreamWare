package com.android.mafia_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login_screen);
	
	    // TODO Auto-generated method stub
	    Button btn2 = (Button)findViewById(R.id.Login);
        btn2.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent(Login.this, SoketChat.class);
        		startActivity(intent);
        	}
        });
	}
}
