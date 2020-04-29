package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowSearch extends JFrame implements ActionListener {

    private JLabel labelNum,
                   labelTitle,
                   labelYear,
                   labelDirector,
                   labelSecondTitle,
                   labelCountry;
    private JTextField fieldNum,
                       fieldTitle,
                       fieldYear,
                       fieldDirector,
                       fieldSecondTitle,
                       fieldCountry;
    private JButton boutonTerminer, buttonSearch;
    private Container container;

    public WindowSearch(){
        super("Recherche de films");
        container = getContentPane();
        container.setLayout(new GridLayout(7, 2, 6, 6));

        //Numéro
        labelNum = new JLabel("Numéro : ", SwingConstants.RIGHT);
        fieldNum = new JTextField();
        fieldNum.addActionListener(this);
        container.add(labelNum);
        container.add(fieldNum);
        //Titre
        labelTitle = new JLabel("Titre : ", SwingConstants.RIGHT);
        fieldTitle = new JTextField();
        fieldTitle.addActionListener(this);
        container.add(labelTitle);
        container.add(fieldTitle);
        //Année
        labelYear = new JLabel("Année : ", SwingConstants.RIGHT);
        fieldYear = new JTextField();
        fieldYear.addActionListener(this);
        container.add(labelYear);
        container.add(fieldYear);
        //Réalisateur
        labelDirector = new JLabel("Réalisateur : ", SwingConstants.RIGHT);
        fieldDirector = new JTextField();
        fieldDirector.addActionListener(this);
        container.add(labelDirector);
        container.add(fieldDirector);
        //Autre titre
        labelSecondTitle = new JLabel("Autre titre : ", SwingConstants.RIGHT);
        fieldSecondTitle = new JTextField();
        fieldSecondTitle.addActionListener(this);
        container.add(labelSecondTitle);
        container.add(fieldSecondTitle);
        //Pays
        labelCountry = new JLabel("Pays : ", SwingConstants.RIGHT);
        fieldCountry = new JTextField();
        fieldCountry.addActionListener(this);
        container.add(labelCountry);
        container.add(fieldCountry);

        boutonTerminer = new JButton("Terminer");
        boutonTerminer.addActionListener(this);
        container.add(boutonTerminer);

        buttonSearch = new JButton("Rechercher");
        buttonSearch.addActionListener(this);
        container.add(buttonSearch);

        setSize(300, 250);
//        setLocation(200, 200);
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
        }
    }
}
