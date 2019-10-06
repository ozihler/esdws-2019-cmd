package library.application.outbound_ports;

import library.domain.entities.Customer;

public interface CustomerRepository {
    Customer findByUsername(String username);
}
