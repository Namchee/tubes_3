package com.example.tubes_3.messages.request;

import com.example.tubes_3.messages.RequestMessage;
import com.example.tubes_3.model.HistoryRaw;

import java.util.List;

public class MangaHistoryRequestMessage extends RequestMessage {
    private List<HistoryRaw> historyList;

    public MangaHistoryRequestMessage(List<HistoryRaw> historyList) {
        super(REQUEST_HISTORY);
        this.historyList = historyList;
    }

    public List<HistoryRaw> getHistoryList() {
        return historyList;
    }
}
