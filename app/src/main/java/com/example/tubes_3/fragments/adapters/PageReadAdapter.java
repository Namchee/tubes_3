package com.example.tubes_3.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tubes_3.R;
import com.example.tubes_3.presenters.PagePresenter;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PageReadAdapter extends PagerAdapter {
    private PagePresenter pagePres;
    private Context mContext;

    public PageReadAdapter(List<String> imgURL, Context context){
        this.pagePres = new PagePresenter();
        this.pagePres.setImageList(imgURL);
        this.mContext=context;
    }

    @Override
    public void destroyItem(ViewGroup container,int position, Object object){
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return this.pagePres.getLength();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.manga_page,container,false);
        PhotoView ph = view.findViewById(R.id.page_photoView);
        if(pagePres.getImage(position)!=null && pagePres.getImage(position)!=""){
            try{
                Picasso.get().load(pagePres.getImage(position)).placeholder(R.drawable.ic_progress_animation).into(ph);
            } catch (Exception e){

            }
        }

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}