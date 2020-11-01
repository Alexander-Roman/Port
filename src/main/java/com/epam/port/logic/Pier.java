package com.epam.port.logic;

import java.util.Objects;

public class Pier {

    private final Port port;

    public Pier(Port port) {
        this.port = port;
    }

    public void process(Ship ship) {
        if (ship.getCargo() > 0) {
            unload(ship);
        }
        if (ship.getCargo() < ship.getCapacity()) {
            load(ship);
        }
        port.releasePier(this);
    }

    private void unload(Ship ship) {
        int cargo = ship.getCargo();
        while (cargo > 0) {
            cargo--;
            port.incrementCargo();
        }
        ship.setCargo(cargo);
    }

    private void load(Ship ship) {
        int cargo = ship.getCargo();
        int capacity = ship.getCapacity();
        while (cargo < capacity && port.decrementCargo()) {
            cargo++;
        }
        ship.setCargo(cargo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pier pier = (Pier) o;
        return Objects.equals(port, pier.port);
    }

    @Override
    public int hashCode() {
        return port != null ? port.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "port=" + port +
                '}';
    }
}
