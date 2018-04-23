package com.essent.testing.config;

public enum ConnectionType {
    SIMPLE("local"),
    SSH_TUNNEL("remote");

    ConnectionType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public static ConnectionType fromType(String type) {
        ConnectionType result = ConnectionType.SIMPLE;

        for (ConnectionType connectionType : values()) {
            if (connectionType.getType().equalsIgnoreCase(type)) {
                result = connectionType;
                break;
            }
        }
        return result;
    }
}
