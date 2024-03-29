package com.example.tubes_3.fragments.home;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.adapters.HistoryAdapter;
import com.example.tubes_3.fragments.adapters.SwipeToDeleteCallback;
import com.example.tubes_3.messages.request.MangaHistoryRequestMessage;
import com.example.tubes_3.messages.response.MangaHistoryResponseMessage;
import com.example.tubes_3.model.HistoryDetail;
import com.example.tubes_3.model.HistoryRaw;
import com.example.tubes_3.presenters.HistoryPresenter;
import com.example.tubes_3.sharedPreference.MangaStorage;
import com.google.android.material.button.MaterialButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.history_list) RecyclerView historyView;
    @BindView(R.id.history_progress_loader) ProgressBar loader;
    @BindView(R.id.no_history_text) TextView tvNo;
    @BindView(R.id.clear_history_button) MaterialButton clearHistory;

    Unbinder unbinder;

    MangaStorage preferences;

    HistoryAdapter adapter;
    HistoryPresenter presenter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        this.unbinder = ButterKnife.bind(this, view);

        this.preferences = new MangaStorage(this.getContext());

        this.historyView.setItemAnimator(new LandingAnimator());

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

        List<HistoryRaw> historyRaws = this.preferences.getHistories();

        EventBus.getDefault().postSticky(new MangaHistoryRequestMessage(historyRaws));
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(MangaHistoryResponseMessage message) {
        this.loader.setVisibility(View.GONE);

        List<HistoryDetail> historyDetails = message.getHistoryDetails();

        if (historyDetails.size() > 0) {
            this.tvNo.setVisibility(View.GONE);
        } else {
            this.tvNo.setVisibility(View.VISIBLE);
        }

        this.presenter = new HistoryPresenter(historyDetails);
        this.adapter = new HistoryAdapter(this, this.presenter);

        ScaleInAnimator animator = new ScaleInAnimator();

        ItemTouchHelper helper = new ItemTouchHelper(new SwipeToDeleteCallback(this.adapter));
        helper.attachToRecyclerView(this.historyView);

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        this.historyView.setLayoutManager(llm);
        this.historyView.setAdapter(this.adapter);

        this.historyView.setItemAnimator(animator);

        this.clearHistory.setOnClickListener(this);

        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.clearHistory.getId()) {
            int size = this.presenter.getSize();
            this.presenter.clear();

            this.adapter.notifyItemRangeRemoved(0, size);

            this.showEmptyText();
        }
    }

    public void showEmptyText() {
        this.tvNo.setVisibility(View.VISIBLE);
    }
}
