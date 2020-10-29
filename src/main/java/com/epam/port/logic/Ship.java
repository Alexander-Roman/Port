package com.epam.port.logic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonAutoDetect
public class Ship implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private long id;
    private String name;
    private int cargo;
    private int capacity;
    private Port port;

    public Ship() {
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public void run() {
        Pier pier;
        try {
            pier = port.acquirePier();
            pier.accept(this);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cargo=" + cargo +
                ", capacity=" + capacity +
                ", port=" + port +
                '}';
    }
}
