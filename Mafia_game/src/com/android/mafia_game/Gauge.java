package com.android.mafia_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;

public class Gauge {
	public boolean isTimeout = false;
	public Bitmap imgGauge;
	private long startTime;
	private int sec;
	private int pos = 0;
	private Canvas canvas = new Canvas();
	private Paint paint = new Paint();
	
	public Gauge(long _startTime, int _sec) {
		startTime=_startTime;
		sec=_sec;
		pos=0;
		imgGauge = Bitmap.createBitmap(150, 15, Bitmap.Config.ARGB_8888);
		canvas.setBitmap(imgGauge);
		
		//게이지 만들기
		paint.setShader(new LinearGradient (0, 0, 0, 15, Color.GREEN, Color.BLACK, TileMode.CLAMP));
		canvas.drawRect(pos, 0, 150, 15, paint);
		Progress();
	}

	//Progress
	private boolean Progress() {
		long thisTime = System.currentTimeMillis();
		float f = (thisTime - startTime) / 1000f;
		pos = (int)(150.f/sec*f);
		Paint pnt = new Paint();
		pnt.setColor(Color.WHITE);
		canvas.drawRect(0, 0, 150, 15, pnt);
		canvas.drawRect(pos, 0, 150, 15, pnt);		
		return false;		
	}
}



/*
    Gauge mGauge;
    mGauge = new Gauge(System.currentTimeMillis(), 60);
    mGauge.Progress();
    canvas.drawBitmap(mGauge.imGauge, width - 155, 5, null);
*/
