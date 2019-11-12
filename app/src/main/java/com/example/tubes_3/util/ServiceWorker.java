package com.example.tubes_3.util;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tubes_3.model.Manga;
import com.example.tubes_3.model.RequestMessage;
import com.example.tubes_3.model.URL_BASE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceWorker {
    private static ServiceWorker instance;
    RequestQueue requestQueue;

    private ServiceWorker(Context ctx) {
        Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024 * 4); // 4MB
        Network network = new BasicNetwork(new HurlStack());

        this.requestQueue = new RequestQueue(cache, network);
    }

    @Subscribe
    public void requestManga(RequestMessage requestMessage) {
        if (requestMessage.getMessageType() == 0) {
            String location = URL_BASE.API.getUrl();
            List<Manga> mangaList = new ArrayList<>();

            try {
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET,
                        location,
                        null,
                        (JSONObject response) -> {
                            try {
                                JSONArray jsonArray = response.getJSONArray("manga");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject rawObject = jsonArray.getJSONObject(i);

                                    mangaList.add(this.convertToManga(rawObject));
                                }

                                EventBus.getDefault().post();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        },
                        (VolleyError error) -> {
                            // DO NOTHING
                        }
                );

                this.requestQueue.add(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Manga convertToManga(JSONObject rawObject) {
        Manga manga = null;

        try {
            String id = rawObject.getString("i");
            String title = rawObject.getString("t");
            String imgUrl = "";

            if (rawObject.has("im") && rawObject.getString("im") != "null") {
                imgUrl = URL_BASE.IMAGE_SRC.getUrl() + rawObject.getString("im");
            }

            Date lastUpdated = null;

            if (rawObject.has("ld") && rawObject.getString("ld") != null) {
                lastUpdated = Parser.parseDate(rawObject.getString("ld"));
            }

            JSONArray categoriesRaw = rawObject.getJSONArray("c");
            List<String> categories = new ArrayList<>();

            for (int i = 0; i < categoriesRaw.length(); i++) {
                categories.add(categoriesRaw.getString(i));
            }

            String status = Parser.parseStatus(Integer.parseInt(rawObject.getString("s")));
            int hits = Integer.parseInt(rawObject.getString("h"));

            manga = new Manga(id, title, imgUrl, lastUpdated, categories, status, hits);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return manga;
    }

    public static synchronized ServiceWorker getInstance(Context ctx) {
        if (instance == null) {
            instance = new ServiceWorker(ctx);
        }

        return instance;
    }
}
