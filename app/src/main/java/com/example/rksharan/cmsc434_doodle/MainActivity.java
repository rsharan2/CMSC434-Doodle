package com.example.rksharan.cmsc434_doodle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);
        final Button redoButton = (Button) findViewById(R.id.redoButton);
        final Button undoButton = (Button) findViewById(R.id.undoButton);

        doodleView.setUndoButton(undoButton);
        doodleView.setRedoButton(redoButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.clear();
            }

        });



        redoButton.setClickable(false);
        redoButton.setEnabled(false);
        //redoButton.setBackgroundColor(Color.GRAY);
        undoButton.setClickable(false);
        undoButton.setEnabled(false);
        //undoButton.setBackgroundColor(Color.GRAY);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.undo();
            }
        });

        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.redo();
            }
        });

        final SeekBar brushSizeSeekBar = (SeekBar) findViewById(R.id.brushSizeSeekBar);
        final TextView brushSizeText = (TextView) findViewById(R.id.brushSizeText);
        brushSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               // brushSizeText.setTextSize(10+progress/10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"Brush size set to: " + brushSizeSeekBar.getProgress(),Toast.LENGTH_SHORT).show();
                doodleView.setBrushSize(brushSizeSeekBar.getProgress());
            }
        });

        final SeekBar brushColorSeekBar = (SeekBar) findViewById(R.id.colorSeekBar);
        final TextView colorText = (TextView) findViewById(R.id.colorText);
        colorText.setTextColor(Color.RED);
        brushColorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //TextView colorText = (TextView) findViewById(R.id.colorText);
                float[] hsv = {progress,1,1};
                colorText.setTextColor(Color.HSVToColor(hsv));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"Brush color set to: " + brushColorSeekBar.getProgress(),Toast.LENGTH_SHORT).show();
                doodleView.setBrushColor(brushColorSeekBar.getProgress());
            }
        });

        final SeekBar opacitySeekbar = (SeekBar) findViewById(R.id.opacitySeekBar);
        opacitySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"Brush opacity set to: " + opacitySeekbar.getProgress(),Toast.LENGTH_SHORT).show();
                doodleView.setBrushOpacity(opacitySeekbar.getProgress());
            }
        });


    }
}
