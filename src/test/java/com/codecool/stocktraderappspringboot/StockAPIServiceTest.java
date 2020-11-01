package com.codecool.stocktraderappspringboot;

import com.codecool.stocktraderappspringboot.stockApp.model.RemoteURLReader;
import com.codecool.stocktraderappspringboot.stockApp.model.StockAPIService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StockAPIServiceTest {
    private final RemoteURLReader mockedRemoteURLReader = Mockito.mock(RemoteURLReader.class);
    private final StockAPIService stockAPIService = new StockAPIService(mockedRemoteURLReader);
    private final String testUrl = "https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=test";

    @Test // everything works
    void testGetPriceNormalValues() {
        try {
            Mockito.when(mockedRemoteURLReader.readFromUrl(testUrl))
                    .thenReturn("{\"symbol\": \"TEST\", \"price\" : 332.12}");
            double expected = 332.12;
            double actual = stockAPIService.getPrice("test");
            assertEquals(expected, actual);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Test // readFromURL threw an exception
    void testGetPriceServerDown() {
        try {
            Mockito.when(mockedRemoteURLReader.readFromUrl(testUrl))
                    .thenThrow(new IOException());
            assertThrows(IOException.class, () -> stockAPIService.getPrice("test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test // readFromURL returned wrong JSON
    void testGetPriceMalformedResponse() {
        try {
            Mockito.when(mockedRemoteURLReader.readFromUrl(testUrl))
                    .thenReturn("price=2313");
            assertThrows(JSONException.class, () -> stockAPIService.getPrice("test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}