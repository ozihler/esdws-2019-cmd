package library.application.outbound_ports;

import library.Customer;

public interface CustomerRepository {
    Customer findByUsername(String username);
}
