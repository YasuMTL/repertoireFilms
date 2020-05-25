package com.repertoire;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.repertoire.Main.SQLite;

public class Read {
    private String[] columnTitles;

    //Empty constructor
    Read(){}

    void readCsvFile() throws IOException {
        String line, originalTitle, year, director, secondTitle, country, filePath;
        //Choose a csv file
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("E:\\Films Alexe\\Répertoire\\"));
        
        int result = jFileChooser.showOpenDialog(new JFrame()),
            lineNumber = 0;
        columnTitles = null;
        File selectedFile;
        BufferedReader entryFile;

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = jFileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            entryFile = new BufferedReader(
                    new FileReader(selectedFile)
            );
        }else{
            entryFile = null;
        }

        // reading the first line of the file
        try {
            line = entryFile.readLine();

            while (line != null){
                if (lineNumber == 0){
                    //How to pass these values to setColumnTitles() of listFilms.java ?
                    columnTitles = line.split(";");
                }else{
                    String[] columns = line.split(";");
                    //System.out.println("columns.length = " + columns.length);

                    originalTitle = columns[0];

                    if (columns[1].isEmpty()) {
                        year = "9999";
                    }else {
                        year = columns[1];
                    }

                    director = columns[2];
                    secondTitle = columns[3];
                    country = columns[4];

                    if (columns.length == 5){
                        filePath = null;
                    }else{
                        filePath = columns[5];
                    }

                    //Debug
                    //System.out.println(originalTitle + ", " + year + ", " + director + ", " + secondTitle + ", " + country + ", " + filePath);

                    SQLite.addOneFilm(originalTitle, year, director, secondTitle, country, filePath);
                }

                line = entryFile.readLine();
                lineNumber++;
            }//END of while

            entryFile.close();
            JOptionPane.showMessageDialog(null, "Le chargement a terminé.");
        }catch (NullPointerException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Le chargement n'a pas été fait.");
        }

    }//END of readFile()

    //Getter for columnTitles
    public String[] getColumnTitles(){
        return columnTitles;
    }

}//END of Read class