package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.repertoire.Main.SQLite;

public class WindowInsert extends JFrame implements ActionListener {

    private JLabel labelTitle,
            labelYear,
            labelDirector,
            labelSecondTitle,
            labelCountry,
            labelFilmPath;
    private JTextField fieldTitle,
            fieldYear,
            fieldDirector,
            fieldSecondTitle,
            fieldCountry,
            fieldFilmPath;
    private JButton buttonFinish, buttonAdd, buttonRemoveAll;
    private Container container;

    public WindowInsert(){
        super("Ajout d'un film");
        container = getContentPane();
        container.setLayout(new GridLayout(8, 2, 6, 6));

        createTitleLabelField();
        createYearLabelField();
        createDirectorLabelField();
        createSecondTitleLabelField();
        createCountryLabelField();
        createFilmPathLabelField();

        createButtonEnd();
        createButtonAdd();
        createButtonRemoveAll();

        setSize(300, 250);
        setLocation(400, 400);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonFinish){
            dispose();
        }
        else if (e.getSource() == buttonAdd){
            SQLite.addOneFilm(fieldTitle.getText(),
                    fieldYear.getText(),
                    fieldDirector.getText(),
                    fieldSecondTitle.getText(),
                    fieldCountry.getText(),
                    fieldFilmPath.getText());
            JOptionPane.showMessageDialog(null, "Un film a été ajouté !");
            removeAllField();
        }
        else if (e.getSource() == buttonRemoveAll){
            removeAllField();
        }
    }

    private void removeAllField() {
        fieldTitle.setText("");
        fieldYear.setText("");
        fieldDirector.setText("");
        fieldSecondTitle.setText("");
        fieldCountry.setText("");
        fieldFilmPath.setText("");
    }

    private void createButtonRemoveAll(){
        buttonRemoveAll = new JButton("Vider");
        buttonRemoveAll.addActionListener(this);
        container.add(buttonRemoveAll);
    }

    private void createButtonAdd() {
        buttonAdd = new JButton("Ajouter");
        buttonAdd.addActionListener(this);
        container.add(buttonAdd);
    }

    private void createButtonEnd() {
        buttonFinish = new JButton("Annuler");
        buttonFinish.addActionListener(this);
        container.add(buttonFinish);
    }

    private void createFilmPathLabelField(){
        labelFilmPath = new JLabel("Chemin du fichier : ", SwingConstants.RIGHT);
        fieldFilmPath = new JTextField();
        fieldFilmPath.addActionListener(this);
        container.add(labelFilmPath);
        container.add(fieldFilmPath);
    }

    private void createCountryLabelField() {
        labelCountry = new JLabel("Pays : ", SwingConstants.RIGHT);
        fieldCountry = new JTextField();
        fieldCountry.addActionListener(this);
        container.add(labelCountry);
        container.add(fieldCountry);
    }

    private void createSecondTitleLabelField() {
        labelSecondTitle = new JLabel("Autre titre : ", SwingConstants.RIGHT);
        fieldSecondTitle = new JTextField();
        fieldSecondTitle.addActionListener(this);
        container.add(labelSecondTitle);
        container.add(fieldSecondTitle);
    }

    private void createDirectorLabelField() {
        labelDirector = new JLabel("Réalisateur : ", SwingConstants.RIGHT);
        fieldDirector = new JTextField();
        fieldDirector.addActionListener(this);
        container.add(labelDirector);
        container.add(fieldDirector);
    }

    private void createYearLabelField() {
        labelYear = new JLabel("Année : ", SwingConstants.RIGHT);
        fieldYear = new JTextField();
        fieldYear.addActionListener(this);
        container.add(labelYear);
        container.add(fieldYear);
    }

    private void createTitleLabelField() {
        labelTitle = new JLabel("Titre : ", SwingConstants.RIGHT);
        fieldTitle = new JTextField();
        fieldTitle.addActionListener(this);
        container.add(labelTitle);
        container.add(fieldTitle);
    }
}