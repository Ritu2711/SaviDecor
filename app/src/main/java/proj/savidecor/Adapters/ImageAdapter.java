package proj.savidecor.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import proj.savidecor.R;


/**
 * Created by sai on 9/11/17.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;

    ArrayList<String> al;
    public ImageAdapter(Context c ,ArrayList<String> arrayList)
    {
        al=arrayList;
        context = c;
        // sets a grey background; wraps around the images

    }
    // returns the number of images
    public int getCount() {
        return al.size();
    }
    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }
    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }
    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        Log.d("mydadad",al.get(position)+"\n");
        Glide.with(context).load(al.get(position)).thumbnail(Glide.with(context).load(R.drawable.loading).override(50,50)).error(R.mipmap.ic_launcher).dontAnimate().override(100,100).into(imageView);
        //imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));
        return imageView;
    }
}

