package Logic;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class PanelCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof JPanel) {
            panel = (JPanel) value;
        } else {
            panel = new JPanel();
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return panel;
    }
}
