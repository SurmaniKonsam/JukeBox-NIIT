package Jukebox;

import java.sql.*;

public class Genre {
    public static void genreList() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = "Select * from genre";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next())
        {
            System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2));
        }
    }
    public static void RockList() throws SQLException, ClassNotFoundException {
        int count = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = """
                select artistName,type from artist
                    join Genre
                    on artist.genreReference = genre.genreId
                    where type = 'rock'""";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("______________________________________________________________");
        System.out.println("ArtistId\t\tArtistName\t\tType");
        System.out.println("_______________________________________________________________");
        while(rs.next())
        {
            count++;
            if(count == 3)
            {
                break;
            }
            System.out.format("%d%20s%15s\n",count,rs.getString(1),rs.getString(2));
        }
    }


    public static void PopList() throws ClassNotFoundException, SQLException {
        int count = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = """
                select artistName,type from artist
                    join Genre
                    on artist.genreReference = genre.genreId
                    where type = 'Pop'""";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("______________________________________________________________");
        System.out.println("ArtistId\t\tArtistName\t\tType");
        System.out.println("_______________________________________________________________");
        while(rs.next())
        {
            count++;
            System.out.format("%d%20s%15s\n",count,rs.getString(1),rs.getString(2));
        }
    }


    public static void MetalList() throws ClassNotFoundException, SQLException {
        int count = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = """
                select artistName,type from artist
                    join Genre
                    on artist.genreReference = genre.genreId
                    where type = 'Metal'""";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("______________________________________________________________");
        System.out.println("ArtistId\t\t\t\tArtistName\t\tType");
        System.out.println("_______________________________________________________________");
        while(rs.next())
        {
            count++;
            System.out.format("%d%30s%15s\n",count,rs.getString(1),rs.getString(2));
        }
    }
    public static void BollywoodList() throws ClassNotFoundException, SQLException {
        int count = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukebox";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        String query = """
                select artistName,type from artist
                    join Genre
                    on artist.genreReference = genre.genreId
                    where type = 'Bollywood'""";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("______________________________________________________________");
        System.out.println("ArtistId\t\t\tArtistName\t\t\t\tType");
        System.out.println("_______________________________________________________________");
        while(rs.next())
        {
            count++;
            System.out.format("%d%30s%20s\n",count,rs.getString(1),rs.getString(2));
        }
    }

}
