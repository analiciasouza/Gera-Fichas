package geraFicha.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseService {
    private static final String URL = "jdbc:postgresql://localhost:5432/FichasRPG"; // Atualize com a URL do seu banco
    private static final String USER = "postgres"; // Atualize com o usuário do banco
    private static final String PASSWORD = "POSTGRES"; // Atualize com a senha do banco

    private Connection connection;

    public DatabaseService() {

    }

    private void abrirConexao() {
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
        this.abrirConexao();

        String sql = "INSERT INTO usuario (usuario_nome, usuario_senha) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            stmt.execute();
            System.out.println("Usuário inserido com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.fecharConexao();
    }

    // Buscar usuários
    public List<String> listarUsuarios() {
        this.abrirConexao();
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
        this.fecharConexao();
        return usuarios;

    }




    // Inserir um personagem
    public void inserirPersonagem(String nome, int idClasse, int vida) {
        this.abrirConexao();
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
        this.fecharConexao();
    }


    public Map<String, String> listarUsuariosComSenha() {
        this.abrirConexao();
        Map<String, String> usuarios = new HashMap<>();
        String sql = "SELECT usuario_nome, usuario_senha FROM usuario";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.put(rs.getString("usuario_nome"), rs.getString("usuario_senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.fecharConexao();
        }
        return usuarios;
    }

    public void excluirUsuario(String nome) {
        this.abrirConexao();
        String sql = "DELETE FROM usuario WHERE usuario_nome = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuário excluído do banco de dados.");
            } else {
                System.out.println("Usuário não encontrado no banco de dados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.fecharConexao();
    }


    public boolean excluirPersonagem(String nome) {
        this.abrirConexao();
        String sql = "DELETE FROM personagens WHERE personagem_nome = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // Retorna true se a exclusão foi bem-sucedida
        }

        catch (SQLException e) {
            e.printStackTrace();
            return false;  }

        finally {
            this.fecharConexao();
        }
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