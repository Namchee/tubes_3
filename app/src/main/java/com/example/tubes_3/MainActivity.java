package com.example.tubes_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.example.tubes_3.messages.request.MangaHistoryRequestMessage;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fragmentManager = this.getSupportFragmentManager();

        this.handleToHomepage();
    }

    @Override
    public void onBackPressed(){
        if(this.homeFragment.isVisible()){
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Are you sure to quit?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else if(this.mangaDetailFragment.isVisible()){
            this.handleToHomepage();
        } else {
            this.handleToMangaDetail(this.mangaDetailFragment.getMangaRaw());
        }
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

                this.handleToChapterRead(chapterRequestMessage.getChapter());
                break;
            }
            case RequestMessage.REQUEST_FAVORITE: {
                MangaFavoriteRequestMessage mangaFavoriteRequestMessage = (MangaFavoriteRequestMessage)message;

                ServiceWorker.getInstance(this.getApplicationContext()).getFavoritesInfo(mangaFavoriteRequestMessage.getMangaIds());
                break;
            }
            case RequestMessage.REQUEST_HISTORY: {
                MangaHistoryRequestMessage mangaHistoryRequestMessage = (MangaHistoryRequestMessage)message;

                ServiceWorker.getInstance(this.getApplicationContext()).getHistoriesInfo(mangaHistoryRequestMessage.getHistoryList());
                break;
            }
        }
    }

    public void handleToHomepage(){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        if (this.mangaReadFragment != null && this.mangaReadFragment.isAdded()) {
            ft.remove(this.mangaReadFragment);
        }

        if(this.mangaDetailFragment != null && this.mangaDetailFragment.isAdded()){
            ft.remove(this.mangaDetailFragment);
        }

        this.homeFragment = new HomeFragment();
        ft.add(R.id.fragment_container,this.homeFragment).addToBackStack(null);

        ft.show(this.homeFragment);

        ft.commit();
    }


    public void handleToMangaDetail(MangaRaw mangaRaw) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        ft.setCustomAnimations(R.anim.fragment_animation_in, R.anim.fragment_animation_out);

        if (this.homeFragment != null && this.homeFragment.isAdded()) {
            ft.remove(this.homeFragment);
        }

        if (this.mangaReadFragment != null && this.mangaReadFragment.isAdded()) {
            ft.remove(this.mangaReadFragment);
        }

        this.mangaDetailFragment = new MangaDetailFragment(mangaRaw);

        ft.add(R.id.fragment_container, this.mangaDetailFragment).addToBackStack("");

        ft.commit();

    }

    public void handleToChapterRead(Chapter ch) {
        //TODO: implement!
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        ft.setCustomAnimations(R.anim.fragment_animation_in, R.anim.fragment_animation_out);

        if (this.homeFragment != null && this.homeFragment.isAdded()) {
            ft.remove(this.homeFragment);
        }

        if (this.mangaDetailFragment != null && this.mangaDetailFragment.isAdded()) {
            ft.remove(this.mangaDetailFragment);
        }

        this.mangaReadFragment = new MangaReadFragment(ch);

        ft.add(R.id.fragment_container, this.mangaReadFragment).addToBackStack("");

        ft.commit();
    }
}
