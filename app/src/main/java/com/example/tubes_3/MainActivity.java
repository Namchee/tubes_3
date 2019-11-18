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
import com.example.tubes_3.messages.response.MangaDetailResponseMessage;
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

        this.handleToHomepage();
    }

    @Override
    public void onBackPressed(){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        int fragmentStack_count = this.fragmentManager.getBackStackEntryCount();
        Log.d("onBackPressed: ",fragmentStack_count+"");
        if(fragmentStack_count<=1){
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
        } else {
            this.handleToHomepage();
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
    public void handleRequestMessage(RequestMessage message) {
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
        }
    }

    public void handleToHomepage(){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        if (this.mangaReadFragment != null && this.mangaReadFragment.isAdded()) {
            ft.hide(this.mangaReadFragment);
            ft.remove(this.mangaReadFragment);
        }

        if(this.mangaDetailFragment != null && this.mangaDetailFragment.isAdded()){
            ft.hide(this.mangaDetailFragment);
            ft.remove(this.mangaDetailFragment);
        }


        if (this.homeFragment == null ) {
            this.homeFragment = new HomeFragment();
        }

        if(!this.homeFragment.isAdded()) ft.add(R.id.fragment_container,this.homeFragment, null).addToBackStack("home");

        ft.show(this.homeFragment);

        ft.commit();
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

    public void handleToChapterRead(Chapter ch) {
        //TODO: implement!
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        ft.setCustomAnimations(R.anim.fragment_animation_in, R.anim.fragment_animation_out);

        if (this.homeFragment != null && this.homeFragment.isAdded()) {
            ft.hide(this.homeFragment);
        }

        if (this.mangaDetailFragment != null && this.mangaDetailFragment.isAdded()) {
            ft.hide(this.mangaDetailFragment);
        }

        this.mangaReadFragment = new MangaReadFragment(ch);

        ft.add(R.id.fragment_container, this.mangaReadFragment, null).addToBackStack("read");

        ft.commit();
    }
}
