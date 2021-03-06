package fr.unice.polytech.isa.tcf.components;


import fr.unice.polytech.isa.tcf.CartModifier;
import fr.unice.polytech.isa.tcf.Payment;
import fr.unice.polytech.isa.tcf.entities.Customer;
import fr.unice.polytech.isa.tcf.entities.Item;
import fr.unice.polytech.isa.tcf.exceptions.EmptyCartException;
import fr.unice.polytech.isa.tcf.exceptions.PaymentException;
import fr.unice.polytech.isa.tcf.interceptors.CartCounter;
import fr.unice.polytech.isa.tcf.interceptors.Logger;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import java.util.Optional;
import java.util.Set;

public abstract class CartBean implements CartModifier {

	@EJB protected Payment cashier;

	@Override
	@Interceptors({CartCounter.class})
	public String validate(Customer c) throws PaymentException, EmptyCartException {
		if(contents(c).isEmpty())
			throw new EmptyCartException(c.getName());
		return cashier.payOrder(c, contents(c));
	}

	@Override
	public final boolean remove(Customer c, Item item) {
		return add(c, new Item(item.getCookie(), -item.getQuantity()));
	}

	/**
	 * Protected method to update the cart of a given customer, shared by both stateful and stateless beans
	 */
	protected Set<Item> updateCart(Customer c, Item item) {
		Set<Item> items = contents(c);
		Optional<Item> existing = items.stream().filter(e -> e.getCookie().equals(item.getCookie())).findFirst();
		if(existing.isPresent()) {
			items.remove(existing.get());
			Item toAdd = new Item(item.getCookie(), item.getQuantity() + existing.get().getQuantity());
			if(toAdd.getQuantity() > 0) { items.add(toAdd); }
		} else {
			items.add(item);
		}
		return items;
	}
}
