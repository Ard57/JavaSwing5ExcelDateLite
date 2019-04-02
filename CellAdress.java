public class CellAdress {
    public int row;
    public int column;

    public CellAdress(String rowStr, String columnStr) {
        setRow(rowStr);
        setColumn(columnStr);
    }

    public void setColumn(String str) {
        column = 0;
        int n = str.length();
        for (int i = 0; i < n; i++) {
            column += str.charAt(i) - 'A';
        }
    }

    public void setRow(String str) {
        row = Integer.parseInt(str) - 1;
    }
}
