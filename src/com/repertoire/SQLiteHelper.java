package com.repertoire;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.repertoire.Main.dbName;

public class SQLiteHelper {
    private final String URL;
    public SQLiteHelper(String dbName){
        URL = "jdbc:sqlite:E:\\Films Alexe\\Répertoire\\" + dbName;
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

    public void createNewDB(){
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = this.connect();
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

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//END of createNewTable()

    public void createNewTableForColumnTitles() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS titles (\n"
                + "	filmID text NOT NULL,\n"
                + "	original_title text NOT NULL,\n"
                + "	year text NOT NULL,\n"
                + "	director text NOT NULL,\n"
                + "	second_title text NOT NULL,\n"
                + "	country text NOT NULL,\n"
                + "	file_path text NOT NULL,\n"
                + "	capacity real\n"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//END of createNewTableForColumnTitles()

    public void backUpDB() throws IOException {
        FileInputStream fileIn = new FileInputStream("E:\\Films Alexe\\Répertoire\\" + dbName);
        FileOutputStream fileOut = new FileOutputStream("E:\\Films Alexe\\Répertoire\\" + "backup_" + dbName);

        byte[] buffer = new byte[256];
        int len;

        // Read until the end of the file (fileIn) and write
        while((len = fileIn.read(buffer)) != -1){
            fileOut.write(buffer);
        }

        //write to the file (fileOut)
        fileOut.flush();

        fileOut.close();
        fileIn.close();
    }

    public void addColumnTitles(String[] columnTitles) {
        //Need to add "id" too!
        String sqlAdd = "INSERT INTO titles (filmId, original_title, year, director, second_title, country, file_path) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlAdd))
        {
            int index = 1;
            for (String column : columnTitles) {
                pstmt.setString(index++, column);
            }

            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String[] getColumnTitles(){
        String[] columnTitles = new String[7];
        String sql = "SELECT * FROM titles";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();

            columnTitles[0] = rs.getString("filmID");
            columnTitles[1] = rs.getString("original_title");
            columnTitles[2] = rs.getString("year");
            columnTitles[3] = rs.getString("director");
            columnTitles[4] = rs.getString("second_title");
            columnTitles[5] = rs.getString("country");
            columnTitles[6] = rs.getString("file_path");


        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vous avez besoin de charger un fichier CSV !");
        }

        return columnTitles;
    }

    public int getNumLines(){
        int numLines = 0;
        String sql = "SELECT * FROM films";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            ResultSet rs  = pstmt.executeQuery();

            // count all the records in the films table
            while (rs.next()) {
                numLines++;
            }
        }catch (SQLException e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Vous avez besoin de charger un fichier CSV !");
        }

        return numLines;
    }

    public void addOneFilm(String originalTitle, String year, String director, String secondTitle, String country, String filePath) {
        String sqlAdd = "INSERT INTO films (original_title, year, director, second_title, country, file_path) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlAdd))
        {
            pstmt.setString(1, originalTitle);
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

    public void removeOneFilm(String filmIdSelected){
        String sqlUpdate = "DELETE FROM films WHERE filmID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate))
        {
            // set the value
            pstmt.setInt(1, Integer.parseInt(filmIdSelected));
            System.out.println("filmIdSelected = " + filmIdSelected);
            // update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearUpTable(){
        String sqlUpdate = "DELETE FROM films";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate))
        {
            // update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyOneFilm(String filmIdSelected, String originalTitle, String year,
                              String director, String secondTitle,
                              String country, String filePath){
        String sqlUpdate = "UPDATE films SET original_title = ?";

        List<String> parameters = new ArrayList<>();

        sqlUpdate += ", year = ? ";
        if (!year.equals("")){
            parameters.add("year");
        }else{
            parameters.add("yearEmpty");
        }

        sqlUpdate += ", director = ? ";
        if (!director.equals("")){
            parameters.add("director");
        }else{
            parameters.add("directorEmpty");
        }

        sqlUpdate += ", second_title = ? ";
        if (!secondTitle.equals("")){
            parameters.add("secondTitle");
        }else{
            parameters.add("secondTitleEmpty");
        }

        sqlUpdate += ", country = ? ";
        if (!country.equals("")){
            parameters.add("country");
        }else{
            parameters.add("countryEmpty");
        }

        sqlUpdate += ", file_path = ? ";
        if (!filePath.equals("")){
            parameters.add("filePath");
        }else{
            parameters.add("filePathEmpty");
        }

        sqlUpdate += " WHERE filmID = ? ";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdate))
        {
            //first parameter = original_title
            int columnIndex = 1;

            // set the value
            pstmt.setString(columnIndex, originalTitle);
            System.out.println("originalTitle = " + originalTitle);

            if (parameters.contains("year")){
                pstmt.setString(++columnIndex, year);
                System.out.println("year = " + year);
            }else if (parameters.contains("yearEmpty")){
                pstmt.setString(++columnIndex, "");
                System.out.println("year = \" \"");
            }

            if (parameters.contains("director")){
                pstmt.setString(++columnIndex, director);
                System.out.println("director = " + director);
            }else if (parameters.contains("directorEmpty")){
                pstmt.setString(++columnIndex, "");
                System.out.println("director = \" \"");
            }

            if (parameters.contains("secondTitle")){
                pstmt.setString(++columnIndex, secondTitle);
                System.out.println("secondTitle = " + secondTitle);
            }else if (parameters.contains("secondTitleEmpty")){
                pstmt.setString(++columnIndex, "");
                System.out.println("secondTitle = \" \"");
            }

            if (parameters.contains("country")){
                pstmt.setString(++columnIndex, country);
                System.out.println("country = " + country);
            }else if (parameters.contains("countryEmpty")){
                pstmt.setString(++columnIndex, "");
                System.out.println("countryEmpty = \" \"");
            }

            if (parameters.contains("filePath")){
                pstmt.setString(++columnIndex, filePath);
                System.out.println("filePath = " + filePath);
            }else if (parameters.contains("filePathEmpty")){
                pstmt.setString(++columnIndex, "");
                System.out.println("filePath = \" \"");
            }

            pstmt.setInt(++columnIndex, Integer.parseInt(filmIdSelected));
            System.out.println("filmIdSelected = " + filmIdSelected);

            // update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchByTitle(String titleOrSecondTitle, String year, String director, String country){

        listFilms listFilms = new listFilms("Liste des films cherchés", getColumnTitles());
        listFilms.pack();
        listFilms.setVisible(true);

        String sql = "SELECT * "
                + "FROM films "
                + "WHERE 1 = 1 ";
        List<String> parameters = new ArrayList<>();

        if (!titleOrSecondTitle.equals("")){
            sql += "AND (original_title like ? OR second_title like ?) ";
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

        if (!country.equals("")){
            sql += "AND country like ? ";
            parameters.add("country");
        }

        try (Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){

            int columnIndex = 0;

            // set the value
            if (parameters.contains("title")){
                //For title
                pstmt.setString(++columnIndex, "%" + titleOrSecondTitle.trim() + "%");
                //For second_title
                pstmt.setString(++columnIndex, "%" + titleOrSecondTitle.trim() + "%");
                System.out.println("titleOrSecondTitle = " + titleOrSecondTitle);
            }

            if (parameters.contains("year")){
                pstmt.setString(++columnIndex, "%" + year.trim() + "%");
                System.out.println("year = " + year);
            }

            if (parameters.contains("director")){
                pstmt.setString(++columnIndex, "%" + director.trim() + "%");
                System.out.println("director = " + director);
            }

            if (parameters.contains("country")){
                pstmt.setString(++columnIndex, "%" + country.trim() + "%");
                System.out.println("country = " + country);
            }

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                listFilms.refreshListFilms(rs.getString("filmID"),
                        rs.getString("original_title"),
                        rs.getString("year"),
                        rs.getString("director"),
                        rs.getString("second_title"),
                        rs.getString("country"),
                        rs.getString("file_path"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//END searchByTitle()

    public String[][] getAllLines(){
        //prepare an array at the length of column titles
        String[][] allLines = new String[getNumLines()][getColumnTitles().length];
        int linePosition = 0;

        String sql = "SELECT * FROM films";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            ResultSet rs  = pstmt.executeQuery();

            int columnPosition;

            while (rs.next()) {
                columnPosition = 0;

                allLines[linePosition][columnPosition++] = rs.getString("filmID");
                allLines[linePosition][columnPosition++] = rs.getString("original_title");
                allLines[linePosition][columnPosition++] = rs.getString("year");
                allLines[linePosition][columnPosition++] = rs.getString("director");
                allLines[linePosition][columnPosition++] = rs.getString("second_title");
                allLines[linePosition][columnPosition++] = rs.getString("country");
                allLines[linePosition][columnPosition++] = rs.getString("file_path");

                linePosition++;
                System.out.println(linePosition);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allLines;
    }

    public void modifyAllFilePath(){
        String sqlUpdate = "UPDATE films SET file_path = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate))
        {
            // set the value
            //pstmt.setString(1, originalTitle);
            //System.out.println("originalTitle = " + originalTitle);

            // update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}