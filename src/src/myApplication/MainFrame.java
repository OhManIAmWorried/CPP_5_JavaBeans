package src.myApplication;

import src.Data;
import src.DataSheet;
import src.mybeans.DataSheetChangeEvent;
import src.mybeans.DataSheetChangeListener;
import src.mybeans.DataSheetGraph;
import src.mybeans.DataSheetTable;
import src.xml.DataSheetToXML;
import src.xml.SAXRead;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oleg on 05.05.2017.
 */
public class MainFrame {
    private static JFrame frame;
    private static DataSheet dataSheet;
    private static DataSheetTable dataSheetTable;
    private static DataSheetGraph dataSheetGraph;
    private static final JFileChooser fileChooser = new JFileChooser();
    private static JButton openButton;
    private static JButton saveButton;
    private static JButton clearButton;
    private static JButton exitButton;

    private static void setDefaults() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setSize(400,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    private static void initializeElements() {
        dataSheet = new DataSheet("New DataSheet");
        dataSheet.addDataItem(new Data());

        dataSheetGraph = new DataSheetGraph();
        dataSheetGraph.setDataSheet(dataSheet);
        dataSheetGraph.setPreferredSize(new Dimension(200,300));

        dataSheetTable = new DataSheetTable(dataSheet);
        dataSheetTable.getTableModel().setDataSheet(dataSheet);
        dataSheetTable.getTableModel().addDataSheetChangeListener(new DataSheetChangeListener() {
            public void dataChanged(DataSheetChangeEvent e) {
                dataSheetGraph.revalidate();
                dataSheetGraph.repaint();
            }
        });
        dataSheetTable.setPreferredSize(new Dimension(200,300));

        fileChooser.setCurrentDirectory(new java.io.File("."));

        openButton = new JButton("Open");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    dataSheet = SAXRead.parseFile(fileName);
                    dataSheetTable.getTableModel().setDataSheet(dataSheet);
                    dataSheetTable.revalidate();
                    dataSheetGraph.setDataSheet(dataSheet);
                }
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    DataSheetToXML.writeToFile(dataSheet,fileName);
                    JOptionPane.showMessageDialog(null,"File " + fileName.trim() + " saved!",
                            "Results saved", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataSheet = new DataSheet("New DataSheet");
                dataSheet.addDataItem(new Data());
                dataSheetTable.getTableModel().setDataSheet(dataSheet);
                dataSheetTable.revalidate();
                dataSheetGraph.setDataSheet(dataSheet);
            }
        });

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    private static void addElements() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(openButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(exitButton);

        frame.setLayout(new BorderLayout());
        frame.add(buttonsPanel,BorderLayout.SOUTH);
        frame.add(dataSheetGraph,BorderLayout.EAST);
        frame.add(dataSheetTable,BorderLayout.WEST);
    }

    public static void main(String[] args) {
        frame = new JFrame();
        setDefaults();
        initializeElements();
        addElements();
        frame.setVisible(true);
    }

}
