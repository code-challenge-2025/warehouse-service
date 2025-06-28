package br.com.dantebasso.warehouse.factory;

import br.com.dantebasso.warehouse.model.DataMeasure;
import br.com.dantebasso.warehouse.model.SensorType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static br.com.dantebasso.warehouse.factory.DataMeasureFactory.METADATA_KEY_REMOTE_ADDRESS;
import static br.com.dantebasso.warehouse.factory.DataMeasureFactory.METADATA_KEY_UTC_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataMeasureFactoryUnitTest {
    
    @ParameterizedTest(name = "{index} => \"{5}\"")
    @MethodSource("messagesForTesting")
    public void testString(final String message, final String remoteAddress, final String expectedSensor
            , final Integer expectedValue, final SensorType sensorType, final String logMessage) {
        
        final DataMeasure dataMeasure = DataMeasureFactory.fromString(message, remoteAddress, sensorType);
        assertNotNull(dataMeasure.metadata());
        assertEquals(remoteAddress, dataMeasure.metadata().get(METADATA_KEY_REMOTE_ADDRESS));
        assertNotNull(dataMeasure.metadata().get(METADATA_KEY_UTC_TIME));
        assertNotNull(dataMeasure.id());
        assertEquals(sensorType, dataMeasure.sensorType());
        assertEquals(expectedSensor, dataMeasure.sensorId());
        assertEquals(expectedValue, dataMeasure.sensorValue());
    }
    
    private static Stream<Arguments> messagesForTesting() {
        return Stream.of(
            Arguments.of("sensor_id=t1; value=30", "localhost", "t1", 30, SensorType.TEMPERATURE, "Temperature sensor, T1 with value 30 from localhost"),
            Arguments.of("sensor_id=h1; value=100", "localhost", "h1", 100, SensorType.HUMIDITY, "Humidity sensor, H1 with value 100 from localhost"),
            Arguments.of("sensor_id=SensorTemp1; value=1000", "192.168.200.100", "SensorTemp1", 1000, SensorType.HUMIDITY, "Temperature sensor, SensorTemp1 with value 1000 from 192.168.200.100")
        );
    }

}
