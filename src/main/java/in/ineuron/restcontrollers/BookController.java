package in.ineuron.restcontrollers;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.ineuron.services.BookService;
import in.ineuron.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ineuron.dto.BookAddRequest;
import in.ineuron.dto.BookResponse;
import in.ineuron.models.Book;
import in.ineuron.models.ImageFile;
import in.ineuron.utils.BookUtils;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;
    private AppUtils appUtils;
    private ObjectMapper mapper;
    BookUtils bookUtils;

    @PostMapping("seller/add-book")
    public ResponseEntity<?> saveBookData(@RequestParam MultipartFile coverImage, @RequestParam String bookInfo) throws IOException {

        //convert string into BookAddRequest object
        BookAddRequest bookRequest = mapper.readValue(bookInfo, BookAddRequest.class);

        Book book = new Book();
        BeanUtils.copyProperties(bookRequest, book);

        ImageFile imageFile = new ImageFile();
        imageFile.setName(coverImage.getOriginalFilename());
        imageFile.setType(coverImage.getContentType());
        imageFile.setImageData(coverImage.getBytes());

        book.setCoverImage(imageFile);

        bookService.insertBookInfo(book);

        return ResponseEntity.ok("Book info inserted successfully...");
    }

    @GetMapping("seller/{seller-id}/all-book")
    public ResponseEntity<List<BookResponse>> getBookBySeller(@PathVariable("seller-id") Long sellerId) {

        List<Book> bookList = bookService.fetchBooksBySellerId(sellerId);

        List<BookResponse> bookResponseList = bookUtils.getBookResponse(bookList);

        return ResponseEntity.ok(bookResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {

        Optional<Book> bookOptional = bookService.fetchBookById(id);

        if (bookOptional.isPresent()) {

            Book book = bookOptional.get();

            BookResponse bookResponse = new BookResponse();
            BeanUtils.copyProperties(book, bookResponse);
            bookResponse.setImageURL(appUtils.getBaseURL() + "/api/image/" + book.getCoverImage().getId());
            return ResponseEntity.ok(bookResponse);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("book not found for this book id");
        }
    }

    @PutMapping("seller/update-book")
    public ResponseEntity<String> updateBook(@RequestParam MultipartFile coverImage, @RequestParam String bookInfo) throws IOException {

        //convert string into BookAddRequest object
        BookAddRequest bookRequest = mapper.readValue(bookInfo, BookAddRequest.class);

        Book book = new Book();
        BeanUtils.copyProperties(bookRequest, book);

        ImageFile imageFile = new ImageFile();
        imageFile.setName(coverImage.getOriginalFilename());
        imageFile.setType(coverImage.getContentType());
        imageFile.setImageData(coverImage.getBytes());

        book.setCoverImage(imageFile);

        Book updatedBook = bookService.updateBook(book);

        String msg = "";

        if (updatedBook != null){
            msg = "Books Info updated successfully";
        }else{
            msg = "Books Info updation unsuccessfully";
        }

        return ResponseEntity.ok(msg);
    }

    @PatchMapping("/{id}/change-status")
    public ResponseEntity<String> changeBookStatus(@PathVariable Long id) {

        Boolean status = bookService.checkBookStatus(id);

        if (status){
            bookService.deactivateBookStatus(id);
        }else{
            bookService.activateBookStatus(id);
        }

        return ResponseEntity.ok("Status changed successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> getSearchedBooks(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String query) throws JsonProcessingException {

        List<BookResponse> searchedBooks=new ArrayList<>();

        if (!query.isBlank()) {
            searchedBooks = bookService.enhancedSearchBooks(query, page, size);
        }
        return ResponseEntity.ok(searchedBooks);
    }

    @GetMapping("/suggest-book-names")
    public ResponseEntity<List<String>> getSuggestedBookNames(@RequestParam String query, @RequestParam Integer size) {

        List<String> suggestedBooks = new ArrayList<>();

        if (!query.isBlank()) {
            suggestedBooks = bookService.getSuggestedBookNamesByTitle(query.trim(), size);
        }

        return ResponseEntity.ok(suggestedBooks);
    }

}











