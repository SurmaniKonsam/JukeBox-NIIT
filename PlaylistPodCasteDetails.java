package Jukebox;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class PlaylistPodCasteDetails {
    public static void PodCaster() throws ClassNotFoundException, SQLException {
        int count = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = """
                select nameOfThePodCasteEpisode,PodCasterName from PodCaste\s
                    join
                    PodCastEpisodes
                    on PodCaste.podcasteId = PodCastEpisodes.podcasteId""";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        System.out.println("_____________________________________________________________________");
        System.out.println("podCasteId\t\tTopic\t\t\t\t\t\tPodCasterName");
        System.out.println("______________________________________________________________________");
        while(rs.next())
        {
            count++;
            System.out.format("%d%24s%30s\n",count,rs.getString(1),rs.getString(2));
            System.out.println();
            //System.out.println(count+"\t\t\t"+rs.getString(1)+"\t\t\t"+rs.getString(2));
        }
    }
    public static void PlayPodCaste(String choice) throws ClassNotFoundException, SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner take = new Scanner(System.in).useDelimiter("\n");
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String podQuery = "select * from PodCaste";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(podQuery);
        while(rs.next())
        {
            if(rs.getString(4).equalsIgnoreCase(choice))
            {
                File file = new File(rs.getString(5));
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                String response = "";
                while(!response.equals("Q"))
                {
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
    }
}
