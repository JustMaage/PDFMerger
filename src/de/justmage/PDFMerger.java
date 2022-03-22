package de.justmage;

import de.justmage.ui.MainFrame;
import de.justmage.ui.designer.GuiFrame;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PDFMerger {

    private final boolean isGui;

    public PDFMerger(String[] args) {
        isGui = !Arrays.asList(args).contains("--nogui");
    }

    public void startGuiMode() {
        GuiFrame frame = new GuiFrame(this);
        frame.setup();
    }

    public void merge(File[] files, String outputFileName) throws IOException {
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        for (File file : files)
            mergerUtility.addSource(file);
        mergerUtility.setDestinationFileName(outputFileName);
        mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }

    public File[] createFileArray() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        File currentFile = new File(System.getProperty("user.dir"));
        if(currentFile.listFiles() == null)
            return null;
        for (File file : currentFile.listFiles()) {
            if(file.getName().endsWith(".pdf"))
                fileArrayList.add(file);
        }
        if(fileArrayList.size() == 0)
            return null;
        if(fileArrayList.size() == 1) {
            System.err.println("Only found one PDF-File! (It's already merged, right?)");
            System.exit(3);
        }
        return fileArrayList.toArray(new File[0]);
    }

    public void printBeginning() {
        System.out.println("$$$$$$$\\  $$$$$$$\\  $$$$$$$$\\       $$\\      $$\\                                                   \n" +
                "$$  __$$\\ $$  __$$\\ $$  _____|      $$$\\    $$$ |                                                  \n" +
                "$$ |  $$ |$$ |  $$ |$$ |            $$$$\\  $$$$ | $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\  \n" +
                "$$$$$$$  |$$ |  $$ |$$$$$\\          $$\\$$\\$$ $$ |$$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ \n" +
                "$$  ____/ $$ |  $$ |$$  __|         $$ \\$$$  $$ |$$$$$$$$ |$$ |  \\__|$$ /  $$ |$$$$$$$$ |$$ |  \\__|\n" +
                "$$ |      $$ |  $$ |$$ |            $$ |\\$  /$$ |$$   ____|$$ |      $$ |  $$ |$$   ____|$$ |      \n" +
                "$$ |      $$$$$$$  |$$ |            $$ | \\_/ $$ |\\$$$$$$$\\ $$ |      \\$$$$$$$ |\\$$$$$$$\\ $$ |      \n" +
                "\\__|      \\_______/ \\__|            \\__|     \\__| \\_______|\\__|       \\____$$ | \\_______|\\__|      \n" +
                "                                                                     $$\\   $$ |                    \n" +
                "                                                                     \\$$$$$$  |                    \n" +
                "                                                                      \\______/                     ");
        System.out.println("  __  __           _        _                  _           _   __  __                  \n" +
                " |  \\/  |         | |      | |                | |         | | |  \\/  |                 \n" +
                " | \\  / | __ _  __| | ___  | |__  _   _       | |_   _ ___| |_| \\  / | __ _  __ _  ___ \n" +
                " | |\\/| |/ _` |/ _` |/ _ \\ | '_ \\| | | |  _   | | | | / __| __| |\\/| |/ _` |/ _` |/ _ \\\n" +
                " | |  | | (_| | (_| |  __/ | |_) | |_| | | |__| | |_| \\__ \\ |_| |  | | (_| | (_| |  __/\n" +
                " |_|  |_|\\__,_|\\__,_|\\___| |_.__/ \\__, |  \\____/ \\__,_|___/\\__|_|  |_|\\__,_|\\__, |\\___|\n" +
                "                                   __/ |                                     __/ |     \n" +
                "                                  |___/                                     |___/      ");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println("PDFMerger is way more advanced and customizable in UI Mode (run without --nogui)");
        System.out.println();
        System.out.println("How to use it:");
        System.out.println("1. Name the PDF-Files in a number sequence (e.g.: 1.pdf, 2.pdf...)");
        System.out.println("2. Press Enter as soon as you finished renaming your files. The merged file will have the name 'merged.pdf'");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public boolean isGui() {
        return isGui;
    }
}
