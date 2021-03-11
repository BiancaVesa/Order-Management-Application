package dataAccessLayer;
import businessLayer.*;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instantele acestei clase reprezinta obiecte care mostenesc metode de: creare de statement-uri SQL, primire a rezultatelor returnate de acestea si convertire a lor la o clasa concreta T.
 * @param <T> tipul de clasa abstracta DAO pe care vrem sa o construim; reprezinta si numele tabelului cu care vrem sa lucram in baza de date
 */
public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    /**
     * Pune in type numele clasei care o mosteneste.
     */
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Construieste un String care reprezinta un delete query in limbajul SQL.
     * @return SQL delete query
     */
    private String createDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append("FROM warehouse.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE name = ? ;");
        return sb.toString();
    }
    /**
     * Construieste un String care reprezinta un insert query (insereaza valori intr-un tabel cu 3 coloane) in limbajul SQL.
     * @return SQL insert query
     */
    private String createInsert3Query() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append("INTO warehouse.");
        sb.append(type.getSimpleName());
        sb.append(" VALUES(?, ?, ?)");
        return sb.toString();
    }
    /**
     * Construieste un String care reprezinta un insert query (insereaza valori intr-un tabel cu 4 coloane) in limbajul SQL.
     * @return SQL insert query
     */
    private String createInsert4Query() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append("INTO warehouse.");
        sb.append(type.getSimpleName());
        sb.append(" VALUES(?, ?, ?, ?)");
        return sb.toString();
    }

    /**
     * Construieste un String care reprezinta un select query (returneaza toate valorile dintr-un tabel) in limbajul SQL.
     * @return SQL select query
     */
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("* ");
        sb.append("FROM warehouse.");
        sb.append(type.getSimpleName());
        sb.append(";");
        return sb.toString();
    }
    /**
     * Construieste un String care reprezinta un select query (returneaza valorile dintr-un tabel, dupa un criteriu dat) in limbajul SQL.
     * @param field coloana pe care aplicam conditia WHERE
     * @return SQL select query
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("* ");
        sb.append("FROM warehouse.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?;");
        return sb.toString();
    }

    /**
     * Construieste un String care reprezinta un update query (modifica valori dintr-un tabel, dupa un criteriu dat) in limbajul SQL.
     * @param setField coloana pe care vrem sa o modificam
     * @param searchField coloana pe care aplicam conditia WHERE
     * @return SQL update query
     */
    private String createUpdateQuery(String setField, String searchField) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE warehouse.");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        sb.append(setField + " =?");
        sb.append(" WHERE " + searchField + " =?;");
        return sb.toString();
    }

    /**
     * Face conexiunea cu baza de date, creeaza statement-ul SQL, apeland createInsertQuery si il executa.
     * @param field valorile de inserat in tabel
     */
    public void add(List<Object> field) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        if (field.size() == 3) {
            query = createInsert3Query();
        } else {
            query = createInsert4Query();
        }
        try {
            connection = DBConnect.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for (Object o : field) {
                if (o instanceof String)
                    statement.setString(i++, (String) o);
                if (o instanceof Integer)
                    statement.setInt(i++, (Integer) o);
                if (o instanceof Float)
                    statement.setFloat(i++, (Float) o);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:add " + e.getMessage());
        } finally {
            DBConnect.close(statement);
            DBConnect.close(connection);
        }
    }

    /***
     * Face conexiunea cu baza de date, creeaza statement-ul SQL, apeland createDeleteQuery si il executa.
     * @param name coloana din tabel dupa care identificam obiectul pe care il stergem
     */
    public void delete(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = DBConnect.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);


            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:delete " + e.getMessage());
        } finally {
            DBConnect.close(statement);
            DBConnect.close(connection);
        }
    }

    /**
     * Face conexiunea cu baza de date, creeaza statement-ul SQL, apeland createSelectQuery si il executa.
     * @param field coloana din tabel dupa care identificam obiectul
     * @param value valoarea obiectului la coloana field
     * @return obiectul din tabelul T, care corespunde criteriilor de cautare; null daca select query nu gaseste niciun obiect in tabel cu criteriile impuse
     */
    public T find(String field, Object value) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);
        try {
            connection = DBConnect.getConnection();
            statement = connection.prepareStatement(query);

            if (value instanceof String)
                statement.setString(1, (String) value);
            if (value instanceof Integer)
                statement.setInt(1, (Integer) value);

            resultSet = statement.executeQuery();

            List<T> list =createObjects(resultSet) ;
            if (list.isEmpty())
                return null;
            return list.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:find " + e.getMessage());
        } finally {
            DBConnect.close(resultSet);
            DBConnect.close(statement);
            DBConnect.close(connection);
        }
        return null;
    }

    /**
     * Face conexiunea cu baza de date, creeaza statement-ul SQL, apeland createUpdateQuery si il executa.
     * @param setField coloana din tabel pe care vrem sa o modificam
     * @param newValue noua valoare pe care o setam la coloana setField
     * @param searchField coloana din tabel dupa care identificam obiectul
     * @param searchValue valoarea obiectului la coloana searchField
     */
    public void update(String setField, Object newValue, String searchField, Object searchValue) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(setField, searchField);
        try {
            connection = DBConnect.getConnection();
            statement = connection.prepareStatement(query);
            if (newValue instanceof Integer)
                statement.setInt(1, (Integer) newValue);
            if (newValue instanceof Float)
                statement.setFloat(1, (Float) newValue);
            if (searchValue instanceof String)
                statement.setString(2, (String) searchValue);
            if (searchValue instanceof Integer)
                statement.setInt(2, (Integer) searchValue);

            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:update " + e.getMessage());
        } finally {
            DBConnect.close(statement);
            DBConnect.close(connection);
        }
    }
    /**
     * Face conexiunea cu baza de date, creeaza statement-ul SQL, apeland createSelectAllQuery si il executa.
     * @return toate obiectele din tabelul T; null daca select query nu gaseste nicio valoare in tabel
     */
    public List<T> report() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = DBConnect.getConnection();
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();


            List<T> list = createObjects(resultSet);
            if (list.isEmpty())
                return null;
            return list;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:report " + e.getMessage());
        } finally {
            DBConnect.close(resultSet);
            DBConnect.close(statement);
            DBConnect.close(connection);
        }
        return null;
    }

    /**
     * Un select query in SQL returneaza una sau mai multe valori din tabel, sub forma unui ResultSet.
     * Aceasta metoda are scopul de a converti aceste valori la obiectele corespunzatoare claselor Java.
     * @param resultSet valorile primite in urma unui select query
     * @return un List de obiecte corespunzatoare clasei la care sunt convertite valorile (numele clasei trebuie sa fie acelasi cu numele tabelului din baza de date)
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor((String)field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return list;
    }
}
