package geraFicha.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private static final String URL = "jdbc:postgresql://localhost:5432/sua_base"; // Atualize com a URL do seu banco
    private static final String USER = "postgres"; // Atualize com o usuário do banco
    private static final String PASSWORD = "POSTGRES"; // Atualize com a senha do banco

    private Connection connection;

    public DatabaseService() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco de dados estabelecida.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha ao conectar ao banco de dados.");
        }
    }

    // Inserir um novo usuário
    public void inserirUsuario(String nome, String senha) {
        String sql = "INSERT INTO usuario (usuario_nome, usuario_senha) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            stmt.executeUpdate();
            System.out.println("Usuário inserido com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar usuários
    public List<String> listarUsuarios() {
        List<String> usuarios = new ArrayList<>();
        String sql = "SELECT usuario_nome FROM usuario";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(rs.getString("usuario_nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Inserir um personagem
    public void inserirPersonagem(String nome, int idClasse, int vida) {
        String sql = "INSERT INTO personagens (personagem_nome, id_classe, vida) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, idClasse);
            stmt.setInt(3, vida);
            stmt.executeUpdate();
            System.out.println("Personagem inserido com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar personagens
    public List<String> listarPersonagens() {
        List<String> personagens = new ArrayList<>();
        String sql = "SELECT personagem_nome FROM personagens";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                personagens.add(rs.getString("personagem_nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personagens;
    }

    // Fechar conexão
    public void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}