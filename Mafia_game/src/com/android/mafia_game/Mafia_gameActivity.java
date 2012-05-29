package com.android.mafia_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mafia_gameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn1 = (Button)findViewById(R.id.Sign_Up);
        btn1.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent(Mafia_gameActivity.this, Sign_Up.class);
        		startActivity(intent);
        	}
        });
        Button btn2 = (Button)findViewById(R.id.Login);
        btn2.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent(Mafia_gameActivity.this, Login.class);
        		startActivity(intent);
        	}
        });
    }
}