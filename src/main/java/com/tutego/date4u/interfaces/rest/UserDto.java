package com.tutego.date4u.interfaces.rest;

import java.time.LocalDate;

public record UserDto(
        String email, String password, String nickname, LocalDate birthdate
) {
}