package com.example.kulde.instagram.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ListView;

import java.util.List;

public class Photo implements Parcelable {
    private String caption;
    private String date_created;
    private String image_path;
    private String photo_id;
    private String user_id;
    private String tags;
    private List<Likes> likes;
    private List<Comment> comments;



    public Photo() {

    }

    public Photo(String caption, String date_created, String image_path, String photo_id, String user_id, String tags, List<Likes> likes, List<Comment> comments) {
        this.caption = caption;
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.tags = tags;
        this.likes = likes;
        this.comments = comments;
    }

    public Photo(String caption, String date_created, String image_path, String photo_id, String user_id, String tags) {
        this.caption = caption;
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "caption='" + caption + '\'' +
                ", date_created='" + date_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", tags='" + tags + '\'' +
                ", likes=" + likes +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
