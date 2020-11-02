package com.codecool.stocktraderappspringboot;

import com.codecool.stocktraderappspringboot.stockApp.model.StockAPIService;
import com.codecool.stocktraderappspringboot.stockApp.model.Trader;
import com.codecool.stocktraderappspringboot.stockApp.model.logger.FileLogger;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TraderTest {

    private final StockAPIService mockedStockAPIService = Mockito.mock(StockAPIService.class);
    private final Trader trader = new Trader(new FileLogger(), mockedStockAPIService);

    @Test // Bid was lower than price, buy should return false.
    void testBidLowerThanPrice(){
        try {
            Mockito.when(mockedStockAPIService.getPrice("symbol"))
                    .thenReturn(1200.0);
            assertFalse(trader.buy("symbol", 10));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Test // bid was equal or higher than price, buy() should return true.
    void testBidHigherThanPrice() {
        try {
            Mockito.when(mockedStockAPIService.getPrice("symbol"))
                    .thenReturn(12.0);
            assertTrue(trader.buy("symbol", 1000));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}