package com.example.tubes_3.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_3.R;
import com.example.tubes_3.interfaces.SearchableFragment;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.presenters.MangaPresenter;
import com.example.tubes_3.util.MangaRawDiffUtils;

import java.util.ArrayList;
import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaViewHolder> implements Filterable {
    private MangaAdapter instance;
    private MangaPresenter presenter;
    private LayoutInflater inflater;

    private List<MangaRaw> filteredData;

    private ItemFilter itemFilter;

    SearchableFragment searchableFragment;

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterString = charSequence.toString().toLowerCase();
            FilterResults results = new FilterResults();

            final List<MangaRaw> list = presenter.getMangaRawList();

            int count = list.size();

            final ArrayList<MangaRaw> nResult = new ArrayList<>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getTitle();

                if (filterableString.toLowerCase().contains(filterString)) {
                    nResult.add(list.get(i));
                }
            }

            results.values = nResult;
            results.count = nResult.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<MangaRaw> newFilteredData = (List<MangaRaw>)filterResults.values;
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MangaRawDiffUtils(filteredData, newFilteredData));

            filteredData = newFilteredData;

            diffResult.dispatchUpdatesTo(instance);

            searchableFragment.hideLoadingSpinner();

            if (searchableFragment != null) {
                searchableFragment.setPageSize(filterResults.count);
            }
        }
    }

    public MangaAdapter(Context ctx, MangaPresenter presenter) {
        this.instance = this;
        this.inflater = LayoutInflater.from(ctx);
        this.presenter = presenter;
        this.searchableFragment = null;

        this.filteredData = presenter.getMangaRawList();

        this.itemFilter = new ItemFilter();

        this.setHasStableIds(true);
    }

    @Override
    public Filter getFilter() {
        return this.itemFilter;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.manga_layout, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        holder.setManga(this.filteredData.get(position));
    }

    @Override
    public int getItemCount() {
        return this.filteredData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public int getFilteredDataSize() {
        return filteredData.size();
    }

    public void setSearchableFragment(SearchableFragment fragment) {
        this.searchableFragment = fragment;
    }
}
