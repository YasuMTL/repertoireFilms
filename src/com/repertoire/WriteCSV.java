package com.repertoire;

import java.io.*;

public class WriteCSV {
    WriteCSV(){}

    void exportCSV(String[] columnTitles, String[][] lines){
        try {
            // 出力ファイルの作成
            PrintWriter p = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("E:\\Films Alexe\\Répertoire\\test.csv", false),"unicode")));

            // header
            for(int i = 0; i < columnTitles.length; i++){
                p.print(columnTitles[i]);
                p.print(";");
            }
            p.println();    // 改行

            // 内容をセットする
            for(int i = 0; i < lines.length; i++){
                for (int j = 0; j < lines[0].length; j++){
                    p.print(lines[i][j]);
                    p.print(";");
                }
                p.println();    // 改行
            }

            p.close();
            System.out.println("ファイル出力完了！");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
