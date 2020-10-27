package controllers;

import Services.AmpifyServices;
import Services.MediaPlayerService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Notification;
import model.Playlist;
import model.Song;
import model.UserHistory;
import utilities.HomeScreenDisplays;
import utilities.HomeScreenWidgets;
import utilities.UserApi;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class HomeController implements Initializable {

    public JFXButton logoutButton;
    public Pane displayPane;
    public Pane bottomPane;
    public JFXListView<Song> nowPlayingList;
    public Text userEmailText;
    UserApi userApi = UserApi.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUserData();

        HomeScreenWidgets.displayPane = displayPane;
        HomeScreenWidgets.bottomPane = bottomPane;
        HomeScreenWidgets.nowPlayingList = nowPlayingList;

        try {
            System.out.println("Works1");
            Pane newPane = FXMLLoader.load(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
            displayPane.getChildren().clear();
            displayPane.getChildren().add(newPane);
            HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.MAIN_PAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Displaying the last played song in bottomPane
        try {
            System.out.println("Works2");
            MediaPlayerService.previousSong = AmpifyServices.getUserLastPlayedSong();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (MediaPlayerService.previousSong != null) {
            try {
                Pane mediaController = FXMLLoader.load(getClass().getResource("/resources/fxml/mediaPlayer.fxml"));
                bottomPane.getChildren().add(mediaController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

/**
 * testing playlist creation !!
 * place at apt place later on
 */
/**
 * when creating a playlist
 *          u r required to give playlist name, category(group,or user),privacy(public or private)
 *          pass to function named createPlaylist
 * when u want to add a song to playlist
 *          u r reqd to pass playlistID ,songID to func named addSongToPlaylist()
 * when u want to fetch playlists of whose user is owner(of whose user is member wala function nahi hua h abhi)
 *      u r only reqd to call the function getMyPlaylists()
 * when u want to fetch songs of a particular playlist
 *      u r required to pass the playlist ID to function getSongsOfPlaylist()
 *
 */




        try {
            System.out.println("fetching personal playlists!!!");

            List<Playlist> collection = AmpifyServices.getMyPlaylists();
            for (Playlist p : collection)
                System.out.println(p.getPlaylistName() + " " + p.getOwner() + " " + p.getPrivacy()+p.getCategory());


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            System.out.println("fetching noti!!!");

            List<Notification> collection = AmpifyServices.getMyNotifications();
            for (Notification p : collection)
                System.out.println(p.getPlaylistName() + " " + p.getSender() );


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("sending noti!!!");

            String collection = AmpifyServices.sendNotification("errbbbor@gmail.com",11);
            System.out.print(collection);


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("fetching most played songs!!!");

            List<UserHistory> collection = AmpifyServices.getUserMostPlayedSong();
            for (UserHistory p : collection)
                System.out.println(p.getSongName() + " " + p.getNumberOfTimesPlayed() );


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("fetching history!!!!");

            List<UserHistory> collection = AmpifyServices.getUserHistory(0,10);
            for (UserHistory p : collection)
                System.out.println(p.getSongName() + " " +p.getTimePlayed() );


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



        /**
         * this is testing for sending notification
         * u r reqd to give me details like receiver mailId and playlist Id
         * and call function sendNotifications!!
         */


    }


    private void displayUserData() {
        System.out.println("display " + userApi.getEmail());
        userEmailText.setText(userApi.getEmail());
    }

    public void onLogoutClicked(ActionEvent actionEvent) throws IOException {

        // TODO: STOP MEDIA PLAYER ON LOGOUT

        // Removing user's info from UserApi class
        UserApi userApi = UserApi.getInstance();
        userApi.setLikedLanguages(null);
        userApi.setLikedArtists(null);
        userApi.setLikedGenres(null);
        userApi.setEmail(null);

        // Removing login info from local storage
        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        preferences.put("isLoggedIn", "FALSE");
        preferences.put("email", "");

        goToLoginScreen(actionEvent);
    }

    private void goToLoginScreen(ActionEvent actionEvent) throws IOException {
        // Scene to be displayed
        Parent loginScreenParent = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));
        Scene loginScreenScene = new Scene(loginScreenParent);

        // Getting the current stage window
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Setting the new scene in the window
        window.setScene(loginScreenScene);
        window.show();
    }

    public void historyButtonAction(ActionEvent actionEvent) {

        if (HomeScreenWidgets.currentDisplayPage != HomeScreenDisplays.HISTORY_PAGE) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/historyScreen.fxml"));
                Pane newLoadedPane = loader.load();
                displayPane.getChildren().clear();
                displayPane.getChildren().add(newLoadedPane);
                HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.HISTORY_PAGE;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onHomeButtonClicked() {
        if (HomeScreenWidgets.currentDisplayPage != HomeScreenDisplays.MAIN_PAGE) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/homeContentsPane.fxml"));
                Pane newLoadedPane = loader.load();
                displayPane.getChildren().clear();
                displayPane.getChildren().add(newLoadedPane);
                HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.MAIN_PAGE;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadsButtonAction() {
        if(HomeScreenWidgets.currentDisplayPage != HomeScreenDisplays.DOWNLOADS_SCREEN){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/downloadsScreen.fxml"));
                Pane newLoadedPane = loader.load();
                displayPane.getChildren().clear();
                displayPane.getChildren().add(newLoadedPane);
                HomeScreenWidgets.currentDisplayPage = HomeScreenDisplays.DOWNLOADS_SCREEN;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
