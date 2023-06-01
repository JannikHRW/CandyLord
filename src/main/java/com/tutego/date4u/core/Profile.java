package com.tutego.date4u.core;

import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Access( AccessType.FIELD )
public class Profile {
    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @OneToMany(
            mappedBy = "profile",
            fetch    = FetchType.EAGER )
    private List<Photo> photos = new ArrayList<>();

    @OneToOne( mappedBy = "profile")
    private Unicorn unicorn;

    public static final int FEE = 1;
    public static final int MAA = 2;

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String nickname;
    private LocalDate birthdate;
    private short hornlength;
    private byte gender;

    @Column( name = "attracted_to_gender" )
    private Byte attractedToGender;

    private String description;
    private LocalDateTime lastseen;

    public Profile() {
    }

    public Profile( String nickname, LocalDate birthdate, int hornlength,
                    int gender, Integer attractedToGender, String description,
                    LocalDateTime lastseen ) {
        setNickname( nickname );
        setBirthdate( birthdate );
        setHornlength( hornlength );
        setGender( gender );
        setAttractedToGender( attractedToGender );
        setDescription( description );
        setLastseen( lastseen );
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname( String nickname ) {
        this.nickname = nickname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate( LocalDate birthdate ) {
        this.birthdate = birthdate;
    }

    public int getHornlength() {
        return hornlength;
    }

    public void setHornlength( int hornlength ) {
        this.hornlength = (short) hornlength;
    }

    public int getGender() {
        return gender;
    }

    public void setGender( int gender ) {
        this.gender = (byte) gender;
    }

    public @Nullable Integer getAttractedToGender() {
        return attractedToGender == null ? null : attractedToGender.intValue();
    }

    public void setAttractedToGender( @Nullable Integer attractedToGender ) {
        this.attractedToGender = attractedToGender == null ? null : attractedToGender.byteValue();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public LocalDateTime getLastseen() {
        return lastseen;
    }

    public void setLastseen( LocalDateTime lastseen ) {
        this.lastseen = lastseen;
    }

    @Override public boolean equals( Object o ) {
        return o instanceof Profile profile
                && Objects.equals( nickname, profile.nickname );
    }

    @Override public int hashCode() {
        return Objects.hashCode( nickname );
    }

    @Override public String toString() {
        return "Profile[id=%d]".formatted( id );
    }
}
