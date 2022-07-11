package Jukebox;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

public class Playlist {
    static int emailCount = 0;
    static int passwordCount = 0;
    static int userNameCount = 0;
    static int songPlaylistUserId = 0;
    static int podcastPlaylistUserId = 0;
    static int existingUserSong = 0;
    static int existingUserPodcast = 0;
    public static void PlayListUser(String ans) throws ClassNotFoundException, SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        Scanner take = new Scanner(System.in).useDelimiter("\n");
        Predicate<String> predict = (t) -> t.equalsIgnoreCase("yes");//predicate interface used.
        if (predict.test(ans)) {

            String songPlaylist = "Insert into PlayListSongDetail(PlaylistCreatedOn,songId,userId) " +
                    "values (?,?,?)";

            String podcastList = "Insert into playListPodCasteDetails(playListCreatedOn,podCasteId,userId) " +
                    "values(?,?,?)";

            PreparedStatement addingPodCast = con.prepareStatement(podcastList);
            PreparedStatement addingSong = con.prepareStatement(songPlaylist);

            System.out.println("Enter your userId");
            int userId = 0;
            try {
                userId = take.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input Mismatched");
            }
            System.out.println("Enter your password");
            String password = take.next();
            String query = "Select * from User";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int UseId = 0;
            String uniquePassword = "";
            while (rs.next()) {
                if (userId == rs.getInt(1)) {
                    UseId = rs.getInt(1);
                    //added userId to the song playlist ##1
                    addingSong.setInt(3, rs.getInt(1));
                    //added userId to the podCastPlaylist  ##3
                    addingPodCast.setInt(3, rs.getInt(1));
                    uniquePassword = rs.getString(4);
                    break;
                }
            }
            int finalUseId = UseId;
            Predicate<Integer> predictUser = (t) -> t.equals(finalUseId);
            if (predictUser.test(userId)) {
                if (uniquePassword.equalsIgnoreCase(password)) {
                    existingUserSong = userId;
                    existingUserPodcast = userId;
                    System.out.println("yes you are our valid customer");
                    System.out.println("Do you want to add song sir/mam yes/no?");
                    String answer = take.next();
                    if (answer.equalsIgnoreCase("yes")) {
                        System.out.println("Which playlist do you want to add of?");
                        System.out.println("1. Songs");
                        System.out.println("2. Podcaste");
                        int choice = 0;
                        try {
                            choice = take.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Input mismatched");
                        }
                        switch (choice) {
                            //to add song to the playlistSong.
                            case 1 -> {
                                System.out.println("These are your Song Playlist");
                                Songs.listOfSongs();
                                System.out.println("Select songId from above that you want to create");
                                int SongId = 0;
                                try {
                                    SongId = take.nextInt();
                                }catch (InputMismatchException e) {
                                    System.out.println("Input mismatched");
                                }
                                    // String songPlaylist = "Insert into PlayListSongDetail(PlaylistCreatedOn,songId,userId) " +
                                    // "values (?,?,?)";
                                    //song added to the songPlaylist ##2
                                    addingSong.setInt(2, SongId);

                                    //added on date/time. ##3
                                    LocalDateTime timeObj = LocalDateTime.now();
                                    String time = "" + timeObj;
                                    addingSong.setString(1, time);

                                int checkSongPlaylist = 0;
                                try {
                                    checkSongPlaylist = addingSong.executeUpdate();
                                } catch (SQLIntegrityConstraintViolationException e) {
                                    System.out.println(e.getMessage());
                                }
                                if (checkSongPlaylist == 1) {
                                    System.out.println("Song playlist Successfully updated");
                                    System.out.println("These are your playlist sir/mam of userId::"+userId);
                                    Playlist.SongPlayList();
                                } else {
                                    System.out.println("Wrong in the query");
                                }
                            }


                            //to add podcast to the podcast playlist.
                            case 2 -> {
                                System.out.println("these are your list of podCaste");
                                PlaylistPodCasteDetails.PodCaster();
                                System.out.println("Select podCaster name from above than you want to add");
                                String podCaster = take.next();
                                String podQuery = "Select * from PodCaste";

                                //adding current date in the podcast playlist created. ##1
                                LocalDateTime timeObj1 = LocalDateTime.now();
                                String time2 = "" + timeObj1;
                                addingPodCast.setString(1, time2);
                                Statement podSt = con.createStatement();
                                ResultSet rsPod = podSt.executeQuery(podQuery);
                                while (rsPod.next()) {
                                    if (podCaster.equalsIgnoreCase(rsPod.getString(4))) {
                                        //adding podcast id to the podcast playlist. #2
                                        addingPodCast.setInt(2, rsPod.getInt(1));
                                    }
                                }
                                int checkPod = 0;
                                try {
                                    checkPod = addingPodCast.executeUpdate();
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                                if (checkPod == 1) {
                                    System.out.println("podCaste playlist successfully update");
                                    System.out.println("These are your podcast playlist created");
                                    Playlist.PodCastPlaylist();
                                } else {
                                    System.out.println("not updated");
                                }
                            }
                            default -> System.out.println("Wrong input");
                        }
                    }
                    else
                    {
                        System.out.println("Do you want to listen to your playlist yes/no?");
                        String answerPlay = take.next();
                        if(answerPlay.equalsIgnoreCase("yes"))
                        {
                            System.out.println("Which playlist do you want to hear of?");
                            System.out.println("1. Song Playlist");
                            System.out.println("2. PodCast playlist");
                            int choice = 0;
                            try {
                                choice = take.nextInt();
                            }catch (InputMismatchException e)
                            {
                                System.out.println("Input mismatched");
                            }
                            System.out.println("Here is your selected playlist");
                            switch (choice) {
                                case 1 -> Playlist.SongPlayList();
                                case 2 -> Playlist.PodCastPlaylist();
                            }
                        }
                        else
                        {
                            System.out.println("thank you for visiting jukebox!!!");
                        }
                    }
                } else {
                    System.out.println("Either you haven't created account with jukebox or your password/user_id is wrong");
                }
            }
        }

            //if new user
            else {
                String insertQuery = "Insert into user(userId,name,emailId,password) values (?,?,?,?)";
                PreparedStatement userUpdate = con.prepareStatement(insertQuery);

                //creating userid
                boolean idCheck;
                int idCount = 0;
                do {
                    if (idCount > 4) {
                        System.out.println("You have exhausted your limit");
                        break;
                    }
                    System.out.println("Please create your userId");
                    String userId2 = take.next();
                    //a user id will be created here which will be inserted both in Podcast playlist and song playlist.
                    int id = Integer.parseInt(userId2);
                    //so id here will go inside the podcast and song playlist table.
                    //let's first put value into song playlist table.
                    //for song playlist id == userId of song playlist.
                    String idValidation = "[1-9][0-9][0-9]";
                    idCheck = userId2.matches(idValidation);
                    if (idCheck) {
                        System.out.println("Valid userId");
                        System.out.println("Entering value into the table");
                        songPlaylistUserId = id;
                        podcastPlaylistUserId = id;
                        userUpdate.setInt(1, id);

                        //also need to enter value into the song playlist table.
                        System.out.println();
                        boolean userCheck;
                        do {
                            if (userNameCount > 4) {
                                System.out.println("You have exhausted your limit");
                                break;
                            }
                            System.out.println("Please enter your name");
                            String name = take.next();
                            String regexName = "^[A-Za-z]\\w{5,29}$";
                            userCheck = name.matches(regexName);
                            if (userCheck) {
                                System.out.println("You have entered valid username");
                                System.out.println("Entering value into the table");
                                userUpdate.setString(2, name);
                                System.out.println();
                                boolean result;
                                do {
                                    System.out.println("Please Create your emailId");
                                    String emailId = take.next();
                                    String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                                    result = emailId.matches(regex);
                                    if (result) {
                                        if (emailCount > 4) {
                                            System.out.println("You have exhausted your limit");
                                            break;
                                        }
                                        System.out.println("Given email-id is valid");
                                        System.out.println("Entering value into the table");
                                        userUpdate.setString(3, emailId);
                                        System.out.println();
                                        System.out.println("Please enter your password");
                                        String password2 = "^(?=.*[0-9])"
                                                + "(?=.*[a-z])(?=.*[A-Z])"
                                                + "(?=.*[@#$%^&+=])"
                                                + "(?=\\S+$).{8,20}$";
                                        System.out.println();
                                        boolean checkPassword;
                                        do {
                                            if (passwordCount > 4) {
                                                System.out.println("You have exhausted your limit");
                                                break;
                                            }
                                            System.out.println("enter your password");
                                            String enteredPassword = take.next();
                                            checkPassword = enteredPassword.matches(password2);
                                            if (checkPassword) {
                                                System.out.println("Enter password is correct");
                                                System.out.println("Entering value into the table");
                                                userUpdate.setString(4, enteredPassword);
                                                System.out.println();
                                            } else {
                                                passwordCount++;
                                                System.out.println("Re-enter your password");
                                                System.out.println();
                                            }
                                        } while (!checkPassword);

                                    } else {
                                        emailCount++;
                                        System.out.println("Given email-id is not valid");
                                        System.out.println();
                                    }
                                } while (!result);

                            } else {
                                userNameCount++;
                                System.out.println("Re-enter your username");
                                System.out.println();
                            }
                        } while (!userCheck);

                    } else {
                        System.out.println("Re-enter your user-id");
                        System.out.println();
                        idCount++;
                    }
                } while (!idCheck);

                int check = 0;
                try {
                    check = userUpdate.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                if (check == 1) {
                    System.out.println("table updated successfully");
                    System.out.println("Dear user you are now eligible to create your own playlist::");
                    System.out.println("Which playlist do you want to create");
                    System.out.println("1. Songs");
                    System.out.println("2. PodCast");
                    int choice = 0;
                    try {
                        choice = take.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Input mismatched");
                    }

                    //now switch operation to create playlist for songPlaylist or podcastPlaylist.
                    switch (choice) {
                        case 1 -> {
                            String songPlayUpdate = "Insert into PlayListSongDetail(userId,playlistCreatedOn,songId)" +
                                    "values (?,?,?)";
                            PreparedStatement songUpdate = con.prepareStatement(songPlayUpdate);
                            songUpdate.setInt(1, songPlaylistUserId);
                            System.out.println("These are your list of songs");
                            Songs.listOfSongs();
                            System.out.println();
                            System.out.println("Select songId from above that you want to create");
                            int SongId;
                            try {
                                SongId = take.nextInt();
                                songUpdate.setInt(3, SongId);
                            } catch (InputMismatchException e) {
                                System.out.println("Input mismatched");
                            }

                            //entering song added dated.
                            LocalDateTime timeObj = LocalDateTime.now();
                            String time = "" + timeObj;
                            songUpdate.setString(2, time);
                            int checkSongPlaylist = 0;
                            try {
                                checkSongPlaylist = songUpdate.executeUpdate();
                            } catch (SQLIntegrityConstraintViolationException e) {
                                System.out.println(e.getMessage());
                            }
                            if (checkSongPlaylist == 1) {
                                System.out.println("Song playlist Successfully updated");
                            } else {
                                System.out.println("Wrong in the query");
                            }
                        }
                        case 2 -> {
                            String podCastPlaylistUpdate = "Insert into playListPodCasteDetails(playListCreatedOn,podCasteId,userId)" +
                                    "values (?,?,?)";
                            PreparedStatement podUpdate = con.prepareStatement(podCastPlaylistUpdate);

                            //adding unique user id to the podcastplaylist table to map and join. #3
                            podUpdate.setInt(3, podcastPlaylistUserId);
                            System.out.println("these are your list of podCaste");
                            PlaylistPodCasteDetails.PodCaster();
                            //this will fetch  the podCaster id and then store in the podcast playlist.
                            System.out.println("Select podCaster name from above than you want to add");
                            String podCaster = take.next();
                            LocalDateTime timeObj1 = LocalDateTime.now();
                            String time2 = "" + timeObj1;
                            String podQuery = "Select * from PodCaste";

                            //adding current date in the podcast playlist created. ##1
                            podUpdate.setString(1, time2);
                            Statement podSt = con.createStatement();
                            ResultSet rsPod = podSt.executeQuery(podQuery);
                            while (rsPod.next()) {
                                if (podCaster.equalsIgnoreCase(rsPod.getString(4))) {
                                    //adding podcast id to the podcast playlist. #2
                                    podUpdate.setInt(2, rsPod.getInt(1));
                                }
                            }
                            int checkPod = 0;
                            try {
                                checkPod = podUpdate.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                            if (checkPod == 1) {
                                System.out.println("podCaste playlist successfully update");

                            } else {
                                System.out.println("not updated");
                            }
                        }
                        default -> System.out.println("Wrong input");
                    }
                } else {
                    System.out.println("Wrong query inserted");
                }
            }
        }

        //song playlist.
    public static void SongPlayList() throws ClassNotFoundException, SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner take = new Scanner(System.in).useDelimiter("\n");
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = """
                select  nameOfTheSong,language,playlistCreatedOn,userId from PlayListSongDetail pl
                    join
                    Songs s on
                    pl.songId = s.songId
                    where userId = ?""";
        //Statement stmt = con.createStatement();
        PreparedStatement songUpdate = con.prepareStatement(query);
        songUpdate.setInt(1, existingUserSong);
        //i need to write this down.
        ResultSet rs = songUpdate.executeQuery(); //execute query is used for basically select statement.
        System.out.println("_________________________________________________________________________________________________________________");
        System.out.println("\t\tNameOfTheSong\t\t\t\t\t\tLanguage\t\t\tDateCreatedOn\t\t\t\tUserID");
        System.out.println("__________________________________________________________________________________________________________________");
        int checkSong = 0;
        while (rs.next()) {
            checkSong = rs.getInt(4);
            System.out.format("%24s%28s%32s%13s",rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getInt(4));
            System.out.println();
        }
        if (checkSong != 0) {
            System.out.println("__________________________________________________________________________________________________________________");
            System.out.println("Enter the songName you want to hear::");
            String songName = take.next();
            String songQuery = "Select * from Songs";
            Statement songSt = con.createStatement();
            ResultSet rsSong = songSt.executeQuery(songQuery);
            while (rsSong.next()) {
                if (rsSong.getString(2).equalsIgnoreCase(songName)) {
                    File file = new File(rsSong.getString(7));
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    String response = "";
                    while (!response.equals("Q")) {
                        System.out.println("P = play, S = stop, L = loop, R = reset, Q = quite");
                        System.out.println("Enter your choice");
                        response = take.next();
                        response = response.toUpperCase();
                        switch (response) {
                            case ("P") -> clip.start();
                            case ("S") -> clip.stop();
                            case ("L") -> clip.loop(Clip.LOOP_CONTINUOUSLY);
                            case ("R") -> clip.setMicrosecondPosition(0);
                        }
                    }
                    clip.start();
                }
            }
        } else {
            System.out.println("No song added");
        }
    }
    public static void PodCastPlaylist() throws ClassNotFoundException, SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner take = new Scanner(System.in).useDelimiter("\n");
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        System.out.println("______________________________________________________________________________________________________________________");
        System.out.println("DateCreatedOn\t\t\t\t\t\t NameOfthePodCaste\t\t\tLanguage\t\t\t PodCaster\t\t\t userId");
        System.out.println("_______________________________________________________________________________________________________________________");
        String PodQuery = """
                select playListCreatedOn,nameOfThePodCasteEpisode,languageOfTheEpisode,PodCasterName,userId from PlayListPodCasteDetails pl
                    join podcaste pd
                    on pl.podcasteId = pd.podCasteId
                    where userId = ?""";
        //Statement stmt = con.createStatement();
        PreparedStatement podUpdate = con.prepareStatement(PodQuery);
        podUpdate.setInt(1, existingUserPodcast);
        ResultSet rs = podUpdate.executeQuery();
        int checkId = 0;
        while (rs.next()) {
            checkId = rs.getInt(5);
            System.out.format("%20s%20s%26s%20s%20s", rs.getString(1),
                    rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
            System.out.println();
        }
        //System.out.println(checkId);
        if (checkId != 0) {
            System.out.println("_______________________________________________________________________________________________________________________");
            System.out.println("Enter the PodCaster you want to hear::");
            String PodCaster = take.next();
            String songQuery = "Select * from PodCaste";
            Statement songSt = con.createStatement();
            ResultSet rsSong = songSt.executeQuery(songQuery);
            while (rsSong.next()) {
                if (rsSong.getString(4).equalsIgnoreCase(PodCaster)) {
                    File file = new File(rsSong.getString(5));
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    String response = "";
                    while (!response.equals("Q")) {
                        System.out.println("P = play, S = stop, L = loop, R = reset, Q = quite");
                        System.out.println("Enter your choice");
                        response = take.next();
                        response = response.toUpperCase();
                        switch (response) {
                            case ("P") -> clip.start();
                            case ("S") -> clip.stop();
                            case ("L") -> clip.loop(Clip.LOOP_CONTINUOUSLY);
                            case ("R") -> clip.setMicrosecondPosition(0);
                        }
                    }
                    clip.start();
                }
            }
        } else {
            System.out.println("No playlist added");
        }
    }
}
