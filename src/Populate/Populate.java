/**
 * Created by Ting on 3/4/17.
 */
package Populate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.io.File;

import DBUtil.*;

public class Populate {
    public static void main(String[] args) throws IOException {
        Populate test = new Populate();
        test.run();
    }

    public void run() {
        Connection connect = null;
        try {
            connect = DButil.openConnection();
            insertAllData(connect);
        } catch (Exception e) {
            System.err.println("Error occurs when insert data: " + e.getMessage());
        } finally {
            DButil.closeConnection(connect);
        }
    }

    private void insertAllData(Connection connect) throws SQLException, IOException {
        movies(connect);
        System.out.println("Insert movies data finished.");
        movieGenres(connect);
        System.out.println("Insert movieGenres data finished.");
        director(connect);
        System.out.println("Insert director data finished.");
        movieActors(connect);
        System.out.println("Insert movieActors data finished.");
        movieCountries(connect);
        System.out.println("Insert movieCountries data finished.");
        ratedMovies(connect);
        System.out.println("Insert ratedMovies data finished.");
        tags(connect);
        System.out.println("Insert tags data finished.");
        movieTags(connect);
        System.out.println("Insert movieTags data finished.");
        taggedMovies(connect);
        System.out.println("Insert taggedMovies data finished.");
        movieLocations(connect);
        System.out.println("Insert movieLocations data finished.");
    }

    // get data from movies.dat
    private void movies(Connection c) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/movies.dat");
        PreparedStatement prestate = c.prepareStatement("INSERT INTO movies VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        int[] index = {0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null){
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                for (int i = 1; i < index.length; i++) {
                    if (strs[index[i]].equals("\\N")) {
                        prestate.setObject(i, null);
                        continue;
                    }
                    if (i == 1 || i == 6 || i == 9 || i == 14 || i == 19) {
                        int valInt = Integer.parseInt(strs[index[i]]);
                        prestate.setInt(i, valInt);
                    } else if (i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 21) {
                        prestate.setString(i, strs[index[i]]);
                    } else {
                        double valDouble = Double.parseDouble(strs[index[i]]);
                        prestate.setDouble(i, valDouble);
                    }
                }
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from movie_genres.dat
    private void movieGenres(Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/movie_genres.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO movie_genres VALUES(?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null){
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                prestate.setInt(1, Integer.parseInt(strs[0]));
                prestate.setString(2, strs[1]);
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from movie_directors.dat
    private void director(Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/movie_directors.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO movie_directors VALUES(?, ?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null){
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                prestate.setInt(1, Integer.parseInt(strs[0]));
                prestate.setString(2, strs[1]);
                prestate.setString(3, strs[2]);
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from movie_actors.dat
    private void movieActors(Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/movie_actors.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO movie_actors VALUES(?, ?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null){
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                prestate.setInt(1, Integer.parseInt(strs[0]));
                prestate.setString(2, strs[1]);
                prestate.setString(3, strs[2]);
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from movie_countries.dat
    private void movieCountries(Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/movie_countries.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO movie_countries VALUES(?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                prestate.setInt(1, Integer.parseInt(strs[0]));
                if (strs.length == 2) {
                    prestate.setString(2, strs[1]);
                } else {
                    prestate.setString(2, null);
                }
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from user_ratedmovies-timestamps.dat
    private void ratedMovies (Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/user_ratedmovies-timestamps.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO ratedMovies VALUES(?, ?, ?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                prestate.setInt(1, Integer.parseInt(strs[0]));
                prestate.setInt(2, Integer.parseInt(strs[1]));
                prestate.setDouble(3, Double.parseDouble(strs[2]));
                prestate.setTimestamp(4, new Timestamp(Long.parseLong(strs[3])));
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from tags.dat
    private void tags (Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/tags.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO tags VALUES(?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                prestate.setInt(1, Integer.parseInt(strs[0]));
                prestate.setString(2, strs[1]);
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from movie_tags.dat
    private void movieTags (Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/movie_tags.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO movie_tags VALUES(?, ?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                prestate.setInt(1, Integer.parseInt(strs[0]));
                prestate.setInt(2, Integer.parseInt(strs[1]));
                prestate.setInt(3, Integer.parseInt(strs[2]));
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from user_taggedmovies-timestamps.dat
    private void taggedMovies (Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/user_taggedmovies-timestamps.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO taggedMovies VALUES(?, ?, ?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                for (int i = 0; i < strs.length; i++) {
                    if (i == 3) {
                        prestate.setTimestamp(4, new Timestamp(Long.parseLong(strs[i])));
                    } else {
                        prestate.setInt(i+1, Integer.parseInt(strs[i]));
                    }
                }
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }

    // get data from movie_locations.dat
    private void movieLocations (Connection connect) throws SQLException, IOException {
        File data = new File("/Users/Ting/Documents/DBHW/data/movie_locations.dat");
        PreparedStatement prestate = connect.prepareStatement("INSERT INTO movie_locations VALUES(?, ?, ?, ?, ?)");
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                // split every row into string[]
                String[] strs = (line.trim()).split("\t");
                prestate.setInt(1, Integer.parseInt(strs[0]));
                for (int i = 1; i < 5; i++) {
                    if (i < strs.length) {
                        prestate.setString(i + 1, strs[i]);
                    } else {
                        prestate.setString(i + 1, null);
                    }
                }
                prestate.executeUpdate();
            }
        }
        prestate.close();
    }
}


