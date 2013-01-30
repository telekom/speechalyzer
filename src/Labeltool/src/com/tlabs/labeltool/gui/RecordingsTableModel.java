package com.tlabs.labeltool.gui;
import javax.swing.table.AbstractTableModel;

import com.tlabs.labeltool.IRecorder;
import com.tlabs.labeltool.Recording;
import com.tlabs.labeltool.RecordingTable;


import java.util.Vector;

/**
 * a special table model to handle a list of recordings and show them in a table. Most of the code was copied from sun's
 * bingo example (see tutorial)
 * 
 * @version 1.0
 * @author Felix Burkhardt
 */
public class RecordingsTableModel extends AbstractTableModel implements RecordingTable {

	private static final long serialVersionUID = 1L;

	/**
     * to columns: name and size.
     */
    protected int COLUMN_NUM = 7;

    protected String indexName;

    protected String recDialogName;

    protected String recNameName;

    protected String recWordsName;

    protected String recLabName;

    protected String recPredName;

    protected String recSizeName;

    boolean useSympalog, alaw;

    IRecorder recorder;

    /**
     * number of recordings in the table.
     */
    int rowNum = 0;

    /**
     * Vector of recordings.
     */
    protected Vector<Recording> data = null;

    /**
     * constructor: inits the Vector.
     */
    public RecordingsTableModel(Recorder r, boolean useSympalog, boolean alaw) {
        this.recorder = r;
        this.useSympalog = useSympalog;
        data = new Vector<Recording>();
        indexName = r.getParameter("indexName");
        recDialogName = r.getParameter("recDialogName");
        recNameName = r.getParameter("recNameName");
        recWordsName = r.getParameter("recWordsName");
        recLabName = r.getParameter("recLabName");
        recPredName = r.getParameter("recPredName");
        recSizeName = r.getParameter("recSizeName");
        if (!useSympalog) {
            COLUMN_NUM = 6;
        }
        this.alaw = alaw;
    }

    public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    /**
     * returns num of columns.
     */
    public synchronized int getColumnCount() {
        return COLUMN_NUM;
    }

    /**
     * returns number of rows (=recordings).
     */
    public synchronized int getRowCount() {
        return rowNum;
    }

    /**
     * returns names of columns.
     */
    public synchronized String getColumnName(int col) {
        switch (col) {
        case 0:
            return indexName;
        case 1:
            return recDialogName;
        case 2:
            return recNameName;
        case 3:
            return recSizeName;
        case 4:
            return recWordsName;
        case 5:
            return recLabName;
        case 6:
            return recPredName;
        }
        return "";
    }

    /**
     * returns value in table.
     */
    public synchronized Object getValueAt(int row, int col) {
        try {
            Recording r = (Recording) data.elementAt(row);
            switch (col) {
            case 0:
                return row + 1;
            case 1:
                return new String(r.dialog);
            case 2:
                return new String(r.name);
            case 3:
                if (!alaw) {
                    return new String(r.getTimeInSecString() + " sec");
                } else {
                    return "NA for alaw";
                }
            case 4:
            	if (recorder.showTranscript()) {
                    return new String(r.words);            		
            	} else {
            	       return r.recognition;           
            	}
            case 5:
                return new String(r.getAngerLabString());
            case 6:
                return new String(r.getAngerPredString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * inserts a new recording into the table.
     */
    public void insertRecording(Recording r) {
        int index;
        index = rowNum;
        data.add(rowNum, r);

        fireTableRowsInserted(index, index);
        rowNum++;
    }

    /**
     * removes the recording in specified row from the table.
     */
    public void deleteRecording(int row) {
        data.removeElementAt(row);
        fireTableRowsDeleted(row, row + 1);
    }

    /**
     * returns the name of a recording.
     */
    public String getNameAtRow(int row) {
        Recording r = (Recording) data.elementAt(row);
        return r.name;
    }

    /**
     * returns the recording.
     */
    public Recording getRecordingAtRow(int row) {
        Recording r = (Recording) data.elementAt(row);
        return r;
    }

    /**
     * removes all elements from table.
     */
    public void clear() {
        int oldRowNum = rowNum;
        rowNum = 0;
        data.removeAllElements();
        fireTableRowsDeleted(0, oldRowNum);
    }
}