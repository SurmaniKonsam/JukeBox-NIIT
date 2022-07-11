package Jukebox;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Songs {
    public static void listOfSongs() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = "Select songId,nameOfTheSong,duration,language from Songs";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("____________________________________________________________________________");
        System.out.println("SongID\t\t\t\tNameOfTheSong\t\t\tduration\t\t\tlanguage");
        System.out.println("_____________________________________________________________________________");

        while (rs.next()) {
            System.out.format("%d\t\t\t%20s%20s%20s", rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getString(4));
            System.out.println();
        }
    }

    public static void SongChoice(String songName) throws ClassNotFoundException, SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner take = new Scanner(System.in);
        int count = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = """
                select albumName,nameOfTheSong from songs s
                    join album al
                    on s.albumId = al.albumId
                    where albumName = ?    order by nameOfTheSong""";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, songName);
        ResultSet rs = ps.executeQuery();
        System.out.println("__________________________________________________________");
        System.out.println("songId\t\talbumName\t\t\t\t\tnameOfTheSong");
        System.out.println("__________________________________________________________");
        while (rs.next()) {
            count++;
            System.out.format("%d%20s%30s\n", count, rs.getString(1), rs.getString(2));
        }
        System.out.println("Select your song choice");
        String song = "";
        song += take.nextLine();
        //System.out.println("your select ::"+song);
        String querySong = "Select * from songs";
        Statement stmt = con.createStatement();
        ResultSet rsSong = stmt.executeQuery(querySong);
        while (rsSong.next()) {
            if (song.equalsIgnoreCase(rsSong.getString(2))) {
                try {
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
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
