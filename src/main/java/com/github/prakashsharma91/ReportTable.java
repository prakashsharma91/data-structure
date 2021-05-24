package com.github.prakashsharma91;

import java.util.HashMap;
import java.util.Map;

public class ReportTable<K, Column, ColumnValue> {

    int rowId;

    /**
     * Every row has fields that have value
     */
    Map<Column, ColumnValue> columns;

    /**
     * Each row can have child rows
     */
    Map<K, ReportTable<K, Column, ColumnValue>> rows;

    public ReportTable(int rowId) {
        this.rowId = rowId;
        this.columns = new HashMap();
        this.rows = new HashMap();
    }

    public void addRow(Column column, ColumnValue value, int... level) throws InstantiationException, IllegalAccessException {
        ColumnValue val = (ColumnValue) value;
        /**
         * 1. update currentRow row
         */
        ColumnValue previousValue = columns.get(column);
        if (previousValue == null) {
            columns.put(column, val);
        } else {
            ((com.github.prakashsharma91.domain.ColumnValue) columns.get(column)).append(val);
        }

        /**
         * 2. create/update child rows
         */
        ReportTable currentRow = this;
        int len = level.length;
        for (int i = 0; i < len; i++) {
            int childRowId = level[i];
            ReportTable childRow = (ReportTable) currentRow.rows.get(childRowId);

            if (childRow == null) {
                childRow = new ReportTable(childRowId);
                childRow.columns.put(column, value.getClass().newInstance());
                currentRow.rows.put(childRowId, childRow);
            } else {
                if (childRow.columns.get(column) == null) {
                    /** childRow row present but column not present */
                    childRow.columns.put(column, value.getClass().newInstance());
                }
            }
            /** update all currentRow row of target rows */
            ((com.github.prakashsharma91.domain.ColumnValue) childRow.columns.get(column)).append(val);
            currentRow = childRow;
        }
    }

    /**
     * return parent row's one column data
     *
     * @param column
     * @return
     */
    public ColumnValue getColumnValue(Column column) throws InstantiationException, IllegalAccessException {
        if (columns.containsKey(column))
            return columns.get(column);
        else
            return (ColumnValue) column.getClass().newInstance();
    }

    /**
     * return nested level row data
     * example: get row level data for accountID, seriesID, InvestmentID
     *
     * @return
     */
    public Map<Column, ColumnValue> getAllColumns(int... rows) {
        int len = rows.length;
        ReportTable node = this;
        for (int i = 0; i < len; i++) {
            int childRowId = rows[i];
            node = (ReportTable) node.rows.get(childRowId);

            if (node == null) {
                throw new NullPointerException();
            }
        }
        return node.columns;
    }

    /**
     * @param column
     * @param level
     * @return
     */
    public ColumnValue getColumnValue(Column column, int... level) throws InstantiationException, IllegalAccessException {
        Map<Column, ColumnValue> columns = getAllColumns(level);
        if (columns.containsKey(column)) {
            return columns.get(column);
        } else {
            return (ColumnValue) columns.getClass().newInstance();
        }
    }
}
