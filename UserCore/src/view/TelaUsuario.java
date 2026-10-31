package view;

import view.components.*;
import view.components.ModernButton.Style;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Interface gráfica principal do CRUD de Usuários.
 * Design limpo, focado em usabilidade profissional, espaçamento refinado e sem distrações visuais.
 */
public class TelaUsuario extends JFrame {

    // Componentes de Entrada
    public final ModernTextField txtId = new ModernTextField(10);
    public final ModernTextField txtNome = new ModernTextField("Ex: Mariana Vasconcelos", 20);
    public final ModernTextField txtEmail = new ModernTextField("Ex: mariana.vasconcelos@empresa.com", 20);
    public final ModernTextField txtBusca = new ModernTextField("Filtrar por nome ou e-mail...", 16);

    // Botões de Ação
    public final ModernButton btnSalvar = new ModernButton("Salvar Usuário", Style.PRIMARY);
    public final ModernButton btnEditar = new ModernButton("Salvar Alterações", Style.PRIMARY);
    public final ModernButton btnLimpar = new ModernButton("Limpar Campos", Style.SECONDARY);
    public final ModernButton btnExcluir = new ModernButton("Excluir", Style.DANGER);
    public final ModernButton btnListar = new ModernButton("Recarregar", Style.SECONDARY);

