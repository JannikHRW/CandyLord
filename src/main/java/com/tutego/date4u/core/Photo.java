package com.tutego.date4u.core;

import com.tutego.date4u.core.annotations.Filename;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Access( AccessType.FIELD )
public class Photo {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    public Long id;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @ManyToOne
    @JoinColumn( name = "profile_fk" )
    private Profile profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Filename
    public String name;

    public boolean isProfilePhoto() {
        return isProfilePhoto;
    }

    public void setProfilePhoto(boolean profilePhoto) {
        isProfilePhoto = profilePhoto;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Column( name = "is_profile_photo" )
    public boolean isProfilePhoto;
    @NotNull @Past public LocalDateTime created;

    public Photo() { }
    public Photo( long id, Profile profile, String name, boolean isProfilePhoto, LocalDateTime date ) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.isProfilePhoto = isProfilePhoto;
        this.created = date;
    }

    @Override public String toString() {
        return "Foto " + name + " mit id" + id + "wurde am " + created + " erstellt.";
    }
}