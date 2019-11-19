package com.example.tubes_3.presenters;

import com.example.tubes_3.model.HistoryDetail;

import java.util.ArrayList;
import java.util.List;

public class HistoryPresenter {
    private List<HistoryDetail> historyDetails;

    public HistoryPresenter() {
        this.historyDetails = new ArrayList<>();
    }

    public HistoryPresenter(List<HistoryDetail> historyDetails) {
        this.historyDetails = historyDetails;
    }

    public void addHistoryDetail(HistoryDetail detail) {
        this.historyDetails.add(detail);
    }

    public List<HistoryDetail> getList() {
        return this.historyDetails;
    }

    public HistoryDetail getHistoryDetail(int idx) {
        return this.historyDetails.get(idx);
    }

    public int getSize() {
        return this.historyDetails.size();
    }

    public void deleteItem(int idx) {
        this.historyDetails.remove(idx);
    }
}
