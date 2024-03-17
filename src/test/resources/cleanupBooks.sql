DELETE FROM cart_items WHERE book_id IN (SELECT id FROM books);
DELETE FROM books;
