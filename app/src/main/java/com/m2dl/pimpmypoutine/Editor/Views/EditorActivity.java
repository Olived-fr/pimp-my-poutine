package com.m2dl.pimpmypoutine.Editor.Views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.m2dl.pimpmypoutine.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


public class EditorActivity extends AppCompatActivity {
private EditorView viewtest;
    static String pimpedPhoto;
    private Button buttonFiltre1;
    // Initial offset for rectangle.
    private static final int OFFSET = 120;
    // Multiplier for randomizing color.
    private static final int MULTIPLIER = 100;

    // The Canvas object stores information on WHAT to draw
    // onto its associated bitmap.
    // For example, lines, circles, text, and custom paths.
    private Canvas mCanvas;

    // The Paint object stores HOW to draw.
    // For example, what color, style, line thickness, or text size.
    // The Paint class offers a rich set of coloring and drawing options.
    private Paint mPaint = new Paint();

    // Create a Paint object for underlined text.
    // The Paint clas offers a full complement of typographical styling methods.
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

    // The bitmap represents the pixels that will be displayed.
    private Bitmap mBitmap;
    private Bitmap bitmap2;

    // The view is the container for the bitmap.
    // Layout on the screen and all user interaction is through the view.
    private ImageView mImageView;

    private Rect mRect = new Rect();
    // Distance of rectangle from edge of canvas.
    private int mOffset = OFFSET;
    // Bounding box for text.
    private Rect mBounds = new Rect();

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;
    float x = 200;
    float y = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        pimpedPhoto = intent.getStringExtra("pathPhoto");
      //  viewtest = new EditorView(this);
        setContentView(R.layout.activity_editor);

        viewtest =(EditorView) findViewById(R.id.editorView);

        buttonFiltre1 = findViewById(R.id.buttonFiltre1);
        buttonFiltre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonFiltre1.setText("oui");
                viewtest.getFiltre("lol");
                //   setContentView(R.layout.activity_editor);
            }
        });







        // viewtest = findViewById(R.id.editorView);
      //  this.a
      /*  buttonFiltre1 = findViewById(R.id.buttonFiltre1);
        buttonFiltre1 = findViewById(R.id.buttonFiltre1);
        viewtest = findViewById(R.id.editorView);
        buttonFiltre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonFiltre1.setText("oui");
             //   setContentView(R.layout.activity_editor);
            }
        });*/

        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);

        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);

        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);

        // Set properties of the Paint used to draw on the canvas.
        mPaint.setColor(mColorBackground);
        mPaintText.setColor(
                ResourcesCompat.getColor(getResources(),
                        R.color.colorPrimaryDark, null)
        );
        mPaintText.setTextSize(70);

        // Get a reference to the ImageView.
        mImageView = (ImageView) findViewById(R.id.myimageview);
        /*mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("onTouch");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                     v.invalidate();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = event.getX();
                        y = event.getY();
                       v.invalidate();
                        break;
                }
                return true;            }

        });*/
        // You cannot create the Canvas in onCreate(),
        // because the views have not been
        // laid out, so their final size is not available.
        // When you create a custom view in a later lesson,
        // you will learn other ways of initializing your drawing surface.
    }

    /**
     * Click handler that responds to user taps by drawing an increasingly
     * smaller rectangle until it runs out of room. Then it draws a circle
     * with the text "Done!". Demonstrates basics of drawing on canvas.
     *   1. Create bitmap.
     *   2. Associate bitmap with view.
     *   3. Create canvas with bitmap.
     *   4. Draw on canvas.
     *   5. Invalidate the view to force redraw.
     *
     * @param view The view in which we are drawing.
     */
    public void drawSomething(View view) {
        System.out.println("drawSomething");
        x = view.getScrollX();
        y = view.getScrollX();
        System.out.println("drawSomething" + x + " " + y);

        //view.even
        mBitmap = BitmapFactory.decodeFile(pimpedPhoto);
        // mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
       // mBitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
    //    mImageView.setImageBitmap(mBitmap);

        //bitmap2 = BitmapFactory.decodeFile(pimpedPhoto);
       // mCanvas.drawBitmap(bitmap2, 0, 0, null);
        mCanvas = new Canvas(mBitmap);

        mCanvas.drawBitmap(mBitmap, 0, 0, null);
        view.invalidate();

    }
   /*     int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;
        // Only do this first time view is clicked after it has been created.
        if (mOffset == OFFSET) { // Only true once, so don't need separate flag.
            // Each pixel takes 4 bytes, with alpha channel.
            // Use RGB_565 if you don't need alpha and a huge color palette.
            mBitmap = Bitmap.createBitmap(
                    vWidth, vHeight, Bitmap.Config.ARGB_8888);
            // Associate the bitmap to the ImageView.
            mImageView.setImageBitmap(mBitmap);
            // Create a Canvas with the bitmap.
            mCanvas = new Canvas(mBitmap);
            // Fill the entire canvas with this solid color.
            mCanvas.drawColor(mColorBackground);
            // Draw some text styled with mPaintText.
            mCanvas.drawText(getString(R.string.app_name), 100, 100, mPaintText);
            // Increase the indent.
            mOffset += OFFSET;
        } else {
            // Draw in response to user action.
            // As this happens on the UI thread, there is a limit to complexity.
            if (mOffset < halfWidth && mOffset < halfHeight) {
                // Change the color by subtracting an integer.
                mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset);
                mRect.set(
                        mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                // Increase the indent.
                mOffset += OFFSET;
            } else {
                mPaint.setColor(mColorAccent);
                mCanvas.drawCircle(
                        halfWidth, halfHeight, halfWidth / 3, mPaint);
                String text = getString(R.string.take_picture);
                // Get bounding box for text to calculate where to draw it.
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                // Calculate x and y for text so it's centered.
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }
        // Invalidate the view, so that it gets redrawn.
        view.invalidate();
    }*/

   /* public class EditorView extends View {
        Bitmap bitmap, bitmap2, mbitmap;
        float x = 200;
        float y = 200;
        private Button buttonFiltre1;

        public EditorView(Context context  ) {
            super(context);
            Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
            Drawable background = new BitmapDrawable(backgroundBitmap);
            this.setBackground(background);
            Button b = new Button(context);
            // b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
            //         LayoutParams.WRAP_CONTENT));
            // this.addView(b);

            Button yourButton = new Button(context);
            //  buttonFiltre1 = findViewById(R.id.buttonFiltre1);
            yourButton.setText("LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOL");
            yourButton.setTextColor(5000);
            yourButton.setWidth(Integer.parseInt("200"));
            yourButton.setHeight(Integer.parseInt("200"));
            //      this.addView(buttonFiltre1);
            //   this.addView(yourButton);

            //bitmap = BitmapFactory.decodeFile(pimpedPhoto);
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
            bitmap2 = BitmapFactory.decodeFile(pimpedPhoto);
           // mBitmap = Bitmap. createBitmap(50, 50, Bitmap.Config.ARGB_8888);
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
            canvas.drawBitmap(bitmap2, 0, 0, null);
            canvas.drawBitmap(bitmap, x, y, null);
            //canvas.drawBitmap(mbitmap, 100, 100, null);
        }
    }*/

}
