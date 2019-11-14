package com.example.tubes_3.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.messages.request.MangaDetailRequestMessage;
import com.example.tubes_3.messages.response.MangaDetailResponseMessage;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MangaDetailFragment extends Fragment {
    MangaRaw mangaRaw;

    @BindView(R.id.detail_manga_picture) ImageView mangaPic;
    @BindView(R.id.detail_manga_title) TextView tvTitle;
    @BindView(R.id.detail_manga_author) TextView tvAuthor;
    @BindView(R.id.detail_manga_artist) TextView tvArtist;
    @BindView(R.id.detail_manga_status) TextView tvStatus;
    @BindView(R.id.detail_manga_synopsis) TextView tvSynopsis;
    @BindView(R.id.detail_manga_chapters) ListView lvChapters;
    // @BindView(R.id.detail_progress_loader) ProgressBar loader;

    Unbinder unbinder;

    public MangaDetailFragment() {
        // Required empty public constructor
    }

    public MangaDetailFragment(MangaRaw mangaRaw) {
        this.mangaRaw = mangaRaw;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_detail, container, false);

        this.unbinder = ButterKnife.bind(this, view);

        this.tvSynopsis.setMovementMethod(new ScrollingMovementMethod());

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

        // this.loader.setVisibility(View.VISIBLE);

        EventBus.getDefault().register(this);

        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMangaDetailResponseMessage(MangaDetailResponseMessage mangaDetailResponseMessage) {
        this.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        MangaDetail mangaDetail = mangaDetailResponseMessage.getMangaRawDetail();

        if (mangaDetail.getImgUrl() != null && mangaDetail.getImgUrl() != "") {
            Picasso.get().load(Uri.decode(mangaDetail.getImgUrl())).placeholder(R.drawable.ic_progress_animation).into(this.mangaPic);
        } else {
            this.mangaPic.setImageResource(R.drawable.ic_no);
        }

        this.tvTitle.setText(mangaDetail.getTitle());
        this.tvAuthor.setText(mangaDetail.getAuthor());
        this.tvArtist.setText(mangaDetail.getArtist());
        this.tvStatus.setText(mangaDetail.getStatus());
        this.tvSynopsis.setText(mangaDetail.getDesc());
    }
}
