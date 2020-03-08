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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.m2dl.pimpmypoutine.R;

import static android.content.Context.SENSOR_SERVICE;
import static com.m2dl.pimpmypoutine.Editor.Views.EditorActivity.pimpedPhoto;

public class EditorView extends View {
        Bitmap bitmap, bitmap2;
        private String luminosityHexa;
        int hexa, hexa1, hexa2, hexa3 ;
    int color;
    int colorMagnet = (0xff) << 24 | (0xff) << 16 | (0xff) << 8 | (0xff) ;
    float x = 200;
    float y = 200;
    float luminosity = 0;
    Paint paint = new Paint();
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor mMagneticField;
    private SensorEventListener lightEventListener;
    // Bitmap drawingBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher_background);
    private float maxValue;

    public EditorView(Context context, AttributeSet attrs ) {
        super(context, attrs);
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        Drawable background = new BitmapDrawable(backgroundBitmap);
        this.setBackground(background);
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (lightSensor == null) {
            System.out.println("lightSensor == null");
        }
        maxValue = lightSensor.getMaximumRange();

        //bitmap = BitmapFactory.decodeFile("./app/res/mipmap-hdpi/ic_launcher.png");
        bitmap = BitmapFactory.decodeFile(pimpedPhoto);

        //  bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        bitmap2 = BitmapFactory.decodeFile(pimpedPhoto);
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                System.out.println("lightSensor value d " );
                System.out.println(" lightSensor alues[0]x " + sensorEvent.values);

                float value = sensorEvent.values[0];
                if (luminosity < value - 5 || luminosity > value + 5) {

                    System.out.println("lightSensor value d " + value + " last " + luminosity);
                    luminosity = value;

                    float lumi = luminosity * 5;
                    if (lumi > 255) lumi = 255;
                  //  if (lumi < 100) lumi = 100;

                    //hexa = Integer.parseInt(Integer.toString((int) lumi,16));
                    hexa = (int) lumi;
                  //  if(Integer.toString((int) lumi,16).length() < 2 ) hexa = Integer.parseInt("0" + hexa);

                    color = (hexa & 0xff) << 24 | (hexa & 0xff) << 16 | (hexa & 0xff) << 8 | (hexa & 0xff);
                //    luminosityHexa =  "0x00" + hexa +  hexa +  hexa;
                    System.out.println("hexa " + color);
                    invalidate();

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //float value = sensorEvent.values[0];
                System.out.println("lightSensor value " + accuracy);
            }
        };

        SensorEventListener listenerMagnetic = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                float [] values = sensorEvent.values;
                System.out.println("sensorEvent.values[0].values[0]x " + value);

                 synchronized (this) {
                         float magField_x = values[0];
                     float magField_y = values[1];
                     float magField_z = values[2];
                         System.out.println("magField_x " + magField_x + " magField_y " + magField_y + " magField_z " + magField_z);
                     int magField_xResult = (int) (150  + magField_x);
                     int magField_yResult = (int) (150  + magField_y);
                     int magField_zResult = (int) (150  + magField_z);
                        // float lumi = luminosity;
                         if (magField_xResult > 255) magField_xResult = 255;
                         if (magField_xResult < 0) magField_xResult = 0;
                     if (magField_yResult > 255) magField_yResult = 255;
                     if (magField_yResult < 0) magField_yResult = 0;
                     if (magField_zResult > 255) magField_zResult = 255;
                     if (magField_zResult < 0) magField_zResult = 0;
                         //hexa = Integer.parseInt(Integer.toString((int) lumi,16));
                     hexa1 = (int) magField_xResult;
                     hexa2 = (int) magField_yResult;
                     hexa3 = (int) magField_zResult;
                         //  if(Integer.toString((int) lumi,16).length() < 2 ) hexa = Integer.parseInt("0" + hexa);

                     colorMagnet = (0xff) << 24 | (hexa1 & 0xff) << 16 | (hexa2 & 0xff) << 8 | (hexa3 & 0xff);
                         //    luminosityHexa =  "0x00" + hexa +  hexa +  hexa;
                         System.out.println("hexa " + color);
                         invalidate();

                     }
              /*  if (luminosity < value - 5 || luminosity > value + 5) {

                    System.out.println("lightSensor value d " + value + " last " + luminosity);
                    luminosity = value;

                    float lumi = luminosity;
                    if (lumi > 255) lumi = 255;
                    if (lumi < 100) lumi = 100;

                    //hexa = Integer.parseInt(Integer.toString((int) lumi,16));
                    hexa = (int) lumi;
                    //  if(Integer.toString((int) lumi,16).length() < 2 ) hexa = Integer.parseInt("0" + hexa);

                    color = (hexa & 0xff) << 24 | (hexa & 0xff) << 16 | (hexa & 0xff) << 8 | (hexa & 0xff);
                    //    luminosityHexa =  "0x00" + hexa +  hexa +  hexa;
                    System.out.println("hexa " + color);
                    invalidate();

                }*/
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //float value = sensorEvent.values[0];
                System.out.println("onAccuracyChanged value " + accuracy);
            }
        };
        sensorManager.registerListener(
                listener, lightSensor, SensorManager.SENSOR_DELAY_UI);
       // sensorManager.registerListener(
        //        listener, mMagneticField, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(
                listenerMagnetic, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.out.println("test");

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
        canvas.drawBitmap(makeTintedBitmap(bitmap2, color), 0, 0, paint);
        canvas.drawBitmap(bitmap, x, y, null);
    }
    public Bitmap makeTintedBitmap(Bitmap src, int color) {
        Bitmap result = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas c = new Canvas(result);
        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(color,colorMagnet));
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
