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

    public static void main(String[] args){
        EventQueue.invokeLater
        (() -> {
            JFrame frameTest = new JFrame("Test");
            frameTest.setContentPane(new Main().panel);
            frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameTest.setLocation(400, 400);
            frameTest.pack();
            frameTest.setVisible(true);
        });
    }
}
