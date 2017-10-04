package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by welcome on 9/12/2017.
 */

public class SliderPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<Integer> image_arraylist;
    ArrayList<String> slider_text_list;

    public SliderPagerAdapter(Activity activity, ArrayList<Integer> image_arraylist, ArrayList<String> slider_text_list) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.slider_text_list=slider_text_list;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);
        TextView textview= (TextView) view.findViewById(R.id.text);
        textview.setText(slider_text_list.get(position));
       im_slider.setAdjustViewBounds(true);
      im_slider.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(activity.getApplicationContext())
                .load(image_arraylist.get(position))
                .placeholder(R.drawable.im) // optional
                .error(R.drawable.hosp)         // optional
                .into(im_slider);


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

