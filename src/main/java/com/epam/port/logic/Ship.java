package com.epam.port.logic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

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

    public Ship(long id, String name, int cargo, int capacity, Port port) {
        this.id = id;
        this.name = name;
        this.cargo = cargo;
        this.capacity = capacity;
        this.port = port;
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
            pier.process(this);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ship ship = (Ship) o;
        return id == ship.id &&
                cargo == ship.cargo &&
                capacity == ship.capacity &&
                Objects.equals(name, ship.name) &&
                Objects.equals(port, ship.port);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + cargo;
        result = 31 * result + capacity;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cargo=" + cargo +
                ", capacity=" + capacity +
                ", port=" + port +
                '}';
    }
}
