package edu.pitt.cs1635.inp2.prog2;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawingPanel extends View implements OnTouchListener 
{
	ArrayList<PathyPath> paths;
	private Canvas mCanvas;
    private PathyPath mPath;
    private Paint mPaint, mCirclePaint, mOutercirclePaint;
    
    public DrawingPanel(Context context) 
    {
    	super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        
        paths = new ArrayList<PathyPath>();
        mCirclePaint = new Paint();
        mPaint = new Paint();
        mOutercirclePaint = new Paint();
        mOutercirclePaint.setAntiAlias(true);
        mCirclePaint.setAntiAlias(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xFF000000);
        mOutercirclePaint.setColor(0x44FFF000);
        mCirclePaint.setColor(0xAADD5522);
        mOutercirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);
        mOutercirclePaint.setStrokeWidth(6);
        mCanvas = new Canvas();
        mPath = new PathyPath(700, 700);
        paths.add(mPath);
      }
    
    public void colorChanged(int color) 
    {
        mPaint.setColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) 
    {
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    @Override
    protected void onDraw(Canvas canvas) 
    {
        for (PathyPath p : paths) 
        {
            canvas.drawPath(p, mPaint);
        }
    }
    
    public String encode()
    {
    	StringBuilder builder = new StringBuilder();
    	builder.append("[");
    	for(PathyPath p: paths)
    	{
    		ArrayList<Integer> points = p.encodedPoints();
   
    		if(points.isEmpty())
    		{
    			continue;
    		}
    		for(Integer i: points)
    		{
    			builder.append(i + ", ");
    		} 
    		builder.append("255, 0, ");
    	}
    	builder.append("255, 255]");
    	return builder.toString();
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 0;

    private void touch_start(float x, float y) 
    {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) 
    {
    	mPath.lineTo(x, y);
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
        {
            mX = x;
            mY = y;
        }
    }

    private void touch_up() 
    {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mPaint);
        mPath = new PathyPath(700, 700);
        mPath.setLastPoint(mX, mY);
        paths.add(mPath);
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) 
    {
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            touch_start(x, y);
            invalidate();
            break;
        case MotionEvent.ACTION_MOVE:
            touch_move(x, y);
            invalidate();
            break;
        case MotionEvent.ACTION_UP:
            touch_up();
            invalidate();
            break;
        }
        return true;
    }
}

