package com.epam.port.data;

import com.epam.port.logic.Ship;

import java.util.List;

public interface ShipCreator {

    List<Ship> createShips(String fileName) throws DataException;
}
