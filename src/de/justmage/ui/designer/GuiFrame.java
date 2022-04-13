package de.justmage.ui.designer;

import de.justmage.PDFMerger;
import de.justmage.ui.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

public class GuiFrame extends JFrame {

    private final PDFMerger merger;

    private JPanel mainPane;
    private JList<String> fileList;
    private JButton mergeButton;
    private JTextField outputField;
    private JButton addButton;
    private JButton removeButton;
    private JScrollPane scrollPane;

    private final DefaultListModel<String> items = new DefaultListModel<>();

    public GuiFrame(PDFMerger merger) {
        this.merger = merger;
        init();
        addButton.addActionListener(e -> {
            String file = Utils.chooseFile(null, this, new FileNameExtensionFilter("PDF Files", "pdf"), true);
            items.addElement(file);
        });
        removeButton.addActionListener(e -> {
            int index = fileList.getSelectedIndex();
            if (index != -1) {
                items.remove(index);
            }
        });
        mergeButton.addActionListener(e -> {
            if(items.size() == 0 || items.size() == 1) {
                JOptionPane.showMessageDialog(this, "Add at least 2 files", "WARNING", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String output = outputField.getText();
            if(output.equalsIgnoreCase(""))
                output = "merged.pdf";
            if(!output.endsWith(".pdf"))
                output = output + ".pdf";
            File[] files = new File[items.size()];
            for (int i = 0; i < items.size(); i++)
                files[i] = new File(items.get(i));
            try {
                merger.merge(files, output);
                JOptionPane.showMessageDialog(this, "Success!", "PDFMerger - Made by JustMage", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Something went wrong... [IOException]", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void init() {
        fileList.setModel(items);
        fileList.setDragEnabled(true);
        fileList.setDropMode(DropMode.INSERT);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setTransferHandler(new TransferHandler() {
            private int index;
            private boolean beforeIndex = false; //Start with 'false' therefore if it is removed from or added to the list it still works

            @Override
            public int getSourceActions(JComponent comp) {
                return MOVE;
            }

            @Override
            public Transferable createTransferable(JComponent comp) {
                index = fileList.getSelectedIndex();
                return new StringSelection(fileList.getSelectedValue());
            }

            @Override
            public void exportDone(JComponent comp, Transferable trans, int action) {
                if (action == MOVE) {
                    if (beforeIndex)
                        items.remove(index + 1);
                    else
                        items.remove(index);
                }
            }

            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.stringFlavor);
            }

            @Override
            public boolean importData(TransferHandler.TransferSupport support) {
                try {
                    String s = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                    items.add(dl.getIndex(), s);
                    beforeIndex = dl.getIndex() < index;
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });
        scrollPane.setViewportView(fileList);
    }

    public void setup() {
        setContentPane(mainPane);
        setSize(640, 640 / 12 * 9);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("PDFMerger - Made by JustMage");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
