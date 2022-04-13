package de.justmage;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        PDFMerger merger = new PDFMerger(args);
        if(merger.isGui()) {
            if(!merger.enableDarculaLaf())
                System.err.println("Failed to enable DarculaLaf... continuing with default LAF");
            merger.startGuiMode();
        } else {
            merger.printBeginning();
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            try {
                File[] files = merger.createFileArray();
                if(files == null) {
                    System.out.println(System.getProperty("user.dir"));
                    System.err.println("Did not find any PDF-Files! Make sure to name them as described in the usage!");
                    System.exit(2);
                }
                merger.merge(files, "merged.pdf");
                System.out.println("Created the file 'merged.pdf' with the contents of:");
                System.out.println(Arrays.toString(files).replaceAll("(\\[)|(])", ""));
            } catch (Exception ex) {
                System.err.println("Something went wrong... [" + ex.getMessage() + "]");
            }
        }
    }

}
