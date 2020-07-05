package com.example.wipet.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private int id, id_user, id_adopsi;
    private String comment, created_at;
    private User user;

    public Comment() {
    }

    protected Comment(Parcel in) {
        id = in.readInt();
        id_user = in.readInt();
        id_adopsi = in.readInt();
        comment = in.readString();
        created_at = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public int getId_adopsi() {
        return id_adopsi;
    }

    public void setId_adopsi(int id_adopsi) {
        this.id_adopsi = id_adopsi;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        dest.writeInt(id_adopsi);
        dest.writeString(comment);
        dest.writeString(created_at);
        dest.writeParcelable(user, flags);
    }
}
