package com.epam.port.main;

import com.epam.port.data.DataException;
import com.epam.port.data.JsonShipCreator;
import com.epam.port.data.ShipCreator;
import com.epam.port.logic.Port;
import com.epam.port.logic.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {

    private static final String SHIPS_JSON = "data/Ships.json";
    private static final Port PORT = Port.getInstance();
    private static final Logger LOGGER = LogManager.getLogger();


    public static void main(String[] args) {

        try {
            process();
        } catch (DataException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    private static void process() throws DataException {
        ShipCreator creator = new JsonShipCreator(PORT);
        List<Ship> ships = creator.createShips(SHIPS_JSON);
        int size = ships.size();
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        ships.forEach(executorService::submit);
        executorService.shutdown();
    }
}
