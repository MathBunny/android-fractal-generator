package ca.horatiu.fractles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;




public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView renderScreen = new MyView(this);
        setContentView(renderScreen);

        Intent intent = getIntent();

        renderScreen.CONSTANT_SHIFT = intent.getIntExtra(CustomizationScreen.COLOR_SHIFT, 10);
        renderScreen.MAX_ITER = intent.getIntExtra(CustomizationScreen.QUALITY_DEPTH, 570);
        renderScreen.ZOOM = intent.getIntExtra(CustomizationScreen.ZOOM_AMOUNT, 500);
        renderScreen.ADDITIVE_FACTOR = intent.getIntExtra(CustomizationScreen.SCALING_FACTOR, 3);
        //renderScreen.HEIGHT = intent.getIntExtra(CustomizationScreen.HEIGHT, 3);
        //renderScreen.WIDTH = intent.getIntExtra(CustomizationScreen.HEIGHT, 3);

        //CustomizationScreen.
    }

    public class MyView extends View {
        double ZOOM = 500;
        int MAX_ITER =  570;
        int CONSTANT_SHIFT = 10;
        int ADDITIVE_FACTOR = 3;
        int once = 0;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp;

        Paint paint;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
        }

        public void mandelbrotRenderBMP(Canvas canvas){
            //Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            //Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), conf);

            double zx, zy, cX, cY, tmp;

            Log.d("Fractals", "hi!" + ADDITIVE_FACTOR + " by: " + getWidth() + " " + getHeight());

            for (int y = 0; y < getHeight(); y+= ADDITIVE_FACTOR) {
                Log.d("OK", "Ok so: " + y);
                for (int x = 0; x < getWidth(); x+= ADDITIVE_FACTOR) {
                    CustomizationScreen.progress.setProgress((((y*x)+x)/(getHeight()*getWidth()))*100);
                    //Log.d("Progress: " + )
                    zx = zy = 0;
                    cX = (x - 400) / ZOOM;
                    cY = (y - 300) / ZOOM;
                    int iter = MAX_ITER;
                    while (zx * zx + zy * zy < 4 && iter > 0) {
                        tmp = zx * zx - zy * zy + cX;
                        zy = 2.0 * zx * zy + cY;
                        zx = tmp;
                        iter--;
                    }
                    Paint color = new Paint();
                    int binaryColorRepresentation = (iter | iter << CONSTANT_SHIFT);
                    int blue = binaryColorRepresentation & 0xff;
                    int green = (binaryColorRepresentation & 0xff00) >> 8;
                    int red = (binaryColorRepresentation & 0xff0000) >> 16;
                    paint.setColor(Color.parseColor(String.format("#%02x%02x%02x", red, green, blue)));  //using paint instead :)
                    for(int xx = 0; xx < ADDITIVE_FACTOR; xx++){
                        for(int yy = 0; yy < ADDITIVE_FACTOR; yy++){
                            if (xx + x >= getWidth() || yy+y >= getHeight())
                                continue;
                            bmp.setPixel(xx+x, yy+y, Color.rgb(red, green,  blue));
                        }
                    }


                    //canvas.drawRect(x, y, x + ADDITIVE_FACTOR, y + ADDITIVE_FACTOR, paint);
                }
            }
            canvas.drawBitmap(bmp, new Matrix(), null);
            Log.d("Fractals", "Done!");
        }

        public void mandelbrotRender(Canvas canvas){
            int width = getWidth();
            int height = getHeight();
            int radius;
            if (once < 2) {
                once++;
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.WHITE);
                canvas.drawPaint(paint);
                paint.setColor(Color.parseColor("#CD5C5C"));

                double zx, zy, cX, cY, tmp;

                Log.d("Fractals", "hi!" + ADDITIVE_FACTOR);
                for (int y = 0; y < getHeight(); y+= ADDITIVE_FACTOR) {
                    for (int x = 0; x < getWidth(); x+= ADDITIVE_FACTOR) {
                        zx = zy = 0;
                        cX = (x - 400) / ZOOM;
                        cY = (y - 300) / ZOOM;
                        int iter = MAX_ITER;
                        while (zx * zx + zy * zy < 4 && iter > 0) {
                            tmp = zx * zx - zy * zy + cX;
                            zy = 2.0 * zx * zy + cY;
                            zx = tmp;
                            iter--;
                        }
                        Paint color = new Paint();
                        int binaryColorRepresentation = (iter | iter << CONSTANT_SHIFT);
                        int blue = binaryColorRepresentation & 0xff;
                        int green = (binaryColorRepresentation & 0xff00) >> 8;
                        int red = (binaryColorRepresentation & 0xff0000) >> 16;
                        paint.setColor(Color.parseColor(String.format("#%02x%02x%02x", red, green, blue)));  //using paint instead :)
                        canvas.drawRect(x, y, x + ADDITIVE_FACTOR, y + ADDITIVE_FACTOR, paint);
                    }
                }
                Log.d("Fractals", "Done!");
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //mandelbrotRender(canvas);
            if (once <2) {
                if (once == 0)
                    bmp= Bitmap.createBitmap(getWidth(), getHeight(), conf);
                mandelbrotRenderBMP(canvas);
                once++;
            }

        }
    }
}
