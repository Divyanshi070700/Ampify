package Services;

import mainClass.Main;
import model.Album;
import model.Artist;
import model.Song;
import serverClasses.requests.AlbumFetchRequest;
import serverClasses.requests.ArtistFetchRequest;
import serverClasses.requests.SongFetchRequest;
import utilities.ArtistsAlbumFetchType;
import utilities.SongFetchType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class AmpifyServices {

    private static ObjectOutputStream oos = Main.userOutputStream;
    private static ObjectInputStream ois = Main.userInputStream;

    /*
    Function to get a list of top artists
     */
    public static List<Artist> getTopArtists() throws IOException, ClassNotFoundException {

        ArtistFetchRequest artistsFetchRequest = new ArtistFetchRequest(String.valueOf(ArtistsAlbumFetchType.TOP));
        oos.writeObject(artistsFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Artist>) ois.readObject();
    }


    /*
    Function to get a list of top albums
     */
    public static List<Album> getTopAlbums() throws IOException, ClassNotFoundException {

        AlbumFetchRequest albumFetchRequest = new AlbumFetchRequest(String.valueOf(ArtistsAlbumFetchType.TOP));
        oos.writeObject(albumFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Album>) ois.readObject();
    }


    /*
    Function to get top songs
     */
    public static List<Song> getTopSongs() throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchRequest = new SongFetchRequest(String.valueOf(SongFetchType.TOP));
        oos.writeObject(songFetchRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }


    /*
    Function to fetch songs of a particular artist
     */
    public static List<Song> getSongsOfArtist(int artistId) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ARTIST), artistId);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }


    /*
    Function to fetch songs of a particular album
     */
    public static List<Song> getSongsOfAlbum(int albumId) throws IOException, ClassNotFoundException {

        SongFetchRequest songFetchArtistRequest = new SongFetchRequest(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ALBUM), albumId);
        oos.writeObject(songFetchArtistRequest);
        oos.flush();
        ois = Main.userInputStream;

        return (List<Song>) ois.readObject();
    }
}
