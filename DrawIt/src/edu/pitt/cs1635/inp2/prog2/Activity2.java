package edu.pitt.cs1635.inp2.prog2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Activity2 extends Activity implements OnClickListener
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw_it2);  
		
		((Button) findViewById(R.id.Clear)).setOnClickListener(new OnClickListener() 
		{
			@Override
            public void onClick(View v) 
			{
				Context context = getApplicationContext();
            	CharSequence text = "Clear Screen";
            	int duration = Toast.LENGTH_SHORT;
            	
            	Toast toast = Toast.makeText(context, text, duration);
            	toast.show();
				Intent intent = new Intent(Activity2.this, DrawIt.class);
				startActivity(intent);      
				finish();
			}	
		});
	}
	
	@Override
	public void onClick(View v) 
	{ }
}
