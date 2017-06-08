package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.DeviceInfo;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public class MySQLDeviceInfoDAO implements DeviceInfoDAO {
    @Override
    public ArrayList<DeviceInfo> selectData() {
        return null;
    }

    @Override
    public void updateData(DeviceInfo deviceInfo) {
        // Kan handig zijn voor de toekomst
    }
}
