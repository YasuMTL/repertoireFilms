package com.repertoire;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.repertoire.Main.dbName;

public class listFilms extends JFrame implements ActionListener {
    private JPanel listFilms;
    private JTable jTableListFilms;
    private JTextField textFieldTitle;
    private JLabel labelTitle;
    private JLabel labelYear;
    private JLabel labelDirector;
    private JLabel labelSecondTitle;
    private JLabel labelCountry;
    private JLabel labelFilmPath;
    private JButton buttonModify;
    private JButton buttonRemove;
    private JButton buttonClear;
    private JButton buttonBack;
    private JTextField textFieldYear;
    private JTextField textFieldDirector;
    private JTextField textFieldSecondTitle;
    private JTextField textFieldCountry;
    private JTextField textFieldFilmPath;

    private JScrollPane scrollPane;
    private final DefaultTableModel model;

    private String filmIdSelected;

    public listFilms(String title, String[] columnTitles){
        super(title);

        buttonModify.addActionListener(this);
        buttonRemove.addActionListener(this);
        buttonClear.addActionListener(this);
        buttonBack.addActionListener(this);

        model = (DefaultTableModel) jTableListFilms.getModel();

        setColumnTitles(columnTitles);

        jTableListFilms.setModel(model);

        addListenerToJtable();

        this.setContentPane(listFilms);
        this.setPreferredSize(new Dimension(1200, 400));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }//END of constructor

    private void setColumnTitles(String[] columnTitles) {
        for (String column : columnTitles) {
            model.addColumn(column);
        }
    }

    public void refreshListFilms(String filmID, String title, String year, String director, String secondTitle, String country, String filmPath){
        model.addRow(new Object[]{
                filmID,
                title,
                year,
                director,
                secondTitle,
                country,
                filmPath}
        );
    }

