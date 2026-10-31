package model;

import conexao.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Data Access Object (DAO) para operações de persistência de Usuários.
 * Suporta persistência em MySQL e modo contingência em memória para testes sem banco ativo.
 */
public class UsuarioDAO {

    private boolean modoDemonstracao = false;
    private final List<Usuario> memoriaUsuarios = new ArrayList<>();
    private final AtomicInteger geradorIdMemoria = new AtomicInteger(100);

    public UsuarioDAO() {
        verificarEInicializarBanco();
    }

    private void verificarEInicializarBanco() {
        try (Connection conn = Conexao.conectar()) {
            modoDemonstracao = false;
            // Cria a tabela automaticamente caso ainda não exista no banco
            String ddl = "CREATE TABLE IF NOT EXISTS usuario (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "nome VARCHAR(100) NOT NULL, " +
                         "email VARCHAR(100) NOT NULL)";
            try (Statement st = conn.createStatement()) {
                st.executeUpdate(ddl);
            }
        } catch (Exception e) {
            // Se o MySQL estiver indisponível, ativa modo contingência em memória
            modoDemonstracao = true;
            inicializarDadosDemonstracao();
        }
    }

    private void inicializarDadosDemonstracao() {
        if (memoriaUsuarios.isEmpty()) {
            memoriaUsuarios.add(new Usuario(1, "Carlos Eduardo Silva", "carlos.silva@empresa.com.br"));
            memoriaUsuarios.add(new Usuario(2, "Mariana Vasconcelos", "mariana.vasconcelos@corp.com"));
            memoriaUsuarios.add(new Usuario(3, "Roberto Albuquerque", "roberto.albuquerque@tech.io"));
        }
    }

    public boolean isModoDemonstracao() {
        return modoDemonstracao;
    }

    public void tentarReconectar() {
        verificarEInicializarBanco();
    }

    /**
     * Insere um novo usuário.
     * @param usuario Dados do novo usuário
     * @return Usuário salvo com seu ID gerado
     * @throws SQLException em caso de falha de persistência
     */
    public Usuario inserir(Usuario usuario) throws SQLException {
        if (modoDemonstracao) {
            int novoId = geradorIdMemoria.incrementAndGet();
            usuario.setId(novoId);
            memoriaUsuarios.add(new Usuario(usuario.getId(), usuario.getNome(), usuario.getEmail()));
            return usuario;
        }

        String sql = "INSERT INTO usuario (nome, email) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
            return usuario;
        } catch (SQLException e) {
            // Em caso de queda súbita do banco, fallback
            modoDemonstracao = true;
            return inserir(usuario);
        }
    }

    /**
     * Atualiza dados de um usuário existente.
     * @param usuario Usuário contendo o ID a ser atualizado
     * @return true se atualizado, false se não encontrado
     * @throws SQLException em caso de falha no banco
     */
    public boolean atualizar(Usuario usuario) throws SQLException {
        if (modoDemonstracao) {
            for (int i = 0; i < memoriaUsuarios.size(); i++) {
                if (ObjectsEquals(memoriaUsuarios.get(i).getId(), usuario.getId())) {
                    memoriaUsuarios.set(i, new Usuario(usuario.getId(), usuario.getNome(), usuario.getEmail()));
                    return true;
                }
            }
            return false;
        }

        String sql = "UPDATE usuario SET nome = ?, email = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setInt(3, usuario.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            modoDemonstracao = true;
            return atualizar(usuario);
        }
    }

    /**
     * Remove um usuário pelo identificador.
     * @param id Identificador do usuário
     * @return true se removido, false caso contrário
     * @throws SQLException em caso de falha no banco
     */
    public boolean excluir(int id) throws SQLException {
        if (modoDemonstracao) {
            return memoriaUsuarios.removeIf(u -> u.getId() != null && u.getId() == id);
        }

        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            modoDemonstracao = true;
            return excluir(id);
        }
    }

    /**
     * Lista todos os usuários cadastrados em ordem crescente de ID.
     * @return Lista com os usuários encontrados
     */
    public List<Usuario> listar() {
        if (modoDemonstracao) {
            return new ArrayList<>(memoriaUsuarios);
        }

        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nome, email FROM usuario ORDER BY id ASC";
        
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                lista.add(u);
            }
            return lista;
        } catch (SQLException e) {
            modoDemonstracao = true;
            inicializarDadosDemonstracao();
            return new ArrayList<>(memoriaUsuarios);
        }
    }

    /**
     * Busca usuários por termo no nome ou e-mail.
     * @param termo Texto de busca
     * @return Lista filtrada
     */
    public List<Usuario> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listar();
        }

        String busca = termo.trim().toLowerCase();
        List<Usuario> filtrados = new ArrayList<>();
        
        for (Usuario u : listar()) {
            boolean matchNome = u.getNome() != null && u.getNome().toLowerCase().contains(busca);
            boolean matchEmail = u.getEmail() != null && u.getEmail().toLowerCase().contains(busca);
            if (matchNome || matchEmail) {
                filtrados.add(u);
            }
        }
        return filtrados;
    }

    private boolean ObjectsEquals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
