package com.example.tubes_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.tubes_3.fragments.HomeFragment;
import com.example.tubes_3.fragments.MangaDetailFragment;
import com.example.tubes_3.fragments.MangaReadFragment;
import com.example.tubes_3.interfaces.FragmentListener;
import com.example.tubes_3.messages.RequestMessage;
import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.messages.request.ChapterRequestMessage;
import com.example.tubes_3.messages.request.MangaDetailRequestMessage;
import com.example.tubes_3.messages.request.MangaFavoriteRequestMessage;
import com.example.tubes_3.messages.response.MangaDetailResponseMessage;
import com.example.tubes_3.messages.response.MangaFavoriteResponseMessage;
import com.example.tubes_3.model.Chapter;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.util.ServiceWorker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    HomeFragment homeFragment;
    MangaDetailFragment mangaDetailFragment;
    MangaReadFragment mangaReadFragment;

    public static final int HOME_FRAGMENT_ID = 11;
    public static final int MANGA_DETAIL_ID = 12;
    public static final int MANGA_READ_ID = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        this.homeFragment = new HomeFragment();

        ft.add(R.id.fragment_container, homeFragment, "").addToBackStack("home");

        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(RequestMessage message) {
        switch (message.getMessageType()) {
            case RequestMessage.REQUEST_ALL: {
                ServiceWorker.getInstance(this.getApplicationContext()).getAllManga();
                break;
            }
            case RequestMessage.REQUEST_DETAIL: {
                MangaDetailRequestMessage mangaDetailRequestMessage = (MangaDetailRequestMessage)message;

                this.handleToMangaDetail(mangaDetailRequestMessage.getMangaRaw());
                break;
            }
            case RequestMessage.REQUEST_CHAPTER: {
                ChapterRequestMessage chapterRequestMessage = (ChapterRequestMessage)message;

                this.handleToChapterRead();
                break;
            }
            case RequestMessage.REQUEST_FAVORITE: {
                MangaFavoriteRequestMessage mangaFavoriteResponseMessage = (MangaFavoriteRequestMessage)message;

                ServiceWorker.getInstance(this.getApplicationContext()).getFavoritesInfo(mangaFavoriteResponseMessage.getMangaIds());
                break;
            }
        }
    }

    public void handleToMangaDetail(MangaRaw mangaRaw) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        ft.setCustomAnimations(R.anim.fragment_animation_in, R.anim.fragment_animation_out);

        if (this.homeFragment != null && this.homeFragment.isAdded()) {
            ft.hide(this.homeFragment);
        }

        if (this.mangaReadFragment != null && this.mangaReadFragment.isAdded()) {
            ft.hide(this.mangaReadFragment);
        }

        this.mangaDetailFragment = new MangaDetailFragment(mangaRaw);

        ft.add(R.id.fragment_container, this.mangaDetailFragment, null).addToBackStack("details");

        ft.commit();

    }

    public void handleToChapterRead() {
        //TODO: implement!
    }
}
