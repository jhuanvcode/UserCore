package view.components;

import java.awt.Color;
import java.awt.Font;

/**
 * Paleta de cores, tipografia e diretrizes de design visual profissional (Clean Enterprise).
 */
public final class UIConstants {

    private UIConstants() {}

    // Tipografia base
    public static final String FONT_FAMILY = "Segoe UI";
    public static final Font FONT_HEADER_TITLE = new Font(FONT_FAMILY, Font.BOLD, 17);
    public static final Font FONT_HEADER_SUBTITLE = new Font(FONT_FAMILY, Font.PLAIN, 12);
    public static final Font FONT_SECTION_TITLE = new Font(FONT_FAMILY, Font.BOLD, 14);
    public static final Font FONT_LABEL = new Font(FONT_FAMILY, Font.BOLD, 12);
    public static final Font FONT_REGULAR = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, 12);
    public static final Font FONT_SMALL = new Font(FONT_FAMILY, Font.PLAIN, 11);

    // Cores de Superfície e Fundo
    public static final Color BG_APP = new Color(0xF8, 0xFA, 0xFC);          // Slate 50
    public static final Color BG_CARD = Color.WHITE;
    public static final Color BORDER_SUBTLE = new Color(0xE2, 0xE8, 0xF0);    // Slate 200
    public static final Color BORDER_STRONG = new Color(0xCB, 0xD5, 0xE1);    // Slate 300
    public static final Color BORDER_FOCUS = new Color(0x0F, 0x17, 0x2A);     // Slate 900

    // Cores de Texto
    public static final Color TEXT_TITLE = new Color(0x0F, 0x17, 0x2A);       // Slate 900
    public static final Color TEXT_MAIN = new Color(0x1E, 0x29, 0x3B);        // Slate 800
    public static final Color TEXT_MUTED = new Color(0x64, 0x74, 0x8B);       // Slate 500
    public static final Color TEXT_DISABLED = new Color(0x94, 0xA3, 0xB8);    // Slate 400

    // Botões
    public static final Color BTN_PRIMARY_BG = new Color(0x0F, 0x17, 0x2A);   // Deep Slate
    public static final Color BTN_PRIMARY_HOVER = new Color(0x33, 0x41, 0x55);
    public static final Color BTN_PRIMARY_FG = Color.WHITE;

    public static final Color BTN_SECONDARY_BG = Color.WHITE;
    public static final Color BTN_SECONDARY_HOVER = new Color(0xF1, 0xF5, 0xF9);
    public static final Color BTN_SECONDARY_BORDER = new Color(0xCB, 0xD5, 0xE1);
    public static final Color BTN_SECONDARY_FG = new Color(0x33, 0x41, 0x55);

    public static final Color BTN_DANGER_BG = new Color(0xFE, 0xF2, 0xF2);
    public static final Color BTN_DANGER_HOVER = new Color(0xFE, 0xE2, 0xE2);
    public static final Color BTN_DANGER_BORDER = new Color(0xFE, 0xCA, 0xCA);
    public static final Color BTN_DANGER_FG = new Color(0xB9, 0x1C, 0x1C);

    // Tabela
    public static final Color TABLE_HEADER_BG = new Color(0xF1, 0xF5, 0xF9);  // Slate 100
    public static final Color TABLE_HEADER_FG = new Color(0x47, 0x55, 0x69);
    public static final Color TABLE_ROW_ALT = new Color(0xF8, 0xFA, 0xFC);
    public static final Color TABLE_GRID = new Color(0xEE, 0xF2, 0xF6);
    public static final Color TABLE_SELECTION_BG = new Color(0xEE, 0xF2, 0xFF);
    public static final Color TABLE_SELECTION_FG = new Color(0x1E, 0x29, 0x3B);

    // Estados e Badges
    public static final Color BADGE_ONLINE_BG = new Color(0xEC, 0xFD, 0xF5);
    public static final Color BADGE_ONLINE_FG = new Color(0x04, 0x78, 0x57);
    public static final Color BADGE_DEMO_BG = new Color(0xFE, 0xF3, 0xC7);
    public static final Color BADGE_DEMO_FG = new Color(0xB4, 0x53, 0x09);
}
