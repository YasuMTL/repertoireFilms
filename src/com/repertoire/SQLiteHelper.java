package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
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
        JTextArea result = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(result);
        result.setLineWrap(true);
        result.setWrapStyleWord(true);
        scrollPane.setPreferredSize( new Dimension( 900, 500 ) );
        result.append("filmID / Titre Original / Année / Réalisateur / Autre titre / Pays / Chemin du fichier");

        //String[] columnNames = {"filmID", "Titre Original", "Année", "Réalisateur", "Autre titre", "Pays", "Chemin du fichier"};
        //Object[][] data;
        //Vector<Vector<Object>> data = new Vector<>();
        //http://www.java2s.com/Tutorial/Java/0240__Swing/publicJTableVectorrowDataVectorcolumnNames.htm
        int counter = 0;

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                //Object[counter][0] = new Object{};
                //data.get(counter++).add({});

                result.append("\n" + rs.getString("filmID") + " "
                        + rs.getString("original_title") + " "
                        + rs.getInt("year") + " "
                        + rs.getString("director") + " "
                        + rs.getString("second_title") + " "
                        + rs.getString("country") + " "
                        + rs.getString("file_path") + " ");

                //Debug
                /*System.out.println(rs.getString("filmID") +  "\t" +
                    rs.getString("original_title") + "\t" +
                    rs.getInt("year") + "\t" +
                    rs.getString("director") + "\t" +
                    rs.getString("second_title") + "\t" +
                    rs.getString("country") + "\t" +
                    rs.getString("file_path")
                );*/
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        result.setFont(new Font("Courier", Font.PLAIN, 12));
        //JOptionPane.showMessageDialog(null, result, "Liste de tous les films", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(null, scrollPane, "Liste de tous les films", JOptionPane.PLAIN_MESSAGE);
    }//END of selectAll()

    public void searchByTitle(String title){
        String sql = "SELECT * "
                + "FROM films WHERE original_title LIKE ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, "%" + title + "%");
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("filmID") +  "\t" +
                        rs.getString("original_title") + "\t" +
                        rs.getInt("year") + "\t" +
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