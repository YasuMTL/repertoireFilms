package com.repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowInsert extends JFrame implements ActionListener {

    private JLabel etiqNoProduit, etiqDescript, etiqPrix;
    private JTextField champNoProduit, champDescript, champPrix;
    private JButton boutonTerminer;

    public WindowInsert(){
        Container c = getContentPane();
        c.setLayout(new GridLayout(4, 2, 6, 6));
        etiqNoProduit = new JLabel("Numéro produit : ",SwingConstants.RIGHT);
        champNoProduit = new JTextField();
        champNoProduit.addActionListener(this);
        c.add(etiqNoProduit);
        c.add(champNoProduit);

        etiqDescript = new JLabel("Description : ", SwingConstants.RIGHT);
        champDescript = new JTextField();

        champDescript.setEditable(false);

        c.add(etiqDescript);
        c.add(champDescript);

        etiqPrix = new JLabel("Prix : ", SwingConstants.RIGHT);
        champPrix = new JTextField();
        champPrix.setEditable(false);
        c.add(etiqPrix);
        c.add(champPrix);

        boutonTerminer = new JButton("Terminer");
        boutonTerminer.addActionListener(this);
        c.add(boutonTerminer);

        setSize(300, 130);
        setLocation(200, 200);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
