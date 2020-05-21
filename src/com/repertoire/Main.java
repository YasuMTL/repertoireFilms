package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {
    private JButton btnSearch, btnInsert;
    private JFrame window;
    private JMenu menuSearch, menuInsert;
    private JPanel panel;
    public static SQLiteHelper SQLite;
    public static String dbName = "films.db";

    public static void main(String[] args){
        EventQueue.invokeLater
                (() -> {
                    SQLite = new SQLiteHelper(dbName);
                    SQLite.createNewDB();
                    SQLite.createNewTable();
                    //backup the database
                    try {
                        SQLite.backUpDB();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    //read the file
                    /*try {
                        readFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    JFrame frameFilms = new JFrame("Répertoire de films");
                    frameFilms.setContentPane(new Main().panel);
                    frameFilms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frameFilms.setLocationRelativeTo(null);
                    frameFilms.setPreferredSize(new Dimension(300, 80));
                    frameFilms.pack();
                    frameFilms.setVisible(true);
                });
    }

    private Main() {
        window = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        menuSearch = new JMenu("Rechercher");
        menuInsert = new JMenu("Ajouter");
        menuBar.add(menuSearch);
        menuBar.add(menuInsert);
        window.setJMenuBar(menuBar);
        window.setSize(400, 400);

        btnSearch.addActionListener(this);
        btnInsert.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch){
            WindowSearch windowSearch = new WindowSearch();
        }
        else if (e.getSource() == btnInsert){
            WindowInsert windowInsert = new WindowInsert();
        }
    }
}