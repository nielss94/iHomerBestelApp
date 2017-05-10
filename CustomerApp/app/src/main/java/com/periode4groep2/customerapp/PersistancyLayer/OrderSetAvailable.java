package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.Order;

import java.util.ArrayList;

/**
 * Created by jesse on 10/05/17.
 */

public interface OrderSetAvailable {
    
    void orderSetAvailable(ArrayList<Order> orders);

}
