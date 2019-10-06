package library.application.outbound_ports.persistence;

import library.domain.entities.Customer;

public interface CustomerRepository {
    Customer findByUsername(String username);
}
