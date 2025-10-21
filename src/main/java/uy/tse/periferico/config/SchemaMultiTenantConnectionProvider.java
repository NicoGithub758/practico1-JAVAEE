package uy.tse.periferico.config;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String> {
    private final DataSource dataSource;
    public SchemaMultiTenantConnectionProvider(DataSource dataSource) { this.dataSource = dataSource; }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantIdentifier);
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
    public Connection getAnyConnection() throws SQLException { return dataSource.getConnection(); }
    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException { connection.close(); }
    @Override
    public boolean supportsAggressiveRelease() { return false; }
    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) { return false; }
    @Override
    public <T> T unwrap(Class<T> unwrapType) { return null; }
}

