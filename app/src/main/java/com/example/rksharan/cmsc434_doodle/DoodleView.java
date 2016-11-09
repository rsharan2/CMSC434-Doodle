package com.example.rksharan.cmsc434_doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by rksharan on 10/31/2016.
 */
public class DoodleView extends View {

    private ArrayList<PaintSegment> paintSegments;
    private ArrayList<PaintSegment> oldsegments;
    private PaintSegment currentPaintSegment;
    private Button redoButton;
    private Button undoButton;

    private float width = 14;
    private int opacity = 255;
    private int color = 0;
    //private Paint _paintDoodle = new Paint();
    //private Path _path = new Path();

    public DoodleView(Context context){
        super(context);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(attrs,0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defstyle){
        super(context, attrs, defstyle);
        init(attrs,defstyle);
    }

    private void init(AttributeSet attrs, int defStyle){
        paintSegments = new ArrayList<PaintSegment>();
        oldsegments = new ArrayList<PaintSegment>();
        //currentPaintSegment = new PaintSegment(color,width,opacity);
        //paintSegments.add(currentPaintSegment);
    }

    public void setRedoButton(Button r){
        redoButton = r;
    }

    public void setUndoButton(Button u){
        undoButton = u;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //int i =0;
        Log.i("onDraw","starting");
        Log.i("onDraw", "Paint seg:" + paintSegments);
        if(currentPaintSegment != null) {
            Log.i("onDraw","Current: "+ currentPaintSegment);
            canvas.drawPath(currentPaintSegment.getPath(), currentPaintSegment.getPaint());
        } else {
            Log.i("onDraw", "Current: null");
        }
        for(PaintSegment p : paintSegments) {
            //Log.i("onDraw",p.toString());
            canvas.drawPath(p.getPath(), p.getPaint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        //System.out.print("draw");

        //final Button undoButton = (Button) findViewById(R.id.undoButton);

//        if(paintSegments.size() != 0 && paintSegments.get(paintSegments.size() -1 ) == currentPaintSegment){
//            undoButton.setEnabled(true);
//            undoButton.setClickable(true);
//            //redoButton.setBackgroundColor(android.R.drawable.btn_default);
//        }

        //if(currentPaintSegment == null){
        //    currentPaintSegment = new PaintSegment(color, width, opacity);
        //}
        //Path newPath = currentPaintSegment.getPath();
        Path newPath;
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(currentPaintSegment == null) {
                    currentPaintSegment = new PaintSegment(color, width, opacity);
                } else {
                    paintSegments.add(currentPaintSegment);
                    currentPaintSegment = new PaintSegment(color, width, opacity);
                }
                newPath = currentPaintSegment.getPath();
                newPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                newPath = currentPaintSegment.getPath();
                newPath.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                //Log.i("adding path",currentPaintSegment.toString());
                //Log.i("segments",paintSegments.toString());
                //Log.i("Action","UP");
                //paintSegments.add(currentPaintSegment);
                //currentPaintSegment = null;
                //currentPaintSegment = new PaintSegment(color,width,opacity);
                //paintSegments.add(currentPaintSegment);
                //Log.i("adding path",paintSegments.toString());
                break;
        }
        //paintSegments.add(newPath);
        invalidate();
        undoButton.setEnabled(true);
        undoButton.setClickable(true);

        return true;
        //return super.onTouchEvent(motionEvent);
    }

    public void clear(){
        paintSegments  = new ArrayList<PaintSegment>();
        currentPaintSegment = null;
        //paintSegments.add(currentPaintSegment);
        oldsegments = new ArrayList<PaintSegment>();
        redoButton.setClickable(false);
        redoButton.setEnabled(false);
        undoButton.setClickable(false);
        undoButton.setEnabled(false);
        invalidate();
    }

    public void setBrushSize(float w){
        //currentPaintSegment = new PaintSegment(color,w,opacity);
        //paintSegments.add(currentPaintSegment);
        width = w;
        //paintSegments.add(addPaintSegment(currentPaintSegment.getColor()));
        //getCurrentPaint().setWidth(w);
    }

    public void setBrushOpacity(int op){
        //currentPaintSegment = new PaintSegment(color,width,op);
        //paintSegments.add(currentPaintSegment);
        opacity = op;
    }

    public void setBrushColor(int c){
        //currentPaintSegment = new PaintSegment(c, width,opacity);
        //paintSegments.add(currentPaintSegment);
        color = c;

    }

    public void undo() {
        //Log.i("undo bf currentPaint",currentPaintSegment.toString());
        //Log.i("undo bf paintsegments",paintSegments.toString());
        //Log.i("undo bf oldsegments",oldsegments.toString());

        oldsegments.add(currentPaintSegment);

        if(paintSegments.size() > 0){
            //oldsegments.add(paintSegments.remove(paintSegments.size()-1));
            currentPaintSegment = paintSegments.remove(paintSegments.size() -1 );
        } else {
            currentPaintSegment = null;
            //currentPaintSegment = new PaintSegment(color,width,opacity);
            //paintSegments.add(currentPaintSegment);
        }

        redoButton.setClickable(true);
        redoButton.setEnabled(true);
        if(currentPaintSegment == null){
            undoButton.setEnabled(false);
            undoButton.setClickable(false);
        }
        if(paintSegments.size() > 0){
            undoButton.setEnabled(true);
            undoButton.setClickable(true);
        }
        //Log.i("undo after",currentPaintSegment.toString());
        //Log.i("undo after",paintSegments.toString());
        //Log.i("undo after",oldsegments.toString());
        //Log.i("MINE","Starting undo");
        invalidate();
        //Log.i("MINE","UNDO");
    }

    public void redo() {
        if(currentPaintSegment != null){
            paintSegments.add(currentPaintSegment);
        }
        if(oldsegments.size() > 0) {
            currentPaintSegment = oldsegments.remove(oldsegments.size() - 1);
            invalidate();
        }
        if(oldsegments.size() == 0){
            redoButton.setClickable(false);
            redoButton.setEnabled(false);
        }
        if(paintSegments.size() > 0){
            undoButton.setEnabled(true);
            undoButton.setClickable(true);
        }
        //paintSegments.add(currentPaintSegment);
        Log.i("MINE","redo");

    }

    class PaintSegment{
        int _color;
        Path _path;
        Paint _paint;

        public PaintSegment(int c, float strokeWidth, int opacity){
            _color = c;
            _paint = new Paint();
            float[] hsv = {c,1,1};
            _paint.setColor(Color.HSVToColor(opacity,hsv));
            _paint.setAntiAlias(true);
            _paint.setStyle(Paint.Style.STROKE);
            _paint.setStrokeWidth(strokeWidth);
            _path = new Path();
            //_size =_paint.getStrokeWidth();
        }

        public Paint getPaint() {
            return _paint;
        }

        public Path getPath() {
            return _path;
        }

        public String toString(){
            return "Color: " +_color;
        }
    }
}
