package com.epam.port.state;

import com.epam.port.logic.Port;
import com.epam.port.logic.Ship;

public class LoadingState implements State {

    @Override
    public void process(Ship ship) {
        Port port = ship.getPort();
        int cargo = ship.getCargo();
        int capacity = ship.getCapacity();
        while (cargo < capacity && port.decrementCargo()) {
            cargo++;
        }
        ship.setCargo(cargo);
    }
}
