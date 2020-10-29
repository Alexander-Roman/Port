package com.epam.port.data;

import com.epam.port.logic.Port;
import com.epam.port.logic.Ship;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonShipCreator implements ShipCreator {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String SHIPS = "ships";
    private final Port port;

    public JsonShipCreator(Port port) {
        this.port = port;
    }

    @Override
    public List<Ship> createShips(String fileName) throws DataException {
        List<Ship> ships = new ArrayList<Ship>();

        JsonNode root;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            root = MAPPER.readTree(reader);
        } catch (IOException e) {
            throw new DataException(e);
        }
        JsonNode shipsNode = root.get(SHIPS);

        for (JsonNode shipNode : shipsNode) {
            try {
                String shipJson = shipNode.toString();
                Ship ship = MAPPER.readValue(shipJson, Ship.class);
                ship.setPort(port);
                ships.add(ship);
            } catch (JsonProcessingException e) {
                throw new DataException(e);
            }
        }
        return ships;
    }
}
