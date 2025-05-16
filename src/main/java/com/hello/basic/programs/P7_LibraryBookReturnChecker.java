package com.hello.basic.programs;

import java.time.LocalDate;
import java.util.*;

public class P7_LibraryBookReturnChecker {

    public static void main(String[] args) {

        Library library = new Library(12345, "Ai lib", List.of(
                new Library.Book(101, "ramayan"),
                new Library.Book(101, "ramayan"),
                new Library.Book(101, "ramayan"),
                new Library.Book(100, "mahabharat")
        ));

        Library.Member member = library.createMember("sahil", 18, Person.Gender.MALE);

        Library.Book book = library.borrowBook(101, member.getId());

        library.returnBook(book.getISBN(), book.getId(), member.getId());

        try {
//            book not found
            library.borrowBook(102, member.getId());
        } catch (BookNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
//            not issued to anyone
            library.returnBook(101, UUID.randomUUID(), UUID.randomUUID());
        } catch (BookNotBorrowedException e) {
            System.err.println(e.getMessage());
        }

        try {
            Library.Book mahabharatBook1 = library.borrowBook(100, member.getId());
//            out of stock
            Library.Book mahabharatBook2 = library.borrowBook(100, member.getId());
        } catch (BookNotAvailableException e) {
            System.err.println(e.getMessage());
        }

        try {
//            member not found
            library.borrowBook(101, UUID.randomUUID());
        } catch (MemberNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
//            time expired
            Library.Book borrowedBook = library.borrowBook(101, member.getId());
            library.returnBook(borrowedBook.getISBN(), borrowedBook.getId(), member.getId(), LocalDate.now().minusDays(1));
        } catch (BookOverdueException e) {
            System.err.println(e.getMessage());
        }

    }

}


class Library {
    private final long registerNo;
    private final String name;
    private final Map<Long, LinkedHashMap<UUID, Book>> availableBooksByISBN;
    private final Map<Long, LinkedHashMap<UUID, Book>> borrowedBooksByISBN;
    private final LinkedHashMap<UUID, Member> memberById;

    public Library(long registerNo, String name, List<Book> books) {
        this.registerNo = registerNo;
        this.name = name;
        this.availableBooksByISBN = new HashMap<>();
        this.borrowedBooksByISBN = new HashMap<>();
        this.memberById = new LinkedHashMap<>();
        this.addAll(books);
    }

    public void addBook(Book book) {
        availableBooksByISBN.putIfAbsent(book.getISBN(), new LinkedHashMap<>());
        availableBooksByISBN.get(book.getISBN()).put(book.getId(), book);
    }

    public void addAll(List<Book> books) {
        books.forEach(this::addBook);
    }

    public long getRegisterNo() {
        return registerNo;
    }

    public String getName() {
        return name;
    }

    public Member createMember(String name, int age, Person.Gender gender) {
        Member member = new Member(name, age, gender);
        memberById.put(member.getId(), member);
        return member;
    }

    public void printAll() {
        System.out.println("availableBooksByISBN = " + availableBooksByISBN);
        System.out.println("borrowedBooksByISBN = " + borrowedBooksByISBN);
        System.out.println("memberById = " + memberById);
    }

    public Book borrowBook(long ISBN, UUID memberId) {
        printAll();
        Optional.ofNullable(availableBooksByISBN.get(ISBN)).orElseThrow(() -> new BookNotFoundException("book with ISBN:" + ISBN + "is not found"));
        LinkedHashMap<UUID, Book> availableBooksById = availableBooksByISBN.get(ISBN);
        if (availableBooksById.size() == 0) {
            throw new BookNotAvailableException("book with ISBN:" + ISBN + " is not available");
        }
        Optional.ofNullable(memberById.get(memberId)).orElseThrow(() -> new MemberNotFoundException("member with id:" + memberId + " is not found"));
        Member member = memberById.get(memberId);
        Book book = availableBooksById.pollFirstEntry().getValue();
        book.setBookIsBorrowed(true);
        book.setIssuedToMember(member);
        book.setIssueDate(LocalDate.now());
        book.setReturnDate(LocalDate.now().plusDays(7));
        borrowedBooksByISBN.putIfAbsent(ISBN, new LinkedHashMap<>());
        borrowedBooksByISBN.get(ISBN).put(book.getId(), book);
        printAll();
        return book;
    }

