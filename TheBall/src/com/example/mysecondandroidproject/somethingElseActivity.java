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

	int ballX = 5;
	int ballY = 5;
	int ballXSpeed = 1;
	int ballYSpeed = 1;

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
				c.drawCircle(ballX, ballY, 100, p);
				ballX += ballXSpeed;
				ballY += ballYSpeed;
				if(ballX >= c.getWidth()) {
					ballXSpeed = -ballXSpeed;
				}
				if(ballY >= c.getHeight()) {
					ballYSpeed = -ballYSpeed;
				}
				if(ballX <= 0) {
					ballXSpeed *= -1;
				}
				if(ballY <= 0) {
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
