package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static com.repertoire.Read.readFile;

public class Main extends JFrame implements ActionListener {
    private JButton btnSearch, btnInsert, btnRead;
    private JFrame window;
    private JMenu menuSearch, menuInsert, menuRead;
    private JPanel panel;
    public static SQLiteHelper SQLite;
    public static String dbName = "filmsTest.db";

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

                    JFrame frameFilms = new JFrame("Répertoire de films");
                    frameFilms.setContentPane(new Main().panel);
                    frameFilms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frameFilms.setLocationRelativeTo(null);
                    frameFilms.setPreferredSize(new Dimension(400, 80));
                    frameFilms.pack();
                    frameFilms.setVisible(true);
                });
    }

    private Main() {
        window = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        menuSearch = new JMenu("Rechercher");
        menuInsert = new JMenu("Ajouter");
        menuRead = new JMenu("Charger d'un fichier CSV");
        menuBar.add(menuSearch);
        menuBar.add(menuInsert);
        window.setJMenuBar(menuBar);
        window.setSize(400, 400);

        btnSearch.addActionListener(this);
        btnInsert.addActionListener(this);
        btnRead.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch){
            WindowSearch windowSearch = new WindowSearch();
        }
        else if (e.getSource() == btnInsert){
            WindowInsert windowInsert = new WindowInsert();
        }
        else if (e.getSource() == btnRead){
            int dialogReadCsv = JOptionPane.showConfirmDialog(null,
                    "Voulez-vous charger de nouvelles données d'un fichier CSV ?",
                    "De nouvelles données",
                    JOptionPane.YES_NO_OPTION);
            //Yes
            if (dialogReadCsv == 0){
                //read a csv file
                try {
                    readFile();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }//END if
            else{
                JOptionPane.showMessageDialog(null, "Le chargement n'a pas été fait.");
            }
        }
    }
}