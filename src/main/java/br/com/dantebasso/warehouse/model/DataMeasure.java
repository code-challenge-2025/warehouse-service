package br.com.dantebasso.warehouse.model;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public record DataMeasure(
        UUID id,
        String sensorId,
        Integer sensorValue,
        SensorType sensorType,
        Map<String, Serializable> metadata
) {
}
