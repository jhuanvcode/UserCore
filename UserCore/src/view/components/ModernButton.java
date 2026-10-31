package view.components;

import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Botão com acabamento visual minimalista, arredondado e sem excessos.
 */
public class ModernButton extends JButton {

    public enum Style {
        PRIMARY,
        SECONDARY,
        DANGER
    }

    private final Style style;
    private boolean isHovered = false;
    private boolean isPressed = false;
    private int cornerRadius = 6;

    public ModernButton(String text, Style style) {
        super(text);
        this.style = style;
        init();
    }

    private void init() {
        setFont(UIConstants.FONT_BUTTON);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(8, 16, 8, 16));
        setPreferredSize(new Dimension(getPreferredSize().width, 36));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    isHovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isEnabled()) {
                    isPressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        if (!isEnabled()) {
            g2.setColor(UIConstants.BORDER_SUBTLE);
            g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
            setForeground(UIConstants.TEXT_DISABLED);
        } else {
            switch (style) {
                case PRIMARY:
                    g2.setColor(isPressed ? UIConstants.TEXT_TITLE : (isHovered ? UIConstants.BTN_PRIMARY_HOVER : UIConstants.BTN_PRIMARY_BG));
                    g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
                    setForeground(UIConstants.BTN_PRIMARY_FG);
                    break;

                case SECONDARY:
                    g2.setColor(isHovered ? UIConstants.BTN_SECONDARY_HOVER : UIConstants.BTN_SECONDARY_BG);
                    g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
                    g2.setColor(UIConstants.BTN_SECONDARY_BORDER);
                    g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
                    setForeground(UIConstants.BTN_SECONDARY_FG);
                    break;

                case DANGER:
                    g2.setColor(isHovered ? UIConstants.BTN_DANGER_HOVER : UIConstants.BTN_DANGER_BG);
                    g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
                    g2.setColor(UIConstants.BTN_DANGER_BORDER);
                    g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
                    setForeground(UIConstants.BTN_DANGER_FG);
                    break;
            }
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
