/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

/**
 *
 * @author administrator
 */

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 * Basic JTable with some convenience methods.
 * @author dvargo
 */
public class BaseTableFunctionClass extends JTable
{

    /**
     * The pop up menu for the table
     */
    private JPopupMenu Pmenu = new JPopupMenu();

    /**
     * Holds the currently being/last row clicked in the table.
     */
    private int rowClicked = -1;

    /**
     * Holds the currently being/last column clicked in the table.
     */
    private int colClicked = -1;

    /**
     * If the table is editable or not
     */
    private boolean isEditable = true;

    /**
     * Default construct that uses the default table model
     */
    public BaseTableFunctionClass()
    {
        super();
        addPopUpMenu();
    }

    /**
     * Constructor that lets you set the table model to use
     * @param model The table model you would like to use
     */
    public BaseTableFunctionClass(AbstractTableModel model)
    {
        super(model);
        addPopUpMenu();
    }

    /**
     * Returns the contents of of a cell as a string
     * @param row The row of the cell
     * @param col The col of the cell
     * @return The string representation of a cell
     */
    public String getValueforCell(int row, int col)
    {
        return (String) getModel().getValueAt(row, col).toString();
    }

    /**
     * Inserts a single row with all cells empty into the table
     */
    public void insertRow()
    {
        //get the model the table is using
        TableModel temp = getModel();
        //cast it so we can do something useful with it
        DefaultTableModel tm = (DefaultTableModel) temp;
        tm.addRow(new Object[]{});
        resizeAndRepaint();
    }

    /**
     * Inserts multiple rows with each cell empty into the table
     * @param numberOfRowsToAdd The number of rows to add to the table
     */
    public void insertRow(int numberOfRowsToAdd)
    {
        //get the model the table is using
        TableModel temp = getModel();
        //cast it so we can do something useful with it
        DefaultTableModel tm = (DefaultTableModel) temp;
        for (int i = 0; i < numberOfRowsToAdd; i++)
        {
            tm.addRow(new Object[]{});
        }
    }
        /**
     * Insets a row with the given data as the rows contents
     * @param data The data to input into the row
     */
    public void insertRow(String[] data)
    {
        insertRow();
        int row = getRowCount() - 1;
        int colCounter = 0;
        for(String currData : data)
        {
            setValueAt(currData,row,colCounter);
            colCounter++;
            if(colCounter  >= getColumnCount())
            {
                break;
            }
        }
    }


    /**
     * Inserts a column at the end of the columns.
     * @param colName The name for this column
     */
    public void insertColumn(String colName)
    {
        //get the model the table is using
        TableModel temp = getModel();
        //cast it so we can do something useful with it
        DefaultTableModel tm = (DefaultTableModel) temp;
        tm.addColumn(colName, new Object[]
                {
                });
        getTableHeader().resizeAndRepaint();
        resizeAndRepaint();
    }

    /**
     * Inserts a multiple columns into the table
     * @param numberOfColumns The number of columns to add to the table
     */
    public void insertColumn(int numberOfColumns)
    {
        //get the model the table is using
        TableModel temp = getModel();
        //cast it so we can do something useful with it
        DefaultTableModel tm = (DefaultTableModel) temp;
        for (int i = 0; i < numberOfColumns; i++)
        {
            tm.addColumn(new Integer(i).toString(), new Object[]
                    {
                    });
            getTableHeader().resizeAndRepaint();
        }
    }

    /**
     * Deletes a row from the table at a given index
     * @param rowIndex The row index to delete. Row index 0 is below the column
     * headers
     */
    public void deleteRow(int rowIndex)
    {
        if (rowIndex < 0)
        {
            return;
        }
        //get the model the table is using
        TableModel temp = getModel();
        //cast it so we can do something useful with it
        DefaultTableModel tm = (DefaultTableModel) temp;
        tm.removeRow(rowIndex);
        getTableHeader().resizeAndRepaint();
    }

    /**
     * Delete a column from the table
     * @param colIndex The index of the column to delete
     */
    public void deleteColumn(int colIndex)
    {
        if (colIndex < 1)
        {
            return;
        }
        removeColumn(getColumnModel().getColumn(colIndex));
        getTableHeader().resizeAndRepaint();
    }

    /**
     * Changes the name of a column
     * @param colIndex The index of the column to change name
     * @param newName The name of the column
     */
    public void setColumnName(int colIndex, String newName)
    {
        getColumnModel().getColumn(colIndex).setHeaderValue(newName);
        getTableHeader().resizeAndRepaint();
    }

