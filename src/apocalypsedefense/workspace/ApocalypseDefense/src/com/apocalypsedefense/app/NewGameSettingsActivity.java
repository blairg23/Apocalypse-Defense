/**
 * 
 */
package com.apocalypsedefense.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Pat
 *
 */
public class NewGameSettingsActivity extends Activity {
	/** Called when the activity is first created. */
	// http://developer.android.com/resources/tutorials/views/hello-gallery.html
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game_settings);

        Gallery gallery = (Gallery) findViewById(R.id.mapGallery);
        gallery.setAdapter(new ImageAdapter(this));

//        gallery.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                Toast.makeText(NewGameSettingsActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
        
        // from http://mobiforge.com/designing/story/understanding-user-interface-android-part-3-more-views
        gallery.setOnItemClickListener(new OnItemClickListener() 
        {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
            	// Go to ingame screen
            	startActivity(new Intent(getBaseContext(), InGameActivity.class));
//                Toast.makeText(getBaseContext(), 
//                        "map " + (position + 1) + " selected", 
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;

        private Integer[] mImageIds = {
        		R.drawable.map_desert
        };

        public ImageAdapter(Context c) {
            mContext = c;
            TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
            mGalleryItemBackground = attr.getResourceId(
                    R.styleable.HelloGallery_android_galleryItemBackground, 0);
            attr.recycle();
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);

            imageView.setImageResource(mImageIds[position]);
//            imageView.setLayoutParams(new Gallery.LayoutParams(400, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(mGalleryItemBackground);

            return imageView;
        }
    }
}
