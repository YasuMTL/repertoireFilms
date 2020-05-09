package com.repertoire;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static com.repertoire.Read.readFile;

public class Main extends JFrame implements ActionListener {
    private JButton btnSearch, btnInsert, btnModify, btnDelete;
    private JFrame window;
    private JMenu menuSearch, menuInsert;
    private JPanel panel;
    public static SQLiteHelper SQLite;

    String filePath;

    public static void main(String[] args){
        EventQueue.invokeLater
                (() -> {
                    SQLite = new SQLiteHelper();
                    SQLite.createNewDB("test_1.db");
                    SQLite.createNewTable();

                    //read the file
                    /*try {
                        readFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    JFrame frameTest = new JFrame("Répertoire de films");
                    frameTest.setContentPane(new Main().panel);
                    frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frameTest.setLocation(400, 400);
                    frameTest.pack();
                    frameTest.setVisible(true);
                });
    }

    private Main() {
        window = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        menuSearch = new JMenu("Rechercher");
        menuInsert = new JMenu("Ajouter");
        menuBar.add(menuSearch);
        menuBar.add(menuInsert);
        window.setJMenuBar(menuBar);
        //window.setLocation(400, 400);
        window.setSize(400, 400);

        //test
        filePath = "C:\\Users\\Yasunari\\Desktop\\repertoireFilms.xlsx";

        btnSearch.addActionListener(this);
        btnInsert.addActionListener(this);
        btnModify.addActionListener(this);
        btnDelete.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch){
            WindowSearch windowSearch = new WindowSearch();
        }
        else if (e.getSource() == btnInsert){
            JHyperlink linkFilePath = new JHyperlink("Click to watch the film");
            linkFilePath.setURL(filePath);
            linkFilePath.setToolTipText("Click this!!!!!!");

            //JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Ajouter\".\nThe file path: " + filePath, "Ajouter", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showMessageDialog(null, linkFilePath);
        }
        else if (e.getSource() == btnModify){
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Modifier\".\nLa copie commence !", "Modifier", JOptionPane.PLAIN_MESSAGE);
            try {
                fileInOut();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "La copie est terminée !", "Modifier", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getSource() == btnDelete){
            JOptionPane.showMessageDialog(null, "Vous avez cliqué \"Supprimer\".", "Supprimer", JOptionPane.PLAIN_MESSAGE);

            sampleJFileChooser();
        }
    }

    public void fileInOut() throws IOException{

        //FileInputStreamのオブジェクトを生成する
        FileInputStream fileIn = new FileInputStream("C:\\Users\\Yasunari\\Desktop\\S1 - 21 [1080p].mkv");

        //FileOutputStreamのオブジェクトを生成する
        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Yasunari\\Desktop\\Copied_S1 - 21 [1080p].mkv");

        // byte型の配列を宣言
        byte[] buf = new byte[256];
        int len;

        // ファイルの終わりまで読み込む
        while((len = fileIn.read(buf)) != -1){
            fileOut.write(buf);
        }

        //ファイルに内容を書き込む
        fileOut.flush();

        //ファイルの終了処理
        fileOut.close();
        fileIn.close();
    }

    public void sampleJFileChooser(){

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("E:\\Films Alexe"));

        int result = jFileChooser.showOpenDialog(new JFrame());


        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    }
}