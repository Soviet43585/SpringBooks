package by.kazakevich.springbooks.controller;

import by.kazakevich.springbooks.entity.Book;
import by.kazakevich.springbooks.forms.BookForm;
import by.kazakevich.springbooks.repository.BookRepository;
import by.kazakevich.springbooks.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookEntityController {

  private final BookRepository bookRepository;
  private final BookService bookService;

  @Value("${welcome.message}")
  private String message;

  @Value("${error.message}")
  private String errorMessage;

  @GetMapping({"/alldbbooks"})
  public ModelAndView getAllBooksPage(Model model) {
    ModelAndView modelAndView = new ModelAndView("booklist");
    List<Book> books = bookService.findAll();
    model.addAttribute("books", books);
    log.info("/allbooks was called");
    return modelAndView;
  }

  @GetMapping({"/adddbbook"})
  public ModelAndView getAddBookPage(Model model) {
    ModelAndView modelAndView = new ModelAndView("addbook");
    BookForm bookForm = new BookForm();
    model.addAttribute("bookform", bookForm);
    log.info("/addbook(GET) was called");
    return modelAndView;
  }

  @PostMapping({"/adddbbook"})
  public ModelAndView add(Model model, @ModelAttribute("bookform") BookForm bookForm) {
    ModelAndView modelAndView = new ModelAndView("booklist");
    String title = bookForm.getTitle();
    String author = bookForm.getAuthor();
    if (!StringUtils.isEmpty(title) && !StringUtils.isEmpty(author)) {
      bookRepository.save(new Book(title, author));
      List<Book> books = bookRepository.findAll();
      model.addAttribute("books", books);
      return new ModelAndView("redirect:/alldbbooks");
    }

    model.addAttribute("errorMessage", this.errorMessage);
    modelAndView.setViewName("addbook");
    log.info("/addbook(POST) was called");
    return modelAndView;
  }
}
