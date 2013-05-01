package edu.pitt.cs1635.inp2.prog2;

import java.util.ArrayList;

import android.graphics.Path;

public class PathyPath extends Path
{
	ArrayList<Integer> points;
    float normalizedWidth, normalizedHeight;
    
	 public PathyPath(int width, int height)
	    {
	    	super();
	    	final float NORMALIZED_WIDTH = 254.0f;
	    	final float NORMALIZED_HEIGHT = 254.0f;
	    	points = new ArrayList<Integer>();
	    	normalizedWidth = NORMALIZED_WIDTH / width;
	    	normalizedHeight = NORMALIZED_HEIGHT / height;
	    }
	    
	    public void lineTo(float dx, float dy)
	    {
	    	super.lineTo(dx, dy);
	    	points.add((int)(dx * normalizedWidth));
	    	points.add((int)(dy * normalizedHeight));
	    }
	    
	    public ArrayList<Integer>encodedPoints()
	    {
	    	return points;
	    }
}
