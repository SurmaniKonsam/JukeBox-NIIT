package Jukebox;

import javax.sound.sampled.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Entry {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        System.out.println("Welcome to our JukeBox!!!");
        Scanner take = new Scanner(System.in).useDelimiter("\n");
        System.out.println("How would you want to avail our services");
        System.out.println("1. Listen to song");
        System.out.println("2. Listen to PodCaste");
        System.out.println("3. Create your own Playlist");
        System.out.println("Enter your choice please");
        int choice = 0;
        try {
            choice = take.nextInt();
        }catch (InputMismatchException e)
        {
            System.out.println("Input MisMatched");
        }
        switch (choice) {
            case 1 -> {
                System.out.println("Available genre in jukebox");
                Genre.genreList();
                System.out.println("Select your Genre");
                int genChoice = 0;
                try {
                    genChoice = take.nextInt();
                }catch (InputMismatchException e)
                {
                    System.out.println("Input mismatch");
                }
                switch (genChoice) {
                    case 1 -> {
                        Genre.RockList();
                        System.out.println("Select artistId of your choice");
                        int artistChoice = 0;
                        try {
                            artistChoice = take.nextInt();
                        }catch (InputMismatchException e)
                        {
                            System.out.println("Input mismatch");
                        }
                        Album.RockChoice(artistChoice);
                        if(artistChoice>2 || artistChoice<1)
                        {
                            break;
                        }
                        System.out.println("Select album of your choice");
                        String albumChoice = take.next();
                        if(albumChoice.equalsIgnoreCase("hybrid")||albumChoice.equalsIgnoreCase("meteora")
                        ||albumChoice.equalsIgnoreCase("fat lip")||albumChoice.equalsIgnoreCase("still waiting")) {
                            Songs.SongChoice(albumChoice);
                        }
                        else
                        {
                            System.out.println("wrong album input");
                        }
                    }

                    case 2 -> {
                        Genre.PopList();
                        System.out.println("Select artistId of your choice");
                        int popChoice = 0;
                        try {
                            popChoice = take.nextInt();
                        }catch (InputMismatchException e)
                        {
                            System.out.println("Input mismatch");
                        }
                        Album.PopChoice(popChoice);
                        if(popChoice>3||popChoice<1)
                        {
                            break;
                        }
                        System.out.println("Select album of your choice");
                        String albumChoice2 = take.next();
                        if(albumChoice2.equalsIgnoreCase("me")||albumChoice2.equalsIgnoreCase("love story")
                        ||albumChoice2.equalsIgnoreCase("mohabbat")||albumChoice2.equalsIgnoreCase("kamli")
                        ||albumChoice2.equalsIgnoreCase("nine track mind")||albumChoice2.equalsIgnoreCase("charlie")) {
                            //System.out.println(albumChoice2);
                            Songs.SongChoice(albumChoice2);
                        }
                        else
                        {
                            System.out.println("wrong album input");
                        }
                    }
                    case 3 -> {
                        Genre.MetalList();
                        System.out.println("Select artistId of your choice");
                        int metalChoice = 0;
                        try {
                            metalChoice = take.nextInt();
                        }catch (InputMismatchException e)
                        {
                            System.out.println("Input mismatch");
                        }
                        Album.MetalChoice(metalChoice);
                        if(metalChoice<1||metalChoice>3)
                        {
                            break;
                        }
                        System.out.println("Select album of your choice");
                        String albumChoice3 = take.next();
                        if(albumChoice3.equalsIgnoreCase("Last resort")||albumChoice3.equalsIgnoreCase("Balling apart")
                        ||albumChoice3.equalsIgnoreCase("Dear god")||albumChoice3.equalsIgnoreCase("seize the day")
                        ||albumChoice3.equalsIgnoreCase("Tears dont fall")||albumChoice3.equalsIgnoreCase("walking the demon")) {
                            //System.out.println(albumChoice3);
                            Songs.SongChoice(albumChoice3);
                        }
                        else
                        {
                            System.out.println("wrong album input");
                        }
                    }


                    case 4 -> {
                        Genre.BollywoodList();
                        System.out.println("Select artistId of your choice");
                        int bollywoodChoice = 0;
                        try {
                            bollywoodChoice = take.nextInt();
                        }catch (InputMismatchException e)
                        {
                            System.out.println("Input mismatch");
                        }
                        if(bollywoodChoice<1||bollywoodChoice>3)
                        {
                            break;
                        }
                        Album.BollywoodChoice(bollywoodChoice);
                        System.out.println("Select album of your choice");
                        String albumChoice4 = take.next();
                        //System.out.println(albumChoice4);
                        if(albumChoice4.equalsIgnoreCase("Papa mere papa")||albumChoice4.equalsIgnoreCase("Allah Allah")
                        ||albumChoice4.equalsIgnoreCase("agar tum sath ho")||albumChoice4.equalsIgnoreCase("tum hi ho")
                        ||albumChoice4.equalsIgnoreCase("tum se hi")||albumChoice4.equalsIgnoreCase("kun faya kun")) {
                            Songs.SongChoice(albumChoice4);
                        }else
                        {
                            System.out.println("wrong album input");
                        }
                    }
                    default -> System.out.println("No further genre available");
                }
            }//case 1 break;

            case 2 -> {
                System.out.println("Available PodCaster in the jukebox");
                PlaylistPodCasteDetails.PodCaster();
                System.out.println("Select your celebrity above::");
                String podCeleb = take.next();
                if(podCeleb.equalsIgnoreCase("Bill nye")||podCeleb.equalsIgnoreCase("BeerBiceps")
                ||podCeleb.equalsIgnoreCase("Joe Rogan")||podCeleb.equalsIgnoreCase("Fridman")
                ||podCeleb.equalsIgnoreCase("Fashion School")) {
                    PlaylistPodCasteDetails.PlayPodCaste(podCeleb);
                }else
                {
                    System.out.println("Wrong celeb input");
                }

            }//case 2 break

            case 3 -> {
                System.out.println("Are you an existing Customer yes/no?");
                String ans = take.next();
                if(ans.equalsIgnoreCase("yes")||ans.equalsIgnoreCase("no")) {
                    Playlist.PlayListUser(ans);
                }
                else
                {
                    System.out.println("Wrong input");
                }
            }//case 3 break

            default -> System.out.println("no further option available");
        }

    }
}
