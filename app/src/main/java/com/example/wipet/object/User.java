package com.example.wipet.object;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id, id_kelurahan;
    private String name;
    private String username;
    private String email;
    private String email_verified_at;
    private String password;
    private String photo;
    private String job;
    private String phone;
    private String about;
    private String kelurahan;
    private String kecamatan;
    private String kota;

    public User(){

    }

    protected User(Parcel in) {
        id = in.readInt();
        id_kelurahan = in.readInt();
        name = in.readString();
        username = in.readString();
        email = in.readString();
        email_verified_at = in.readString();
        password = in.readString();
        photo = in.readString();
        job = in.readString();
        phone = in.readString();
        about = in.readString();
        kelurahan = in.readString();
        kecamatan = in.readString();
        kota = in.readString();
        provinsi = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    private String provinsi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_kelurahan() {
        return id_kelurahan;
    }

    public void setId_kelurahan(int id_kelurahan) {
        this.id_kelurahan = id_kelurahan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_kelurahan);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(email_verified_at);
        dest.writeString(password);
        dest.writeString(photo);
        dest.writeString(job);
        dest.writeString(phone);
        dest.writeString(about);
        dest.writeString(kelurahan);
        dest.writeString(kecamatan);
        dest.writeString(kota);
        dest.writeString(provinsi);
    }
}
