package br.com.dantebasso.warehouse.factory;

import br.com.dantebasso.warehouse.model.DataMeasure;
import br.com.dantebasso.warehouse.model.SensorType;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataMeasureFactory {

    private static final String MESSAGE_DATA_SEPARATOR = ";";
    private static final String MESSAGE_VALUE_SEPARATOR = "=";
    
    private static final String KEY_SENSOR_ID = "sensor_id";
    private static final String KEY_SENSOR_VALUE = "value";
    
    protected static final String METADATA_KEY_UTC_TIME = "utcTime";
    protected static final String METADATA_KEY_REMOTE_ADDRESS = "remoteAddress";
    
    public static DataMeasure fromBytes(final byte[] data, final String remoteAddress, final SensorType sensorType) {
        final String message = new String(data, 0, data.length);
        return fromString(message, remoteAddress, sensorType);
    }

    public static DataMeasure fromString(final String message, final String remoteAddress, final SensorType sensorType) {
        final String[] splitData = message.split(MESSAGE_DATA_SEPARATOR);
        final Map<String, String> dataMapper = Arrays.stream(splitData)
                .map(part -> part.split(MESSAGE_VALUE_SEPARATOR))
                .filter(kv -> kv.length == 2)
                .collect(Collectors.toMap(
                        kv -> kv[0].trim(),
                        kv -> kv[1].trim()
                ));
        
        final String sensorId = dataMapper.get(KEY_SENSOR_ID);
        final Integer sensorValue = parseInt(dataMapper.get(KEY_SENSOR_VALUE));
        final Map<String, Object> metaData = new HashMap<String, Object>();
        metaData.put(METADATA_KEY_UTC_TIME, Instant.now());
        metaData.put(METADATA_KEY_REMOTE_ADDRESS, remoteAddress);
        return new DataMeasure(UUID.randomUUID(), sensorId, sensorValue, sensorType, metaData);
    }
    
    private static Integer parseInt(final String val) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
}