    private void addListenerToJtable() {
        jTableListFilms.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTableListFilms.rowAtPoint(new Point(e.getX(), e.getY()));
                int col = jTableListFilms.columnAtPoint(new Point(e.getX(), e.getY()));
                System.out.println(row + " " + col);

                filmIdSelected = (String)jTableListFilms.getModel().getValueAt(row, 0);

                //show the parameters in the textFields
                String title = (String)jTableListFilms.getModel().getValueAt(row, 1),
                        year = (String)jTableListFilms.getModel().getValueAt(row, 2),
                        director = (String)jTableListFilms.getModel().getValueAt(row, 3),
                        secondTitle = (String)jTableListFilms.getModel().getValueAt(row, 4),
                        country = (String)jTableListFilms.getModel().getValueAt(row, 5),
                        filmPath = (String)jTableListFilms.getModel().getValueAt(row, 6);

                textFieldTitle.setText(title);
                textFieldYear.setText(year);
                textFieldDirector.setText(director);
                textFieldSecondTitle.setText(secondTitle);
                textFieldCountry.setText(country);
                textFieldFilmPath.setText(filmPath);

                if (col == 6){
                    //get the film's path
                    String url = (String) jTableListFilms.getModel().getValueAt(row, col);
                    System.out.println(url + " was clicked");

                    // DO here what you want to do with your url
                    int input = JOptionPane.showConfirmDialog(null,
                            "Voulez-vous copier ce film ?",
                            "Copier le film",
                            JOptionPane.YES_NO_OPTION);

                    //Yes
                    if (input == 0){
                        //get the directory's path
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        //Default: the USB key's path
                        fileChooser.setCurrentDirectory(new File("H:\\"));
                        int option = fileChooser.showOpenDialog(new JFrame());

                        //After you choose a directory
                        if(option == JFileChooser.APPROVE_OPTION){
                            File file = fileChooser.getSelectedFile();
                            System.out.println("Folder Selected: " + file.getAbsolutePath());
                            //Dialog to confirm if you wish to proceed the copy
                            int dialogCopyFilm = JOptionPane.showConfirmDialog(null,
                                    "Voulez-vous faire la copie suivante ?\n" + url + "\n--> " + file.getAbsolutePath(),
                                    "Copier le film",
                                    JOptionPane.YES_NO_OPTION);
                            //Yes
                            if (dialogCopyFilm == 0){
                                try
                                {
                                    SimpleDateFormat fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm_E");
                                    String pathUsbAndFileName = file.getAbsolutePath() + "\\film_copié_" + fileName.format(Calendar.getInstance().getTime()) + "avi";
                                    //copier le film
                                    fileInOut(url, pathUsbAndFileName);
                                    JOptionPane.showMessageDialog(null, "La copie est faite avec succès!");
                                }
                                catch (IOException ioException)
                                {
                                    ioException.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "La copie échouée...");
                                }
                            }
                            //No
                            else
                            {
                                JOptionPane.showMessageDialog(null, "La copie n'a pas été faite.");
                            }
                        }
                        //You didn't chose the directory
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Le dossier n'a pas été choisi.");
                        }
                    }
                    //No
                    else{
                        JOptionPane.showMessageDialog(null, "Le film n'a pas été copié.");
                    }//END if (input == 0)
                }//END if
            }//END mouseClicked

        });//END addMouseListener

    }//END of addListenerToJtable()

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonBack)
        {
            dispose();
        }
        else if(e.getSource() == buttonClear)
        {
            textFieldTitle.setText("");
            textFieldYear.setText("");
            textFieldDirector.setText("");
            textFieldSecondTitle.setText("");
            textFieldCountry.setText("");
            textFieldFilmPath.setText("");
        }
        else if(e.getSource() == buttonModify)
        {
            int dialogModifyFilm = JOptionPane.showConfirmDialog(null,
                    "Voulez-vous modifier ce film ?",
                    "Modification du film",
                    JOptionPane.YES_NO_OPTION);
            //Yes
            if (dialogModifyFilm == 0){
                SQLiteHelper sql = new SQLiteHelper(dbName);

                String newTitle = textFieldTitle.getText(),
                        newYear = textFieldYear.getText(),
                        newDirector = textFieldDirector.getText(),
                        newSecondTitle = textFieldSecondTitle.getText(),
                        newCountry = textFieldCountry.getText(),
                        newFilmPath = textFieldFilmPath.getText();

                sql.modifyOneFilm(filmIdSelected,
                        newTitle,
                        newYear,
                        newDirector,
                        newSecondTitle,
                        newCountry,
                        newFilmPath);

                model.setValueAt(newTitle, jTableListFilms.getSelectedRow(), 1);
                model.setValueAt(newYear, jTableListFilms.getSelectedRow(), 2);
                model.setValueAt(newDirector, jTableListFilms.getSelectedRow(), 3);
                model.setValueAt(newSecondTitle, jTableListFilms.getSelectedRow(), 4);
                model.setValueAt(newCountry, jTableListFilms.getSelectedRow(), 5);
                model.setValueAt(newFilmPath, jTableListFilms.getSelectedRow(), 6);

                JOptionPane.showMessageDialog(this, "La mise à jour a été faite !");
            }else{
                JOptionPane.showMessageDialog(this, "La modification a été annulée !");
            }
        }
        else if (e.getSource() == buttonRemove)
        {
            int dialogRemoveFilm = JOptionPane.showConfirmDialog(null,
                    "Voulez-vous supprimer ce film ?",
                    "Supression du film",
                    JOptionPane.YES_NO_OPTION);
            //Yes
            if (dialogRemoveFilm == 0){
                SQLiteHelper sql = new SQLiteHelper(dbName);
                sql.removeOneFilm(filmIdSelected);
                JOptionPane.showMessageDialog(this, "La supression a été faite !");

                dispose();
            }else{
                JOptionPane.showMessageDialog(this, "La supression a été annulée !");
            }
        }
    }

    public void fileInOut(String pathFileIn, String pathFileOut) throws IOException {

        FileInputStream fileIn = new FileInputStream(pathFileIn);
        FileOutputStream fileOut = new FileOutputStream(pathFileOut);

        byte[] buffer = new byte[256];
        int len;

        while((len = fileIn.read(buffer)) != -1){
            fileOut.write(buffer);
        }

        fileOut.flush();

        fileOut.close();
        fileIn.close();
    }
}