    public void returnBook(long returnBookISBN, UUID returnBookId, UUID memberId) {
        printAll();
        Optional.ofNullable(borrowedBooksByISBN.get(returnBookISBN)).orElseThrow(() -> new BookNotFoundException("book with ISBN:" + returnBookISBN + " is not found"));
        LinkedHashMap<UUID, Book> borrowedBooksById = borrowedBooksByISBN.get(returnBookISBN);
        Optional.ofNullable(borrowedBooksById.get(returnBookId)).orElseThrow(() -> new BookNotBorrowedException("book with id:" + returnBookId + " was not borrowed"));
        Book book = borrowedBooksById.get(returnBookId);
        Optional.ofNullable(memberById.get(memberId)).orElseThrow(() -> new MemberNotFoundException("member with id:" + memberId + " is not found"));
        Member member = memberById.get(memberId);
        if (book.getReturnDate().isBefore(LocalDate.now())) {
            throw new BookOverdueException("book with id:" + book.getId() + " is not returned in specified time");
        }
        book.setBookIsBorrowed(false);
        book.setIssuedToMember(null);
        book.setIssueDate(null);
        book.setReturnDate(null);
        borrowedBooksById.remove(book.getId());
        availableBooksByISBN.get(book.getISBN()).put(book.getId(), book);
        printAll();
    }

    public void returnBook(long returnBookISBN, UUID returnBookId, UUID memberId, LocalDate returnDate) {
        printAll();
        Optional.ofNullable(borrowedBooksByISBN.get(returnBookISBN)).orElseThrow(() -> new BookNotFoundException("book with ISBN:" + returnBookISBN + " is not found"));
        LinkedHashMap<UUID, Book> borrowedBooksById = borrowedBooksByISBN.get(returnBookISBN);
        Optional.ofNullable(borrowedBooksById.get(returnBookId)).orElseThrow(() -> new BookNotBorrowedException("book with id:" + returnBookId + " was not borrowed"));
        Book book = borrowedBooksById.get(returnBookId);
        Optional.ofNullable(memberById.get(memberId)).orElseThrow(() -> new MemberNotFoundException("member with id:" + memberId + " is not found"));
        Member member = memberById.get(memberId);
        if (returnDate.isBefore(LocalDate.now())) {
            throw new BookOverdueException("book with id:" + book.getId() + " is not returned in specified time");
        }
        book.setBookIsBorrowed(false);
        book.setIssuedToMember(null);
        book.setIssueDate(null);
        book.setReturnDate(null);
        borrowedBooksById.remove(book.getId());
        availableBooksByISBN.get(book.getISBN()).put(book.getId(), book);
        printAll();
    }

    public static class Book {
        private final long ISBN;
        private final UUID id;
        private final String name;
        private boolean isBookIsBorrowed;
        private Member issuedToMember;
        private LocalDate issueDate;
        private LocalDate returnDate;

        public long getISBN() {
            return ISBN;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public boolean isBookIsBorrowed() {
            return isBookIsBorrowed;
        }

        public Member getIssuedToMember() {
            return issuedToMember;
        }

        public LocalDate getIssueDate() {
            return issueDate;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        public void setBookIsBorrowed(boolean bookIsBorrowed) {
            isBookIsBorrowed = bookIsBorrowed;
        }

        public void setIssuedToMember(Member issuedToMember) {
            this.issuedToMember = issuedToMember;
        }

        public void setIssueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
        }

        public void setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
        }

        public Book(long ISBN, String name) {
            this.ISBN = ISBN;
            this.id = UUID.randomUUID();
            this.name = name;
            this.isBookIsBorrowed = false;
            this.issuedToMember = null;
            this.issueDate = null;
            this.returnDate = null;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "ISBN=" + ISBN +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", isBookIsBorrowed=" + isBookIsBorrowed +
                    ", issuedToMember=" + issuedToMember +
                    ", issueDate=" + issueDate +
                    ", returnDate=" + returnDate +
                    '}';
        }
    }

    public static class Member extends Person {
        private final UUID id;

        private Member(String name, int age, Gender gender) {
            super(name, age, gender);
            this.id = UUID.randomUUID();
        }

        public UUID getId() {
            return id;
        }

        @Override
        public String toString() {
            return "{ Member{" +
                    "id=" + id +
                    "} " + super.toString() + " }";
        }

    }
}


class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("book not found");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}

class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException() {
        super("book is not available");
    }

    public BookNotAvailableException(String message) {
        super(message);
    }
}

class BookNotBorrowedException extends RuntimeException {
    public BookNotBorrowedException() {
        super("book was not borrowed");
    }

    public BookNotBorrowedException(String message) {
        super(message);
    }
}

class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super("member not found");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}

class BookOverdueException extends RuntimeException {
    public BookOverdueException() {
        super("book is not returned in specified time");
    }

    public BookOverdueException(String message) {
        super(message);
    }
}