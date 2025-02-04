package com.repertoire;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.repertoire.Main.SQLite;

public class ReadCSV {
    private String[] columnTitles;

    //Empty constructor
    ReadCSV(){}

    void readCsvFile() throws IOException {
        String line, originalTitle, year, director, secondTitle, country, filePath, memo;
        //Choose a csv file
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("E:\\Films Alexe\\Répertoire\\"));
        
        int result = jFileChooser.showOpenDialog(new JFrame()),
            lineNumber = 0;
        columnTitles = null;
        File selectedFile;
        BufferedReader entryFile = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = jFileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            entryFile = new BufferedReader(
                    new FileReader(selectedFile)
            );
        }

        System.out.println("entryFile: " + entryFile);

        // reading the first line of the file
        try {
            while ((line = entryFile.readLine()) != null){

                //debug
                /*if (tests.length > 1){
                    for(String test: tests){
                        System.out.println(test);
                    }
                    System.out.println("counter = " + counter++);
                }
                else{
                    System.out.println("array of tests is seemingly empty");
                }*/

                if (lineNumber == 0){
                    columnTitles = line.split(";");
                }else{
                    String[] columns = line.split(";");

                    if (columns.length > 1){

                        originalTitle = columns[1];
                        if (columns[2].isEmpty()) {
                            year = "9999";
                        }else {
                            year = columns[2];
                        }

                        director = columns[3];
                        secondTitle = columns[4];
                        country = columns[5];

                        filePath = columns[6];

                        if (columns.length == 7){
                            memo = "";
                        }else{
                            memo = columns[7];
                        }

                        //Debug
                        //System.out.println(originalTitle + ", " + year + ", " + director + ", " + secondTitle + ", " + country + ", " + filePath);

                        SQLite.addOneFilm(originalTitle, year, director, secondTitle, country, filePath, memo);
                    }
                }

                System.out.println("lineNumber = " + lineNumber);
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