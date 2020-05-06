package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
                + "	year integer,\n"
                + "	director text,\n"
                + "	second_title text,\n"
                + "	country text NOT NULL,\n"
                + "	file_path text,\n"
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

    public void insert(String originalTitle, String year, String director, String secondTitle, String country, String filePath) {
        String sql = "INSERT INTO films(original_title, year, director, second_title, country, file_path) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, originalTitle);
            //pstmt.setInt(2, year);
            pstmt.setString(2, year);
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

    public void searchByTitle(String titleOrSecondTitle, String year, String director, /*String second_title,*/ String country){

        JTextArea result = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(result);
        result.setLineWrap(true);
        result.setWrapStyleWord(true);
        scrollPane.setPreferredSize( new Dimension( 900, 500 ) );
        result.append("filmID / Titre Original / Année / Réalisateur / Autre titre / Pays / Chemin du fichier");

        String[] columns = {"filmID", "Titre Original", "Année", "Réalisateur", "Autre titre", "Pays", "Chemin du fichier"};

        String sql = "SELECT * "
                + "FROM films "
                + "WHERE 1 = 1 ";
        List<String> parameters = new ArrayList<>();

        if (!titleOrSecondTitle.equals("")){
            sql += "AND (original_title like ? OR second_title like ?) ";
            //parameters.add("originalTitle");
            parameters.add("title");
        }

        if (!year.equals("")){
            sql += "AND year like ? ";
            parameters.add("year");
        }

        if (!director.equals("")){
            sql += "AND director like ? ";
            parameters.add("director");
        }

        /*if (!second_title.equals("")){
            sql += "AND second_title like ? ";
            parameters.add("second_title");
        }*/

        if (!country.equals("")){
            sql += "AND country like ? ";
            parameters.add("country");
        }

        try (Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){

            int columnIndex = 0;

            // set the value
            //if (parameters.contains("originalTitle")){
            if (parameters.contains("title")){
                //pstmt.setString(++columnIndex, "%" + originalTitle + "%");
                //For title
                pstmt.setString(++columnIndex, "%" + titleOrSecondTitle + "%");
                //For second_title
                pstmt.setString(++columnIndex, "%" + titleOrSecondTitle + "%");
                System.out.println("titleOrSecondTitle = " + titleOrSecondTitle);
            }

            if (parameters.contains("year")){
                pstmt.setString(++columnIndex, "%" + year + "%");
                System.out.println("year = " + year);
            }

            if (parameters.contains("director")){
                pstmt.setString(++columnIndex, "%" + director + "%");
                System.out.println("director = " + director);
            }

            /*if (parameters.contains("second_title")){
                pstmt.setString(++columnIndex, "%" + second_title + "%");
            }*/

            if (parameters.contains("country")){
                pstmt.setString(++columnIndex, "%" + country + "%");
                System.out.println("country = " + country);
            }

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                result.append("\n" + rs.getString("filmID") + " "
                        + rs.getString("original_title") + " "
                        + rs.getString("year") + " "
                        + rs.getString("director") + " "
                        + rs.getString("second_title") + " "
                        + rs.getString("country") + " "
                        + rs.getString("file_path") + " ");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        result.setFont(new Font("Courier", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(null, scrollPane, "Liste des films cherchés", JOptionPane.PLAIN_MESSAGE);
    }
}