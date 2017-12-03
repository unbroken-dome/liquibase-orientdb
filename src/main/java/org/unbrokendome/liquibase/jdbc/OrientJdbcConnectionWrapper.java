package org.unbrokendome.liquibase.jdbc;

import com.orientechnologies.orient.jdbc.OrientJdbcConnection;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;


public class OrientJdbcConnectionWrapper implements Connection {

    private final OrientJdbcConnection orientJdbcConnection;


    public OrientJdbcConnectionWrapper(OrientJdbcConnection orientJdbcConnection) {
        this.orientJdbcConnection = orientJdbcConnection;
    }


    @Override
    public Statement createStatement() throws SQLException {
        Statement statement = orientJdbcConnection.createStatement();
        return new OrientJdbcStatementWrapper(statement);
    }


    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        Statement statement = orientJdbcConnection.createStatement(resultSetType, resultSetConcurrency);
        return new OrientJdbcStatementWrapper(statement);
    }


    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        Statement statement = orientJdbcConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        return new OrientJdbcStatementWrapper(statement);
    }


    /* All remaining methods just delegate to the wrapped Connection */

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return orientJdbcConnection.prepareStatement(sql);
    }


    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return orientJdbcConnection.prepareCall(sql);
    }


    @Override
    public String nativeSQL(String sql) throws SQLException {
        return orientJdbcConnection.nativeSQL(sql);
    }


    @Override
    public void clearWarnings() throws SQLException {
        orientJdbcConnection.clearWarnings();
    }


    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return orientJdbcConnection.unwrap(iface);
    }


    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return orientJdbcConnection.isWrapperFor(iface);
    }


    public String getUrl() {
        return orientJdbcConnection.getUrl();
    }


    @Override
    public void close() throws SQLException {
        orientJdbcConnection.close();
    }


    @Override
    public void commit() throws SQLException {
        orientJdbcConnection.commit();
    }


    @Override
    public void rollback() throws SQLException {
        orientJdbcConnection.rollback();
    }


    @Override
    public boolean isClosed() throws SQLException {
        return orientJdbcConnection.isClosed();
    }


    @Override
    public boolean isReadOnly() throws SQLException {
        return orientJdbcConnection.isReadOnly();
    }


    @Override
    public void setReadOnly(boolean iReadOnly) throws SQLException {
        orientJdbcConnection.setReadOnly(iReadOnly);
    }


    @Override
    public boolean isValid(int timeout) throws SQLException {
        return orientJdbcConnection.isValid(timeout);
    }


    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return orientJdbcConnection.createArrayOf(typeName, elements);
    }


    @Override
    public Blob createBlob() throws SQLException {
        return orientJdbcConnection.createBlob();
    }


    @Override
    public Clob createClob() throws SQLException {
        return orientJdbcConnection.createClob();
    }


    @Override
    public NClob createNClob() throws SQLException {
        return orientJdbcConnection.createNClob();
    }


    @Override
    public SQLXML createSQLXML() throws SQLException {
        return orientJdbcConnection.createSQLXML();
    }


    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return orientJdbcConnection.createStruct(typeName, attributes);
    }


    @Override
    public boolean getAutoCommit() throws SQLException {
        return orientJdbcConnection.getAutoCommit();
    }


    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        orientJdbcConnection.setAutoCommit(autoCommit);
    }


    @Override
    public String getCatalog() throws SQLException {
        return orientJdbcConnection.getCatalog();
    }


    @Override
    public void setCatalog(String catalog) throws SQLException {
        orientJdbcConnection.setCatalog(catalog);
    }


    @Override
    public Properties getClientInfo() throws SQLException {
        return orientJdbcConnection.getClientInfo();
    }


    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        orientJdbcConnection.setClientInfo(properties);
    }


    @Override
    public String getClientInfo(String name) throws SQLException {
        return orientJdbcConnection.getClientInfo(name);
    }


    @Override
    public int getHoldability() throws SQLException {
        return orientJdbcConnection.getHoldability();
    }


    @Override
    public void setHoldability(int holdability) throws SQLException {
        orientJdbcConnection.setHoldability(holdability);
    }


    @Override
    public int getTransactionIsolation() throws SQLException {
        return orientJdbcConnection.getTransactionIsolation();
    }


    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        orientJdbcConnection.setTransactionIsolation(level);
    }


    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return orientJdbcConnection.getTypeMap();
    }


    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        orientJdbcConnection.setTypeMap(map);
    }


    @Override
    public SQLWarning getWarnings() throws SQLException {
        return orientJdbcConnection.getWarnings();
    }


    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return orientJdbcConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }


    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return orientJdbcConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }


    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return orientJdbcConnection.prepareStatement(sql, autoGeneratedKeys);
    }


    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return orientJdbcConnection.prepareStatement(sql, columnIndexes);
    }


    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return orientJdbcConnection.prepareStatement(sql, columnNames);
    }


    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return orientJdbcConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }


    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return orientJdbcConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }


    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        orientJdbcConnection.releaseSavepoint(savepoint);
    }


    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        orientJdbcConnection.rollback(savepoint);
    }


    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        orientJdbcConnection.setClientInfo(name, value);
    }


    @Override
    public Savepoint setSavepoint() throws SQLException {
        return orientJdbcConnection.setSavepoint();
    }


    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return orientJdbcConnection.setSavepoint(name);
    }


    @Override
    public void abort(Executor arg0) throws SQLException {
        orientJdbcConnection.abort(arg0);
    }


    @Override
    public int getNetworkTimeout() throws SQLException {
        return orientJdbcConnection.getNetworkTimeout();
    }


    @Override
    public String getSchema() throws SQLException {
        return orientJdbcConnection.getSchema();
    }


    @Override
    public void setSchema(String arg0) throws SQLException {
        orientJdbcConnection.setSchema(arg0);
    }


    @Override
    public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
        orientJdbcConnection.setNetworkTimeout(arg0, arg1);
    }


    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return orientJdbcConnection.getMetaData();
    }
}
