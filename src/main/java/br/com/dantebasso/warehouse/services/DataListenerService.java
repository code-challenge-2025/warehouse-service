package br.com.dantebasso.warehouse.services;

import br.com.dantebasso.warehouse.model.SensorType;

public interface DataListenerService {

    void dataReader(Integer udpPort, SensorType sensorType);

}
