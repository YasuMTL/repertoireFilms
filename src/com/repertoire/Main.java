package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private JButton btnSearch, btnInsert, btnModify, btnDelete;
    private JFrame window;
    private JMenu menuSearch, menuInsert;
    private JPanel panel;
    public static SQLiteHelper SQLite;

    String filePath;

    public static void main(String[] args){
        EventQueue.invokeLater
                (() -> {
                    SQLite = new SQLiteHelper();
                    SQLite.createNewDB("test_1.db");
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
                    frameTest.setLocation(400, 400);
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
        //window.setLocation(400, 400);
        window.setSize(400, 400);

        //test
        filePath = "C:\\Users\\Yasunari\\Desktop\\repertoireFilms.xlsx";

        btnSearch.addActionListener(this);
        btnInsert.addActionListener(this);
        btnModify.addActionListener(this);
        btnDelete.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch){
            WindowSearch windowSearch = new WindowSearch();
        }
        else if (e.getSource() == btnInsert){
            JHyperlink linkFilePath = new JHyperlink("Click to watch the film");
            linkFilePath.setURL(filePath);
            linkFilePath.setToolTipText("Click this!!!!!!");

            //JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Ajouter\".\nThe file path: " + filePath, "Ajouter", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showMessageDialog(null, linkFilePath);
        }
        else if (e.getSource() == btnModify){
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Modifier\".\nLa copie commence !", "Modifier", JOptionPane.PLAIN_MESSAGE);


        }
        else if (e.getSource() == btnDelete){
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Supprimer\".", "Supprimer", JOptionPane.PLAIN_MESSAGE);
        }
    }
}