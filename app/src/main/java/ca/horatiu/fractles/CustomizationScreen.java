package ca.horatiu.fractles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;




public class CustomizationScreen extends AppCompatActivity {
    public final static String SCALING_FACTOR = "ca.horatiulazu.fractals.SCALING_FACTOR";
    public final static String ZOOM_AMOUNT = "ca.horatiulazu.fractals.ZOOM_AMOUNT";
    public final static String HEIGHT = "ca.horatiulazu.fractals.HEIGHT";
    public final static String WIDTH = "ca.horatiulazu.fractals.WIDTH";
    public final static String COLOR_SHIFT = "ca.horatiulazu.fractals.COLOR_SHIFT";
    public final static String QUALITY_DEPTH = "ca.horatiulazu.fractals.QUALITY_DEPTH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization_screen);
    }

    public void render(View view){
        int scalingFactor;
        int zoomAmount;
        int height;
        int width;
        int colorShift;
        int qualityDepth;


        try{
            scalingFactor = Integer.parseInt(((EditText)findViewById(R.id.scale_text)).getText().toString());
        }
        catch(NumberFormatException e){
            scalingFactor = 8;
        }

        try{
            zoomAmount = Integer.parseInt(((EditText)findViewById(R.id.zoom_text)).getText().toString());
        }
        catch(NumberFormatException e){
            zoomAmount = 500;
        }

        try {
            height = Integer.parseInt(((EditText) findViewById(R.id.height_text)).getText().toString());
        } catch (NumberFormatException e) {
            height = -1; //-1 means max!
        }


        try{
            width = Integer.parseInt(((EditText)findViewById(R.id.width_text)).getText().toString());
        }
        catch(NumberFormatException e){
            width = -1;
        }

        try{
            colorShift = Integer.parseInt(((EditText)findViewById(R.id.color_text)).getText().toString());
        }
        catch(NumberFormatException e){
            colorShift = 10;
        }

        try{
            qualityDepth = Integer.parseInt(((EditText)findViewById(R.id.depth_text)).getText().toString());
        }
        catch(NumberFormatException e){
            qualityDepth = 570;
        }

        Intent render = new Intent(this, MainActivity.class);
        render.putExtra(SCALING_FACTOR, scalingFactor);
        render.putExtra(ZOOM_AMOUNT, zoomAmount);
        render.putExtra(HEIGHT, height);
        render.putExtra(WIDTH, width);
        render.putExtra(COLOR_SHIFT, colorShift);
        render.putExtra(QUALITY_DEPTH, qualityDepth);
        startActivity(render);
    }
}
