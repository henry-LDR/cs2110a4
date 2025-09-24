package cs2110;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.*;

import cs2110.BookSorter.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;

/**
 * Smoke test suite for Selection Sort
 *
 * The smoke tests ensure that your code runs and works as expected for a few simple cases.
 * The tests provided here are the ones that will run on gradescope every time you
 * upload your code.
 *
 * As a reminder, smoke tests are not a complete test suite. Passing all smoke tests does not
 * mean that your code has entirely correct behavior. We will run additional hidden autograder
 * tests on your code after the homework submission deadline to further verify its correctness.
 *
 * You should add more tests to this file to gain a greater level of confidence in your code!
 */
public class SelectionSortSmokeTest {

    @DisplayName("WHEN this list of books has one element, THEN sorting with ordering 1"
            + "yields the same list")
    @Test
    void testEmptyOrdering1() {
        Book[] input = new Book[]{new Book("Race After Technology", "Ruha Benjamin")};

        BookSorter.selectionSort(input, 1);

        // Input has been modified. We are now thinking of it as actual output.
        Book[] actual = input;

        Book[] expected = new Book[]{new Book("Race After Technology", "Ruha Benjamin")};
        assertArrayEquals(actual, expected);
    }

    @DisplayName("WHEN this list of books has one element, THEN sorting with ordering 2"
            + "yields the same list")
    @Test
    void testEmptyOrdering2() {
        Book[] input = new Book[]{new Book("Race After Technology", "Ruha Benjamin")};

        BookSorter.selectionSort(input, 2);

        // Input has been modified. We are now thinking of it as actual output.
        Book[] actual = input;

        Book[] expected = new Book[]{new Book("Race After Technology", "Ruha Benjamin")};
        assertArrayEquals(actual, expected);
    }

    @DisplayName("WHEN this list of multiple books is already sorted, THEN sorting with ordering 1 "
            + "yields the same list")
    @Test
    void testAlreadySortedTitle() {
        Book[] input = new Book[]{
                new Book("Invisible Women", "Caroline Criado Perez"),
                new Book("Race After Technology", "Ruha Benjamin"),
                new Book("Weapons of Math Destruction", "Cathy O'Neil")
        };

        BookSorter.selectionSort(input, 1);

        // Input has been modified. We are now thinking of it as actual output.
        Book[] actual = input;

        Book[] expected = new Book[]{
                new Book("Invisible Women", "Caroline Criado Perez"),
                new Book("Race After Technology", "Ruha Benjamin"),
                new Book("Weapons of Math Destruction", "Cathy O'Neil")
        };
        assertArrayEquals(actual, expected);
    }

    @Test
    @DisplayName("Test empty array")
    void testEmptyArray() {
        Book[] books = new Book[]{};
        Book[] expected = new Book[]{};
        BookSorter.selectionSort(books, 1);
        assertArrayEquals(expected, books, "Sorting an empty array should do nothing");
    }

    @Test
    @DisplayName("Test single element array")
    void testSingleElement() {
        Book[] books = new Book[]{new Book("Alpha", "Author A")};
        Book[] expected = new Book[]{new Book("Alpha", "Author A")};
        BookSorter.selectionSort(books, 1);
        assertArrayEquals(expected, books, "Sorting a single element should not change the array");
    }

    @Test
    @DisplayName("Test already sorted array")
    void testAlreadySorted() {
        Book[] books = new Book[]{
                new Book("Aardvark", "Author X"),
                new Book("Bravo", "Author Y"),
                new Book("Charlie", "Author Z")
        };
        Book[] expected = new Book[]{
                new Book("Aardvark", "Author X"),
                new Book("Bravo", "Author Y"),
                new Book("Charlie", "Author Z")
        };
        BookSorter.selectionSort(books, 1);
        assertArrayEquals(expected, books, "Array already sorted by title should remain unchanged");
    }

    @Test
    @DisplayName("Test reverse sorted array")
    void testReverseSorted() {
        Book[] books = new Book[]{
                new Book("Charlie", "Author Z"),
                new Book("Bravo", "Author Y"),
                new Book("Aardvark", "Author X")
        };
        Book[] expected = new Book[]{
                new Book("Aardvark", "Author X"),
                new Book("Bravo", "Author Y"),
                new Book("Charlie", "Author Z")
        };
        BookSorter.selectionSort(books, 1);
        assertArrayEquals(expected, books,
                "Array reverse sorted by title should be sorted ascending");
    }

