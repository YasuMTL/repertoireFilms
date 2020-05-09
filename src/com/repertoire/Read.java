package com.repertoire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.repertoire.Main.SQLite;

public class Read {
    //Empty constructor
    Read(){}

    static void readFile() throws IOException {
        String entryLine, originalTitle, year, director, secondTitle, country, filePath;
        //int year;
        BufferedReader entryFile = new BufferedReader(
                                        new FileReader("data.txt")
                                   );
        // reading the first line of the file
        entryLine = entryFile.readLine();

        while (entryLine != null){
            String[] columns = entryLine.split(";");
            System.out.println("columns.length = " + columns.length);

            originalTitle = columns[0];

            if (columns[1].isEmpty()) {
                //year = 1000;
                year = "9999";
            }else {
                //year = Integer.parseInt(columns[1]);
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
            System.out.println(originalTitle + ", " + year + ", " + director + ", " + secondTitle + ", " + country + ", " + filePath);

            SQLite.insert(originalTitle, year, director, secondTitle, country, filePath);

            entryLine = entryFile.readLine();
        }

        entryFile.close();
    }
}