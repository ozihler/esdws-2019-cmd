package library.application.outbound_ports.persistence;

import library.domain.entities.Book;

import java.util.List;

public interface IFindSingleBooks {
    Book byId(int bookId);
}