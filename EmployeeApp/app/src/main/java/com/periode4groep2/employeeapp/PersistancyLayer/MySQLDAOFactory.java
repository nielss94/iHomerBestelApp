package com.periode4groep2.employeeapp.PersistancyLayer;

/**
 * Created by Niels on 5/5/2017.
 */

public class MySQLDAOFactory implements DAOFactory {
    @Override
    public AccountDAO createAccountDAO() {
        return new MySQLAccountDAO();
    }

//    @Override
//    public DeviceInfoDAO createDeviceInfoDAO() {
//        return new MySQLDeviceInfoDAO();
//    }
//
//    @Override
//    public OrderDAO createOrderDAO() {
//        return new MySQLOrderDAO();
//    }
//
//    @Override
//    public ProductDAO createProductDAO() {
//        return new MySQLProductDAO();
//    }
}
