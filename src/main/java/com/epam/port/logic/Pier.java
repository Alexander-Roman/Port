package com.epam.port.logic;

import com.epam.port.state.LoadingState;
import com.epam.port.state.State;
import com.epam.port.state.UnloadingState;

public class Pier {

    private Ship ship;

    public void changeStateRequest(State state) {
        state.process(ship);
    }

    public void accept(Ship ship) {

        this.ship = ship;
        if (ship.getCargo() > 0) {
            changeStateRequest(new UnloadingState());
        }
        if (ship.getCargo() < ship.getCapacity()) {
            changeStateRequest(new LoadingState());
        }

        Port port = ship.getPort();
        port.releasePier(this);
    }
}
