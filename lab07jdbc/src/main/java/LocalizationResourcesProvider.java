package main.java;

import java.text.DateFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationResourcesProvider {
	private String language;
	private String country;
    private Locale currentLocale;
    private ResourceBundle messages;
	
	
    public LocalizationResourcesProvider() {
    	this.language = "pl";
    	this.country = "PL";
    	currentLocale = new Locale(this.language, this.country);
    	messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    }
    
    public LocalizationResourcesProvider(String language, String country) {
    	this.language = language;
    	this.country = country;
    	currentLocale = new Locale(this.language, this.country);
    	messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    }
    
    public String getCurrencySymbol() {
    	return Currency.getInstance(currentLocale).getSymbol(currentLocale);
    }
    
    public String[] getSupportedLocales() {
    	String[] supportedLocales = {			  
			    new Locale("pl", "PL").toString(),
			    new Locale("en", "US").toString()
    	};
    	return supportedLocales;
    }
    
	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}

	public ResourceBundle getMessages() {
		return messages;
	}
	
	public Locale getCurrentLocale() {
		return currentLocale;
	}
}
