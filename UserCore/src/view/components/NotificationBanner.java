package view.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Banner de notificação visual discreto para feedback de ações (sucesso, aviso ou erro).
 */
public class NotificationBanner extends JPanel {

    public enum Type {
        SUCCESS,
        ERROR,
        INFO
    }

    private final JLabel lblMessage = new JLabel();
    private Color bgColor = UIConstants.BG_APP;
    private Color borderColor = UIConstants.BORDER_SUBTLE;
    private Color fgColor = UIConstants.TEXT_MAIN;
    private int cornerRadius = 6;

    public NotificationBanner() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        lblMessage.setFont(UIConstants.FONT_REGULAR);
        add(lblMessage, BorderLayout.CENTER);
        setVisible(false);
    }

    public void showMessage(String message, Type type) {
        lblMessage.setText(message);
        
        switch (type) {
            case SUCCESS:
                bgColor = new Color(0xF0, 0xFD, 0xF4);
                borderColor = new Color(0xBB, 0xF7, 0xD0);
                fgColor = new Color(0x16, 0x65, 0x34);
                break;
            case ERROR:
                bgColor = new Color(0xFE, 0xF2, 0xF2);
                borderColor = new Color(0xFE, 0xCA, 0xCA);
                fgColor = new Color(0x99, 0x1B, 0x1B);
                break;
            case INFO:
                bgColor = new Color(0xF1, 0xF5, 0xF9);
                borderColor = new Color(0xCB, 0xD5, 0xE1);
                fgColor = new Color(0x33, 0x41, 0x55);
                break;
        }

        lblMessage.setForeground(fgColor);
        setVisible(true);
        revalidate();
        repaint();
    }

    public void clear() {
        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isVisible()) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }
}
