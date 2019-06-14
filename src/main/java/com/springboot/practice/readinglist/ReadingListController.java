package com.springboot.practice.readinglist;

import com.springboot.practice.bean.Book;
import com.springboot.practice.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class ReadingListController {
    @Autowired
    private ReadingListRepository readingListRepository;

    public ReadingListController(
            ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }
    @RequestMapping(value="/readinglist/{reader}", method= RequestMethod.GET)
    public String readersBooks(
            @PathVariable("reader") String reader,
            Model model) {
        List<Book> readingList =
                readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }
    @RequestMapping(value="/readinglist/{reader}", method=RequestMethod.POST)
    public String addToReadingList(
            @PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/{reader}";
    }
    @RequestMapping(value="/author/{author}", method= RequestMethod.GET)
    public String readersAuthorBooks(
            @PathVariable("author") String author,
            Model model) {
        List<Book> readingList =
                readingListRepository.findByAuthor(author);
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam("error") String error, Model model){
        if(error != null && !error.isEmpty() && "true".equals(error)){
            model.addAttribute("loginError", true);
            return "login";
        }

        return "login";
    }

}
