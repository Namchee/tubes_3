package com.example.tubes_3.messages.request;

import com.example.tubes_3.messages.RequestMessage;
import com.example.tubes_3.model.History;

import java.util.List;

public class MangaHistoryRequestMessage extends RequestMessage {
    private List<History> historyList;

    public MangaHistoryRequestMessage(List<History> historyList) {
        super(REQUEST_HISTORY);
        this.historyList = historyList;
    }

    public List<History> getHistoryList() {
        return historyList;
    }
}
