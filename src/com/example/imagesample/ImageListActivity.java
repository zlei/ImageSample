package com.example.imagesample;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.imagesample.Constants.Extra;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageListActivity extends AbsListViewBaseActivity {
	DisplayImageOptions options; // DisplayImageOptions����������ͼƬ��ʾ����

	String imageUrl;

	Constants constant = new Constants();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_list);
		constant.updateJSONArray();
		constant.updateList();
		// Bundle bundle = getIntent().getExtras();
		// imageUrls = bundle.getStringArray(Extra.IMAGES);

		// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.ic_stub) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		// .showImageForEmptyUri(R.drawable.ic_empty) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		// .showImageOnFail(R.drawable.ic_error) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.showStubImage(R.drawable.ic_launcher) // ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.ic_launcher) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.ic_launcher) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
				.displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
				.build(); // �������ù���DisplayImageOption����

		listView = (ListView) findViewById(android.R.id.list);
		((ListView) listView).setAdapter(new ItemAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// set image url
				imageUrl = constant.selectedImage(position);
				startImagePagerActivity(position);
			}
		});
	}

	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}

	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		String[] image = new String[1];
		image[0] = imageUrl;

		intent.putExtra(Extra.IMAGES, image);
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}

	/**
	 * 
	 * 
	 * 
	 */
	class ItemAdapter extends BaseAdapter {

		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		private class ViewHolder {
			public TextView text;
			// public ImageView image;
		}

		@Override
		public int getCount() {
			return constant.listFile.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_list_image,
						parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.text);
				// holder.image = (ImageView) view.findViewById(R.id.image);
				view.setTag(holder); // ��View���һ�����������
			} else {
				holder = (ViewHolder) view.getTag(); // ������ȡ����
			}
			String textFile = constant.listFilename.get(position).toString();
			holder.text.setText(textFile); // TextView�����ı�
			// Things to do: Gather data from server, use real json, disable
			// small image, add hash value to list

			/**
			 * ��ʾͼƬ ����1��ͼƬurl ����2����ʾͼƬ�Ŀؼ� ����3����ʾͼƬ������ ����4��������
			 */
			// imageLoader.displayImage(imageUrls[position], holder.image,
			// options, animateFirstListener);

			return view;
		}
	}

	/**
	 * ͼƬ���ص�һ����ʾ������
	 * 
	 * @author Administrator
	 * 
	 */
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// �Ƿ��һ����ʾ
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// ͼƬ����Ч��
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}