package com.m2dl.pimpmypoutine.Editor.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.m2dl.pimpmypoutine.R;

import static com.m2dl.pimpmypoutine.Editor.Views.EditorActivity.pimpedPhoto;

public class EditorView extends View {
        Bitmap bitmap, bitmap2;
    float x = 200;
    float y = 200;
    private Button buttonFiltre1;

    public EditorView(Context context, AttributeSet attrs ) {
        super(context, attrs);
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        Drawable background = new BitmapDrawable(backgroundBitmap);
        this.setBackground(background);

        //bitmap = BitmapFactory.decodeFile("./app/res/mipmap-hdpi/ic_launcher.png");
        bitmap = BitmapFactory.decodeFile(pimpedPhoto);

        //  bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        bitmap2 = BitmapFactory.decodeFile(pimpedPhoto);
        //  buttonFiltre1 = findViewById(R.id.buttonFiltre1);
     /*   buttonFiltre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        });*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
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

       // paint.setAntiAlias(true);
       // paint.setColor(500);
        canvas.drawBitmap(makeTintedBitmap(bitmap2, 200), 0, 0, paint);
        canvas.drawBitmap(bitmap, x, y, null);
    }
    public Bitmap makeTintedBitmap(Bitmap src, int color) {
        Bitmap result = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas c = new Canvas(result);
        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(color,0));
        c.drawBitmap(src, 0, 0, paint);
        return result;
    }
    public void getFiltre (String filtre) {
       /* switch (filtre) {
            case "filtre 1":
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
            case "filtre 2":
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
            System.out.println("OUI !!!! ");
        }*/
    }
}
