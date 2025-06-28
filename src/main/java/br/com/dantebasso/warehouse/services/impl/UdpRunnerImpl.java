package br.com.dantebasso.warehouse.services.impl;

import br.com.dantebasso.warehouse.model.SensorType;
import br.com.dantebasso.warehouse.services.DataListenerService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class UdpRunnerImpl {
    
    private final DataListenerService dataListenerService;
    
    @PostConstruct
    public void init() {
        dataListenerService.dataReader(3344, SensorType.TEMPERATURE);
        dataListenerService.dataReader(3355, SensorType.HUMIDITY);
    }
    
}
