package main.java;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import main.DAL.*;
import main.POJO.*;

public class JdbcApp {

	public static void main(String[] args) {
		LocalizationResourcesProvider provider = new LocalizationResourcesProvider("en", "US");
		Locale locale = provider.getCurrentLocale();
		String[] supportedLocales = provider.getSupportedLocales();
/*
		testSupportedLocales(supportedLocales);
		testMessages(provider);
		testCurrency(locale);
		testCustomNumber(locale);
		testDates(locale);
		testPrices(provider);
*/
		 testDatabase();
	}

	private static void testSupportedLocales(String[] supportedLocales) {
		System.out.println("Dostepne locale: ");
		for (String s : supportedLocales) {
			System.out.println(s);
		}
		System.out.println("");
	}

	private static void testDatabase() {
		try {
			ClientDAL clientDal = new ClientDAL();
			ReservationDAL reservationDal = new ReservationDAL();
			testClients(clientDal);
			testReservation(reservationDal);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testClients(ClientDAL clientDal) throws SQLException, Exception {
		ClientPOJO client = new ClientPOJO("Stanisław Zoltaniecki", "608542518");
		client = clientDal.createClient(client);
		System.out.println(client.getId());

		client = clientDal.getClientById(9);
		System.out.println("Client: \n Id " + client.getId() + " Name " + client.getName() + " Phone " + client.getPhone());
		
		ArrayList<ClientPOJO> clients = clientDal.getAllClients();
		System.out.println("Number of Clients: " + clients.size());//client.getId() + " " + client.getName() + " " + client.getPhone());
	}

	private static void testReservation(ReservationDAL reservationDal) throws ParseException, SQLException, Exception {
		String sDate1 = "2019-05-15";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);

		ReservationPOJO reservation = new ReservationPOJO(9, date);
		reservation = reservationDal.createReservation(reservation);
		System.out.println(reservation.getId());

		reservation = reservationDal.getReservationById(1);
		System.out.println("Client: \n Id " + reservation.getId() + " ClietnId " + reservation.getClientId() + " Date " + reservation.getDate());
		
		ArrayList<ReservationPOJO> reservations = reservationDal.getAllReservations();
		System.out.println("Number of Reseravtions: " + reservations.size());
		return;
	}

	private static void testMessages(LocalizationResourcesProvider provider) {
		System.out.println("Messages:");
		ResourceBundle rsx = provider.getMessages();

		System.out.println(rsx.getString("test"));
		System.out.println(rsx.getString("Send"));
		System.out.println(rsx.getString("Order"));
		System.out.println(rsx.getString("Register"));
		System.out.println(rsx.getString("Reservate"));
		System.out.println("");
	}

	private static void testCurrency(Locale locale) {
		System.out.println("Waluta:");
		Double currencyAmount = new Double(9876543.21);
		Double currencyAmount2 = new Double(12);

		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

		System.out.println(currencyFormatter.format(currencyAmount));
		System.out.println(currencyFormatter.format(currencyAmount2));
		System.out.println("");
	}

	private static void testCustomNumber(Locale locale) {
		System.out.println("Custom Numbers:");
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		DecimalFormat df = (DecimalFormat) nf;

		String pattern = "###,###.###";
		df.applyPattern(pattern);
		String output = df.format(123456789.9876);
		System.out.println(pattern + " " + output + " " + locale.toString());

		pattern = ",####.###";
		df.applyPattern(pattern);
		output = df.format(123456789.9876);
		System.out.println(pattern + " " + output + " " + locale.toString());
		System.out.println("");
	}

	private static void testPrices(LocalizationResourcesProvider provider) {
		System.out.println("Cennik:");
		ResourceBundle prices = ResourceBundle.getBundle("main.resources.PriceBundle", provider.getCurrentLocale());
		ResourceBundle rsx = provider.getMessages();
		Enumeration<String> products = prices.getKeys();

		while (products.hasMoreElements()) {
			String product = products.nextElement();
			System.out.println(rsx.getString(product) + ": " + (Double) prices.getObject(product));
		}
		System.out.println("");
	}

	private static void testDates(Locale locale) {
		System.out.println("Data czas: ");
		DateFormat formatter1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
		DateFormat formatter2 = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
		DateFormat formatter3 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
		Date today = new Date();
		System.out.println("Short " + formatter1.format(today));
		System.out.println("Default " + formatter2.format(today));
		System.out.println("FULL " + formatter3.format(today));
		System.out.println("");
	}
}
