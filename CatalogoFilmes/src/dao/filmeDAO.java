package dao;

import model.filme;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class filmeDAO {

    public filme inserir(filme m) throws SQLException {
        String sql = "INSERT INTO filmes (titulo, n_diretor, ano_lancamento, genero) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, m.getTitulo());
            ps.setString(2, m.getDiretor());

            if (m.getAnoLancamento() != null) ps.setInt(3, m.getAnoLancamento());
            else ps.setNull(3, Types.INTEGER);

            ps.setString(4, m.getGenero());

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Falha ao inserir filme, nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getInt(1));
            }
        }
        return m;
    }

    public List<filme> listar() throws SQLException {
        List<filme> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, n_diretor, ano_lancamento, genero FROM filmes ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                filme m = new filme();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setDiretor(rs.getString("n_diretor"));

                int ano = rs.getInt("ano_lancamento");
                if (!rs.wasNull()) m.setAnoLancamento(ano);
                else m.setAnoLancamento(null);

                m.setGenero(rs.getString("genero"));
                lista.add(m);
            }
        }
        return lista;
    }

    public filme buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, titulo, n_diretor, ano_lancamento, genero FROM filmes WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    filme m = new filme();
                    m.setId(rs.getInt("id"));
                    m.setTitulo(rs.getString("titulo"));
                    m.setDiretor(rs.getString("n_diretor"));
                    int ano = rs.getInt("ano_lancamento");
                    if (!rs.wasNull()) m.setAnoLancamento(ano);
                    m.setGenero(rs.getString("genero"));
                    return m;
                }
            }
        }
        return null;
    }

    public boolean atualizar(filme m) throws SQLException {
        String sql = "UPDATE filmes SET titulo = ?, n_diretor = ?, ano_lancamento = ?, genero = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getTitulo());
            ps.setString(2, m.getDiretor());

            if (m.getAnoLancamento() != null) ps.setInt(3, m.getAnoLancamento());
            else ps.setNull(3, Types.INTEGER);

            ps.setString(4, m.getGenero());
            ps.setInt(5, m.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM filmes WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
