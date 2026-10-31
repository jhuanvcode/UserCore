package view.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Indicador visual (Pill) para exibir status do sistema ou do banco de dados.
 */
public class StatusBadge extends JPanel {

    private final JLabel lblText = new JLabel();
    private Color dotColor = UIConstants.BADGE_ONLINE_FG;
    private Color bgColor = UIConstants.BADGE_ONLINE_BG;
    private Color fgColor = UIConstants.BADGE_ONLINE_FG;

    public StatusBadge() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 6, 4));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));

        lblText.setFont(UIConstants.FONT_SMALL);
        add(lblText);
    }

    public void setStatus(boolean online, String text) {
        if (online) {
            dotColor = new Color(0x10, 0xB9, 0x81);
            bgColor = UIConstants.BADGE_ONLINE_BG;
            fgColor = UIConstants.BADGE_ONLINE_FG;
        } else {
            dotColor = new Color(0xF5, 0x9E, 0x0B);
            bgColor = UIConstants.BADGE_DEMO_BG;
            fgColor = UIConstants.BADGE_DEMO_FG;
        }

        lblText.setText(text);
        lblText.setForeground(fgColor);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fundo arredondado
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, width - 1, height - 1, height, height);

        // Ponto indicador sutil
        g2.setColor(dotColor);
        int dotSize = 6;
        int dotY = (height - dotSize) / 2;
        g2.fillOval(10, dotY, dotSize, dotSize);

        g2.dispose();
        super.paintComponent(g);
    }
}
