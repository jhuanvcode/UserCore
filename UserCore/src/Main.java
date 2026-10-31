import controller.UsuarioController;
import view.TelaUsuario;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            TelaUsuario tela = new TelaUsuario();
            new UsuarioController(tela);
            tela.setVisible(true);
        });
    }
}
