// Copyright 2013 Alex Baratti

package com.example.mysecondandroidproject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class somethingElseActivity extends Activity implements OnTouchListener {

	double radius = 54;

	double ballX = radius + 5;
	double ballY = radius + 5;
	double ballXSpeed = 15;
	double ballYSpeed = 15;
	
	double gravity = 9.8;

	SomethingView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		view = new SomethingView(this);
		super.onCreate(savedInstanceState);
		setContentView(view);
		view.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		view.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		view.resume();
	}

	public class SomethingView extends SurfaceView implements Runnable{

		Paint p = new Paint();
		Thread thread = null;
		SurfaceHolder holder;
		boolean isItOk = false;
		
		public SomethingView(Context context) {
			super(context);
			holder = getHolder();
		}

		@Override
		public void run() {
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(isItOk == true) {
				//Perform canvas actions
				if(!holder.getSurface().isValid()) {
					continue;
				}
				Canvas c = holder.lockCanvas();
				p.setColor(Color.BLUE);
				c.drawColor(Color.WHITE);
				c.drawCircle((int)ballX, (int)ballY, (int)radius, p);
				ballX += ballXSpeed;
				ballY += ballYSpeed;
				ballYSpeed += gravity * 0.1;
				if(ballX >= c.getWidth() - radius) {
					ballXSpeed = -ballXSpeed;
				}
				if(ballY >= c.getHeight() - radius) {
					ballY = c.getHeight() - radius;
					ballYSpeed = -ballYSpeed;
				}
				if(ballX <= radius) { 
					ballXSpeed *= -1;
				}
				if(ballY <= radius) {
					ballY = radius;
					ballYSpeed *= -1;
				}
				holder.unlockCanvasAndPost(c);
			}
		}
		public void pause() {
			isItOk = false;	
			while(true) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			thread = null;
		}
		public void resume() {
			isItOk = true;
			thread = new Thread(this);
			thread.start();
		}
	}
}
