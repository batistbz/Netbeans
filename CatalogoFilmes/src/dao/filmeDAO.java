package dao;

import model.Movie;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class filmeDAO {

    public void inserir(Movie m) throws SQLException {
        String sql = "INSERT INTO filmes (titulo, n_diretor, ano_lancamento, genero) VALUES (?, ?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, m.getTitulo());
        ps.setString(2, m.getDiretor());
        ps.setInt(3, m.getAnoLancamento());
        ps.setString(4, m.getGenero());

        ps.execute();
        conn.close();
    }

    public List<Movie> listar() throws SQLException {
        List<Movie> lista = new ArrayList<>();
        String sql = "SELECT * FROM filmes";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Movie m = new Movie();
            m.setId(rs.getInt("id"));
            m.setTitulo(rs.getString("titulo"));
            m.setDiretor(rs.getString("n_diretor"));
            m.setAnoLancamento(rs.getInt("ano_lancamento"));
            m.setGenero(rs.getString("genero"));
            lista.add(m);
        }

        conn.close();
        return lista;
    }

    public void atualizar(Movie m) throws SQLException {
        String sql = "UPDATE filmes SET titulo=?, n_diretor=?, ano_lancamento=?, genero=? WHERE id=?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, m.getTitulo());
        ps.setString(2, m.getDiretor());
        ps.setInt(3, m.getAnoLancamento());
        ps.setString(4, m.getGenero());
        ps.setInt(5, m.getId());

        ps.execute();
        conn.close();
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM filmes WHERE id=?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.execute();
        conn.close();
    }
}
