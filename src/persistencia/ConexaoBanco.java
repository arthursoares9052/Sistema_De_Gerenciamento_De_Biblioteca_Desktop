package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {


    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public Connection getConexao() throws SQLException {

        Connection c = null;
        try {

            c = DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (SQLException se) {

            throw new SQLException("Erro ao conectar! " + se.getMessage());
        }

        return c;
    }

}
