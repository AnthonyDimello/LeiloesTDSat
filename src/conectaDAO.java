import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConectaDAO {

    public Connection connectDB() {
        Connection conn = null;

        try {
            // Opcional, mas bom para versões antigas do Java
            // Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/uc11?useTimezone=true&serverTimezone=UTC&useSSL=false",
                "root",
                "121424@nthonY"
            );
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,
                "Erro ao conectar ao banco de dados:\n" + erro.getMessage(),
                "Erro de Conexão", JOptionPane.ERROR_MESSAGE
            );
        }

        return conn;
    }
}

    

