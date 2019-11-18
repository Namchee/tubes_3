package com.example.tubes_3.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubes_3.messages.response.ChapterResponseMessage;
import com.example.tubes_3.messages.response.MangaListResponseMessage;
import com.example.tubes_3.messages.response.MangaDetailResponseMessage;
import com.example.tubes_3.model.Chapter;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ServiceWorker {
    private static Context ctx;
    private static ServiceWorker instance;
    RequestQueue requestQueue;

    private ServiceWorker(Context context) {
        ctx = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized ServiceWorker getInstance(Context ctx) {
        if (instance == null) {
            instance = new ServiceWorker(ctx);
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }

        return requestQueue;
    }

    public void getAllManga() {
        String location = URL_BASE.MANGA_ALL.getUrl();
        List<MangaRaw> mangaRawList = new ArrayList<>();

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

                                mangaRawList.add(this.convertToManga(rawObject));
                            }

                            EventBus.getDefault().post(new MangaListResponseMessage(mangaRawList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        },
                    (VolleyError error) -> {
                        // DO NOTHING
                    }
                );

                getRequestQueue().add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MangaRaw convertToManga(JSONObject rawObject) {
        MangaRaw mangaRaw = null;

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

            mangaRaw = new MangaRaw(id, title, imgUrl, lastUpdated, categories, status, hits);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mangaRaw;
    }

    public void getMangaDetail(String mangaId) {
        String location = URL_BASE.MANGA_DETAIL.getUrl() + mangaId;

        try {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    location,
                    null,
                    (JSONObject response) -> {
                        MangaDetail mangaDetail = this.convertToMangaDetail(response);

                        EventBus.getDefault().post(new MangaDetailResponseMessage(mangaDetail));
                    },
                    (VolleyError error) -> {
                        // DO NOTHINGS
                    }
            );

            getRequestQueue().add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MangaDetail convertToMangaDetail(JSONObject rawObject) {
        MangaDetail mangaDetail = null;

        try {
            String imgUrl = "";

            if (rawObject.has("image") && rawObject.getString("image") != "null") {
                imgUrl = URL_BASE.IMAGE_SRC.getUrl() + rawObject.getString("image");
            }

            String title = rawObject.getString("title");
            String author = rawObject.getString("author");
            String status = Parser.parseStatus(rawObject.getInt("status"));
            String artist = rawObject.getString("artist");
            String desc = rawObject.getString("description");
            Date createdAt = Parser.parseDate(rawObject.getString("created"));
            Date lastUpdated = Parser.parseDate(rawObject.getString("last_chapter_date"));
            int hits = rawObject.getInt("hits");

            String url = "";

            if (rawObject.has("url")) {
                url = rawObject.getString("url");
            }

            JSONArray rawChapterArray = rawObject.getJSONArray("chapters");
            List<Chapter> chapterList = this.convertToChapterList(rawChapterArray);

            JSONArray categoriesRaw = rawObject.getJSONArray("categories");
            List<String> categories = new ArrayList<>();

            for (int i = 0; i < categoriesRaw.length(); i++) {
                categories.add(categoriesRaw.getString(i));
            }

            mangaDetail = new MangaDetail(imgUrl, title, author, artist, desc, status, categories, createdAt, lastUpdated, chapterList, hits, url);
            System.out.println(mangaDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mangaDetail;
    }

    private List<Chapter> convertToChapterList(JSONArray rawChapterArray) {
        List<Chapter> chapterList = new ArrayList<>();

        try {
            for (int i = 0; i < rawChapterArray.length(); i++) {
                JSONArray rawChapter = rawChapterArray.getJSONArray(i);

                int chapterNum = rawChapter.getInt(0);
                Date lastUpdated = Parser.parseDate(rawChapter.getString(1));
                String title = rawChapter.getString(2);
                String id = rawChapter.getString(3);

                Chapter chapter = new Chapter(chapterNum, id, title, lastUpdated);

                chapterList.add(chapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chapterList;
    }

    public void getChapterImages(String chapterId) {
        String location = URL_BASE.CHAPTERS.getUrl() + chapterId;
        List<String> imageUrls = new ArrayList<>();

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    location,
                    null,
                    (JSONObject response) -> {
                        try {
                            JSONArray array = response.getJSONArray("images");

                            for (int i = array.length() - 1; i >= 0; i--) {
                                String url = URL_BASE.IMAGE_SRC.getUrl() + array.getJSONArray(i).getString(1);

                                imageUrls.add(url);
                            }

                            EventBus.getDefault().post(new ChapterResponseMessage(imageUrls));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    (VolleyError error) -> {

                    }
            );

            getRequestQueue().add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFavoritesInfo(List<String> mangaIds) {
        List<MangaRaw> mangaRaws = new ArrayList<>();

        try {
            for (String id: mangaIds) {
                String location = URL_BASE.MANGA_DETAIL.getUrl() + id;

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET,
                        location,
                        null,
                        (JSONObject response) -> {
                            MangaRaw mangaRaw = this.convertDetailToRaw(id, response);

                            mangaRaws.add(mangaRaw);

                            if (mangaRaws.size() == mangaIds.size()) {
                                EventBus.getDefault().post(new MangaListResponseMessage(mangaRaws));
                            }
                        },
                        (VolleyError error) -> {

                        }
                );

                getRequestQueue().add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MangaRaw convertDetailToRaw(String id, JSONObject rawObject) {
        MangaRaw mangaRaw = null;

        try {
            String imgUrl = "";

            if (rawObject.has("image") && rawObject.getString("image") != "null") {
                imgUrl = URL_BASE.IMAGE_SRC.getUrl() + rawObject.getString("image");
            }

            String title = rawObject.getString("title");
            String status = Parser.parseStatus(rawObject.getInt("status"));
            Date lastUpdated = Parser.parseDate(rawObject.getString("last_chapter_date"));
            int hits = rawObject.getInt("hits");

            JSONArray rawChapterArray = rawObject.getJSONArray("chapters");

            JSONArray categoriesRaw = rawObject.getJSONArray("categories");
            List<String> categories = new ArrayList<>();

            for (int i = 0; i < categoriesRaw.length(); i++) {
                categories.add(categoriesRaw.getString(i));
            }

            mangaRaw = new MangaRaw(id, title, imgUrl, lastUpdated, categories, status, hits);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mangaRaw;
    }
}
