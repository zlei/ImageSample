package com.example.imagesample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parseJSON.ServerImageList;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;

public class HomeActivity extends BaseActivity {

	private static final String TEST_FILE_NAME = "test.jpg";
	Constants constants = new Constants();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_home);
		BaseActivity.imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));
		new ServerImageList().execute();	//parse image list from the server

		File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
		if (!testImageOnSdCard.exists()) {	
			// save to SD card
			copyTestImageToSdCard(testImageOnSdCard);
		}
	}

	public void onImageListClick(View view) {
		Intent intent = new Intent(this, ImageListActivity.class);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		imageLoader.stop();		
		super.onBackPressed();
	}

	private void copyTestImageToSdCard(final File testImageOnSdCard) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream is = getAssets().open(TEST_FILE_NAME);
					FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
					byte[] buffer = new byte[8192];
					int read;
					try {
						while ((read = is.read(buffer)) != -1) {
							fos.write(buffer, 0, read);	
						}
					} finally {
						fos.flush();		
						fos.close();		
						is.close();			
					}
				} catch (IOException e) {
					L.w("Can't copy test image onto SD card");
				}
			}
		}).start();		
	}
}