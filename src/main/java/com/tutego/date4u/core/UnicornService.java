package com.tutego.date4u.core;

import com.tutego.date4u.interfaces.rest.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UnicornService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UnicornRepository unicornRepository;

    public Unicorn getUnicornByEmail(String email) {
        return unicornRepository.findByEmail(email);
    }

    public void createNewUser(UserDto userDto) {
        Unicorn unicorn = new Unicorn();
        unicorn.setEmail(userDto.email());
        unicorn.setPassword("{noop}" + userDto.password());

        Profile profile = new Profile();
        profile.setNickname(userDto.nickname());;
        profile.setBirthdate(userDto.birthdate());
        profile.setLastseen(LocalDateTime.now());

        unicorn.setProfile(profile);

        profileRepository.save(profile);
        unicornRepository.save(unicorn);
    }
}