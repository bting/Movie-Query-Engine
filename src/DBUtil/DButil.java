package DBUtil;

import java.sql.Connection;
import java.sql.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Ting on 3/5/17.
 */

public class DButil {
    public static Connection openConnection() {
        Connection connect = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String host = "localHost";
            String port = "1521";
            String userName = "baoting";
            String password = "0820";
            String serviceName = "orcl";

            // construct the JDBC URL
            String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + serviceName;
            connect = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("Connection can not be opened: " + e.getMessage());
        }
        return connect;
    }

    public static void closeConnection(Connection connect) {
        try {
            connect.close();
        } catch (SQLException e) {
            System.out.println("Connection can not be closed: " + e.getMessage());
        }
    }

    public static void deleteAllData(Connection connect) {
        try {
            System.out.println("Delete data from tables.");
            deleteData(connect, "movies");
            System.out.println("Delete data from movies successfully.");
            deleteData(connect, "movie_genres");
            System.out.println("Delete data from movie_genres successfully.");
            deleteData(connect, "movie_directors");
            System.out.println("Delete data from movie_directors successfully.");
            deleteData(connect, "movie_actors");
            System.out.println("Delete data from movie_actors successfully.");
            deleteData(connect, "movie_countries");
            System.out.println("Delete data from movie_countries successfully.");
            deleteData(connect,"tags");
            System.out.println("Delete data from tags successfully.");
            deleteData(connect, "movie_tags");
            System.out.println("Delete data from movie_tags successfully.");
            deleteData(connect, "ratedMovies");
            System.out.println("Delete data from ratedMovies successfully.");
            deleteData(connect, "movie_locations");
            System.out.println("Delete data from movie_locations successfully.");
            deleteData(connect, "taggedMovies");
            System.out.println("Delete data from taggedMovies successfully.");
        } catch (SQLException e) {
            System.out.println("Error occurs: " + e.toString());
        }
    }

    public static void deleteData(Connection connect, String tableName) throws SQLException {
        Statement prestate = connect.createStatement();
        prestate.execute("DELETE FROM " + tableName);
        prestate.close();
    }

    public static ResultSet fetchAllGenres(Connection connect) throws SQLException {
        String sql = "SELECT DISTINCT genres FROM movie_genres WHERE genres IS NOT NULL ORDER BY genres";
        System.out.println(String.format("fetchAllGenres: %s", sql));
        Statement prestate = connect.createStatement();
        return prestate.executeQuery(sql);
    }

    public static ResultSet fetchAllCountries(Connection connect) throws SQLException {
        String sql = "SELECT DISTINCT countries FROM movie_countries WHERE Country IS NOT NULL ORDER BY Country";
        System.out.println(String.format("fetchAllCountries: %s", sql));
        Statement prestate = connect.createStatement();
        return prestate.executeQuery(sql);
    }

    public static ResultSet getNumberOfGenres(Connection connect) throws SQLException {
        String sql = "SELECT COUNT(*) AS genres_count FROM (SELECT DISTINCT Genres FROM movie_genres WHRER Genres IS NOT NULL)";
        //System.out.println("GetNumberOfGenres: %s", sql);
        Statement prestate = connect.createStatement();
        return prestate.executeQuery(sql);
    }

    public static ResultSet getNumberOfCountries(Connection connect) throws SQLException {
        String sql = "SELECT COUNT(*) AS countries_count FROM (SELECT DISTINCT country FROM movie_countries WHERE country IS NOT NULL)";
        //System.out.println("GetNumberOfCountries: %s", sql);
        Statement prestate = connect.createStatement();
        return prestate.executeQuery(sql);
    }

    public static ResultSet fetchAllFilmCountry(Connection connect) throws SQLException {
        String sql = "SELECT DISTINCT location1\n" + "FROM movie_locations";
        Statement prestate = connect.createStatement();
        return prestate.executeQuery(sql);
    }


    public static ResultSet fetchCountryByGenres(Connection connect, Set<String> selectedGenres, String searchCondition) throws SQLException {
        StringBuilder sb = new StringBuilder(
                "SELECT DISTINCT mc.country " +
                        "FROM movie_countries mc, " +
                        "( SELECT mg.mid, LISTAGG(mg.genres, ',')" +
                        " WITHIN GROUP (ORDER BY mg.mid) AS genres" +
                        " FROM movie_genres mg " + "  GROUP BY mg.mid) mg2" +
                        " WHERE mc.country IS NOT NULL AND mc.mid = mg2.mid ");
        if (selectedGenres.size() != 0) {
            sb.append("AND ( ");
            int count = 0;
            for (String genre : selectedGenres) {
                if (count == 0) {
                    sb.append("mg2.genres LIKE '%").append(genre).append("%' ");
                } else {
                    sb.append(searchCondition).append(" mg2.genres LIKE '%").append(genre).append("%' ");
                }
                count++;
            }
            sb.append(")");
        }
        sb.append(" ORDER BY mc.country");
        System.out.println(String.format("fetchCountryByGenres: %s", sb.toString()));
        Statement prestate = connect.createStatement();
        return prestate.executeQuery(sb.toString());
    }

    public static ResultSet fetchLocationByGenreAndCountry(Connection connect, Set<String> selectedGenres,
                                                           Set<String> selectedCountries,
                                                           String searchCondition) throws SQLException {
        StringBuilder sb = new StringBuilder(
                "SELECT DISTINCT ml.location1" +
                        " FROM movie_countries mc, movie_locations ml, " +
                        " ( SELECT mg.mid, LISTAGG(mg.genres, ',')" +
                        " WITHIN GROUP (ORDER BY mg.mid) AS genres" +
                        " FROM movie_genres mg" +
                        " GROUP BY mg.mid) mg2" +
                        " WHERE ml.location1 IS NOT NULL AND mc.mid = mg2.mid AND mc.mid = ml.mid ");

        if (selectedGenres.size() != 0) {
            sb.append("AND ( ");
            int count1 = 0;
            for (String genre : selectedGenres) {
                if (count1 == 0) {
                    sb.append("mg2.genres LIKE '%").append(genre).append("%' ");
                } else {
                    sb.append(searchCondition).append(" mg2.genres LIKE '%").append(genre).append("%' ");
                }
                count1++;
            }
            sb.append(") ");
        }

        if (selectedCountries.size() != 0) {
            sb.append("AND ( ");
            int count2 = 0;
            for (String country : selectedCountries) {
                if (count2 == 0) {
                    sb.append("mc.country LIKE '%").append(country).append("%' ");
                } else {
                    sb.append(searchCondition).append(" mc.country LIKE '%").append(country).append("%' ");
                }
                count2++;
            }
            sb.append(")");
        }
        sb.append(" ORDER BY ml.location1");
        System.out.println(String.format("fetchLocationByGenresAndCountry: %s", sb.toString()));
        Statement prestate = connect.createStatement();
        return prestate.executeQuery(sb.toString());
    }
}
