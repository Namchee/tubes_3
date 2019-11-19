package com.example.tubes_3.presenters;

import com.example.tubes_3.model.HistoryDetail;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailPresenter {
    private List<HistoryDetail> historyDetails;

    public HistoryDetailPresenter() {
        this.historyDetails = new ArrayList<>();
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
}
