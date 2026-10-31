package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String HOST = "localhost";
    private static final int PORTA = 3306;
    private static final String BANCO = "mvc_crud";
    private static final String PARAMS = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&connectTimeout=3000";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORTA + "/" + BANCO + PARAMS;
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }
    }

    /**
     * Obtém uma conexão com o MySQL.
     * @return Conexão ativa
     * @throws SQLException Em caso de falha de autenticação, servidor offline ou banco inexistente
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Testa se o MySQL está acessível.
     * @return true se conectou com sucesso, false caso contrário
     */
    public static boolean testarConexao() {
        try (Connection conn = conectar()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public static String getUrlInfo() {
        return HOST + ":" + PORTA + "/" + BANCO;
    }
}
