package fr.unice.polytech.isa.tcf.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "orders")
public class Order implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@NotNull
	private Customer customer;

	@ElementCollection
	private Set<Item> items = new HashSet<>();

	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.IN_PROGRESS;

	public Order() {}

	public Order(Customer c) {
		this.customer = c;
	}

	public Order(Customer cust, Cookies cookie, int quantity) {
		this.customer = cust;
		this.addItem(new Item(cookie, quantity));
		this.setStatus(OrderStatus.IN_PROGRESS);
	}


	public Order(Customer customer, Set<Item> items) {
		this.customer = customer;
		this.items = items;
		this.setStatus(OrderStatus.IN_PROGRESS);
	}

	public OrderStatus getStatus() { return status; }
	public void setStatus(OrderStatus status) { this.status = status; }

	public int getId() { return id; }

	public Customer getCustomer() { return customer; }

	public Set<Item> getItems() { return items; }

	public void addItem(Item it) { this.items.add(it); }

	public double getPrice() {
		double result = 0.0;
		for(Item item: items) {
			result += (item.getQuantity() * item.getCookie().getPrice());
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Order)) return false;
		Order order = (Order) o;
		if (getCustomer() != null ? !getCustomer().getName().equals(order.getCustomer().getName()) : order.getCustomer() != null)
			return false;
		if (getItems() != null ? !getItems().equals(order.getItems()) : order.getItems() != null) return false;
		return getStatus() == order.getStatus();

	}

	@Override
	public int hashCode() {
		int result = getCustomer() != null ? getCustomer().getName().hashCode() : 0;
		result = 31 * result + (getItems() != null ? getItems().hashCode() : 0);
		result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
		return result;
	}
}
