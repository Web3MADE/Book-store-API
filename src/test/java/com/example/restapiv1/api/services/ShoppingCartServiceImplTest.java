package com.example.restapiv1.api.services;

import com.example.restapiv1.api.interfaces.BookService;
import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.model.CartItem;
import com.example.restapiv1.api.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private BookService bookService;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    private List<Book> mockBooks;
    private Book bookOne;
    private Book bookTwo;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        // Initialize your mock data here - for example, a list of books
        bookOne = new Book(1L, "Book Title One", "Author One", new BigDecimal("15.99"), "Genre One");
        bookTwo = new Book(2L, "Book Title Two", "Author Two", new BigDecimal("9.99"), "Genre Two");
        ;        mockBooks = Arrays.asList(
                bookOne, bookTwo
        );
        cartItem = new CartItem(1L, bookOne, 5);
    }

    @Test
    void givenAddBookToCart_whenAdded_thenReturnAddedCartItem() {
        Long bookId = 1L;
        int quantity = 1;

        when(bookService.getBookById(bookId)).thenReturn(Optional.of(bookOne));
        when(cartItemRepository.findById(bookId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        CartItem addedCartItem = shoppingCartService.addBookToCart(bookId, quantity);

        assertEquals(addedCartItem, cartItem);
        assertEquals(addedCartItem.getBook(), cartItem.getBook());

    }

    @Test
    void givenAddBookToCart_whenBookNotFound_thenThrowException() {
        Long bookId = 1L;
        int quantity = 1;

        when(bookService.getBookById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            shoppingCartService.addBookToCart(bookId, quantity);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void givenGetCartItems_whenCartItemsFound_thenReturnCartItems() {
        CartItem cartItem1 = new CartItem(); // Assuming CartItem has a no-arg constructor
        cartItem1.setQuantity(1);
        // Set other fields as needed, e.g., cartItem1.setBook(new Book());

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(2);
        // Set other fields as needed

        List<CartItem> mockCartItems = Arrays.asList(cartItem1, cartItem2);
        when(cartItemRepository.findAll()).thenReturn(mockCartItems);

        List<CartItem> result = shoppingCartService.getCartItems();

        assertEquals(2, result.size());
        assertEquals(result, mockCartItems);
    }

    @Test
    void givenGetTotalPrice_whenPriceIsCalculated_thenReturnBigDecimal() {
        Long bookOneId = 1L;
        int itemQuantityOne = 1;
        Long bookTwoId = 2L;
        int itemQuantityTwo = 2;
        CartItem cartItem1 = new CartItem(bookOneId, bookOne, itemQuantityOne);
        CartItem cartItem2 = new CartItem(bookTwoId, bookTwo, itemQuantityTwo);
        List<CartItem> mockCartItems = Arrays.asList(cartItem1, cartItem2);
        when(cartItemRepository.findAll()).thenReturn(mockCartItems);

        BigDecimal totalPrice = shoppingCartService.getTotalPrice();

        System.out.println(totalPrice);
        assertEquals(new BigDecimal("35.97"), totalPrice);
    }

}