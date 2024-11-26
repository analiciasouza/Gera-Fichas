package geraFicha.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/FichasRPG";
    private static final String USER = "postgres";
    private static final String PASSWORD = "POSTGRES";

    public DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/FichasRPG", "postgres", "POSTGRES");
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Conexão bem-sucedida!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

    }
}
