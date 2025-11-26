package app;

import dao.filmeDAO;
import model.filme;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class main {

    private static final Scanner sc = new Scanner(System.in);
    private static final filmeDAO dao = new filmeDAO();

    public static void main(String[] args) {
        int opcao = -1;
        System.out.println("=== Catálogo de Filmes ===");
        while (opcao != 0) {
            mostrarMenu();
            try {
                opcao = Integer.parseInt(sc.nextLine().trim());
                switch (opcao) {
                    case 1 -> cadastrar();
                    case 2 -> listar();
                    case 3 -> atualizar();
                    case 4 -> excluir();
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            } catch (SQLException e) {
                System.out.println("Erro de banco: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n1 - Cadastrar Filme");
        System.out.println("2 - Listar Filmes");
        System.out.println("3 - Atualizar Filme");
        System.out.println("4 - Excluir Filme");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private static void cadastrar() throws SQLException {
        System.out.print("Título: ");
        String titulo = sc.nextLine().trim();

        System.out.print("Diretor: ");
        String diretor = sc.nextLine().trim();

        Integer ano = lerInteiroOpcional("Ano de lançamento (enter para deixar vazio): ");

        System.out.print("Gênero: ");
        String genero = sc.nextLine().trim();

        filme m = new filme(titulo, diretor, ano, genero);
        dao.inserir(m);
        System.out.println("Filme cadastrado com ID: " + m.getId());
    }

    private static void listar() throws SQLException {
        List<filme> filmes = dao.listar();
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado.");
            return;
        }
        System.out.println("\n--- Filmes cadastrados ---");
        for (filme m : filmes) System.out.println(m);
    }

    private static void atualizar() throws SQLException {
        Integer id = lerInteiroObrigatorio("ID do filme a atualizar: ");
        filme atual = dao.buscarPorId(id);
        if (atual == null) {
            System.out.println("Filme não encontrado.");
            return;
        }
        System.out.println("Encontrado: " + atual);

        System.out.print("Novo título (enter para manter): ");
        String titulo = sc.nextLine().trim();
        if (!titulo.isEmpty()) atual.setTitulo(titulo);

        System.out.print("Novo diretor (enter para manter): ");
        String diretor = sc.nextLine().trim();
        if (!diretor.isEmpty()) atual.setDiretor(diretor);

        Integer novoAno = lerInteiroOpcional("Novo ano (enter para manter): ");
        if (novoAno != null) atual.setAnoLancamento(novoAno);

        System.out.print("Novo gênero (enter para manter): ");
        String genero = sc.nextLine().trim();
        if (!genero.isEmpty()) atual.setGenero(genero);

        boolean ok = dao.atualizar(atual);
        System.out.println(ok ? "Atualizado com sucesso." : "Falha ao atualizar.");
    }

    private static void excluir() throws SQLException {
        Integer id = lerInteiroObrigatorio("ID do filme a excluir: ");
        boolean ok = dao.excluir(id);
        System.out.println(ok ? "Filme excluído." : "Filme não encontrado.");
    }

    // Helpers
    private static Integer lerInteiroOpcional(String mensagem) {
        System.out.print(mensagem);
        String linha = sc.nextLine().trim();
        if (linha.isEmpty()) return null;
        try {
            return Integer.valueOf(linha);
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido — será considerado vazio.");
            return null;
        }
    }

    private static Integer lerInteiroObrigatorio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = sc.nextLine().trim();
            try {
                return Integer.valueOf(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }
}
