package exampleform;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import app.MyFormField;

public class OnlineShopForm {

	@MyFormField
	@NotNull(message = " nazwa")
	private String name = null;

	@MyFormField
	@AssertTrue(message = "Towar musi byc dostepny")
	private boolean isAvailable;

	@MyFormField
	@Size(min = 20, max = 200, message = "Twoje uzsadnienie musi byc wieksze od 20 i mniejsze od 200")
	private String description;

	@MyFormField
	@Min(value = 3, message = "Ilosc produktów min 2")
	@Max(value = 20, message = "Ilosc produktów max  20")
	private int amount;

	@MyFormField
	@Email(message = "Podaj poprawny adres email")
	private String email;

	
	@MyFormField
	@NotNull(message = " adres dostawy ")
	private String address;

	public OnlineShopForm() {	
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		this.name = name.equals("") ? null : name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		System.out.print(address);
		return address;
	}

	public void setAddress(String address) {
		this.address = address.equals("") ? null : address;
	}
}