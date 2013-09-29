package com.example.imagesample;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.imagesample.Constants.Extra;
import com.example.log.ISLog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 */
public class ImagePagerActivity extends BaseActivity {

	private static final String STATE_POSITION = "STATE_POSITION";
	DisplayImageOptions options;
	ViewPager pager;
	Constants constant = new Constants();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_pager);

		Bundle bundle = getIntent().getExtras();
		String[] imageUrls = bundle.getStringArray(Extra.IMAGES);
		// ��ǰ��ʾView��λ��
		int pagerPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);
		// ���֮ǰ�б����û�����
		if (savedInstanceState != null) {
			Log.i(ISLog.LOG_TAG, "saved! " + savedInstanceState.toString());
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		options = new DisplayImageOptions.Builder()
//			.showImageForEmptyUri(R.drawable.ic_empty)
//			.showImageOnFail(R.drawable.ic_error)
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher)
			.resetViewBeforeLoading(true)
			.cacheOnDisc(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.displayer(new FadeInBitmapDisplayer(300))
			.build();

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(imageUrls));
		pager.setCurrentItem(pagerPosition);	// ��ʾ��ǰλ�õ�View
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// �����û�����
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return 1; //only current page
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			Log.i(ISLog.LOG_TAG, "position" + position);
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			ImageViewTouch imageView = (ImageViewTouch) imageLayout.findViewById(R.id.image);
			imageView.setDisplayType( DisplayType.FIT_IF_BIGGER );
			//need more?
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			imageLoader.displayImage(images[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {		// ��ȡͼƬʧ������
						case IO_ERROR:				// �ļ�I/O����
							message = "Input/Output error";
							break;
						case DECODING_ERROR:		// �������
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:		// �����ӳ�
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:		    // �ڴ治��
							message = "Out Of Memory error";
							break;
						case UNKNOWN:				// ԭ����
							message = "Unknown error";
							break;
					}
					Toast.makeText(ImagePagerActivity.this, message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);		// ����ʾԲ�ν�����
				}
			});

			((ViewPager) view).addView(imageLayout, 0);		// ��ͼƬ���ӵ�ViewPager
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}
}