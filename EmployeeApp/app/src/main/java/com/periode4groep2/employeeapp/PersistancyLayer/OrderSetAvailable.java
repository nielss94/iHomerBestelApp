package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Order;

import java.util.ArrayList;

/**
 * Created by jesse on 10/05/17.
 */

public interface OrderSetAvailable {
    
    void orderSetAvailable(ArrayList<Order> orders);

}
