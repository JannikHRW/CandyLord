package com.tutego.date4u.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@ConfigurationProperties( "date4u" )
public class Date4uProperties {

    private FileSystem fileSystem = new FileSystem();

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public static class FileSystem {  // https://tiny.one/2p8whad4
        private final Path root =
                Paths.get( System.getProperty("user.home") ).resolve( "fs" );

        private long minimumFreeDiskSpace = 100000;

        public void setMinimumFreeDiskSpace(long minimumFreeDiskSpace) {
            this.minimumFreeDiskSpace = minimumFreeDiskSpace;
        }

        public FileSystem() {
            try { if ( ! Files.isDirectory(root) ) Files.createDirectory(root); }
            catch ( IOException e ) { throw new UncheckedIOException( e ); }
        }

        public long getFreeDiskSpace() {
            return root.toFile().getFreeSpace();
        }

        public long getMinimumFreeDiskSpace() {
            return minimumFreeDiskSpace;
        }

        public byte[] load( String filename ) {
            try { return Files.readAllBytes( root.resolve( filename ) ); }
            catch ( IOException e ) { throw new UncheckedIOException( e ); }
        }

        public void store( String filename, byte[] bytes ) {
            try { Files.write( root.resolve( filename ), bytes ); }
            catch ( IOException e ) { throw new UncheckedIOException( e ); }
        }
    }
}
