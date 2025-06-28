package br.com.dantebasso.warehouse.services.impl;

import br.com.dantebasso.warehouse.factory.DataMeasureFactory;
import br.com.dantebasso.warehouse.model.DataMeasure;
import br.com.dantebasso.warehouse.model.SensorType;
import br.com.dantebasso.warehouse.services.DataListenerService;
import br.com.dantebasso.warehouse.services.DataMeasureProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DataListenerServiceImpl implements DataListenerService {
    
    private static final Integer BUFFER_SIZE = 2048;
    
    private final ExecutorService readerService = Executors.newFixedThreadPool(2);
    
    private final DataMeasureProducer dataMeasureProducer;
    
    @Override
    public void dataReader(final Integer udpPort, final SensorType sensorType) {
        readerService.submit(() -> {
           try (DatagramSocket socket = new DatagramSocket(udpPort)) {
               byte[] buffer = new byte[BUFFER_SIZE];
               log.info("Starting reading on port: {}", udpPort);
               
               while (true) {
                   try {
                       final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                       socket.receive(packet);
                       final String remoteAddress = packet.getAddress().getHostAddress();
                       log.info("Data received on port: {}, from: {}", socket.getLocalPort(), remoteAddress);
                       
                       final DataMeasure dataMeasure = DataMeasureFactory.fromBytes(packet.getData(), remoteAddress, sensorType);
                       log.info(
                           "Generated Data Measurement. Id: {}, Sensor: {} | Value: {}, Sensor: {}, Metadata: {}",
                           dataMeasure.id().toString(),
                           dataMeasure.sensorId(),
                           dataMeasure.sensorValue(),
                           dataMeasure.sensorType(),
                           dataMeasure.metadata()
                       );
                       
                       dataMeasureProducer.addMessageToTopic(dataMeasure);
                   } catch (IOException exception) {
                       log.error("Error receiving data on UDP PORT: {}", udpPort);
                   }
               }
               
           } catch (SocketException exception) {
               log.error("Error to open UDP socket on port: {}. Problem: {}", udpPort, exception.getMessage());
           }
        });
    }
    
}
