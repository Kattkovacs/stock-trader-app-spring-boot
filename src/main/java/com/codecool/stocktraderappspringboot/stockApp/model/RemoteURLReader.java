package com.codecool.stocktraderappspringboot.stockApp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class RemoteURLReader {

    public String readFromUrl(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String lines = reader.lines().collect(Collectors.joining("\n"));
        reader.close();
        return lines;
    }

    /**
     * Stock price service that gets prices from Yahoo.
     **/
    public static class StockAPIService {

        private static final String apiPath = "https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=%s";

        RemoteURLReader remoteURLReader;

        public StockAPIService(RemoteURLReader remoteURLReader) {
            this.remoteURLReader = remoteURLReader;
        }

        /** Get stock price from iex and return as a double
         *  @param symbol Stock symbol, for example "aapl"
         **/
        public double getPrice(String symbol) throws IOException, JSONException {
            String url = String.format(apiPath, symbol);
            String result = remoteURLReader.readFromUrl(url);
            JSONObject json = new JSONObject(result);
            if (!json.get("symbol").toString().toLowerCase().equals(symbol.toLowerCase())) {
                throw new IllegalArgumentException("Symbol does not exist!");
            }
            String price = json.get("price").toString();
            return Double.parseDouble(price);
        }

        /** Buys a share of the given stock at the current price. Returns false if the purchase fails */
        public boolean buy(String symbol) {
            // Stub. No need to implement this.
            return true;
        }
    }
}
