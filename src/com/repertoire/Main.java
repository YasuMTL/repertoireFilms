package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main extends JFrame implements ActionListener {
    private JButton btnSearch, btnInsert, btnModify, btnDelete;
    private JFrame window;
    private JMenu menuSearch, menuInsert;
    private JPanel panel;

    public static void main(String[] args){
        EventQueue.invokeLater
                (() -> {
                    new Main().sqliteTest();

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
        menuInsert = new JMenu("Enregistrer");
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
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Enregistrer\".", "ENREGISTRER", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getSource() == btnModify){
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Modifier\".", "ENREGISTRER", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getSource() == btnDelete){
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Supprimer\".", "ENREGISTRER", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void sqliteTest(){
        final String URL
                = "jdbc:sqlite:C:\\Users\\Yasunari\\Desktop\\test.db";

        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            System.out.println("接続成功");
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
    }
}