    // Tabela e Modelo
    public final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nome Completo", "E-mail Cadastrado"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Linhas não editáveis diretamente na tabela
        }
    };
    public final ModernTable tabela = new ModernTable(modelo);

    // Indicadores e Feedback
    public final NotificationBanner bannerFeedback = new NotificationBanner();
    public final StatusBadge badgeStatus = new StatusBadge();
    public final JLabel lblTotalRegistros = new JLabel("0 usuários");
    public final JLabel lblModoForm = new JLabel("Novo Cadastro");
    public final JLabel lblStatusRodape = new JLabel("Pronto");

    public TelaUsuario() {
        configurarJanela();
        construirInterface();
        setModoEdicao(false);
    }

    private void configurarJanela() {
        setTitle("Sistema de Gestão de Usuários");
        setSize(1040, 660);
        setMinimumSize(new Dimension(860, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIConstants.BG_APP);
        setLayout(new BorderLayout(0, 0));
    }

    private void construirInterface() {
        add(criarHeaderSuperior(), BorderLayout.NORTH);
        add(criarConteudoPrincipal(), BorderLayout.CENTER);
        add(criarBarraStatus(), BorderLayout.SOUTH);
    }

    /**
     * Cabeçalho superior com título, barra de busca rápida e status do banco.
     */
    private JPanel criarHeaderSuperior() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(18, 24, 14, 24));

        // Título e Subtítulo
        JPanel painelTitulo = new JPanel(new GridLayout(2, 1, 0, 2));
        painelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Gestão de Usuários");
        lblTitulo.setFont(UIConstants.FONT_HEADER_TITLE);
        lblTitulo.setForeground(UIConstants.TEXT_TITLE);

        JLabel lblSubtitulo = new JLabel("Cadastro, consulta e administração de contas de acesso");
        lblSubtitulo.setFont(UIConstants.FONT_HEADER_SUBTITLE);
        lblSubtitulo.setForeground(UIConstants.TEXT_MUTED);

        painelTitulo.add(lblTitulo);
        painelTitulo.add(lblSubtitulo);

        // Barra de busca e status à direita
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelAcoes.setOpaque(false);

        txtBusca.setPreferredSize(new Dimension(240, 36));
        painelAcoes.add(txtBusca);
        painelAcoes.add(btnListar);
        painelAcoes.add(badgeStatus);

        header.add(painelTitulo, BorderLayout.WEST);
        header.add(painelAcoes, BorderLayout.EAST);

        return header;
    }

    /**
     * Conteúdo principal: Formulário à esquerda e Tabela de dados à direita.
     */
    private JPanel criarConteudoPrincipal() {
        JPanel container = new JPanel(new BorderLayout(18, 0));
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(0, 24, 14, 24));

        container.add(criarCardFormulario(), BorderLayout.WEST);
        container.add(criarCardTabela(), BorderLayout.CENTER);

        return container;
    }

    /**
     * Card do Formulário de inserção e edição.
     */
    private JPanel criarCardFormulario() {
        ModernCard card = new ModernCard(8);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setPreferredSize(new Dimension(360, 0));

        // Cabeçalho do Card
        JPanel cardHeader = new JPanel(new BorderLayout());
        cardHeader.setOpaque(false);

        JLabel lblSecao = new JLabel("Dados do Usuário");
        lblSecao.setFont(UIConstants.FONT_SECTION_TITLE);
        lblSecao.setForeground(UIConstants.TEXT_TITLE);

        lblModoForm.setFont(UIConstants.FONT_SMALL);
        lblModoForm.setForeground(UIConstants.TEXT_MUTED);

        cardHeader.add(lblSecao, BorderLayout.WEST);
        cardHeader.add(lblModoForm, BorderLayout.EAST);

        // Campos do Formulário
        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new BoxLayout(painelCampos, BoxLayout.Y_AXIS));
        painelCampos.setOpaque(false);

        painelCampos.add(bannerFeedback);
        painelCampos.add(Box.createVerticalStrut(10));

        // Campo ID
        txtId.setEditable(false);
        txtId.setPlaceholder("Gerado automaticamente");
        painelCampos.add(criarItemCampo("Identificador (ID)", txtId, "Código sequencial único gerado pelo banco"));
        painelCampos.add(Box.createVerticalStrut(14));

        // Campo Nome
        painelCampos.add(criarItemCampo("Nome Completo *", txtNome, null));
        painelCampos.add(Box.createVerticalStrut(14));

        // Campo E-mail
        painelCampos.add(criarItemCampo("Endereço de E-mail *", txtEmail, null));
        painelCampos.add(Box.createVerticalStrut(20));

        // Painel de Botões de Ação
        JPanel painelBotoes = new JPanel(new GridLayout(2, 2, 8, 8));
        painelBotoes.setOpaque(false);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnExcluir);

        card.add(cardHeader, BorderLayout.NORTH);
        card.add(painelCampos, BorderLayout.CENTER);
        card.add(painelBotoes, BorderLayout.SOUTH);

        return card;
    }

    /**
     * Cria um agrupamento visual de Label + Input + Dica opcional.
     */
    private JPanel criarItemCampo(String labelText, JComponent input, String hintText) {
        JPanel painel = new JPanel(new BorderLayout(0, 6));
        painel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.FONT_LABEL);
        label.setForeground(UIConstants.TEXT_MAIN);

        painel.add(label, BorderLayout.NORTH);
        painel.add(input, BorderLayout.CENTER);

        if (hintText != null) {
            JLabel hint = new JLabel(hintText);
            hint.setFont(UIConstants.FONT_SMALL);
            hint.setForeground(UIConstants.TEXT_MUTED);
            painel.add(hint, BorderLayout.SOUTH);
        }

        return painel;
    }

    /**
     * Card contendo a tabela de usuários com barra de rolagem e cabeçalho de contagem.
     */
    private JPanel criarCardTabela() {
        ModernCard card = new ModernCard(8);
        card.setLayout(new BorderLayout(0, 10));
        card.setBorder(new EmptyBorder(16, 18, 16, 18));

        // Topo da tabela com contagem de registros
        JPanel headerTabela = new JPanel(new BorderLayout());
        headerTabela.setOpaque(false);

        JLabel lblTituloTabela = new JLabel("Usuários Cadastrados");
        lblTituloTabela.setFont(UIConstants.FONT_SECTION_TITLE);
        lblTituloTabela.setForeground(UIConstants.TEXT_TITLE);

        lblTotalRegistros.setFont(UIConstants.FONT_SMALL);
        lblTotalRegistros.setForeground(UIConstants.TEXT_MUTED);

        headerTabela.add(lblTituloTabela, BorderLayout.WEST);
        headerTabela.add(lblTotalRegistros, BorderLayout.EAST);

        // Configuração de largura das colunas
        tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(0).setMaxWidth(80);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(230);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(270);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_SUBTLE, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        card.add(headerTabela, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    /**
     * Barra inferior com informações contextuais e orientações de atalho.
     */
    private JPanel criarBarraStatus() {
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setOpaque(false);
        rodape.setBorder(new EmptyBorder(6, 26, 12, 26));

        lblStatusRodape.setFont(UIConstants.FONT_SMALL);
        lblStatusRodape.setForeground(UIConstants.TEXT_MUTED);

        JLabel lblDica = new JLabel("Clique em uma linha da tabela para carregar os dados no formulário");
        lblDica.setFont(UIConstants.FONT_SMALL);
        lblDica.setForeground(UIConstants.TEXT_MUTED);

        rodape.add(lblStatusRodape, BorderLayout.WEST);
        rodape.add(lblDica, BorderLayout.EAST);

        return rodape;
    }

    /**
     * Alterna o estado visual entre modo Inserção e modo Edição.
     */
    public void setModoEdicao(boolean editando) {
        btnSalvar.setVisible(!editando);
        btnEditar.setVisible(editando);
        btnExcluir.setEnabled(editando);

        if (editando) {
            lblModoForm.setText("Modo Edição");
            lblModoForm.setForeground(new Color(0x02, 0x84, 0xC7)); // Sky 600
        } else {
            lblModoForm.setText("Novo Cadastro");
            lblModoForm.setForeground(UIConstants.TEXT_MUTED);
            txtId.setText("");
        }
    }
}
