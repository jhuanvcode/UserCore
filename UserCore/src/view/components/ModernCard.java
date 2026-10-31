package view.components;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Painel em estilo 'Card' moderno com cantos arredondados sutis e borda discreta.
 */
public class ModernCard extends JPanel {

    private int cornerRadius = 8;

    public ModernCard() {
        setOpaque(false);
        setBackground(UIConstants.BG_CARD);
    }

    public ModernCard(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false);
        setBackground(UIConstants.BG_CARD);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fundo do card
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        // Borda sutil
        g2.setColor(UIConstants.BORDER_SUBTLE);
        g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }
}
