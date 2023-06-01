package com.tutego.date4u.interfaces.web;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ProfileFormData {

    private List<String> photos;

    private long id;
    private String nickname;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate birthdate;
    private int hornlength;
    private int gender;
    private Integer attractedToGender;
    private String description;
    private LocalDateTime lastseen;

    public ProfileFormData() { }
    public ProfileFormData( long id, String nickname, LocalDate birthdate, int hornlength, int gender, Integer attractedToGender, String description, LocalDateTime lastseen, List<String> photos ) {
        this.photos = photos;
    }
    // + Setter + Getter + toString()

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public int getHornlength() {
        return hornlength;
    }

    public void setHornlength(int hornlength) {
        this.hornlength = hornlength;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Integer getAttractedToGender() {
        return attractedToGender;
    }

    public void setAttractedToGender(Integer attractedToGender) {
        this.attractedToGender = attractedToGender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastseen() {
        return lastseen;
    }

    public void setLastseen(LocalDateTime lastseen) {
        this.lastseen = lastseen;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProfileFormData that = (ProfileFormData) o;
//        return id == that.id && hornlength == that.hornlength && gender == that.gender && Objects.equals(nickname, that.nickname) && Objects.equals(birthdate, that.birthdate) && Objects.equals(attractedToGender, that.attractedToGender) && Objects.equals(description, that.description) && Objects.equals(lastseen, that.lastseen);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, birthdate, hornlength, gender, attractedToGender, description, lastseen);
    }

    public List<String> getPhotos() {
        return photos;
    }
}

