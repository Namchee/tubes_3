package com.example.tubes_3.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.adapters.ChapterAdapter;
import com.example.tubes_3.messages.response.ChapterResponseMessage;
import com.example.tubes_3.messages.response.MangaDetailResponseMessage;
import com.example.tubes_3.model.Chapter;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.presenters.ChapterPresenter;
import com.example.tubes_3.util.ServiceWorker;
import com.example.tubes_3.util.URL_BASE;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MangaReadFragment extends Fragment {
    private Chapter ch;
    private Unbinder unbinder;

    @BindView(R.id.manga_content_container) LinearLayout mangaPage_container;
    @BindView(R.id.detail_progress_loader) ProgressBar loader;


    public MangaReadFragment() {
        // Required empty public constructor
    }


    public MangaReadFragment(Chapter ch){this.ch = ch;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_read, container, false);

        this.unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);

        this.loader.setVisibility(View.VISIBLE);

        ServiceWorker.getInstance(this.getContext()).getChapterImages(ch.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMangaDetailResponseMessage(ChapterResponseMessage chapterResponseMessage) {
        this.loader.setVisibility(View.GONE);

        this.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        List<String> imgURL = chapterResponseMessage.getImageList();
        List<ImageView> iv_list = new ArrayList<ImageView>();

        for (int i = 0; i < imgURL.size() ; i++) {
            iv_list.add(new ImageView(this.getActivity()));
            if(imgURL.get(i)!=null && imgURL.get(i)!=""){
                Picasso.get().load(URL_BASE.IMAGE_SRC+imgURL.get(i)).placeholder(R.drawable.ic_progress_animation).into(iv_list.get(i));
            } else {
                iv_list.get(i).setImageResource(R.drawable.ic_no);
            }
            this.mangaPage_container.addView(iv_list.get(i));
        }
    }
}
