package by.kazakevich.springbooks.controller;

import by.kazakevich.springbooks.forms.BookForm;
import by.kazakevich.springbooks.model.Book;
import java.util.Comparator;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class BookModelController {

  private static final List<Book> books = new ArrayList<>();

  static {
    books.add(
        new Book(1, "Full Stack Development with JHipster", "Deepu K Sasidharan, Sendil Kumar N"));
    books.add(new Book(128, "Pro Spring Security", "Carlo Scarioni, Massimo Nardone"));
  }

  //inject from application.properties
  @Value("${welcome.message}")
  private String message;

  @Value("${error.message}")
  private String errorMessage;

  @GetMapping({"/", "/index"})
  public ModelAndView index(Model model) {
    ModelAndView modelAndView = new ModelAndView("index");
    model.addAttribute("message", this.message);
    return modelAndView;
  }

  @GetMapping({"/allbooks"})
  public ModelAndView getAllBooksPage(Model model) {
    ModelAndView modelAndView = new ModelAndView("booklist");
    model.addAttribute("books", books);
    log.info("/allbooks was called");
    return modelAndView;
  }

  @GetMapping({"/addbook"})
  public ModelAndView getAddBookPage(Model model) {
    ModelAndView modelAndView = new ModelAndView("addbook");
    BookForm bookForm = new BookForm();
    model.addAttribute("bookform", bookForm);
    log.info("/addbook(GET) was called");
    return modelAndView;
  }

  @PostMapping({"/addbook"})
  public ModelAndView add(Model model, @ModelAttribute("bookform") BookForm bookForm) {
    ModelAndView modelAndView = new ModelAndView();
    String title = bookForm.getTitle();
    String author = bookForm.getAuthor();
    if (!StringUtils.isEmpty(title) && !StringUtils.isEmpty(author)) {
      books.add(new Book(getLastId() + 1, title, author));
      model.addAttribute("books", books);
      modelAndView.setViewName("redirect:/allbooks");
      return modelAndView;
    }
    model.addAttribute("errorMessage", this.errorMessage);
    modelAndView.setViewName("addbook");
    log.info("/addbook(POST) was called");
    return modelAndView;
  }

  @DeleteMapping({"/deletebook"})
  public ModelAndView delete(Integer id) {
    books.stream()
        .filter(book -> book.getId().equals(id))
        .findAny()
        .ifPresent(books::remove);

    ModelAndView modelAndView = new ModelAndView("booklist");
    log.info("/deletebook was called");
    return modelAndView;
  }

  @PutMapping({"/edit"})
  public ModelAndView update(Integer id, String beforeChanges, String changes) {
    Book book = books.stream()
        .filter(x -> x.getId().equals(id))
        .findAny()
        .orElseThrow(NoSuchElementException::new);

    if (book.getAuthor().equals(beforeChanges)) {
      book.setAuthor(changes);
    }

    if (book.getTitle().equals(beforeChanges)) {
      book.setTitle(changes);
    }

    ModelAndView modelAndView = new ModelAndView("booklist");
    log.info("/edit was called");
    return modelAndView;
  }

  private Integer getLastId() {
    return books
        .stream()
        .mapToInt(Book::getId)
        .max().orElseThrow(NoSuchElementException::new);
  }
}
