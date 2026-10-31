package view.components;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * JTable customizada com design tabular limpo, espaçoso e contemporâneo.
 */
public class ModernTable extends JTable {

    public ModernTable(TableModel model) {
        super(model);
        init();
    }

    private void init() {
        setRowHeight(38);
        setShowVerticalLines(false);
        setShowHorizontalLines(true);
        setGridColor(UIConstants.TABLE_GRID);
        setSelectionBackground(UIConstants.TABLE_SELECTION_BG);
        setSelectionForeground(UIConstants.TABLE_SELECTION_FG);
        setFont(UIConstants.FONT_REGULAR);
        setFillsViewportHeight(true);
        setIntercellSpacing(new Dimension(0, 1));

        // Customização do Header
        JTableHeader header = getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        header.setFont(UIConstants.FONT_LABEL);
        header.setBackground(UIConstants.TABLE_HEADER_BG);
        header.setForeground(UIConstants.TABLE_HEADER_FG);
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new TableCellRenderer() {
            private final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(UIConstants.FONT_LABEL);
                label.setBackground(UIConstants.TABLE_HEADER_BG);
                label.setForeground(UIConstants.TABLE_HEADER_FG);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_SUBTLE));
                
                if (column == 0) {
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_SUBTLE),
                            BorderFactory.createEmptyBorder(0, 8, 0, 8)
                    ));
                } else {
                    label.setHorizontalAlignment(JLabel.LEFT);
                    label.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_SUBTLE),
                            BorderFactory.createEmptyBorder(0, 14, 0, 14)
                    ));
                }
                return label;
            }
        });

        // Customização dos dados das células
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(UIConstants.TABLE_SELECTION_BG);
                    c.setForeground(UIConstants.TABLE_SELECTION_FG);
                } else {
                    c.setBackground(row % 2 == 0 ? java.awt.Color.WHITE : UIConstants.TABLE_ROW_ALT);
                    c.setForeground(UIConstants.TEXT_MAIN);
                }

                if (column == 0) {
                    setHorizontalAlignment(JLabel.CENTER);
                    setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                } else {
                    setHorizontalAlignment(JLabel.LEFT);
                    setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
                }

                return c;
            }
        });
    }
}
