package ca.horatiu.fractles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Horatiu on 12/06/2016.
 */
public class GenerateBMP extends View{
    double ZOOM = 500;
    int MAX_ITER =  570;
    int CONSTANT_SHIFT = 10;
    int ADDITIVE_FACTOR = 3;
    Paint paint;
    static Bitmap bmp;


    public GenerateBMP(Context context) {
        super(context);
        paint = new Paint();
    }


    public void generate(){

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bmp = Bitmap.createBitmap(getWidth(), getHeight(), conf);

        double zx, zy, cX, cY, tmp;

        Log.d("Fractals", "hi!" + ADDITIVE_FACTOR);

        for (int y = 0; y < getHeight(); y+= ADDITIVE_FACTOR) {
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
    }
}
