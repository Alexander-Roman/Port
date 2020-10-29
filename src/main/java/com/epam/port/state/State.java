package com.epam.port.state;

import com.epam.port.logic.Ship;

public interface State {

    void process(Ship ship);
}
