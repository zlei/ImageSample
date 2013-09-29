package com.example.imagesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.imagesample.Constants.Extra;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ImageListActivity extends AbsListViewBaseActivity {
	DisplayImageOptions options;
	String imageUrl;

	Constants constant = new Constants();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_list);
		constant.updateJSONArray();
		constant.updateList();

		// DisplayImageOptions.Builder() to create DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.ic_stub)
				// .showImageForEmptyUri(R.drawable.ic_empty)
				// .showImageOnFail(R.drawable.ic_error)
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				// .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				// .cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(20))
				.build();

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

	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		String[] image = new String[1];
		image[0] = imageUrl;

		intent.putExtra(Extra.IMAGES, image);
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}

	class ItemAdapter extends BaseAdapter {

		private class ViewHolder {
			public TextView text;
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
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			String textFile = constant.listFilename.get(position).toString();
			holder.text.setText(textFile);

			return view;
		}
	}
}