    @Test
    @DisplayName("Test array with mixed case titles")
    void testMixedCaseTitles() {
        Book[] books = new Book[]{
                new Book("alpha", "Author A"),
                new Book("Bravo", "Author B"),
                new Book("charlie", "Author C"),
                new Book("Alpha", "Author D")
        };
        Book[] expected = new Book[]{
                new Book("alpha", "Author A"),
                new Book("Alpha", "Author D"),
                new Book("Bravo", "Author B"),
                new Book("charlie", "Author C")
        };
        BookSorter.selectionSort(books, 1);
        assertArrayEquals(expected, books,
                "Sorting should ignore case but preserve order for equal titles ignoring case");
    }

    @Test
    @DisplayName("Test large random array")
    void testLargeRandomArray() {
        Book[] books = new Book[]{
                new Book("Delta", "Author 1"),
                new Book("Charlie", "Author 2"),
                new Book("Echo", "Author 3"),
                new Book("Alpha", "Author 4"),
                new Book("Bravo", "Author 5")
        };
        Book[] expected = new Book[]{
                new Book("Alpha", "Author 4"),
                new Book("Bravo", "Author 5"),
                new Book("Charlie", "Author 2"),
                new Book("Delta", "Author 1"),
                new Book("Echo", "Author 3")
        };
        BookSorter.selectionSort(books, 1);
        assertArrayEquals(expected, books,
                "Large random array should be sorted ascending by title");
    }

    private Book b(String title, String author) {
        return new Book(title, author);
    }

    private String[] titles(Book[] arr) {
        return Arrays.stream(arr).map(Book::title).toArray(String[]::new);
    }

    private String[] authors(Book[] arr) {
        return Arrays.stream(arr).map(Book::author).toArray(String[]::new);
    }

    private Book[] sortedCopy(Book[] arr, int ordering) {
        Book[] copy = Arrays.copyOf(arr, arr.length);
        Comparator<Book> cmp = (ordering == 1)
                ? Comparator.comparing(Book::title, String.CASE_INSENSITIVE_ORDER)
                : Comparator.comparing(Book::author, String.CASE_INSENSITIVE_ORDER);
        Arrays.sort(copy, cmp);
        return copy;
    }

    /* ------------ Core Tests ------------ */

    @Test
    @DisplayName("Empty array is valid input and remains empty")
    void testEmpty() {
        Book[] arr = new Book[0];
        BookSorter.selectionSortRecursive(arr, 1, 0);
        assertEquals(0, arr.length);
    }

    @Test
    @DisplayName("Single element array is unchanged")
    void testSingle() {
        Book[] arr = {b("Only", "One")};
        BookSorter.selectionSortRecursive(arr, 1, 0);
        assertEquals("Only", arr[0].title());
    }

    @Test
    @DisplayName("Two elements in wrong order (ordering=1)")
    void testTwoAscendingTitle() {
        Book[] arr = {b("Zebra", "A"), b("Apple", "B")};
        BookSorter.selectionSortRecursive(arr, 1, 0);
        assertArrayEquals(new String[]{"Apple", "Zebra"}, titles(arr));
    }

    @Test
    @DisplayName("Two elements in wrong order (ordering=2)")
    void testTwoAscendingAuthor() {
        Book[] arr = {b("X", "Zulu"), b("Y", "Alpha")};
        BookSorter.selectionSortRecursive(arr, 2, 0);
        assertArrayEquals(new String[]{"Alpha", "Zulu"}, authors(arr));
    }

    @Test
    @DisplayName("Case-insensitive ordering by title")
    void testCaseInsensitiveTitle() {
        Book[] arr = {b("apple", "a"), b("Banana", "b"), b("cherry", "c")};
        Book[] expected = sortedCopy(arr, 1);
        BookSorter.selectionSortRecursive(arr, 1, 0);
        assertArrayEquals(titles(expected), titles(arr));
    }

