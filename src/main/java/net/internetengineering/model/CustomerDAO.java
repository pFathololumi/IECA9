/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.internetengineering.model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.internetengineering.domain.Customer;
import net.internetengineering.domain.dealing.TransactionType;
import net.internetengineering.exception.DBException;

/**
 *
 * @author Hamed Ara
 */
public class CustomerDAO {
    private final static String dropIfExistQuery = "drop table customer if exists";
    private final static String createCustomerTableQuery = "create table customer (" +
                    "    id varchar(80) not null," +
                    "    name varchar(80) not null," +
                    "    family varchar(80) not null," +
                    "    balance bigint not null," +
                    "    primary key (id)" +
                    ")";
    private final static String insertQuery = "insert into customer values (?, ?,?,?)";
    private final static String selectByIdQuery = "select * from customer c where c.id=?";
    private final static String updateBalance = "update customer set balance = ? where id =?";
    
    public static void dropTableIfExist(Connection dbConnection) throws SQLException{
        dbConnection.createStatement().execute(dropIfExistQuery);
    }
    
    public static void createCustomerTable(Connection dbConnection) throws SQLException{
        Statement statement= dbConnection.createStatement();
        statement.execute(createCustomerTableQuery);
        insertAdmin(dbConnection);
    }
    
    public static void updateBalance(String cid,Long balance, Connection dbConnection) throws SQLException{
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateBalance);
        preparedStatement.setLong(1, balance);
        preparedStatement.setString(2, cid);
        preparedStatement.execute();
    }
    
    public static void insertAdmin(Connection dbConnection) throws SQLException{
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
        preparedStatement.setString(1, "1");
        preparedStatement.setString(2, "admin");
        preparedStatement.setString(3, "password");
        preparedStatement.setLong(4, 1000000L);
        preparedStatement.executeUpdate();
    }
    
    public static void addNewCustomer(Customer c, Connection dbConnection) throws SQLException{
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
        preparedStatement.setString(1, c.getId());
        preparedStatement.setString(2, c.getName());
        preparedStatement.setString(3, c.getFamily());
        preparedStatement.setLong(4, c.getMoney());
        preparedStatement.execute();
    }
    
    public static Customer findByID(String id,Connection dbConnection) throws SQLException, DBException{
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectByIdQuery);
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            Customer c = new Customer(rs.getString("id"),rs.getString("name"), rs.getString("family"));
            c.executeTransaction(TransactionType.DEPOSIT, rs.getLong("balance"),dbConnection);
            c.setInstruments(InstrumentDAO.findByCustomerID(id, dbConnection));
            return c;
        }else
            throw new DBException("CustomerID '"+id+"' is unknown" );
    }
    
    public static Boolean containCustomer(String id, Connection dbConnection) throws SQLException{
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectByIdQuery);
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next())
            return true;
        else
            return false;
    }
}
