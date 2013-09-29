package com.example.imagesample;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.example.parseJSON.ParseJSON;

public class Constants {

	private String rootdir = "http://10.0.2.2:4567/images/"; 
	int length;
	private String tempFilename;
	private String tempHashVal;
	// value pair list
	public List<String> listFile = new ArrayList<String>(length);
	// filename list
	public List<String> listFilename = new ArrayList<String>(length);
	// hash value list
	public List<String> listHashVal = new ArrayList<String>(length);

	JSONArray jsonArray = new JSONArray();

	//create a JSONArray to store all the json values
	public void updateJSONArray() {
		String temp = ParseJSON.getServerJSON().replaceAll("[\"{}]", ""); //dismiss "{}"
		String[] input = temp.split(",");
		for (int i = 0; i < input.length; i++) {
			jsonArray.put(input[i]);
		}
	}

	//create three list containing pairs, filename and hash values, with the same index
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
		}
	}

	//generate selected image url
	public String selectedImage(int i){
		StringBuilder builder = new StringBuilder();
        builder.append(rootdir);
        builder.append(listHashVal.get(i));
        return builder.toString();
	}

	//constructor
	Constants() {
	}

	// extra class for images and image position
	public static class Extra {
		public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}
}