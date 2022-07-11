package Jukebox;

import java.sql.*;

public class Album {
    public static void RockChoice(int choice) throws ClassNotFoundException, SQLException {
        int count = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        switch (choice) {
            case 1 -> {
                String query = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Linkin Park'""";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("_________________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t " + "AlbumName");
                System.out.println("_________________________________________________________");
                while (rs.next()) {
                    count++;
                    System.out.format("%d%20s%15s\n", count, rs.getString(1), rs.getString(2));

                }
            }
            case 2 -> {
                String query1 = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Sum_41'""";
                Statement stmt2 = con.createStatement();
                ResultSet rs1 = stmt2.executeQuery(query1);
                System.out.println("____________________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t" + "AlbumName");
                System.out.println("_____________________________________________________________");
                while (rs1.next()) {
                    count++;
                    System.out.format("%d%20s%15s\n", count, rs1.getString(1), rs1.getString(2));

                }
            }
            default -> System.out.println("wrong choice");
        }
    }

    //pop choice
    public static void PopChoice(int choice) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        int count = 0;
        switch (choice) {
            case 1 -> {
                String query = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Taylor_Swift'""";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("_______________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t" + "AlbumName");
                System.out.println("_______________________________________________________");
                while (rs.next()) {
                    count++;
                    System.out.format("%d%20s%15s\n", count, rs.getString(1), rs.getString(2));

                }
            }
            case 2 -> {
                String query1 = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'SunidhiChauhan'""";
                Statement stmt2 = con.createStatement();
                ResultSet rs1 = stmt2.executeQuery(query1);
                System.out.println("_____________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t" + "AlbumName");
                System.out.println("______________________________________________________");
                while (rs1.next()) {
                    count++;
                    System.out.format("%d%20s%20s\n", count, rs1.getString(1), rs1.getString(2));

                }
            }
            case 3 -> {
                String query3 = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Charlie Puth'""";
                Statement stmt3 = con.createStatement();
                ResultSet rs2 = stmt3.executeQuery(query3);
                System.out.println("______________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t" + "AlbumName");
                System.out.println("______________________________________________________");
                while (rs2.next()) {
                    count++;
                    System.out.format("%d%20s%20s\n", count, rs2.getString(1), rs2.getString(2));

                }
            }
            default -> System.out.println("wrong choice");
        }
    }

    //metalChoice
    public static void MetalChoice(int choice) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        int count = 0;
        switch (choice) {
            case 1 -> {
                String query = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Papa_roach'""";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("___________________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t" + "AlbumName");
                System.out.println("___________________________________________________________");
                while (rs.next()) {
                    count++;
                    System.out.format("%d%30s%15s\n", count, rs.getString(1), rs.getString(2));

                }
            }
            case 2 -> {
                String query1 = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'AVX'""";
                Statement stmt2 = con.createStatement();
                ResultSet rs1 = stmt2.executeQuery(query1);
                System.out.println("______________________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t" + "AlbumName");
                System.out.println("_______________________________________________________________");
                while (rs1.next()) {
                    count++;
                    System.out.format("%d%20s%24s\n", count, rs1.getString(1), rs1.getString(2));

                }
            }
            case 3 -> {
                String query3 = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Bullet for my valentine'""";
                Statement stmt3 = con.createStatement();
                ResultSet rs2 = stmt3.executeQuery(query3);
                System.out.println("_________________________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t\t" + "AlbumName");
                System.out.println("__________________________________________________________________");
                while (rs2.next()) {
                    count++;
                    System.out.format("%d%30s%24s\n", count, rs2.getString(1), rs2.getString(2));

                }
            }
            default -> System.out.println("wrong choice");
        }
    }

    //bollywood choice
    public static void BollywoodChoice(int choice) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        int count = 0;
        switch (choice) {
            case 1 -> {
                String query = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Sonu nigam'""";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("______________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t" + "AlbumName");
                System.out.println("_______________________________________________________");
                while (rs.next()) {
                    count++;
                    System.out.format("%d%20s%20s\n", count, rs.getString(1), rs.getString(2));

                }
            }
            case 2 -> {
                String query1 = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Arjit Singh'""";
                Statement stmt2 = con.createStatement();
                ResultSet rs1 = stmt2.executeQuery(query1);
                System.out.println("_____________________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t\t\t" + "AlbumName");
                System.out.println("______________________________________________________________");
                while (rs1.next()) {
                    count++;
                    System.out.format("%d%20s%30s\n", count, rs1.getString(1), rs1.getString(2));

                }
            }
            case 3 -> {
                String query3 = """
                        select artistName,albumName from Album al
                            join Artist a
                            on a.artistID = al.ArtistReference
                            where artistName = 'Mohit chauhan'""";
                Statement stmt3 = con.createStatement();
                ResultSet rs2 = stmt3.executeQuery(query3);
                System.out.println("__________________________________________________________________");
                System.out.println("albumId\t\tArtist Name" + "\t\t\t" + "AlbumName");
                System.out.println("__________________________________________________________________");
                while (rs2.next()) {
                    count++;
                    System.out.format("%d%20s%20s\n", count, rs2.getString(1), rs2.getString(2));

                }
            }
            default -> System.out.println("wrong choice");
        }
    }
}
