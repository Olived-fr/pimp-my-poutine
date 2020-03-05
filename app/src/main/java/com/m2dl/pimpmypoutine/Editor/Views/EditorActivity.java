package com.m2dl.pimpmypoutine.Editor.Views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.m2dl.pimpmypoutine.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class EditorActivity extends AppCompatActivity {

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        requestPermissions(new String[]{READ_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}
                , 10);


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
        mImageView = (ImageView) findViewById(R.id.imageView);

        // You cannot create the Canvas in onCreate(),
        // because the views have not been
        // laid out, so their final size is not available.
        // When you create a custom view in a later lesson,
        // you will learn other ways of initializing your drawing surface.
    }

    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;

        if (mOffset == OFFSET) { // Only true once, so don't need separate flag.
            // Each pixel takes 4 bytes, with alpha channel.
            // Use RGB_565 if you don't need alpha and a huge color palette.
            BitmapFactory.Options opts;
            mBitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/IMG_20200305_111046.jpg");
            // Associate the bitmap to the ImageView.
            mImageView.setImageBitmap(mBitmap);
            // Create a Canvas with the bitmap.
            mCanvas = new Canvas(mBitmap);
            // Fill the entire canvas with this solid color.
            mCanvas.drawColor(mColorBackground);
            // Draw some text styled with mPaintText.
            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
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
                String text = getString(R.string.done);
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
    }
}
