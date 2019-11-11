package com.example.tubes_3.interfaces;

import androidx.fragment.app.FragmentTransaction;

public interface FragmentListener {
    void changePage(int id);
    void hideAllFragment(FragmentTransaction ft);
}
