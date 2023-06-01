package com.tutego.date4u.interfaces.web;

import com.tutego.date4u.core.Photo;
import com.tutego.date4u.core.Profile;
import com.tutego.date4u.core.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class Date4uWebController {

    @Autowired
    ProfileRepository profiles;

    @RequestMapping( "/**" )
    public String indexPage(Model model) {
        model.addAttribute( "numberOfProfiles", profiles.count() );
        return "index"; }


    //regex //d+ lenkt zur Startseite wenn keine Zahlen eingegeben werden
    @RequestMapping( "/profile/{id:\\d+}" )
    public String profilePage(@PathVariable long id, Model model ) {
        Optional<Profile> maybeProfile = profiles.findById( id );
        if (maybeProfile.isPresent()) {
            Profile profile = maybeProfile.get();
            model.addAttribute( "profile",
                    new ProfileFormData(
                            profile.getId(), profile.getNickname(),
                            profile.getBirthdate(),
                            profile.getHornlength(), profile.getGender(),
                            profile.getAttractedToGender(), profile.getDescription(),
                            profile.getLastseen(),
                            profile.getPhotos().stream().sorted(
                                    Comparator.comparing( Photo::isProfilePhoto )
                                            .reversed()
                                            .thenComparing( Photo::getCreated )
                            ).map( Photo::getName ).toList()
) );

            return "profile";
        }
        else {
            return "redirect:/";
        }
    }

    @RequestMapping( "/profile" )
    public String profilePage( Model model ) {
        Optional<Profile> maybeProfile = profiles.findById( 1L );
        model.addAttribute( "profile", maybeProfile.get() );
        return "profile";
    }

    @RequestMapping( "/search" )
    public String searchPage(Model model) {
        List<Profile> profileList = profiles.findAll();
        model.addAttribute( "profiles", profileList );

        return "search"; }


    private final Logger log = LoggerFactory.getLogger( getClass() );

    @PostMapping( "/save" )
    public String saveProfile( @ModelAttribute ProfileFormData profile ) {
        System.out.println(profile.getGender());
        if (profile.getGender() == 0) {
            profile.setAttractedToGender(0);
        }
        log.info( profile.toString() );


        return "redirect:/profile/" + profile.getId();
    }
}