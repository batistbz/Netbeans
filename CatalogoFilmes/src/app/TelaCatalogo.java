package app;

import dao.filmeDAO;
import model.filme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaCatalogo extends JFrame {

    private JTextField txtTitulo, txtDiretor, txtAno, txtGenero;
    private JTable tabela;
    private filmeDAO dao = new filmeDAO();

    public TelaCatalogo() {
        setTitle("Catálogo de Filmes");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        criarComponentes();
        listarFilmes();
    }

    private void criarComponentes() {

        JLabel lblTitulo = new JLabel("Título:");
        JLabel lblDiretor = new JLabel("Diretor:");
        JLabel lblAno = new JLabel("Ano:");
        JLabel lblGenero = new JLabel("Gênero:");

        txtTitulo = new JTextField(15);
        txtDiretor = new JTextField(15);
        txtAno = new JTextField(6);
        txtGenero = new JTextField(10);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");

        tabela = new JTable();
        tabela.setModel(new DefaultTableModel(
                new Object[]{"ID", "Título", "Diretor", "Ano", "Gênero"}, 0
        ));

        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelSuperior = new JPanel(new GridLayout(4, 2));
        painelSuperior.add(lblTitulo);
        painelSuperior.add(txtTitulo);
        painelSuperior.add(lblDiretor);
        painelSuperior.add(txtDiretor);
        painelSuperior.add(lblAno);
        painelSuperior.add(txtAno);
        painelSuperior.add(lblGenero);
        painelSuperior.add(txtGenero);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);

        setLayout(new BorderLayout());
        add(painelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
    }

    private void cadastrar() {
        try {
            String titulo = txtTitulo.getText();
            String diretor = txtDiretor.getText();
            int ano = Integer.parseInt(txtAno.getText());
            String genero = txtGenero.getText();

            filme m = new filme(titulo, diretor, ano, genero);
            dao.inserir(m);

            limparCampos();
            listarFilmes();
            JOptionPane.showMessageDialog(this, "Filme cadastrado com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar!");
        }
    }

    private void atualizar() {
        try {
            int linha = tabela.getSelectedRow();
            int id = Integer.parseInt(tabela.getValueAt(linha, 0).toString());

            String titulo = txtTitulo.getText();
            String diretor = txtDiretor.getText();
            int ano = Integer.parseInt(txtAno.getText());
            String genero = txtGenero.getText();

            filme m = new filme(titulo, diretor, ano, genero);
            m.setId(id);

            dao.atualizar(m);
            limparCampos();
            listarFilmes();

            JOptionPane.showMessageDialog(this, "Filme atualizado!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar!");
        }
    }

    private void excluir() {
        try {
            int linha = tabela.getSelectedRow();
            int id = Integer.parseInt(tabela.getValueAt(linha, 0).toString());

            dao.excluir(id);
            limparCampos();
            listarFilmes();

            JOptionPane.showMessageDialog(this, "Filme excluído!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir!");
        }
    }

    private void listarFilmes() {
        try {
            List<filme> lista = dao.listar();
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            modelo.setRowCount(0);

            for (filme m : lista) {
                modelo.addRow(new Object[]{
                        m.getId(),
                        m.getTitulo(),
                        m.getDiretor(),
                        m.getAnoLancamento(),
                        m.getGenero()
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar filmes!");
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtDiretor.setText("");
        txtAno.setText("");
        txtGenero.setText("");
    }

    public static void main(String[] args) {
    new TelaCatalogo().setVisible(true);
    }
}
