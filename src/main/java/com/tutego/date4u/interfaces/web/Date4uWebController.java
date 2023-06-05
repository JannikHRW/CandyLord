package com.tutego.date4u.interfaces.web;

import com.tutego.date4u.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class Date4uWebController {

    @Autowired
    ProfileRepository profiles;

    @Autowired
    UnicornService unicorns;

    @RequestMapping("/**")
    public String indexPage(Model model) {
        model.addAttribute("numberOfProfiles", profiles.count());
        return "index";
    }


    //regex //d+ lenkt zur Startseite wenn keine Zahlen eingegeben werden
    @RequestMapping("/profile/{id:\\d+}")
    public String profilePage(@PathVariable long id, Model model) {
        Optional<Profile> maybeProfile = profiles.findById(id);
        if (maybeProfile.isPresent()) {
            Profile profile = maybeProfile.get();

            if (isOwnProfile(profile)) {
                model.addAttribute("editable", true);
            } else {
                model.addAttribute("editable", false);
            }

            model.addAttribute("profile",
                    new ProfileFormData(
                            profile.getId(), profile.getNickname(),
                            profile.getBirthdate(),
                            profile.getHornlength(), profile.getGender(),
                            profile.getAttractedToGender(), profile.getDescription(),
                            profile.getLastseen(),
                            profile.getPhotos().stream().sorted(
                                    Comparator.comparing(Photo::isProfilePhoto)
                                            .reversed()
                                            .thenComparing(Photo::getCreated)
                            ).map(Photo::getName).toList()
                    ));

            return "profile";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/profile")
    public String profilePage() {
        return "redirect:/profile/" + getCurrentUnicorn().getProfile().getId();
    }

    @RequestMapping("/search")
    public String searchPage(Model model) {
        List<Profile> profileList = profiles.findAll();
        model.addAttribute("profiles", profileList);

        return "search";
    }

    @PostMapping("/save")
    public String saveProfile(@ModelAttribute ProfileFormData profile) {
        Optional<Profile> profileToUpdate = profiles.findById(profile.getId());

        if (profileToUpdate.isPresent()) {
            Profile existingProfile = profileToUpdate.get();
            existingProfile.setLastseen(LocalDateTime.now());
            existingProfile.setNickname(profile.getNickname());
            existingProfile.setBirthdate(profile.getBirthdate());
            existingProfile.setHornlength(profile.getHornlength());
            existingProfile.setGender(profile.getGender());
            existingProfile.setAttractedToGender(profile.getAttractedToGender());
            existingProfile.setDescription(profile.getDescription());

            Profile updatedProfile = profiles.save(existingProfile);

            return "redirect:/profile/" + updatedProfile.getId();
        }

        return "redirect:/profile/" + profile.getId();
    }

    private boolean isOwnProfile(Profile profile) {
        String emailOfProfile = unicorns.findByProfile(profile).getEmail();
        String emailOfCurrentUser = getCurrentUnicorn().getEmail();
        if (Objects.equals(emailOfProfile, emailOfCurrentUser)) {
            return true;
        }
        return false;
    }

    public Unicorn getCurrentUnicorn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String username = userDetails.getUsername();

            return unicorns.getUnicornByEmail(username);
        }
        return null;
    }
}