package cs2110;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BookSorter {

    /**
     * Parse the file of book data
     *
     * @param filename - input filename relative to current working directory
     * @return an array with one string array for each book - a book is represented as an array
     * whose first element is title and whose second element is author
     * @precondition - input file exists and has: - a header row "Title, Author" and 1 or more rows
     * of data - data rows include a title followed by a comma followed by an author - title and
     * author do not contain commas - no lines besides header and data
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
            Book[] ret = new Book[numLines - 1];
            for (int i = 0; dataScanner.hasNextLine(); i++) {
                String line = dataScanner.nextLine();
                String[] parts = line.split(",");
                ret[i] = new Book(parts[0], parts[1]);
            }
            dataScanner.close();
            return ret;
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        return new Book[]{};
    }

    /**
     * Data class representing a book
     *
     * @param title  - the name of the book
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
     * Based on a starting index (inclusive), selects the index of the element of the array books
     * that should be first based on the provided ordering parameter.
     *
     * @param books      - array of book objects
     * @param start      - starting index of selection, inclusive. must be a valid index in books
     * @param firstSoFar - index representing a prediction of which element belongs in the first
     *                   position
     * @param ordering   - determines which ordering scheme to use. if ordering is 1, then sort by
     *                   title. if ordering is 2, then sort by author. All sorting is done by
     *                   ascending lexicographical order.
     * @return an int representing the index of the element in the array books that belongs in the
     * start position.
     * @precondition - start, firstSoFar are valid indices in books
     * @precondition - ordering == 1 or ordering == 2
     * @precondition - books!=null
     */
    private static int selectFirst(Book[] books, int start, int firstSoFar, int ordering) {
        assert start >= 0 && start <= books.length;
        assert firstSoFar >= 0 && firstSoFar < books.length;
        assert ordering == 1 || ordering == 2;
        assert books != null;

        if (start >= books.length) {
            return firstSoFar;
        }

        if (compareBooks(books[start], books[firstSoFar], ordering) < 0) {
            firstSoFar = start;
        }


        return selectFirst(books, start + 1, firstSoFar, ordering);
    }

    /**
     * Compare two books according to the given ordering.
     *
     * @param a first book
     * @param b second book
     * @param ordering 1 = compare by title, 2 = compare by author
     * @return negative if a < b, 0 if equal, positive if a > b. Compares books by ascending
     * lexicographical order based on ordering parameter.
     * @precondition ordering == 1 or ordering == 2
     *                  a != null
     *                  b != null
     *
     *
     */
    private static int compareBooks(Book a, Book b, int ordering) {
        assert ordering == 1 || ordering == 2;
        assert a != null;
        assert b != null;
        if (ordering == 1) {
            return a.title().compareToIgnoreCase(b.title());
        } else { // ordering == 2
            return a.author().compareToIgnoreCase(b.author());
        }
    }



    /**
     * Swaps the positions of the books at two given indices in a given array of books. Modifies
     * the array in-place.
     *
     * @param books  - array of book objects
     * @param first  - index of the first book to be swapped
     * @param second - index of the second book to be swapped
     *               <p>
     *               Preconditions: first and second must be valid indices in the books array. books
     *               != null
     */
    public static void swap(Book[] books, int first, int second) {
        assert first < books.length && first >= 0;
        assert second < books.length && second >= 0;
        assert books != null;

        Book temp = books[first];
        books[first] = books[second];
        books[second] = temp;
    }

    /**
     *
     * Sorts the books array in-place in an ascending lexicographical manner based on the provided ordering
     * parameter. If ordering == 1, sort by title. If ordering == 2, sort by author name.
     *
     * Calls the selectionSortRecursive function to perform the sort recursively
     *
     * Preconditions: ordering == 1 or ordering == 2
     *                  books != null
     */
    public static void selectionSort(Book[] books, int ordering) {
        assert ordering == 1 || ordering == 2;
        assert books != null;
        selectionSortRecursive(books, ordering, 0);
    }

    /**
     * Recursively sorts the books array using selection sort.
     * <p>
     * For each recursive call, it selects the 'first' book according to the given ordering from the
     * unsorted portion of the array beginning at index start (inclusive). It swaps the 'first' book
     * with the book at the start index and calls itself with an incremented start index to indicate
     * that an additional index has been sorted.
     *
     * @param books the array of books objects to sort
     *              <p>
     *              Preconditions: ordering == 1 or ordering == 2 start must be a valid index in
     *              books books != null
     *
     */
    public static void selectionSortRecursive(Book[] books, int ordering, int start) {

        assert ordering == 1 || ordering == 2;
        assert start <= books.length && start >= 0;
        assert books != null;

        if (start >= books.length) {
            return;
        }
        int firstBook = selectFirst(books, start, start, ordering);
        swap(books, start, firstBook);
        selectionSortRecursive(books, ordering, start + 1);

    }

    public static void main(String[] args) {
        // Edit this filename to use a different data set
        String filename = "data/books.csv";

        Book[] books = parseBookList(filename);
        System.out.println("=== Before Sorting ===");
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
        selectionSort(books, 1);

        System.out.println();
        System.out.println("=== After Sorting ===");
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
    }
}