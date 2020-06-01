package com.repertoire;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WriteCSV {
    WriteCSV(){}

    void exportCSV(String[] columnTitles, String[][] lines){
        try {
            // create a csv file
            PrintWriter p = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("E:\\Films Alexe\\Répertoire\\list_de_films.csv"), StandardCharsets.UTF_8)));

            // header
            for(int i = 0; i < columnTitles.length; i++){
                p.print(columnTitles[i]);
                p.print(";");
                //p.print(",");
            }
            p.println();    //new line

            // write the content in the file
            for(int i = 0; i < lines.length; i++){
                for (int j = 0; j < lines[0].length; j++){
                    System.out.println("lines[" + i + "][" + j + "] = " + lines[i][j] + ";");
                    p.print(lines[i][j]);
                    p.print(";");
                    //p.print(",");
                }
                p.println();//new line
            }

            p.close();
            System.out.println("A csv file was written.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}