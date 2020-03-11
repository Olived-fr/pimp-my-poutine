package com.m2dl.pimpmypoutine.Editor.Views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.m2dl.pimpmypoutine.R;

import static android.content.Context.SENSOR_SERVICE;
import static com.m2dl.pimpmypoutine.Editor.Views.EditorActivity.pimpedPhoto;

public class EditorView extends View {
        Bitmap bitmap, bitmap2, bitmap3;
        private String luminosityHexa;
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
    Resources image;

    ImageView imageView;
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
    private float maxValue;
View view;
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
        BitmapFactory.Options bmpFactory = new BitmapFactory.Options();
        bmpFactory.inSampleSize = 2;
        bitmap2 = BitmapFactory.decodeFile(pimpedPhoto, bmpFactory);

        //bitmap = BitmapFactory.decodeFile("./app/res/mipmap-hdpi/ic_launcher.png");
      //  bitmap = BitmapFactory.decodeFile(pimpedPhoto);
       // Drawable d = getResources().getDrawable(R.drawable.ic_launcher_background);
    //    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.);
       // ImageView imageView = (ImageView) findViewById(R.id.myimageview);
       // Drawable dr = getResources().getDrawable(R.drawable.test2);
        //On transforme l'objet Drawable en Bitmap pour redéfinir sa taille
      //  bitmap = ((BitmapDrawable) dr).getBitmap();
       // Resources res = getResources();
      //  Drawable drawable = res.getDrawable(R.drawable.sp, getTheme());        //  bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        bitmap2 = BitmapFactory.decodeFile(pimpedPhoto, bmpFactory);
     //  view.measure (MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
      //  int widht = view.getMeasuredWidth ();
      //  int height = view.getMeasuredHeight ();
       // System.out.println("lightSensor value d " + widht);
      //  bitmap2 = resizeBitmap(bitmap2, widht,height);
      //  bitmap2.compress(Bitmap.CompressFormat.JPEG,80,);
     //   Canvas canvas = new Canvas(bitmap);
//this.draw(canvas);
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
               // System.out.println("lightSensor value d " );
               // System.out.println(" lightSensor alues[0]x " + sensorEvent.values);

                float value = sensorEvent.values[0];
                if (luminosity < value - 5 || luminosity > value + 5) {

                   // System.out.println("lightSensor value d " + value + " last " + luminosity);
                    luminosity = value;

                    float lumi = luminosity * 5;
                    if (lumi > 255) lumi = 255;
                    hexa = (int) lumi;
                    color = (hexa & 0xff) << 24 | (hexa & 0xff) << 16 | (hexa & 0xff) << 8 | (hexa & 0xff);
                    System.out.println("hexa " + color);
                    invalidate();

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
               // System.out.println("lightSensor value " + accuracy);
            }
        };

        SensorEventListener listenerMagnetic = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                float [] values = sensorEvent.values;
              //  System.out.println("sensorEvent.values[0].values[0]x " + value);

                 synchronized (this) {
                         float magField_x = values[0];
                     float magField_y = values[1];
                     float magField_z = values[2];
                   //      System.out.println("magField_x " + magField_x + " magField_y " + magField_y + " magField_z " + magField_z);
                     int magField_xResult = (int) (150  + magField_x);
                     int magField_yResult = (int) (150  + magField_y);
                     int magField_zResult = (int) (150  + magField_z);

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
                         //System.out.println("hexa " + color);
                         invalidate();

                     }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //float value = sensorEvent.values[0];
                //System.out.println("onAccuracyChanged value " + accuracy);
            }
        };
        sensorManager.registerListener(
                listener, lightSensor, SensorManager.SENSOR_DELAY_UI);
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
        canvas.drawBitmap(makeTintedBitmap(RotateBitmap(bitmap2, 90), color), 0, 0, null);
//canvas.setBitmap(makeTintedBitmap(RotateBitmap(bitmap2, 90), color));
        if(poutine1.equals("fromage")) {
            //Drawable myDrawable = getResources().getDrawable(R.mipmap.test);
            System.out.println("fromage ");


            // imageView.setImageResource(R.drawable.ic_launcher_background);
            Drawable d = getResources().getDrawable(R.drawable.poutine2t);
            bitmap = drawableToBitmap(d);

            // bitmap = ((BitmapDrawable)image.getDrawable(0)).getBitmap();
            // imageView.setImageResource(R.drawable.ic_launcher_background);
            //this.setDrawingCacheEnabled(true);
            //bitmap= ((BitmapDrawable) myDrawable).getBitmap();
            System.out.println("tpriority.equals(\"fromage\")   " + priorityg + " "+ priorityg.equals("fromage"));
            System.out.println("PRIORITY +" +  priorityg);

            if (poutineClick1 == 1 && priorityg.equals("fromage")) {
                System.out.println("poutineClick1isPoutine " + isPoutine);

                if(priority.equals("fromage") && isPoutine == 1) {
                    System.out.println("pisPoutine1 isPoutine " + isPoutine);

                    x = coordPoutine1x;
                    y = coordPoutine1y;
                    isPoutinetest = 1;
                    priority = "";
                }
                canvas.drawBitmap(bitmap, x, y, null);
                coordPoutine1x = x;
                coordPoutine1y = y;
            } else if (poutineClick1 != 10) {
                System.out.println("poutineClick1 != 10 " + isPoutine);

                canvas.drawBitmap(bitmap, coordPoutine1x, coordPoutine1y, null);

            }

        }


        if(poutine2.equals("effiloché")) {
    System.out.println("effiloché");


    Drawable dr = getResources().getDrawable(R.drawable.poutine1t);
    //On transforme l'objet Drawable en Bitmap pour redéfinir sa taille
    bitmap3 = ((BitmapDrawable) dr).getBitmap();
            System.out.println("tpriority.equals(\"effiloché\")   " + priorityg.equals("effiloché"));

            if (poutineClick2 == 2 && priorityg.equals("effiloché")) {
                System.out.println("poutineClick2 isPoutine " + isPoutine);

                if(priority.equals("effiloché") && isPoutine == 2) {
                    System.out.println("pisPoutine2 isPoutine " + isPoutine);

                    x = coordPoutine2x;
                    y = coordPoutine2y;
                    isPoutinetest = 2;
                    priority = "";

                }
                canvas.drawBitmap(bitmap3, x, y, null);
                coordPoutine2x = x;
                coordPoutine2y = y;
            } else if (poutineClick2 != 20){
                System.out.println("ppoutineClick2 != 20 " + isPoutine);

                canvas.drawBitmap(bitmap3, coordPoutine2x, coordPoutine2y, null);
//Canvas can = new Canvas(canvas.);
}
}    //    return bitmap = Bitmap.createBitmap(canvas.);


        //     canvas.drawBitmap(bitmap, x, y, null);

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
                System.out.println("fromage !!!! ");
        if(poutine.equals("fromage"))  {
            if(this.poutine1.equals("fromage")) {
                this.poutine1 = "";
                System.out.println("this.poutine2   " + this.poutine2);

                if(this.poutine2.equals("effiloché")) {
                    priorityg =this.poutine2;
                   poutineClick2 = 2;
                    //else poutineClick2=2;
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
                System.out.println("this.poutine1   " + this.poutine1);
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
        //    priorityg = this.poutine2;


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
