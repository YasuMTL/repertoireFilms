package com.repertoire;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SQLiteHelper {
    private String URL;
    DefaultTableModel model;
    JTable jtable;
    JScrollPane scrollPane;

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

    public void update(String originalTitle, String year, String director, String secondTitle, String country, String filePath) {
        String sql = "UPDATE films SET original_title = ?, " +
                "year = ?, " +
                "director = ?, " +
                "second_title = ?, " +
                "country = ?, " +
                "file_path = ?";

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

    public void searchByTitle(String titleOrSecondTitle, String year, String director, String country){

        createJTable();

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

            if (parameters.contains("country")){
                pstmt.setString(++columnIndex, "%" + country + "%");
                System.out.println("country = " + country);
            }

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("filmID"),
                        rs.getString("original_title"),
                        rs.getString("year"),
                        rs.getString("director"),
                        rs.getString("second_title"),
                        rs.getString("country"),
                        rs.getString("file_path")}
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        JOptionPane.showMessageDialog(null, scrollPane, "Liste des films trouvée.s", JOptionPane.PLAIN_MESSAGE);
    }

    private void createJTable() {
        jtable = new JTable();
        scrollPane = new JScrollPane(jtable);
        scrollPane.setPreferredSize( new Dimension( 900, 500 ) );
        String[] columns = {"filmID", "Titre Original", "Année", "Réalisateur", "Autre titre", "Pays", "Chemin du fichier"};
        model = new DefaultTableModel();
        jtable.setModel(model);

        for (String column : columns) {
            model.addColumn(column);
        }

        addListenerToJtable();
    }//END createJTable()

    private void addListenerToJtable() {
        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jtable.rowAtPoint(new Point(e.getX(), e.getY()));
                int col = jtable.columnAtPoint(new Point(e.getX(), e.getY()));
                System.out.println(row + " " + col);

                if (col == 6){
                    //get the film's path
                    String url = (String) jtable.getModel().getValueAt(row, col);
                    System.out.println(url + " was clicked");

                    // DO here what you want to do with your url
                    int input = JOptionPane.showConfirmDialog(null,
                            "Voulez-vous copier ce film ?",
                            "Copier le film",
                            JOptionPane.YES_NO_OPTION);

                    //Yes
                    if (input == 0){
                        //get the directory's path
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        //Default: the USB key's path
                        fileChooser.setCurrentDirectory(new File("H:\\"));
                        int option = fileChooser.showOpenDialog(new JFrame());

                        //After you choose a directory
                        if(option == JFileChooser.APPROVE_OPTION){
                            File file = fileChooser.getSelectedFile();
                            System.out.println("Folder Selected: " + file.getAbsolutePath());
                            //Dialog to confirm if you wish to proceed the copy
                            int dialogCopyFilm = JOptionPane.showConfirmDialog(null,
                                    "Voulez-vous faire la copie suivante ?\n" + url + "\n--> " + file.getAbsolutePath(),
                                    "Copier le film",
                                    JOptionPane.YES_NO_OPTION);
                            //Yes
                            if (dialogCopyFilm == 0){
                                try
                                {
                                    SimpleDateFormat fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm_E");
                                    String pathUsbAndFileName = file.getAbsolutePath() + "\\film_copié_" + fileName.format(Calendar.getInstance().getTime()) + "avi";
                                    //copier le film
                                    fileInOut(url, pathUsbAndFileName);
                                    JOptionPane.showMessageDialog(null, "La copie est faite avec succès!");
                                }
                                catch (IOException ioException)
                                {
                                    ioException.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "La copie échouée...");
                                }
                            }
                            //No
                            else
                            {
                                JOptionPane.showMessageDialog(null, "La copie n'a pas été faite.");
                            }
                        }
                        //You didn't chose the directory
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Le dossier n'a pas été choisi.");
                        }
                    }
                    //No
                    else{
                        JOptionPane.showMessageDialog(null, "Le film n'a pas été copié.");
                    }//END if (input == 0)
                }//END if (col == 6)
            }//END mouseClicked
        });//END addMouseListener
    }//END of addListenerToJtable()

    public void fileInOut(String pathFileIn, String pathFileOut) throws IOException {

        //FileInputStreamのオブジェクトを生成する
        //FileInputStream fileIn = new FileInputStream("C:\\Users\\Yasunari\\Desktop\\S1 - 21 [1080p].mkv");
        FileInputStream fileIn = new FileInputStream(pathFileIn);

        //FileOutputStreamのオブジェクトを生成する
        //FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Yasunari\\Desktop\\Copied_S1 - 21 [1080p].mkv");
        FileOutputStream fileOut = new FileOutputStream(pathFileOut);

        // byte型の配列を宣言
        byte[] buf = new byte[256];
        int len;

        // ファイルの終わりまで読み込む
        while((len = fileIn.read(buf)) != -1){
            fileOut.write(buf);
        }

        //ファイルに内容を書き込む
        fileOut.flush();

        //ファイルの終了処理
        fileOut.close();
        fileIn.close();
    }
}