    /**
     * Sets the columns of the table
     */
    public void setColumnNames(String[] names)
    {
        setNumberOfCols(names.length);
        for (int i = 0; i < names.length; i++)
        {
            setColumnName(i, names[i]);
        }
    }

    /**
     * Sets the number of row in the table to a certain value. The rows are added
     * to the end of the table
     * @param numberOfRows The number of rows that should be in the table
     *
     */
    public void setNumberOfRows(int numberOfRows)
    {
        if (numberOfRows == getRowCount() || numberOfRows < 0) //user requested the same number of rows
        {
            return;
        }
        else if (numberOfRows  >= getRowCount()) //user requested more rows
        {
            int numberOfRowsToAdd = Math.abs(getRowCount() - numberOfRows);
            insertRow(numberOfRowsToAdd);
        }
        else if (numberOfRows < getRowCount()) //user request les rows
        {
            int numberOfRowsToDelete = Math.abs(getRowCount() - numberOfRows);
            for (int i = 0; i < numberOfRowsToDelete; i++)
            {
                deleteRow(getRowCount() - 1);
            }
        }
        resizeAndRepaint();
    }

    /**
     * Sets the number of cols for the table. The columns are added and deleted
     * at the end of the table
     * @param numberOfCols
     */
    public void setNumberOfCols(int numberOfCols)
    {
        //user requested the same number of rows
        if (numberOfCols == getColumnCount() || numberOfCols < 0)
        {
            return;
        }
        else if (numberOfCols  >= getColumnCount()) //user requested more rows
        {
            int numberOfColsToAdd = Math.abs(getColumnCount() - numberOfCols);
            insertColumn(numberOfColsToAdd);
        }
        else if (numberOfCols   < getColumnCount()) //user request les rows
        {
            int numberOfColsToDelete = Math.abs(getColumnCount() - numberOfCols);
            for (int i = 0; i  <  numberOfColsToDelete; i++)
            {
                deleteColumn(getColumnCount() - 1);
            }
        }
        resizeAndRepaint();
    }

    /**
     * Returns the string name value of the column
     * @param colIndex The index of the column whose name you want
     * @return The name of the column
     */
    public String getColumnName(int colIndex)
    {
        return (String) getColumnModel().getColumn(colIndex).getHeaderValue();
    }

    /**
     * Returns an array holding all the column names
     * @return The column names
     */
    public String[] getColumnNames()
    {
        String[] colNames = new String[getColumnCount()];
        for (int i = 0; i  <  getColumnCount(); i++)
        {
            colNames[i] = getColumnName(i);
        }
        return colNames;
    }

    /**
     * Gets the index in the table for a column with the given name
     * @param colName The name of the column
     * @return The index of the column in the table or -1 if it is not found
     */
    public int getColumnIndex(String colName)
    {
        String [] names = getColumnNames();
        int retVal = -1;
        for(int i = 0; i <  names.length; i++)
        {
            if(names[i].equals(colName))
            {
                retVal = i;
            }
        }
        return retVal;
    }

    /**
     * Allows the user to paste multiple lines and columns into the table
     * @param row The row where the paste is to start from
     * @param col The column where the paste is to start from
     */
    public void paste(int row, int col)
    {
        //used to determine what info goes in which coloumn of the table
        String newLineDelim = "\n";
        //used to determine what info goes in what cell in a row
        String nextElementDelim = "\t";

        String pasteString = "";//ClipboardUtility.getClipboardContents(); //get this info on the clipboard


        //see if we can handle parsing this string
        if (pasteString.contains(newLineDelim) == false || pasteString.contains(nextElementDelim) == false)
        {
            //paste it all into the first cell if we done recognize it
            setValueAt(pasteString, row, col);
        }

        //parse the columns first
        String[] pasteSplitString = pasteString.split(newLineDelim);

        String[] temp = null;
        String[][] finalInfo = new String[pasteSplitString.length][getColumnCount()];

        //now parse the columns into the individual cells
        for (int i = 0; i  <  pasteSplitString.length; i++)
        {
            temp = pasteSplitString[i].split(nextElementDelim);
            finalInfo[i] = temp;
        }

        //put this info in the table
        for (int i = 0; i  <  finalInfo.length; i++)
        {
            for (int j = 0; j  <  finalInfo[i].length; j++)
            {
                //its in a try catch incase the indexes do not match up, allows us to do a partial paste
                try
                {
                    //see if you need to add a row to the table
                    if (i + row  >=  getRowCount() - 1)
                    {
                        insertRow();
                    }
                    getModel().setValueAt(finalInfo[i][j], i + row, j + col);
                }
                catch (Exception e)
                {
                    System.err.println("Could not paste");
                }
            }
        }
    }

