package com.tutego.date4u.core;

import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {
    private FileSystem fs;

    private Thumbnail thumbnail;

    public PhotoService( FileSystem fs, Thumbnail thumbnail ) {
        this.thumbnail = thumbnail;
        this.fs = fs; }

    //Setter Injection
    //@Autowired
    //public void setFileSystem( FileSystem fs ) { this.fs = fs; }
    public Optional<byte[]> download(String name ) {
        try { return Optional.of( fs.load( name + ".jpg" ) ); }
        catch ( UncheckedIOException e ) { return Optional.empty(); }
    }

    public String upload( byte[] imageBytes ) {
        String imageName = UUID.randomUUID().toString();
        // First: store original image
        fs.store( imageName + ".jpg", imageBytes );
        // Second: store thumbnail
        byte[] thumbnailBytes = thumbnail.thumbnail( imageBytes );
        fs.store( imageName + "-thumb.jpg", thumbnailBytes );
        return imageName;
    }
}
