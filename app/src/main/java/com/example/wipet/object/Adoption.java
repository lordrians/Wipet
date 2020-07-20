package com.example.wipet.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Adoption implements Parcelable {
    private int id, size, age;
    private long price;
    private String title, gender, background_story,category,pets_category, desc, medical_notes, kelurahan,kota,kecamatan, provinsi;
    private String created_at, status;
    private int vaccinated, neutered,friendly;
    private User user;
    private ArrayList<String> photo;

    public Adoption() {
    }

    protected Adoption(Parcel in) {
        id = in.readInt();
        size = in.readInt();
        age = in.readInt();
        price = in.readLong();
        title = in.readString();
        gender = in.readString();
        background_story = in.readString();
        category = in.readString();
        pets_category = in.readString();
        desc = in.readString();
        medical_notes = in.readString();
        kelurahan = in.readString();
        kota = in.readString();
        kecamatan = in.readString();
        provinsi = in.readString();
        created_at = in.readString();
        status = in.readString();
        vaccinated = in.readInt();
        neutered = in.readInt();
        friendly = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        photo = in.createStringArrayList();
    }

    public static final Creator<Adoption> CREATOR = new Creator<Adoption>() {
        @Override
        public Adoption createFromParcel(Parcel in) {
            return new Adoption(in);
        }

        @Override
        public Adoption[] newArray(int size) {
            return new Adoption[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPets_category() {
        return pets_category;
    }

    public void setPets_category(String pets_category) {
        this.pets_category = pets_category;
    }

    public int getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(int vaccinated) {
        this.vaccinated = vaccinated;
    }

    public int getNeutered() {
        return neutered;
    }

    public void setNeutered(int neutered) {
        this.neutered = neutered;
    }

    public int getFriendly() {
        return friendly;
    }

    public void setFriendly(int friendly) {
        this.friendly = friendly;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBackground_story() {
        return background_story;
    }

    public void setBackground_story(String background_story) {
        this.background_story = background_story;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMedical_notes() {
        return medical_notes;
    }

    public void setMedical_notes(String medical_notes) {
        this.medical_notes = medical_notes;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<String> photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(size);
        dest.writeInt(age);
        dest.writeLong(price);
        dest.writeString(title);
        dest.writeString(gender);
        dest.writeString(background_story);
        dest.writeString(category);
        dest.writeString(pets_category);
        dest.writeString(desc);
        dest.writeString(medical_notes);
        dest.writeString(kelurahan);
        dest.writeString(kota);
        dest.writeString(kecamatan);
        dest.writeString(provinsi);
        dest.writeString(created_at);
        dest.writeString(status);
        dest.writeInt(vaccinated);
        dest.writeInt(neutered);
        dest.writeInt(friendly);
        dest.writeParcelable(user, flags);
        dest.writeStringList(photo);
    }
}
