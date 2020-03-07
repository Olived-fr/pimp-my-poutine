package com.m2dl.pimpmypoutine.Editor.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.m2dl.pimpmypoutine.R;


public class EditorActivity extends View {
    Bitmap bitmap, bitmap2;
    float x = 200;
    float y = 200;

    public EditorActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        Drawable background = new BitmapDrawable(backgroundBitmap);
        this.setBackground(background);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap2, 0, 0, null);
        canvas.drawBitmap(bitmap, x, y, null);
    }

}
