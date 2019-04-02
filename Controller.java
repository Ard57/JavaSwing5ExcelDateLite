import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class Controller extends DefaultCellEditor implements TableCellEditor {
    static Pattern datePattern = Pattern.compile("(((0?[1-9])|(1[0-9])|(2[0-8]))[.]((0?[1-9])|(1[0-2]))[.](([1-9])([0-9]*)))|" +
            "((29|30)[.](0?1|(0?[3-9])|(1[0-2]))[.](([1-9])([0-9]*)))|" +
            "(31[.]((0?1)|(0?3)|(0?5)|(0?6)|(0?8)|(10)|(12))[.](([1-9])([0-9]*)))|" +
            "(((0?[1-9])|(1[0-9])|(2[0-8]))(/)((0?[1-9])|(1[0-2]))(/)(([1-9])([0-9]*)))|" +
            "((29|30)(/)(0?1|(0?[3-9])|(1[0-2]))(/)(([1-9])([0-9]*)))|" +
            "(31(/)((0?1)|(0?3)|(0?5)|(0?6)|(0?8)|(10)|(12))(/)(([1-9])([0-9]*)))");
    static Pattern dateOperationPattern = Pattern.compile("(=)((((0?[1-9])|(1[0-9])|(2[0-8]))[.]((0?[1-9])|(1[0-2]))[.](([1-9])([0-9]*)))|" +
            "((29|30)[.](0?1|(0?[3-9])|(1[0-2]))[.](([1-9])([0-9]*)))|" +
            "(31[.]((0?1)|(0?3)|(0?5)|(0?6)|(0?8)|(10)|(12))[.](([1-9])([0-9]*)))|" +
            "(((0?[1-9])|(1[0-9])|(2[0-8]))(/)((0?[1-9])|(1[0-2]))(/)(([1-9])([0-9]*)))|" +
            "((29|30)(/)(0?1|(0?[3-9])|(1[0-2]))(/)(([1-9])([0-9]*)))|" +
            "(31(/)((0?1)|(0?3)|(0?5)|(0?6)|(0?8)|(10)|(12))(/)(([1-9])([0-9]*))))([+–\\-])(([1-9])([0-9]*)|[0])");
    static Pattern cellOperetionPattern = Pattern.compile("(=)([A-Z]+)(([1-9])([0-9]*))([+–\\-])(([1-9])([0-9]*)|[0])");

    Model model;
    DateTableCell cell;
    JTextField textField;
    String command;

    public Controller(JTextField textField, Model model) {
        super(textField);
        this.textField = textField;
        this.model = model;
        cell = new DateTableCell();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        cell = model.getDateTableCell(row - 1, column - 1);
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }


    @Override
    public boolean stopCellEditing() {
        command = textField.getText();
        cell.setInputCommand(command);
        cell.setCommand(DateTableCell.NULL);
        if (parseCommand()) {
            textField.setText(cell.toString());
        } else {
            if (command.contains("ERROR!")) {
                textField.setText(command);
            } else {
                textField.setText("ERROR!" + command);
            }
        }
        return super.stopCellEditing();
    }

    public DateTableCell getCell() {
        return cell;
    }

    private boolean parseCommand() {
        if (datePattern.matcher(command).matches()) {
            cell.setCommand(DateTableCell.DATE);
            String[] dateArgs = command.split("[./]");
            GregorianCalendar date = new GregorianCalendar();
            date.set(Integer.parseInt(dateArgs[2]), Integer.parseInt(dateArgs[1]), Integer.parseInt(dateArgs[0]));
            cell.setArg1(date);
            cell.count();
            return true;
        } else if (dateOperationPattern.matcher(command).matches()) {
            cell.setCommand(DateTableCell.DATEOPERATION);
            String[] args = new String[4];
            int i = 1, j = 1;
            while (command.charAt(i) != '.' && command.charAt(i) != '/') {
                i++;
            }
            args[0] = command.substring(1, i);
            i++;
            j = i;
            while (command.charAt(i) != '.' && command.charAt(i) != '/') {
                i++;
            }
            args[1] = command.substring(j, i);
            i++;
            j = i;
            while (command.charAt(i) != '+' && command.charAt(i) != '-' && command.charAt(i) != '–') {
                i++;
            }
            args[2] = command.substring(j, i);
            args[3] = command.substring(i);
            GregorianCalendar date = new GregorianCalendar();
            date.set(Integer.parseInt(args[2]), Integer.parseInt(args[1]), Integer.parseInt(args[0]));
            cell.setArg1(date);
            cell.setArg2(Integer.parseInt(args[3]));
            cell.count();
            return true;
        } else if (cellOperetionPattern.matcher(command).matches()) {
            cell.setCommand(DateTableCell.CELLOPERATION);
            String[] args = new String[3];
            int i = 1, j = 1;
            while (command.charAt(i) >= '0' && command.charAt(i) <= '9') {
                i++;
            }
            i++;
            args[0] = command.substring(1, i);
            j = i;
            while (command.charAt(i) != '+' && command.charAt(i) != '-' && command.charAt(i) != '–') {
                i++;
            }
            args[1] = command.substring(j, i);
            args[2] = command.substring(i);
            CellAdress cellAdress = new CellAdress(args[1], args[0]);
            cell.setArg1(model.getDateTableCell(cellAdress.row, cellAdress.column));
            cell.setArg2(Integer.parseInt(args[2]));
            cell.count();
            return true;
        }
        return false;
    }
}
