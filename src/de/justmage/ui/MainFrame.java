package de.justmage.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class MainFrame extends JFrame {

    private DefaultListModel<String> strings = new DefaultListModel<>();

    private final JScrollPane scrollPane = createPanel(); // the list
    private final JButton addFileButton = new JButton(); // add a file
    private final JLabel outputFileLabel = new JLabel(); // the output file description
    private final JTextField outputFileField= new JTextField(); // the output file name/path
    private final JButton mergeButton = new JButton(); // the merge button

    public void setup() {
        setSize(640, 640 / 12 * 9);
        setLocationRelativeTo(null);
        setTitle("PDFMerger - Made by JustMage");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        addComponents();
    }

    private void addComponents() {
        setSize(scrollPane, 50, 50);
        add(scrollPane);
        /*add(addFileButton);
        add(outputFileLabel);
        add(outputFileField);
        add(mergeButton);*/
    }

    private JScrollPane createPanel() {
        for (int i = 1; i <= 100; i++) {
            strings.addElement("Item " + i);
        }

        JList<String> dndList = new JList<>(strings);
        dndList.setDragEnabled(true);
        dndList.setDropMode(DropMode.INSERT);
        dndList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dndList.setTransferHandler(new TransferHandler() {
            private int index;
            private boolean beforeIndex = false; //Start with 'false' therefore if it is removed from or added to the list it still works

            @Override
            public int getSourceActions(JComponent comp) {
                return MOVE;
            }

            @Override
            public Transferable createTransferable(JComponent comp) {
                index = dndList.getSelectedIndex();
                return new StringSelection(dndList.getSelectedValue());
            }

            @Override
            public void exportDone(JComponent comp, Transferable trans, int action) {
                if (action == MOVE) {
                    if (beforeIndex)
                        strings.remove(index + 1);
                    else
                        strings.remove(index);
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
                    strings.add(dl.getIndex(), s);
                    beforeIndex = dl.getIndex() < index;
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

        return new JScrollPane(dndList);
    }

    private void setSize(Component component, int width, int height) {
        component.setPreferredSize(new Dimension(width, height));
        component.setMaximumSize(new Dimension(width, height));
        component.setMinimumSize(new Dimension(width, height));
    }

}
