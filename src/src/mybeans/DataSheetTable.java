package src.mybeans;

import src.Data;
import src.DataSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oleg on 05.05.2017.
 */
public class DataSheetTable extends JPanel{
    JTable table;
    JButton buttonAdd;
    JButton buttonDel;

    public DataSheetTable(DataSheet dataSheet) {
        setLayout(new BorderLayout());

        buttonAdd = new JButton("Add(+)");
        buttonDel = new JButton("Del(-)");

        table = new JTable();
        DataSheetTableModel tableModel = new DataSheetTableModel(dataSheet);
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        JPanel lowerPane = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER,25,1);
        lowerPane.setLayout(fl);
        lowerPane.add(buttonAdd);
        lowerPane.add(buttonDel);

        add(lowerPane, BorderLayout.SOUTH);

        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(tableModel.getRowCount() + 1);
                tableModel.getDataSheet().addDataItem(new Data());
                table.revalidate();
                tableModel.fireDataSheetChange();
            }
        });

        buttonDel.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (tableModel.getRowCount() > 1) {
                   tableModel.setRowCount(tableModel.getRowCount() - 1);
                   tableModel.getDataSheet().removeDataItem(tableModel.getDataSheet().size() - 1);
                   table.revalidate();
                   tableModel.fireDataSheetChange();
               } else {
                   tableModel.getDataSheet().getDataItem(0).setDate("");
                   tableModel.getDataSheet().getDataItem(0).setX(0);
                   tableModel.getDataSheet().getDataItem(0).setY(0);
                   table.revalidate();
                   table.repaint();
                   tableModel.fireDataSheetChange();
               }
           }
        });
    }

    public DataSheetTableModel getTableModel() {
        return (DataSheetTableModel) table.getModel();
    }

    public void setTableModel(DataSheetTableModel tableModel) {
        table.setModel(tableModel);
        table.revalidate();
    }

    public void revalidate() {
        if (table != null) table.revalidate();
    }
}
