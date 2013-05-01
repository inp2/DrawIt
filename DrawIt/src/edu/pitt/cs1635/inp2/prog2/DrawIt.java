package edu.pitt.cs1635.inp2.prog2;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;



public class DrawIt extends Activity
{  
	DrawingPanel dp;
	
    public void onCreate(Bundle savedInstanceState)
    {
    	dp = new DrawingPanel(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_it);
        FrameLayout frameLayout;
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);     
        frameLayout.addView(dp);

        ((Button) findViewById(R.id.Menu)).setOnClickListener(new OnClickListener() 
        {
                    @Override
                    public void onClick(View v) 
                    {
                    	Intent intent = new Intent(DrawIt.this, Activity2.class);
                        startActivity(intent);      
                        finish();
                    }
        });
        
       ((Button) findViewById(R.id.Black)).setOnClickListener(new OnClickListener() 
        {
                    @Override
                    public void onClick(View v) 
                    {	
                    	dp.colorChanged(Color.BLACK);
                    	Context context = getApplicationContext();
                    	CharSequence text = "Paint Is Now Black";
                    	int duration = Toast.LENGTH_SHORT;
                    	
                    	Toast toast = Toast.makeText(context, text, duration);
                    	toast.show();
                    }
        });
        
        ((Button) findViewById(R.id.Green)).setOnClickListener(new OnClickListener() 
        {
                    @Override
                    public void onClick(View v) 
                    {
                
                    dp.colorChanged(Color.GREEN);
                    	Context context = getApplicationContext();
                    	CharSequence text = "Paint Is Now Green";
                    	int duration = Toast.LENGTH_SHORT;
                    	
                    	Toast toast = Toast.makeText(context, text, duration);
                    	toast.show();
                    }
        });

        ((Button) findViewById(R.id.Red)).setOnClickListener(new OnClickListener() 
        {
                    @Override
                    public void onClick(View v) 
                    {
                    	dp.colorChanged(Color.RED);
                    	Context context = getApplicationContext();
                    	CharSequence text = "Paint Is Now Red";
                    	int duration = Toast.LENGTH_SHORT;
                    	
                    	Toast toast = Toast.makeText(context, text, duration);
                    	toast.show();
                    }
        }); 
        
        ((Button) findViewById(R.id.Submit)).setOnClickListener(new OnClickListener() 
        {
			public void onClick(View view) 
			{
				new DownloadFilesTask().execute();   	
			}
		});
    } 

    private class DownloadFilesTask extends AsyncTask<String, Void, String> 
    {	 
    	@Override
        protected String doInBackground(String... urls) 
        {
    		try
    		{
    			String url = "http://cwritepad.appspot.com/reco/usen"; 
    			String key = "11773edfd643f813c18d82f56a8104ed";
    			HttpClient client = new DefaultHttpClient();
    			HttpPost post = new HttpPost(url);
    			String q = "[";
    			q = dp.encode();
			
				ArrayList <NameValuePair> nvp = new ArrayList<NameValuePair>();
				nvp.add(new BasicNameValuePair("key", key));
				nvp.add(new BasicNameValuePair("q", q));
				post.setEntity(new UrlEncodedFormEntity(nvp));			
				HttpResponse result1 = client.execute(post);


				byte[] buffer = new byte[200];
				int readBytes = 0;
				StringBuilder builder = new StringBuilder();

				do
				{
					readBytes = result1.getEntity().getContent().read(buffer);
					String built = new String(buffer, 0, readBytes);
					builder.append(built);						
				}
				while(readBytes == 200);
				return builder.toString();
			}
			catch (Exception e)
			{
				AlertDialog alertDialog = new AlertDialog.Builder(DrawIt.this).create();
				alertDialog.setTitle("ERROR");
				alertDialog.setMessage(getResources()+e.toString());
				alertDialog.show();		
				e.printStackTrace();
			}
    		return "";
    	}
      
    	protected void onPostExecute(String result) 
        {
        	Log.i("RESULT", result);
			AlertDialog alertDialog = new AlertDialog.Builder(DrawIt.this).create();
			alertDialog.setTitle("RESULT");
			alertDialog.setMessage(result.toString());
        	alertDialog.show();
        }
    }
}


