import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class View extends JFrame {

    public void c() {
        setSize(400, 400);
        Model model = new Model(4, 4);
        JTable table = new JTable(model);

        Controller controller = new Controller(new JTextField(), model);

        table.setDefaultEditor(String.class, controller);
        add(table);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
