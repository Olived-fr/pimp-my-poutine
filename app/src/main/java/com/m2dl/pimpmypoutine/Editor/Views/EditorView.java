package com.m2dl.pimpmypoutine.Editor.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.m2dl.pimpmypoutine.R;

import java.io.File;

import static android.content.Context.SENSOR_SERVICE;
import static com.m2dl.pimpmypoutine.Editor.Views.EditorActivity.pimpedPhoto;

public class EditorView extends View {
    Bitmap bitmap, bitmap2, bitmap3;
    private Integer poutineClick1 = 0;
    private Integer poutineClick2 = 0;
    private float coordPoutine2x, coordPoutine2y, coordPoutine1x, coordPoutine1y;
    int hexa, hexa1, hexa2, hexa3 ;
    private String poutine1="";
    private String poutine2="";
    private Integer isPoutine = 0;
    private Integer isPoutinetest =0;
    private String priority = "";
    private String priorityg = "";
    private Boolean firstTime = true;
    int color;
    int colorMagnet = (0xff) << 24 | (0xff) << 16 | (0xff) << 8 | (0xff) ;
    float x = 200;
    float y = 200;
    float currentField_x = 0;
    float currentField_y = 0;
    float currentField_z = 0;
    float luminosity = 0;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor mMagneticField;

    public EditorView(Context context, AttributeSet attrs ) {
        super(context, attrs);


        File file = new File(pimpedPhoto);
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        bitmap2 = BitmapFactory.decodeFile(file.getAbsolutePath());

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                if (luminosity < value - 5 || luminosity > value + 5) {
                    luminosity = value;
                    float lumi = luminosity * 5;
                    if (lumi > 255) lumi = 255;
                    hexa = (int) lumi;
                    color = (hexa & 0xff) << 24 | (hexa & 0xff) << 16 | (hexa & 0xff) << 8 | (hexa & 0xff);
                    invalidate();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        SensorEventListener listenerMagnetic = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float [] values = sensorEvent.values;

                 synchronized (this) {
                     if (currentField_x == 0 && currentField_y == 0 && currentField_z == 0) {
                         currentField_x = values[0];
                         currentField_y = values[1];
                         currentField_z = values[2];
                     }
                     float magField_x = values[0];
                     float magField_y = values[1];
                     float magField_z = values[2];

                     if(currentField_x - magField_x > 5 || currentField_x - magField_x < -5
                     || currentField_y - magField_y > 5 || currentField_y - magField_y < -5
                     || currentField_z - magField_z > 5 || currentField_z - magField_z < -5) {
                         if (firstTime) {
                            firstTime = false;
                         }
                         int magField_xResult = (int) (50  + magField_x);
                         int magField_yResult = (int) (50  + magField_y);
                         int magField_zResult = (int) (50  + magField_z);

                         if (magField_xResult > 255) magField_xResult = 255;
                         if (magField_xResult < 0) magField_xResult = 0;
                         if (magField_yResult > 255) magField_yResult = 255;
                         if (magField_yResult < 0) magField_yResult = 0;
                         if (magField_zResult > 255) magField_zResult = 255;
                         if (magField_zResult < 0) magField_zResult = 0;
                         hexa1 = (int) magField_xResult;
                         hexa2 = (int) magField_yResult;
                         hexa3 = (int) magField_zResult;

                         colorMagnet = (0xff) << 24 | (hexa1 & 0xff) << 16 | (hexa2 & 0xff) << 8 | (hexa3 & 0xff);
                         currentField_x = values[0];
                         currentField_y = values[1];
                         currentField_z = values[2];
                         invalidate();
                     } else {
                         invalidate();

                     }
                 }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(
                listener, lightSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(
                listenerMagnetic, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);

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
        if (firstTime) {
            int w = canvas.getClipBounds().width();
            int h = canvas.getClipBounds().height();
            canvas.drawBitmap(bitmap2, null, new RectF(0, 0, w, h),  null);
        } else {
            int w = canvas.getClipBounds().width();
            int h = canvas.getClipBounds().height();
            canvas.drawBitmap(makeTintedBitmap(bitmap2, color), null, new RectF(0, 0, w, h),  null);
        }
        if(poutine1.equals("fromage")) {
            Drawable d = getResources().getDrawable(R.drawable.poutine2t);
            bitmap = drawableToBitmap(d);
            if (poutineClick1 == 1 && priorityg.equals("fromage")) {
                if(priority.equals("fromage") && isPoutine == 1) {
                    x = coordPoutine1x;
                    y = coordPoutine1y;
                    isPoutinetest = 1;
                    priority = "";
                }
                canvas.drawBitmap(bitmap, x, y, null);
                coordPoutine1x = x;
                coordPoutine1y = y;
            } else if (poutineClick1 != 10) {
                canvas.drawBitmap(bitmap, coordPoutine1x, coordPoutine1y, null);
            }
        }

        if(poutine2.equals("effiloché")) {
            Drawable dr = getResources().getDrawable(R.drawable.poutine1t);
            //On transforme l'objet Drawable en Bitmap pour redéfinir sa taille
            bitmap3 = ((BitmapDrawable) dr).getBitmap();
            if (poutineClick2 == 2 && priorityg.equals("effiloché")) {
                if(priority.equals("effiloché") && isPoutine == 2) {
                    x = coordPoutine2x;
                    y = coordPoutine2y;
                    isPoutinetest = 2;
                    priority = "";
                }
                canvas.drawBitmap(bitmap3, x, y, null);
                coordPoutine2x = x;
                coordPoutine2y = y;
            } else if (poutineClick2 != 20){
                canvas.drawBitmap(bitmap3, coordPoutine2x, coordPoutine2y, null);
            }
        }
    }

    public Bitmap makeTintedBitmap(Bitmap src, int color) {
        Bitmap result = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas c = new Canvas(result);
        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(color,colorMagnet));
        c.drawBitmap(src, 0, 0, paint);
        return result;
    }
    public void getPoutine (String poutine) {
        if(poutine.equals("fromage"))  {
            if(this.poutine1.equals("fromage")) {
                this.poutine1 = "";
                if(this.poutine2.equals("effiloché")) {
                    priorityg =this.poutine2;
                   poutineClick2 = 2;
                }
            }
            else {
                this.poutine1 = poutine;
                    priorityg = "fromage";
                }
            if (poutineClick1 == 1) poutineClick1 = 10;
            else poutineClick1=1;
            if (isPoutinetest == 2 || isPoutinetest == 0) isPoutine = 1;
            priority = this.poutine1 ;
        }
        if(poutine.equals("effiloché")) {
            if(this.poutine2.equals("effiloché")) {
                this.poutine2 = "";
                if (this.poutine1.equals("fromage")) {
                    priorityg ="fromage";
                    poutineClick1 = 1;
                }
            }
            else {
                this.poutine2 = poutine;
                priorityg = "effiloché";
            }
            if (poutineClick2 == 2) poutineClick2 = 20;
            else poutineClick2=2;
            if (isPoutinetest == 1 || isPoutinetest == 0) isPoutine = 2;
            priority = this.poutine2;
        }
        invalidate();

    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public static Bitmap resizeBitmap(Bitmap orig,int newWidth,int newHeight){
        int width = orig.getWidth();
        int height = orig.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(orig, 0, 0,width, height, matrix, true);
        return resizedBitmap;
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public Bitmap validImage() {
        //Firebase
        this.layout(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache(true);
        Bitmap bitmap5 = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
    return bitmap5;
    }
}
