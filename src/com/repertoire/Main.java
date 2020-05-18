package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private JButton btnSearch, btnInsert;
    private JFrame window;
    private JMenu menuSearch, menuInsert;
    private JPanel panel;
    public static SQLiteHelper SQLite;
    public static String dbName = "test_1.db";

    public static void main(String[] args){
        EventQueue.invokeLater
                (() -> {
                    SQLite = new SQLiteHelper(dbName);
                    SQLite.createNewDB();
                    SQLite.createNewTable();

                    //read the file
                    /*try {
                        readFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    JFrame frameTest = new JFrame("Répertoire de films");
                    frameTest.setContentPane(new Main().panel);
                    frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frameTest.setLocationRelativeTo(null);
                    frameTest.pack();
                    frameTest.setVisible(true);
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