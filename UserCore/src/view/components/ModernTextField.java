package view.components;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Campo de texto limpo, com cantos arredondados, placeholder discreto e feedback de foco.
 */
public class ModernTextField extends JTextField {

    private String placeholder = "";
    private boolean isFocused = false;
    private int cornerRadius = 6;

    public ModernTextField(int columns) {
        super(columns);
        init();
    }

    public ModernTextField(String placeholder, int columns) {
        super(columns);
        this.placeholder = placeholder;
        init();
    }

    private void init() {
        setFont(UIConstants.FONT_REGULAR);
        setForeground(UIConstants.TEXT_TITLE);
        setCaretColor(UIConstants.TEXT_TITLE);
        setBackground(Color.WHITE);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        setPreferredSize(new Dimension(getPreferredSize().width, 36));

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                isFocused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFocused = false;
                repaint();
            }
        });
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fundo
        g2.setColor(isEnabled() ? (isEditable() ? getBackground() : UIConstants.BG_APP) : UIConstants.BG_APP);
        g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        // Borda
        if (isFocused && isEditable() && isEnabled()) {
            g2.setColor(UIConstants.BORDER_FOCUS);
        } else {
            g2.setColor(UIConstants.BORDER_STRONG);
        }
        g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);

        // Placeholder
        if (getText().isEmpty() && !placeholder.isEmpty() && !isFocused) {
            Graphics2D gp = (Graphics2D) g.create();
            gp.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            gp.setColor(UIConstants.TEXT_MUTED);
            gp.setFont(getFont());
            int y = (getHeight() - gp.getFontMetrics().getHeight()) / 2 + gp.getFontMetrics().getAscent();
            gp.drawString(placeholder, 12, y);
            gp.dispose();
        }
    }
}
