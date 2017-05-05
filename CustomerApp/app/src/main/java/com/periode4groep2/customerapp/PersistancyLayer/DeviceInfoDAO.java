package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.DeviceInfo;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public interface DeviceInfoDAO {

    ArrayList<DeviceInfo> selectData();
    void updateData(DeviceInfo deviceInfo);
}