    /**
     * Copies the text in the selected cells in the table to the clipboard
     */
    public void copy()
    {
        //were going to use the \t for next column and \n for next row format
        String newCol = "\t";
        String newRow = "\n";

        //index to start coping from
        int rowStart = getSelectedRow();
        int colStart = getSelectedColumn();

        //index to stop copying from
        int colEnd = colStart + getSelectedColumnCount();
        int rowEnd = rowStart + getSelectedRowCount();

        //will hold the string of data were copying
        StringBuilder copyString = new StringBuilder();

        //copy it cell by cell
        for (int row = rowStart; row  <  rowEnd; row++)
        {
            if (row != rowStart)
            {
                copyString.append(newRow);
            }
            for (int col = colStart; col  <  colEnd; col++)
            {
                String currCellVal = (String) (getValueAt(row, col));
                //so you dont get "null" copied to the clipboard
                if (currCellVal == null)
                {
                    currCellVal = "";
                }
                copyString.append(currCellVal);
                if (col + 1  <  colEnd)
                {
                    copyString.append(newCol);
                }
            }
        }

        //post the string to the clipboard
//        ClipboardUtility.setClipboardContents(copyString.toString());

    }

    /**
     * Returns the table as a array of strings with the data separated by a delimiter.
     * Each string in the array represents a row in the table.
     * @param delim The delimiter to separate the columns by
     * @return The data in the table
     */
    public String[] getTableData(String delim)
    {
        String[] data = new String[getRowCount()];

        int colCount = getColumnCount();
        int rowCount = getRowCount();

        //get the row data
        StringBuffer currRow = new StringBuffer();
        for (int row = 0; row  <  rowCount; row++)
        {
            for (int col = 0; col  <  colCount; col++)
            {
                currRow.append(getValueAt(row, col));
                currRow.append(delim);
            }
            data[row] = currRow.toString();
            currRow = new StringBuffer();
        }
        return data;
    }

    /**
     * Returns the data in the table as a 2d string array [row][col]
     * @return The data in the table
     */
    public String[][] getTableData()
    {
        String[][] data = new String[this.getRowCount()][this.getColumnCount()];
        for (int r = 0; r  <  getRowCount(); r++)
        {
            for (int c = 0; c  <  getColumnCount(); c++)
            {
                data[r][c] = getValueforCell(r, c);
            }
        }
        return data;
    }

    /**
     * Return a row from the table as a array of strings
     * @param rowIndex The index of the row you would like
     * @return Returns the row from the table as an array of strings or null if
     * the index is invalid
     */
    public String[] getRowData(int rowIndex)
    {
        //test the index
        if (rowIndex  >=  getRowCount() || rowIndex  <  0)
        {
            return null;
        }
        ArrayList < String > data = new ArrayList < String > ();
        for (int c = 0; c  <  getColumnCount(); c++)
        {
            data.add((String) getValueforCell(rowIndex, c));
        }
        String[] retVal = new String[data.size()];
        for (int i = 0; i  <  retVal.length; i++)
        {
            retVal[i] = data.get(i);
        }
        return retVal;
    }

    /**
     * Returns the data for a row as a hash map with the column being the key
     * and the cell being the value
     * @param rowIndex The row to get the data for
     * @return A map represneting the row
     */
    public Map < String,String >  getRowDataAsMap(int rowIndex)
    {
        Map < String,String >  retVal = new HashMap < String, String >();
        String [] rowData = getRowData(rowIndex);
        String [] colData = getColumnNames();
        for(int i = 0; i  <  colData.length; i++)
        {
            retVal.put(colData[i],rowData[i]);
        }
        return retVal;
    }

    /**
     * Sets the data in the table
     * @param data String array of delimited data for the table
     * @param delim The delimeter
     */
    public void setData(String[] data, String delim)
    {
        setNumberOfCols(parseTableData(data[0], delim).length);
        setNumberOfRows(data.length);
        String[] currRow;
        for (int r = 0; r  <  data.length; r++)
        {
            currRow = parseTableData(data[r], delim);
            for (int c = 0; c  <  currRow.length; c++)
            {
                setValueAt(currRow[c], r, c);
            }
        }
    }

    /**
     * Sets the values in the table
     * @param data
     */
    public void setData(String[][] data)
    {
        setNumberOfRows(data.length);
        setNumberOfCols(data[0].length);
        for (int r = 0; r  <  data.length; r++)
        {
            for (int c = 0; c  <  data[r].length; c++)
            {
                setValueAt(data[r][c], r, c);
            }
        }
    }

