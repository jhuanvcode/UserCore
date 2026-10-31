package controller;

import conexao.Conexao;
import model.Usuario;
import model.UsuarioDAO;
import view.TelaUsuario;
import view.components.NotificationBanner.Type;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controlador da aplicação seguindo o padrão MVC.
 * Gerencia validação de entradas, eventos de interface e fluxo de persistência de dados.
 */
public class UsuarioController {

    private final TelaUsuario view;
    private final UsuarioDAO dao;
    private TableRowSorter<javax.swing.table.DefaultTableModel> rowSorter;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$"
    );

    public UsuarioController(TelaUsuario view) {
        this(view, new UsuarioDAO());
    }

    public UsuarioController(TelaUsuario view, UsuarioDAO dao) {
        this.view = view;
        this.dao = dao;

        configurarOrdenadorTabela();
        configurarEventos();
        atualizarStatusConexao();
        listar();
    }

    private void configurarOrdenadorTabela() {
        rowSorter = new TableRowSorter<>(view.modelo);
        view.tabela.setRowSorter(rowSorter);
    }

    private void configurarEventos() {
        // Ações de Botões
        view.btnSalvar.addActionListener(e -> salvar());
        view.btnEditar.addActionListener(e -> editar());
        view.btnExcluir.addActionListener(e -> excluir());
        view.btnLimpar.addActionListener(e -> limparFormulario());
        view.btnListar.addActionListener(e -> {
            dao.tentarReconectar();
            atualizarStatusConexao();
            listar();
            view.bannerFeedback.showMessage("Lista atualizada com sucesso.", Type.INFO);
        });

        // Evento de seleção na tabela
        view.tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarUsuarioSelecionado();
            }
        });

        // Busca em tempo real
        view.txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aplicarFiltro();
            }
        });
    }

    private void atualizarStatusConexao() {
        if (dao.isModoDemonstracao()) {
            view.badgeStatus.setStatus(false, "Modo Memória (MySQL Offline)");
            view.lblStatusRodape.setText("Aviso: MySQL não encontrado em " + Conexao.getUrlInfo() + ". Operando em contingência na memória.");
        } else {
            view.badgeStatus.setStatus(true, "MySQL Conectado");
            view.lblStatusRodape.setText("Banco de dados ativo: " + Conexao.getUrlInfo());
        }
    }

    /**
     * Valida os campos do formulário antes de qualquer operação.
     * @return true se válidos, false caso contrário (com banner informativo).
     */
    private boolean validarCampos() {
        String nome = view.txtNome.getText().trim();
        String email = view.txtEmail.getText().trim();

        if (nome.isEmpty()) {
            view.bannerFeedback.showMessage("Por favor, preencha o nome do usuário.", Type.ERROR);
            view.txtNome.requestFocus();
            return false;
        }

        if (nome.length() < 2) {
            view.bannerFeedback.showMessage("O nome deve ter pelo menos 2 caracteres.", Type.ERROR);
            view.txtNome.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            view.bannerFeedback.showMessage("Por favor, informe o e-mail do usuário.", Type.ERROR);
            view.txtEmail.requestFocus();
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            view.bannerFeedback.showMessage("Informe um e-mail válido (ex: usuario@empresa.com).", Type.ERROR);
            view.txtEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void salvar() {
        if (!validarCampos()) return;

        try {
            Usuario u = new Usuario();
            u.setNome(view.txtNome.getText().trim());
            u.setEmail(view.txtEmail.getText().trim());

            Usuario salvo = dao.inserir(u);
            limparFormulario();
            listar();
            view.bannerFeedback.showMessage("Usuário '" + salvo.getNome() + "' cadastrado com sucesso!", Type.SUCCESS);
        } catch (Exception e) {
            view.bannerFeedback.showMessage("Falha ao salvar: " + e.getMessage(), Type.ERROR);
        }
    }

    private void editar() {
        String idStr = view.txtId.getText().trim();
        if (idStr.isEmpty()) {
            view.bannerFeedback.showMessage("Nenhum usuário selecionado para alteração.", Type.ERROR);
            return;
        }

        if (!validarCampos()) return;

        try {
            Usuario u = new Usuario();
            u.setId(Integer.parseInt(idStr));
            u.setNome(view.txtNome.getText().trim());
            u.setEmail(view.txtEmail.getText().trim());

            boolean sucesso = dao.atualizar(u);
            if (sucesso) {
                limparFormulario();
                listar();
                view.bannerFeedback.showMessage("Dados atualizados com sucesso!", Type.SUCCESS);
            } else {
                view.bannerFeedback.showMessage("Usuário não encontrado para atualização.", Type.ERROR);
            }
        } catch (NumberFormatException e) {
            view.bannerFeedback.showMessage("Identificador de usuário inválido.", Type.ERROR);
        } catch (Exception e) {
            view.bannerFeedback.showMessage("Falha ao atualizar: " + e.getMessage(), Type.ERROR);
        }
    }

    private void excluir() {
        String idStr = view.txtId.getText().trim();
        if (idStr.isEmpty()) {
            view.bannerFeedback.showMessage("Selecione um usuário para excluir.", Type.ERROR);
            return;
        }

        String nome = view.txtNome.getText().trim();
        int resposta = JOptionPane.showConfirmDialog(
                view,
                "Tem certeza que deseja remover o usuário '" + nome + "' (ID: " + idStr + ")?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idStr);
                boolean removido = dao.excluir(id);
                if (removido) {
                    limparFormulario();
                    listar();
                    view.bannerFeedback.showMessage("Usuário removido com sucesso.", Type.SUCCESS);
                } else {
                    view.bannerFeedback.showMessage("Não foi possível excluir o usuário informado.", Type.ERROR);
                }
            } catch (Exception e) {
                view.bannerFeedback.showMessage("Erro ao excluir: " + e.getMessage(), Type.ERROR);
            }
        }
    }

    private void listar() {
        view.modelo.setRowCount(0);
        List<Usuario> lista = dao.listar();

        for (Usuario u : lista) {
            view.modelo.addRow(new Object[]{u.getId(), u.getNome(), u.getEmail()});
        }

        atualizarContador(lista.size());
    }

    private void carregarUsuarioSelecionado() {
        int selectedRow = view.tabela.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = view.tabela.convertRowIndexToModel(selectedRow);

            Object id = view.modelo.getValueAt(modelRow, 0);
            Object nome = view.modelo.getValueAt(modelRow, 1);
            Object email = view.modelo.getValueAt(modelRow, 2);

            view.txtId.setText(id != null ? id.toString() : "");
            view.txtNome.setText(nome != null ? nome.toString() : "");
            view.txtEmail.setText(email != null ? email.toString() : "");

            view.setModoEdicao(true);
            view.bannerFeedback.clear();
        }
    }

    public void limparFormulario() {
        view.tabela.clearSelection();
        view.txtId.setText("");
        view.txtNome.setText("");
        view.txtEmail.setText("");
        view.bannerFeedback.clear();
        view.setModoEdicao(false);
        view.txtNome.requestFocus();
    }

    private void aplicarFiltro() {
        String texto = view.txtBusca.getText().trim();
        if (texto.isEmpty()) {
            rowSorter.setRowFilter(null);
            atualizarContador(view.modelo.getRowCount());
        } else {
            // Filtro insensível a maiúsculas/minúsculas nas colunas Nome (1) e E-mail (2)
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 1, 2));
            atualizarContador(view.tabela.getRowCount());
        }
    }

    private void atualizarContador(int totalVisivel) {
        int totalGeral = view.modelo.getRowCount();
        if (totalVisivel == totalGeral) {
            view.lblTotalRegistros.setText(totalGeral + (totalGeral == 1 ? " registro" : " registros"));
        } else {
            view.lblTotalRegistros.setText("Exibindo " + totalVisivel + " de " + totalGeral + " registros");
        }
    }
}
