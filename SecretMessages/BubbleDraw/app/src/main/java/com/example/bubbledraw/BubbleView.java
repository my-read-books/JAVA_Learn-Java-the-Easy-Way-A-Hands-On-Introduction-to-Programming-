package com.example.bubbledraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.View;


import java.util.ArrayList;
import java.util.Random;

public class BubbleView extends ImageView implements View.OnTouchListener {
    private Random rand = new Random();
    private ArrayList<Bubble> bubbleList;
    private int size=50;
    private int delay = 33;
    private Paint myPaint = new Paint();
    private Handler h = new Handler();

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bubbleList = new ArrayList<Bubble>();
//        testBubbles();
        setOnTouchListener(this);
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            for (Bubble b: bubbleList)
                b.update();
            invalidate();
        }
    };

    protected void onDraw(Canvas canvas){
        for (Bubble b: bubbleList)
            b.draw(canvas);
        h.postDelayed(r, delay);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int touchCounter = motionEvent.getPointerCount();
        if (touchCounter > 1){
        for (int n=0; n<touchCounter; n++) {
            int x = (int) motionEvent.getX(n);
            int y = (int) motionEvent.getY(n);
            int s = rand.nextInt(size) + size - touchCounter * 5;
            bubbleList.add(new Bubble(x, y, s));
        }}
        else{
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int s = rand.nextInt(size) + size;
            bubbleList.add(new Bubble(x, y, s, 10, 10));
        }
        return true;
    }

    private class Bubble {

        private int x;
        private int y;
        private int size;
        private int color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 15;

        public Bubble(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            color = Color.argb(rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256), rand.nextInt(256));
            xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            if (xspeed == 0 && yspeed == 0) {
                xspeed = 1;
                yspeed = 1;
            }
        }

        public Bubble(int x, int y, int size, int xspeed, int yspeed){
            this.x = x;
            this.y = y;
            this.size = size;
            color = Color.argb(rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256), rand.nextInt(256));
            this.xspeed = xspeed;
            this.yspeed = yspeed;

        }

        public void draw(Canvas canvas) {
            myPaint.setColor(color);
            canvas.drawOval(x-size/2, y-size/2, x+size/2, y + size/2, myPaint);
        }
        public void update(){
            x += xspeed;
            y += yspeed;
            if (x - size / 2 <= 0 || x + size / 2 >= getWidth())
                xspeed = -xspeed;
            if (y - size / 2 <= 0 || y + size / 2 >= getHeight())
                yspeed = -yspeed;        }
    }
}
