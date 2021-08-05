package by.kazakevich.springbooks.service;

import by.kazakevich.springbooks.entity.Book;
import java.util.List;

public interface BookService {

  List<Book> findAll();
}
