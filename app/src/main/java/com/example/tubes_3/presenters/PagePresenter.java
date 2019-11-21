package com.example.tubes_3.presenters;

import java.util.List;

public class PagePresenter {
    private List<String> imgURL;

    public PagePresenter(){}

    public void setImageList(List<String> imgURL){this.imgURL = imgURL;}

    public int getLength(){return this.imgURL.size();}

    public String getImage(int i){return this.imgURL.get(i);}
}
