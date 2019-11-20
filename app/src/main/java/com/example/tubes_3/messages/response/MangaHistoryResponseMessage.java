package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.Chapter;
import com.example.tubes_3.model.HistoryDetail;
import com.example.tubes_3.model.MangaRaw;

import java.util.List;

public class MangaHistoryResponseMessage extends ResponseMessage {
    private List<HistoryDetail> historyDetails;

    public MangaHistoryResponseMessage(List<HistoryDetail> historyDetails) {
        super(RESPONSE_HISTORY);
        this.historyDetails = historyDetails;
    }

    public List<HistoryDetail> getHistoryDetails() {
        return historyDetails;
    }
}
