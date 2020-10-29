package com.epam.port.state;

import com.epam.port.logic.Port;
import com.epam.port.logic.Ship;

public class UnloadingState implements State {


    @Override
    public void process(Ship ship) {
        Port port = ship.getPort();
        int cargo = ship.getCargo();
        while (cargo > 0) {
            cargo--;
            port.incrementCargo();
        }
        ship.setCargo(cargo);
    }
}
