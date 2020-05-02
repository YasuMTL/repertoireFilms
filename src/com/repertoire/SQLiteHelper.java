package com.repertoire;

import java.sql.*;

public class SQLiteHelper {
    private String URL;

    public SQLiteHelper(){
        //
    }

    public void createNewDB(String fileName){
        //final String URL
        URL = "jdbc:sqlite:C:\\Users\\Yasunari\\Desktop\\" + fileName;

        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            System.out.println("A new database has been created.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//End createNewDB()

    public void createNewTable() {
        // SQLite connection string
        //String url = "jdbc:sqlite:C://sqlite/db/tests.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS films (\n"
                + "	filmID integer PRIMARY KEY,\n"
                + "	original_title text NOT NULL,\n"
                + "	year integer NOT NULL,\n"
                + "	director text NOT NULL,\n"
                + "	second_title text NOT NULL,\n"
                + "	country text NOT NULL,\n"
                + "	file_path text NOT NULL,\n"
                + "	capacity real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//END of createNewTable()

    public void insert(String originalTitle, int year, String director, String secondTitle, String country, String filePath) {
        String sql = "INSERT INTO films(original_title, year, director, second_title, country, file_path) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, originalTitle);
            pstmt.setInt(2, year);
            pstmt.setString(3, director);
            pstmt.setString(4, secondTitle);
            pstmt.setString(5, country);
            pstmt.setString(6, filePath);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAll(){
        String sql = "SELECT * FROM films";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("filmID") +  "\t" +
                    rs.getInt("year") + "\t" +
                    rs.getString("original_title") + "\t" +
                    rs.getString("director") + "\t" +
                    rs.getString("second_title") + "\t" +
                    rs.getString("country") + "\t" +
                    rs.getString("file_path")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
