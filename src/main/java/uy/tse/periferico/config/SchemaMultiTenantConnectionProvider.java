package uy.tse.periferico.config;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String> {
    private final DataSource dataSource;

    public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();
        try {
            // --- INICIO DE LÍNEA DE DEBUG ---
            System.out.println("DEBUG: SchemaProvider -> Intentando cambiar al schema: " + tenantIdentifier);
            // --- FIN DE LÍNEA DE DEBUG ---
            connection.setSchema(tenantIdentifier);
        } catch (SQLException e) {
            System.err.println("ERROR: No se pudo cambiar al schema '" + tenantIdentifier + "'");
            throw new SQLException("No se pudo establecer el schema para el tenant " + tenantIdentifier, e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            connection.setSchema("public"); // Volver al schema por defecto
        } catch (SQLException e) {
            // Ignorar errores al resetear, ya que la conexión se va a cerrar de todas formas.
        }
        connection.close();
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}

