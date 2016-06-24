package com.example.administrator.cbkproject.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/21.
 */
public class Info implements Parcelable{
    private String description;
    private String rCount;
    private String url;
    private String keywords;
    private long time;
    private long id;
    private String title;
    private String image;

    public Info(){

    }


    protected Info(Parcel in) {
        description = in.readString();
        rCount = in.readString();
        url = in.readString();
        keywords = in.readString();
        time = in.readLong();
        id = in.readLong();
        title = in.readString();
        image = in.readString();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRcount() {
        return rCount;
    }

    public void setRcount(String rCount) {
        this.rCount = rCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(description);
        dest.writeString(rCount);
        dest.writeString(url);
        dest.writeString(keywords);
        dest.writeLong(time);
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(image);
    }


    @Override
    public String toString() {
        return "Info{" +
                "description='" + description + '\'' +
                ", rCount='" + rCount + '\'' +
                ", url='" + url + '\'' +
                ", keywords='" + keywords + '\'' +
                ", time=" + time +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}