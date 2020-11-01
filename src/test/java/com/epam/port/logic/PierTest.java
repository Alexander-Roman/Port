package com.epam.port.logic;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PierTest {

    @Test
    public void testProcessShouldUnloadAndLoadShipWhenCargoIsPresent() {
        //given
        Port port = Mockito.mock(Port.class);
        Pier pier = new Pier(port);
        Ship ladenShip = new Ship(1, "Laden Ship", 1, 2, port);
        //when
        Mockito.when(port.decrementCargo()).thenReturn(true);
        pier.process(ladenShip);
        //then
        int actual = ladenShip.getCargo();
        Assert.assertEquals(actual, 2);
        Mockito.verify(port, Mockito.times(1)).incrementCargo();
        Mockito.verify(port, Mockito.times(2)).decrementCargo();
    }

    @Test
    public void testProcessShouldOnlyLoadShipWhenCargoIsNotPresent() {
        //given
        Port port = Mockito.mock(Port.class);
        Pier pier = new Pier(port);
        Ship unloadedShip = new Ship(1, "Unloaded Ship", 0, 2, port);
        //when
        Mockito.when(port.decrementCargo()).thenReturn(true);
        pier.process(unloadedShip);
        //then
        int actual = unloadedShip.getCargo();
        Assert.assertEquals(actual, 2);
        Mockito.verify(port, Mockito.times(0)).incrementCargo();
        Mockito.verify(port, Mockito.times(2)).decrementCargo();
    }
}
