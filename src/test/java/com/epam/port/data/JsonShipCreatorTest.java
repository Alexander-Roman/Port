package com.epam.port.data;

import com.epam.port.logic.Port;
import com.epam.port.logic.Ship;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class JsonShipCreatorTest {

    private static final String FILE_CORRECT = "src/test/resources/ShipsTestCorrect.json";
    private static final String FILE_CORRUPTED = "src/test/resources/ShipsTestCorrupted.json";
    private static final String FILE_LOST = "src/test/resources/ShipsTestLost.json";

    private final Port port = Mockito.mock(Port.class);
    private final JsonShipCreator creator = new JsonShipCreator(port);
    private final List<Ship> shipsCorrect = Arrays.asList(
            new Ship(1, "LEE A.TREGURTHA", 500, 1000, port),
            new Ship(2, "MAERSK MC KINNEY MOLLER", 500, 1000, port)
    );

    @Test
    public void testCreateShipsShouldReturnListOfShipsWhenFileCorrect() throws DataException {
        //given
        //when
        List<Ship> actual = creator.createShips(FILE_CORRECT);
        //then
        Assert.assertEquals(actual, shipsCorrect);
    }

    @Test(expectedExceptions = DataException.class)
    public void testCreateShipsShouldThrowDataExceptionWhenFileCorrupted() throws DataException {
        //given
        //when
        creator.createShips(FILE_CORRUPTED);
        //then
    }

    @Test(expectedExceptions = DataException.class)
    public void testCreateShipsShouldThrowDataExceptionWhenFileLost() throws DataException {
        //given
        //when
        creator.createShips(FILE_LOST);
        //then
    }

}
