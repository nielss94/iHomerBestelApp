package com.periode4groep2.customerapp.PersistancyLayer;

/**
 * Created by Niels on 5/5/2017.
 */

public interface DAOFactory {

    AccountDAO createAccountDAO();
    DeviceInfoDAO createDeviceInfoDAO();
    OrderDAO createOrderDAO();
    ProductDAO createProductDAO();
}
