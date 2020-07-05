package com.example.wipet.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Discussion implements Parcelable {
    private int id, id_user, id_category, id_pets_category,commentsCount, likesCount;
    private String title, content,topic,created_at;
    private ArrayList<String> photo;
    private User user;



    public Discussion(){

    }


    protected Discussion(Parcel in) {
        id = in.readInt();
        id_user = in.readInt();
        id_category = in.readInt();
        id_pets_category = in.readInt();
        commentsCount = in.readInt();
        likesCount = in.readInt();
        title = in.readString();
        content = in.readString();
        topic = in.readString();
        created_at = in.readString();
        photo = in.createStringArrayList();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Discussion> CREATOR = new Creator<Discussion>() {
        @Override
        public Discussion createFromParcel(Parcel in) {
            return new Discussion(in);
        }

        @Override
        public Discussion[] newArray(int size) {
            return new Discussion[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_pets_category() {
        return id_pets_category;
    }

    public void setId_pets_category(int id_pets_category) {
        this.id_pets_category = id_pets_category;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<String> photo) {
        this.photo = photo;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_user);
        dest.writeInt(id_category);
        dest.writeInt(id_pets_category);
        dest.writeInt(commentsCount);
        dest.writeInt(likesCount);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(topic);
        dest.writeString(created_at);
        dest.writeStringList(photo);
        dest.writeParcelable(user, flags);
    }
}
