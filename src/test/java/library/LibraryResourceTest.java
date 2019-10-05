package library;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

public class LibraryResourceTest {

    @Test
    public void getBooks() throws IOException {
        LibraryResource libraryResource = new LibraryResource();
        List<String[]> result = libraryResource.getBooks();
        assertEquals(expectedGetBooks(), format(result));
    }

    private String expectedGetBooks() {
        return "[[\"0\",\"Drama im Augenblick seines Sturzes\",\"Franz-Josef Deiters\",\"IMAGE\",\"http://books.google.com/books/content?id=YoMxy7Mx288C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"1\",\"Gestörte Kommunikation im amerikanischen Drama\",\"Marion Hebach\",\"IMAGE\",\"http://books.google.com/books/content?id=Nz3uabniaLoC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"2\",\"Leidenschaft und Vernunft im Drama des Sturm und Drang\",\"Nagla El-Dandoush\",\"IMAGE\",\"http://books.google.com/books/content?id=g2w-fb_sK7AC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"3\",\"Die Monster, die ich rief\",\"Larry Correia\",\"BOTH\",\"http://books.google.com/books/content?id=kWglAgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"4\",\"Science Fiction in der deutschsprachigen Literatur\",\"Hans-Edwin Friedrich\",\"IMAGE\",\"http://books.google.com/books/content?id=jfgTnCn2V-EC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"5\",\"Das Science Fiction Jahr 2010\",\"Sascha Mamczak,Wolfgang Jeschke\",\"BOTH\",\"http://books.google.com/books/content?id=yHGt8Y-Fr5QC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"6\",\"Refactoring to patterns\",\"Joshua Kerievsky\",\"IMAGE\",\"http://books.google.com/books/content?id=h2sdNbKW65gC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"7\",\"Refactoring\",\"Martin Fowler,Kent Beck\",\"IMAGE\",\"http://books.google.com/books/content?id=UTgFCAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"8\",\"Clean Code\",\"Robert C. Martin\",\"BOTH\",\"http://books.google.com/books/content?id=_i6bDeoCQzsC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"9\",\"Code Complete\",\"Steve McConnell\",\"BOTH\",\"http://books.google.com/books/content?id=LpVCAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"10\",\"Extreme Programming\",\"Kent Beck\",\"IMAGE\",\"http://books.google.com/books/content?id=79dSpPDQdLYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"11\",\"OPTIMIZATION METHODS FOR ENGINEERS\",\"N.V.S. Raju\",\"IMAGE\",\"http://books.google.com/books/content?id=-r1nAgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"12\",\"Implantando Governança Ágil - MAnGve\",\"Alexandre Luna\",\"IMAGE\",\"http://books.google.com/books/content?id=zmeECgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"13\",\"Programming Proverbs\",\"Henry F. Ledgard\",\"TEXT\",\"http://books.google.com/books/content?id=e9AmAAAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\"],[\"14\",\"Der Pragmatische Programmierer\",\"Andrew Hunt,David Thomas\",\"TEXT\",\"http://books.google.com/books/content?id=wzvjQwAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\"],[\"15\",\"Lean Management\",\"Werner Pfeiffer,Enno Weiss\",\"IMAGE\",\"http://books.google.com/books/content?id=xy47RgxZ3_oC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"16\",\"Lean TPM\",\"Andreas Reitz\",\"IMAGE\",\"http://books.google.com/books/content?id=0Qh_Lg5foWkC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"],[\"17\",\"Six Sigma+Lean Toolset\",\"Olin Roenpage,Christian Staudter,Renata Meran,Alexander John,Carmen Beernaert\",\"IMAGE\",\"http://books.google.com/books/content?id=ty6ojSp-HiwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"]]";
    }

    private String format(List<String[]> books) {
        return books.stream()
                .map(book -> String.format("[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"]", book[0], book[1], book[2], book[3], book[4]))
                .collect(joining(",", "[", "]"));
    }

    @Test
    public void calculateFee() throws IOException {
        LibraryResource libraryResource = new LibraryResource();
        List<String> response = libraryResource.calculateFee(new ArrayList<>(List.of("hansmeier", "6 3", "8 4", "14 4")));
        assertEquals(expected(), response.get(0));
    }

    private String expected() {
        return new StringBuilder()
                .append("Rental Record for hansmeier\n")
                .append("\t'Refactoring to patterns' by 'Joshua Kerievsky' for 3 days: \t3.5 $\n")
                .append("\t'Clean Code' by 'Robert C. Martin' for 4 days: \t12.0 $\n")
                .append("\t'Der Pragmatische Programmierer' by 'Andrew Hunt,David Thomas' for 4 days: \t3.0 $\n")
                .append("You owe 18.5 $")
                .append("\nYou earned 4 frequent renter points\n")
                .toString();
    }
}