package by.kazakevich.springbooks.service.impl;

import by.kazakevich.springbooks.entity.Book;
import by.kazakevich.springbooks.repository.BookRepository;
import by.kazakevich.springbooks.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }
}
