package com.codecool.stocktraderappspringboot.stockApp.controller;


import com.codecool.stocktraderappspringboot.stockApp.model.RemoteURLReader;
import com.codecool.stocktraderappspringboot.stockApp.model.StockAPIService;
import com.codecool.stocktraderappspringboot.stockApp.model.Trader;
import com.codecool.stocktraderappspringboot.stockApp.model.loggers.FileLogger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
class TradeController {
	@Autowired
	private FileLogger logger;
	@Autowired
	private RemoteURLReader remoteURLReader;
	@Autowired
	private StockAPIService stockAPIService;
	@Autowired
	private Trader trader;

	@GetMapping("/buy/{stock}/{price}")
	public String buyStock(@PathVariable("stock") String stock, @PathVariable("price")double price) {
		boolean result = false;
		String message;
		try {
			result = trader.buy(stock, price);
			message = (result) ? "Purchased Stock." : "Couldn't purchase stock at that price.";
		} catch (JSONException | IOException e) {
			message = "There was an error while attempting to buy stock.";
			e.printStackTrace();
		}

		return message;
	}
}
//earlier version form stock-trader-app
///**
// * Provides a command line interface for stock trading.
// **/
//public class TradingController {
//
//	Logger logger;
//	Trader trader;
//	StockAPIService stockAPIService;
//	RemoteURLReader remoteURLReader;
//
//	public static void main(String[] args) {
//
//	    TradingController app = new TradingController();
//		Logger logger = new FileLogger();
//		RemoteURLReader remoteURLReader = new RemoteURLReader();
//		StockAPIService stockAPIService = new StockAPIService(remoteURLReader);
//		Trader trader = new Trader(logger, stockAPIService);
//		app.setLogger(logger);
//		app.setStockAPIService(stockAPIService);
//		app.setRemoteURLReader(remoteURLReader);
//		app.setTrader(trader);
//	    app.start();
//
//	}
//
//	public void start() {
//		Scanner keyboard = new Scanner(System.in);
//		System.out.println("Enter a stock symbol (for example aapl):");
//		String symbol = keyboard.nextLine();
//		System.out.println("Enter the maximum price you are willing to pay: ");
//		double price;
//		try {
//			price = keyboard.nextDouble();
//		} catch (InputMismatchException e) {
//			System.out.println("Invalid input. Enter a number");
//			return;
//		}
//
//		try {
//			boolean purchased = trader.buy(symbol, price);
//			if (purchased) {
//				logger.log("Purchased stock!");
//			}
//			else {
//				logger.log("Couldn't buy the stock at that price.");
//			}
//		} catch (Exception e) {
//			logger.log("There was an error while attempting to buy the stock: " + e.getMessage());
//		}
//	}
//
//	public void setLogger(Logger logger) {
//		this.logger = logger;
//	}
//
//	public void setTrader(Trader trader) {
//		this.trader = trader;
//	}
//
//	public void setStockAPIService(StockAPIService stockAPIService) {
//		this.stockAPIService = stockAPIService;
//	}
//
//	public void setRemoteURLReader(RemoteURLReader remoteURLReader) {
//		this.remoteURLReader = remoteURLReader;
//	}
//}