package com.example.parseJSON;

import android.os.AsyncTask;

public class ServerImageList extends AsyncTask<Void,Void,Void>
{
	  ParseJSON json = new ParseJSON();

      protected Void doInBackground(Void ...params)
      {  
    	   json.parseServerJSON();
    	   return null;
      } 

 }