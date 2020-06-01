package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {
    private JButton btnSearch, btnInsert, btnImport, btnExport;
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
                    SQLite.createNewTableForColumnTitles();
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
                    frameFilms.setPreferredSize(new Dimension(450, 80));
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
        btnImport.addActionListener(this);
        btnExport.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch){
            WindowSearch windowSearch = new WindowSearch();
        }
        else if (e.getSource() == btnInsert){
            WindowInsert windowInsert = new WindowInsert();
        }
        else if (e.getSource() == btnImport){
            int dialogReadCsv = JOptionPane.showConfirmDialog(null,
                    "Voulez-vous charger de nouvelles données d'un fichier CSV ?",
                    "Importer un fichier CSV",
                    JOptionPane.YES_NO_OPTION);
            //Yes
            if (dialogReadCsv == 0){
                SQLite.clearUpTable();
                ReadCSV importCsv = new ReadCSV();
                //read a csv file
                try {
                    importCsv.readCsvFile();
                    String[] titles = importCsv.getColumnTitles();
                    SQLite.addColumnTitles(titles);
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }//END if
            else{
                JOptionPane.showMessageDialog(null, "Le chargement n'a pas été fait.");
            }
        }
        else if (e.getSource() == btnExport){
            int dialogExportCsv = JOptionPane.showConfirmDialog(null,
                    "Voulez-vous exporter un fichier CSV ?",
                    "Exporter un fichier CSV",
                    JOptionPane.YES_NO_OPTION);
            //Yes
            if (dialogExportCsv == 0){
                WriteCSV exportCsv = new WriteCSV();
                exportCsv.exportCSV(SQLite.getColumnTitles(), SQLite.getAllLines());
            }//END if
            else{
                JOptionPane.showMessageDialog(null, "L'exportation n'a pas été faite.");
            }
        }
    }
}