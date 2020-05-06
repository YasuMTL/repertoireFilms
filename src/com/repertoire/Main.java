package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import static com.repertoire.Read.readFile;

public class Main extends JFrame implements ActionListener {
    private JButton btnSearch, btnInsert, btnModify, btnDelete;
    private JFrame window;
    private JMenu menuSearch, menuInsert;
    private JPanel panel;
    public static SQLiteHelper SQLite;

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

                    JFrame frameTest = new JFrame("Test");
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
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Ajouter\".", "Ajouter", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getSource() == btnModify){
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Modifier\".", "Modifier", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getSource() == btnDelete){
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Supprimer\".", "Supprimer", JOptionPane.PLAIN_MESSAGE);
        }
    }
}