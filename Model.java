import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Model extends DefaultTableModel {
    public final int rowsCount;
    public final int columnsCount;
    private DateTableCell[][] dateTable;

    public Model(int rowsCount, int columnsCount) {
        this.rowsCount = rowsCount + 1;
        this.columnsCount = columnsCount + 1;
        dateTable = new DateTableCell[this.rowsCount][this.columnsCount];
        this.setRowCount(this.rowsCount);
        String[] columnName = new String[this.columnsCount];
        addColumn("");
        for (int i = 1; i < this.columnsCount; i++) {
            columnName[i] = ((Character) ((char) ('A' - 1 + i))).toString();
            this.addColumn(columnName[i]);
            this.setValueAt(columnName[i], 0, i);
        }
        for (int i = 1; i < this.rowsCount; i++) {
            this.setValueAt(i, i, 0);
        }
        fillModel();
    }

    private void fillModel() {
        for (int i = 1; i < rowsCount; i++) {
            for (int j = 1; j < columnsCount; j++) {
                if (dateTable[i-1][j-1] == null) {
                    dateTable[i-1][j-1] = new DateTableCell();
                }
                this.setValueAt(dateTable[i-1][j-1].toString(), i, j);
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public DateTableCell getDateTableCell(int row, int col) {
        return dateTable[row][col];
    }

    public void setDateTableCell(DateTableCell dateTableCell, int row, int col) {
        dateTable[row][col] = dateTableCell;
    }
}
