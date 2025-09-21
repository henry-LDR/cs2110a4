package cs2110;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BookSorter {
    /**
     * Parse the file of book data
     * @param filename - input filename relative to current working directory
     * @precondition - input file exists and has:
     *    - a header row "Title, Author" and 1 or more rows of data
     *    - data rows include a title followed by a comma followed by an author
     *    - title and author do not contain commas
     *    - no lines besides header and data
     * @return an array with one string array for each book
     *    - a book is represented as an array whose first element is title
     *      and whose second element is author
     */
    public static Book[] parseBookList(String filename) {
        File inputFile = new File(filename).getAbsoluteFile();
        System.out.println(inputFile.getAbsolutePath());
        try {
            Scanner lineCounter = new Scanner(inputFile);
            int numLines;
            for (numLines = 0; lineCounter.hasNextLine(); numLines++) {
                lineCounter.nextLine();
            }
            lineCounter.close();

            Scanner dataScanner = new Scanner(inputFile);
            dataScanner.nextLine(); // ignore header row
            Book[] ret = new Book[numLines-1];
            for (int i = 0; dataScanner.hasNextLine(); i++) {
                String line = dataScanner.nextLine();
                String[] parts = line.split(",");
                ret[i] = new Book(parts[0], parts[1]);
            }
            dataScanner.close();
            return ret;
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        return new Book[] {};
    }

    /**
     * Data class representing a book
     * @param title - the name of the book
     * @param author - string representing the full name(s) of the author(s) of the book
     */
    record Book(String title, String author) {
        /**
         * @return a String representing this Book
         */
        public String toString() {
            // this looks nicer than the default implementation of Record.toString
            return title() + " by " + author();
        }
    }

    /**
     * TODO: Document me!
     */
    private static int selectFirst(Book[] books, int start, int firstSoFar, int ordering) {
        assert start >=0 && start <=books.length;
        assert firstSoFar >=0 && firstSoFar <books.length;
        assert ordering ==1 || ordering == 2;

        if (start >= books.length){
            return firstSoFar;
        }
        if (books[start].title().compareToIgnoreCase(books[firstSoFar].title())>0){
            firstSoFar = start;
        }
        return selectFirst(books, start + 1, firstSoFar, ordering);
    }

    /**
     * TODO: Document me!
     */
    public static void selectionSort(Book[] books, int ordering) {
        // TODO: Implement me!
    }

    public static void main(String[] args) {
        // Edit this filename to use a different data set
        String filename = "data/books.csv";

        Book[] books = parseBookList(filename);
        System.out.println("=== Before Sorting ===");
        for(int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
        selectionSort(books, 1);

        System.out.println();
        System.out.println("=== After Sorting ===");
        for(int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
    }
}