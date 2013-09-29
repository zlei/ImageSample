package com.example.imagesample;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.util.Log;

import com.example.log.ISLog;
import com.example.parseJSON.ParseJSON;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com) 常量类
 */

public class Constants {

	private String rootdir = "http://hmn.cs.wpi.edu/sizeFiles/"; 
	int length;
	private String tempFilename;
	private String tempHashVal;
	// pair list
	public List<String> listFile = new ArrayList<String>(length);
	// filename list
	public List<String> listFilename = new ArrayList<String>(length);
	// hash value list
	public List<String> listHashVal = new ArrayList<String>(length);

	JSONArray jsonArray = new JSONArray();

	public void updateJSONArray() {
		String temp = ParseJSON.getServerJSON().replaceAll("[\"{}]", "");
		String[] input = temp.split(",");
		for (int i = 0; i < input.length; i++) {
			jsonArray.put(input[i]);
		}
		Log.i(ISLog.LOG_TAG, "array done!");
	}

	public void updateList() {
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				listFile.add(jsonArray.getString(i));
				String[] input = listFile.get(i).split(":");
				tempHashVal = input[0];
				tempFilename = input[1];
				listHashVal.add(tempHashVal);
				listFilename.add(tempFilename);
			} catch (Exception e) {
			}
		Log.i(ISLog.LOG_TAG, "list done! ");
		}
	}

	// provide file name in the hash map
	public String getFilename(int i) {
		return null;
	}

	// provide hash value in the hash map
	public String getHashValue() {
		return null;
	}

	//generate selected image url
	public String selectedImage(int i){
		StringBuilder builder = new StringBuilder();
        builder.append(rootdir);
        builder.append(listHashVal.get(i));
        Log.i(ISLog.LOG_TAG, "url: " +	builder.toString());
        return builder.toString();
	}

	Constants() {
	}

	// 配置
	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	// 额外类
	public static class Extra {
		public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}
}