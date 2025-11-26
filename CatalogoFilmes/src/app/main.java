package app;

import dao.MovieDAO;
import model.Movie;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        MovieDAO dao = new MovieDAO();
        int opcao;

        do {
            System.out.println("\n1 - Cadastrar Filme");
            System.out.println("2 - Listar Filmes");
            System.out.println("3 - Atualizar Filme");
            System.out.println("4 - Excluir Filme");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();

                    System.out.print("Diretor: ");
                    String diretor = sc.nextLine();

                    System.out.print("Ano de lançamento: ");
                    int ano = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Gênero: ");
                    String genero = sc.nextLine();

                    Movie m = new Movie(titulo, diretor, ano, genero);
                    dao.inserir(m);
                    System.out.println("Filme cadastrado com sucesso!");
                }

                case 2 -> {
                    List<Movie> filmes = dao.listar();
                    for (Movie m : filmes) {
                        System.out.println(
                            m.getId() + " - " +
                            m.getTitulo() + " | " +
                            m.getDiretor() + " | " +
                            m.getAnoLancamento() + " | " +
                            m.getGenero()
                        );
                    }
                }

                case 3 -> {
                    System.out.print("ID do filme: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Novo título: ");
                    String titulo = sc.nextLine();

                    System.out.print("Novo diretor: ");
                    String diretor = sc.nextLine();

                    System.out.print("Novo ano: ");
                    int ano = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Novo gênero: ");
                    String genero = sc.nextLine();

                    Movie m = new Movie(titulo, diretor, ano, genero);
                    m.setId(id);
                    dao.atualizar(m);
                    System.out.println("Filme atualizado!");
                }

                case 4 -> {
                    System.out.print("ID do filme: ");
                    int id = sc.nextInt();
                    dao.excluir(id);
                    System.out.println("Filme removido!");
                }
            }

        } while (opcao != 0);
    }
}
