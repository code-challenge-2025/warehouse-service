package br.com.dantebasso.warehouse.services;

import br.com.dantebasso.warehouse.model.DataMeasure;

public interface DataMeasureProducer {
    
    void addMessageToTopic(final DataMeasure dataMeasure);
    
}
