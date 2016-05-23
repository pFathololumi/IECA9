/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.internetengineering.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.internetengineering.domain.Role;
import net.internetengineering.exception.DBException;

/**
 *
 * @author Hamed Ara
 */
public class CustomerRoleDAO {
    private final static String dropIfExistQuery = "drop table role_customer if exists";
    private final static String createCustomerRoleTableQuery = "create table role_customer (\n" +
            "    customer_id varchar(80) not null," +
            "    role_name varchar(80) not null," +
            "    primary key (customer_id,role_name)," +
            "    constraint custom_id_fk foreign key(customer_id) references customer(id) on delete cascade," +
            "    constraint role_name_fk foreign key(role_name) references role(name) on delete cascade" +
            ");";
    private final static String insertNewCustomerRole = "insert into role_customer values (?, ?)";
    private final static String selectByCidQuery ="select * from role_customer rc where rc.customer_id =?";
    
    public static void dropTableIfExist(Connection dbConnection) throws SQLException{
        dbConnection.createStatement().execute(dropIfExistQuery);
    }
    
    public static void createRoleTable(Connection dbConnection) throws SQLException{
        dbConnection.createStatement().execute(createCustomerRoleTableQuery);
        insertRole("1", "admin", dbConnection);
    }
   
    private static void insertRole(String cid, String roleName,Connection dbConnection) throws SQLException{
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertNewCustomerRole);
        preparedStatement.setString(1, cid);
        preparedStatement.setString(2, roleName);
        preparedStatement.executeUpdate();
    }
    
//    public static List<Role> findByCustomerID(String cid, Connection dbConnection) throws SQLException, DBException{
//        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectByCidQuery);
//        preparedStatement.setString(1, cid);
//        ResultSet rs = preparedStatement.executeQuery();
//        List<Role> insts = new ArrayList<Role>();
//        while(rs.next()){
//            Role r = RoleDAO.findByRoleName(rs.getString(), dbConnection);
//            Instrument i = new Instrument(rs.getString("symbol"), rs.getLong("quantity"));
//            List<Offering> offers = InstrumentOfferingDAO.selectByCidAndSymbol(cid, rs.getString("symbol"), dbConnection);
//            for(Offering o : offers)
//                if(o instanceof SellingOffer)
//                    i.addSellingOffer((SellingOffer)o);
//                else if(o instanceof BuyingOffer)
//                    i.addBuyingOffer((BuyingOffer)o);
//            insts.add(i);
//        }
//        return insts;
//    }
}
