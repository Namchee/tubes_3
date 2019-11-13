package com.example.tubes_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.tubes_3.fragments.HomeFragment;
import com.example.tubes_3.messages.RequestMessage;
import com.example.tubes_3.messages.request.MangaDetailRequestMessage;
import com.example.tubes_3.util.ServiceWorker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();

        ft.add(R.id.fragment_container, homeFragment, "");

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
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void handleRequestMessage(RequestMessage message) {
        switch (message.getMessageType()) {
            case 0: {
                ServiceWorker.getInstance(this.getApplicationContext()).getAllManga();
            }
            case 1: {
                MangaDetailRequestMessage mangaDetailRequestMessage = (MangaDetailRequestMessage)message;

                ServiceWorker.getInstance(this.getApplicationContext())
            }
        }
    }
}
