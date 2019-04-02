import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTableCell extends JLabel {
    public static final int DATE = 1;
    public static final int DATEOPERATION = 2;
    public static final int CELLOPERATION = 3;
    public static final int MIN = 4;
    public static final int MAX = 5;
    public static final int NULL = 0;

    private Object arg1, arg2;

    private GregorianCalendar date;
    //public String command;
    private int command;
    private String inputCommand;


    public DateTableCell() {
        date = null;
    }

    @Override
    public String toString() {
        if (command == NULL) {
            if (("").equals(inputCommand) || inputCommand == null) {
                return "";
            } else {
                return "ERROR!" + inputCommand;
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(date.get(Calendar.DAY_OF_MONTH)).append('.').append(date.get(Calendar.MONTH)).append('.').append(date.get(Calendar.YEAR));
            return stringBuilder.toString();
        }
    }

    public void count() {
        switch (command) {
            case DATE:
                date = (GregorianCalendar) arg1;
                break;
            case DATEOPERATION:
                date = (GregorianCalendar) arg1;
                date.add(Calendar.DAY_OF_MONTH, (int)arg2);
                break;
            case CELLOPERATION:
                date = ((DateTableCell)arg1).getDateCopy();
                date.add(Calendar.DAY_OF_MONTH ,(int)arg2);
                break;
        }
    }

    public void setDateArgs(int day, int month, int year) {
        if (date == null) {
            date = new GregorianCalendar();
        }
        date.set(Calendar.DAY_OF_MONTH, day);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.YEAR, year);
    }

    public GregorianCalendar getDateCopy() {
        GregorianCalendar ret = new GregorianCalendar();
        if (command != NULL) {
            ret.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        } else {
            ret.set(0, 0, 0);
        }
        return ret;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setDateArgs(GregorianCalendar date) {
        this.date = date;
    }

    public Object getArg1() {
        return arg1;
    }

    public void setArg1(Object arg1) {
        this.arg1 = arg1;
    }

    public Object getArg2() {
        return arg2;
    }

    public void setArg2(Object arg2) {
        this.arg2 = arg2;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getInputCommand() {
        return inputCommand;
    }

    public void setInputCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }
}
