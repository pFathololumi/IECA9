package net.internetengineering.domain;

import java.sql.Connection;
import java.sql.SQLException;
import net.internetengineering.domain.dealing.Instrument;
import net.internetengineering.domain.dealing.Offering;
import net.internetengineering.domain.dealing.TransactionType;

import java.util.ArrayList;
import java.util.List;
import net.internetengineering.exception.DBException;
import net.internetengineering.model.CustomerDAO;
import net.internetengineering.model.InstrumentDAO;


public class Customer {
    private String id;
    private String name;
    private String family;
    private Account customerAccount;
    private List<Instrument> instruments;

    public Customer(String id,String n,String f){
            this.id = id;
            this.name = n;
            this.family = f;
            this.customerAccount = new Account();
            this.instruments = new ArrayList<Instrument>();
    }

    public String getId() {
            return id;
    }

    public List<Instrument> getInstruments(){
        return instruments;
    }

    public void executeTransaction(TransactionType type, Long amount, Connection dbConnection) throws SQLException{
            customerAccount.executeTransaction(type,amount);
            CustomerDAO.updateBalance(id, customerAccount.getBalance(), dbConnection);
    }

    public Long getMoney(){
            return this.customerAccount.getBalance();
    }

    public Boolean hasEnoughMoney(Long amount){
            return customerAccount.isEnoughMoney(amount);
    }

    public Boolean hasEnoughStock(String instrumentName,Offering offer){
            for(Instrument i : instruments){
                    if(i.symbolIsMatched(instrumentName))
                            if(i.HasQuantity(offer.getQuantity()))
                                    return true;	
            }
            return false;
    }

    public void updateInstruments(String type,Long count,String name,Connection dbConnection) throws SQLException, DBException{
            boolean flag = false;
            List<Instrument> instruments = InstrumentDAO.findByCustomerID(id, dbConnection);
            for(Instrument i : instruments){
                    if(i.symbolIsMatched(name)){
                            i.changeQuantity(type, count);
                            InstrumentDAO.updateQuantity(id, name, i.getQuantity(), dbConnection);
                            flag = true;
                            break;
                    }
            }
            if(!flag){
//                    instruments.add(new Instrument(name, count));
                    InstrumentDAO.insertNewInstrument(id, name, count, dbConnection);
            }
    }

    public String getName() {
        return name;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }
    
    public String getFamily() {
        return family;
    }
    
}