    @Test
    @DisplayName("Case-insensitive ordering by author")
    void testCaseInsensitiveAuthor() {
        Book[] arr = {b("X", "alice"), b("Y", "Bob"), b("Z", "charlie")};
        Book[] expected = sortedCopy(arr, 2);
        BookSorter.selectionSortRecursive(arr, 2, 0);
        assertArrayEquals(authors(expected), authors(arr));
    }

    @Test
    @DisplayName("Duplicate titles with different authors")
    void testDuplicateTitles() {
        Book[] arr = {b("Same", "Z"), b("Same", "A")};
        BookSorter.selectionSortRecursive(arr, 1, 0);
        assertEquals("Same", arr[0].title());
        assertEquals("Same", arr[1].title());
    }

    @Test
    @DisplayName("Duplicate authors with different titles")
    void testDuplicateAuthors() {
        Book[] arr = {b("Zebra", "Same"), b("Apple", "Same")};
        BookSorter.selectionSortRecursive(arr, 2, 0);
        assertEquals("Same", arr[0].author());
        assertEquals("Same", arr[1].author());
    }

    @Test
    @DisplayName("Titles with punctuation, numbers, and symbols")
    void testSpecialCharactersInTitles() {
        Book[] arr = {
                b("!exclaim", "a"),
                b("123number", "b"),
                b("apple", "c"),
                b("Zebra", "d")
        };
        Book[] expected = sortedCopy(arr, 1);
        BookSorter.selectionSortRecursive(arr, 1, 0);
        assertArrayEquals(titles(expected), titles(arr));
    }

    @Test
    @DisplayName("Authors with spaces and multiple names")
    void testComplexAuthors() {
        Book[] arr = {
                b("X", "Anne Marie"),
                b("Y", "Bob"),
                b("Z", "Alice Zed")
        };
        Book[] expected = sortedCopy(arr, 2);
        BookSorter.selectionSortRecursive(arr, 2, 0);
        assertArrayEquals(authors(expected), authors(arr));
    }

    @Test
    @DisplayName("Empty strings are valid titles and authors")
    void testEmptyStrings() {
        Book[] arr = {b("", "Z"), b("Apple", ""), b("", "")};
        Book[] expectedTitle = sortedCopy(arr, 1);
        Book[] expectedAuthor = sortedCopy(arr, 2);

        BookSorter.selectionSortRecursive(arr, 1, 0);
        assertArrayEquals(titles(expectedTitle), titles(arr));

        BookSorter.selectionSortRecursive(arr, 2, 0);
        assertArrayEquals(authors(expectedAuthor), authors(arr));
    }

    @Test
    @DisplayName("Partial sort starting from the middle")
    void testPartialSort() {
        Book[] arr = {b("Keep", "A"), b("Keep", "B"), b("Zebra", "C"), b("Apple", "D")};
        BookSorter.selectionSortRecursive(arr, 1, 2);
        assertEquals("Keep", arr[0].title());
        assertEquals("Keep", arr[1].title());
        assertTrue(arr[2].title().compareToIgnoreCase(arr[3].title()) <= 0);
    }

    @Test
    @DisplayName("Larger randomized fuzz test by title and author")
    void testFuzzRandom() {
        Random rnd = new Random(42);
        for (int iter = 0; iter < 50; iter++) {
            int n = rnd.nextInt(10) + 5;
            Book[] arr = new Book[n];
            for (int i = 0; i < n; i++) {
                String t = rnd.ints(97, 123)
                        .limit(3)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                                StringBuilder::append)
                        .toString();
                String a = rnd.ints(97, 123)
                        .limit(3)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                                StringBuilder::append)
                        .toString();
                arr[i] = b(t, a);
            }
            Book[] expectedTitle = sortedCopy(arr, 1);
            Book[] expectedAuthor = sortedCopy(arr, 2);

            BookSorter.selectionSortRecursive(arr, 1, 0);
            assertArrayEquals(titles(expectedTitle), titles(arr));

            BookSorter.selectionSortRecursive(arr, 2, 0);
            assertArrayEquals(authors(expectedAuthor), authors(arr));
        }

    }
}

