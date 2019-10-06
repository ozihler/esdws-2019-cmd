package library;

import library.application.outbound_ports.CustomerRepository;

import java.util.Map;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<String, Customer> customers;

    public InMemoryCustomerRepository() {
        customers = Map.of(
                "AnyUser", new Customer("anyUser")
        );
    }

    @Override
    public Customer findByUsername(String username) {
        return customers.getOrDefault(username, new Customer(username));
    }
}