package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.repertoire.Main.SQLite;

public class WindowSearch extends JFrame implements ActionListener {

    private JLabel //labelNum,
                   labelTitle,
                   labelYear,
                   labelDirector,
                   labelSecondTitle,
                   labelCountry;
    private JTextField //fieldNum,
                       fieldTitle,
                       fieldYear,
                       fieldDirector,
                       fieldSecondTitle,
                       fieldCountry;
    private JButton boutonTerminer, buttonSearch;
    private Container container;

    WindowSearch(){
        super("Recherche de films");
        container = getContentPane();
        container.setLayout(new GridLayout(6, 2, 6, 6));

        //createNumLabelField();
        createTitleLabelField();
        createYearLabelField();
        createDirectorLabelField();
        createSecondTitleLabelField();
        createCountryLabelField();

        createButtonEnd();
        createButtonSearch();

        setSize(300, 250);
        setLocation(400, 400);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonTerminer){
            dispose();
        }
        else if (e.getSource() == buttonSearch){
            //requête SELECT
            //SQLite.selectAll();
            SQLite.searchByTitle(fieldTitle.getText(),
                    fieldYear.getText(),
                    fieldDirector.getText(),
                    fieldSecondTitle.getText(),
                    fieldCountry.getText());
        }
    }

    private void createButtonSearch() {
        buttonSearch = new JButton("Rechercher");
        buttonSearch.addActionListener(this);
        container.add(buttonSearch);
    }

    private void createButtonEnd() {
        boutonTerminer = new JButton("Terminer");
        boutonTerminer.addActionListener(this);
        container.add(boutonTerminer);
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

    /*private void createNumLabelField() {
        labelNum = new JLabel("Numéro : ", SwingConstants.RIGHT);
        fieldNum = new JTextField();
        fieldNum.addActionListener(this);
        container.add(labelNum);
        container.add(fieldNum);
    }*/
}