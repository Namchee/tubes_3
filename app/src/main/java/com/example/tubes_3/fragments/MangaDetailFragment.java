package com.example.tubes_3.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.adapters.ChapterAdapter;
import com.example.tubes_3.messages.response.MangaDetailResponseMessage;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.presenters.ChapterPresenter;
import com.example.tubes_3.sharedPreference.MangaStorage;
import com.example.tubes_3.util.ServiceWorker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MangaDetailFragment extends Fragment implements View.OnClickListener {
    MangaRaw mangaRaw;

    @BindView(R.id.detail_manga_picture) ImageView mangaPic;
    @BindView(R.id.detail_manga_title) TextView tvTitle;
    @BindView(R.id.detail_manga_author) TextView tvAuthor;
    @BindView(R.id.detail_manga_artist) TextView tvArtist;
    @BindView(R.id.detail_manga_status) TextView tvStatus;
    @BindView(R.id.detail_manga_last) TextView tvLast;
    @BindView(R.id.detail_manga_hits) TextView tvHits;
    @BindView(R.id.detail_manga_categories) ChipGroup cgCategory;
    @BindView(R.id.detail_manga_synopsis) TextView tvSynopsis;
    @BindView(R.id.detail_manga_chapters) RecyclerView lvChapters;
    @BindView(R.id.detail_progress_loader) ProgressBar loader;
    @BindView(R.id.favorite_button) MaterialButton favoriteButton;

    MangaStorage preferences;

    ChapterPresenter presenter;
    ChapterAdapter adapter;

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

        this.preferences = new MangaStorage(this.getContext());

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

        EventBus.getDefault().register(this);

        this.loader.setVisibility(View.VISIBLE);

        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        ServiceWorker.getInstance(this.getContext()).getMangaDetail(mangaRaw.getId());
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMangaDetailResponseMessage(MangaDetailResponseMessage mangaDetailResponseMessage) {
        this.loader.setVisibility(View.GONE);

        this.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        MangaDetail mangaDetail = mangaDetailResponseMessage.getMangaDetail();

        if (mangaDetail.getImgUrl() != null && mangaDetail.getImgUrl() != "") {
            Picasso.get().load(Uri.decode(mangaDetail.getImgUrl())).placeholder(R.drawable.ic_progress_animation).into(this.mangaPic);
        } else {
            this.mangaPic.setImageResource(R.drawable.ic_no);
        }

        this.tvTitle.setText(mangaDetail.getTitle());
        this.tvAuthor.setText(mangaDetail.getAuthor());
        this.tvArtist.setText(mangaDetail.getArtist());
        this.tvStatus.setText(mangaDetail.getStatus());
        this.tvSynopsis.setText(Html.fromHtml(mangaDetail.getDesc()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        this.tvLast.setText(dateFormat.format(mangaDetail.getLastUpdated()));
        this.tvHits.setText(new DecimalFormat("#,###.#").format(mangaDetail.getHits()));

        for (String category: mangaDetail.getCategories()) {
            View view = LayoutInflater.from(this.getContext()).inflate(R.layout.chip_layout, this.cgCategory, false);
            Chip chip = view.findViewById(R.id.detail_manga_category);

            chip.setText(category);

            this.cgCategory.addView(chip);
        }

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        this.lvChapters.setLayoutManager(llm);

        this.presenter = new ChapterPresenter(mangaDetail.getChapters());
        this.adapter = new ChapterAdapter(this.getContext(), this.presenter);

        this.lvChapters.setAdapter(this.adapter);

        if (this.preferences.isFavorite(this.mangaRaw.getId())) {
            this.favoriteButton.setIconResource(R.drawable.ic_favorite);
            this.favoriteButton.setIconTintResource(android.R.color.holo_red_dark);
        } else {
            this.favoriteButton.setIconResource(R.drawable.ic_favorite_border);
            this.favoriteButton.setIconTintResource(android.R.color.black);
        }

        this.favoriteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.favoriteButton.getId()) {
            this.handleFavorite();
        }
    }

    private void handleFavorite() {
        if (this.preferences.isFavorite(this.mangaRaw.getId())) {
            this.preferences.deleteFavorite(this.mangaRaw.getId());
            this.favoriteButton.setIconResource(R.drawable.ic_favorite_border);
            this.favoriteButton.setIconTintResource(android.R.color.black);
        } else {
            this.preferences.saveFavorite(this.mangaRaw.getId());
            this.favoriteButton.setIconResource(R.drawable.ic_favorite);
            this.favoriteButton.setIconTintResource(android.R.color.holo_red_dark);
        }
    }
}
