package br.com.dantebasso.warehouse.model;

public enum SensorType {
    
    TEMPERATURE("temperatureSensors"),
    HUMIDITY("humiditySensors");
    
    private final String label;
    
    SensorType(String label) {
        this.label = label;
    }
    
    public static SensorType fromLabel(String label) {
        for (SensorType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown sensor type label: " + label);
    }
    
    public String getLabel() {
        return label;
    }
    
}