    /**
     * Sets the data for a table starting at a given location in the table
     * @param data The delimited data to add to the table
     * @param delim The delimiter for the data
     * @param row The row where to start adding the data
     * @param col The col where to start adding the table
     */
    public void setData(String[] data, String delim, int row, int col)
    {
        setNumberOfCols(col + parseTableData(data[0], delim).length);
        setNumberOfRows(row + data.length);
        String[] currRow;
        for (int r = row; r  <  data.length; r++)
        {
            currRow = parseTableData(data[r], delim);
            for (int c = col; c  <  currRow.length; c++)
            {
                setValueAt(currRow[c], r, c);
            }
        }
    }

    /**
     * Removes all the rows in the table
     */
    public void clearTable()
    {
        DefaultTableModel dm = (DefaultTableModel) getModel();
        dm.getDataVector().removeAllElements();
    }

    /**
     * Adds a menu item to the pop up menu with an action listener.
     * @param newItem The menu item to add
     * @param actionListener The actionlistener to add to this menu item
     */
    public void addPopUpMenuItem(JMenuItem newItem, ActionListener actionListener)
    {
        newItem.addActionListener(actionListener);
        Pmenu.add(newItem);
    }

    /**
     * Adds a menu item to the pop up menu. Not this can be as simple Jseparator
     * or a menu item with a actionlistener already attached.
     * @param newItem The menu item to add
     */
    public void addPopUpMenuItem(JMenuItem newItem)
    {
        Pmenu.add(newItem);
    }

    /**
     * Adds a separator to the JMenu
     */
    public void addPopUpMenuSeperator()
    {
        Pmenu.add(new JSeparator());
    }

    /**
     * Gets the current row being clicked
     * @return The current row being clicked
     */
    public int getRowClicked()
    {
        return rowClicked;
    }

    /**
     * Gets the current column being clicked
     * @return The current column being clicked
     */
    public int getColClicked()
    {
        return colClicked;
    }

    /**
     * Adds the pop up menu and additional functionality to the table. The key
     * thing here is that this function adds the action listeners that determine
     * what row and what column is being clicked in the table.
     */
    private void addPopUpMenu()
    {
        //sets the popup menu so it will show
        addMouseListener(new MouseAdapter()
        {

            public void mouseReleased(MouseEvent e)
            {
                rowClicked = rowAtPoint(e.getPoint());
                colClicked = columnAtPoint(e.getPoint());
                if (e.isPopupTrigger())
                {
                    Pmenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseClicked(MouseEvent e)
            {
                rowClicked = rowAtPoint(e.getPoint());
                colClicked = columnAtPoint(e.getPoint());
            }
        });
    }

    /**
     * Parses out the table data from a string (usually xml) using the passed in
     * delimiter. This is pretty much just like the sting classes split method
     * @param data The data to parse
     * @param delim The delimiter to use
     * @return An array of the tokens parsed out of the data string
     */
    public String[] parseTableData(String data, String delim)
    {
        ArrayList < String >  tempArrayHolder = new ArrayList < String> ();
        StringTokenizer st = new StringTokenizer(data, delim);
        while (st.hasMoreTokens())
        {
            tempArrayHolder.add(st.nextToken());
        }
        String[] retValArray = new String[tempArrayHolder.size()];
        for (int i = 0; i  <  tempArrayHolder.size(); i++)
        {
            retValArray[i] = tempArrayHolder.get(i);
        }
        return retValArray;
    }

    /**
     * Determines if the table is editable or not. Note that this is for the whole
     * table and not individual cells.
     * @param isEditable True if the table is editable, false if the table is not
     * editable
     */
    public void setEditable(boolean isEditable)
    {
        this.isEditable = isEditable;
    }


    /**
     * Overridden to allow the hole table to be set to non editable or editable.
     * Depends on the value of isEditable
     * @param row The for the cell to see if it is editable
     * @param col The col for the cell to see if it is editable
     * @return True if the cell is editable false otherwise
     */
    @Override
    public boolean isCellEditable(int row, int col)
    {
        if (isEditable == false)
        {
            return false;
        }
        return super.isCellEditable(row, col);
    }

    /**
     * Returns the indexes of the selected rows in the table
     * Overridden due to the inconsistent behavior of the default
     * @return A list of the selected rows
     */
    @Override
    public int[] getSelectedRows()
    {
        List < Integer >  temp = new ArrayList < Integer > ();
        for(int i = 0; i  <  getRowCount(); i++)
        {
            if(isRowSelected(i))
            {
                temp.add(i);
            }
        }
        int [] retVal = new int[temp.size()];
        for(int i =0; i  <  temp.size(); i++)
        {
            retVal[i] = temp.get(i);
        }
        return retVal;
    }

}