import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main driver class for NameRecord objects and Names collections storing
 * NameRecord objects. Tests each of the methods in Names.java and
 * NameRecord.java whether directly or indirectly. Displays a menu and allows
 * the user to make various queries of the database.
 * 
 * @author Ryan Parappuram
 *
 */
public class NameSurfer {

    // Menu Option 7 Description:
    /*
     * Useful for finding interesting trends in names at certain points in history,
     * my suddenPopular() method for menu option 7 finds names that experience a
     * sudden increase in popularity, where a sudden increase in popularity is
     * defined as a name that was unranked one decade, ranked within the top (user
     * inputted threshold) names the next decade, then back to being unranked the
     * decade after. This means that a name could be on the chart of 1000 popular
     * names multiple times in the list of ranks, but there must be at least one
     * sequence of ranks where it was 0 for one decade, within the top (user
     * inputted threshold) for the next, and 0 again for the next. An example of
     * this interesting trend can be found when option 7 is called with a user input
     * threshold of 500 using names4.txt and observed for the names Anissa,
     * Claudine, Jennings, Renata, Roseanna, Sheena, and Tyra.
     */

    // Disney Princess Trend Explanation:
    /*
     * Disney princess names appear to be popular at two times: The first is when
     * the Disney movies originally came out (especially if it was during a time
     * period where information traveled fast, possibly due to the advent of social
     * media). For example, the name Jasmine became popular around the time of the
     * release of Aladdin in 1992.
     * 
     * The second time the name appears to be popular is in the more recent 2010s
     * decade. I believe this is due to 2 reasons -
     * 
     * 1) Disney movies last generations due to their family-oriented and magical
     * essence. So, kids who have grown up to be parents tend to name their daughters
     * based on their lifelong love of Disney princess movies. For example, The
     * Little Mermaid came out in 1989, and The Little Mermaid 2 came out in
     * 2000. Kids who watched those movies between 1989 and 2000 fell in love with
     * Ariel, the mermaid, and most likely grew up to become parents that named
     * their daughter identically. Further evidence for the name Ariel is that The
     * Little Mermaid has again become popular because a remake is in the works for
     * 2023, which leads me to the 2nd reason.
     * 
     * 2) Disney has been remaking their content to make them relevant again over
     * the past decade or so. This, occurring during the social media boom, led to
     * Disney princess names making a reappearance in popularity recently. For
     * example, Aurora, the princess from The Sleeping Beauty (which came out in
     * 1959), made a reappearance in highly popular names in the 2010s most likely
     * due to her reappearance in the remake Maleficent.
     * 
     * Disney princess names to search: Aurora, Ariel, Elsa, Jasmine, and Tiana
     */

    /**
     * Testing code for NameRecord.java - tests each method at least twice if not
     * more. Method is public to avoid the following warning: "The method
     * NameRecordTests() from the type NameSurfer is never used locally"
     */
    public static void NameRecordTests() {
        System.out.println("**** Testing NameRecord.java *****\n");
        String ryanRawData = "Ryan 1 2 3 4";
        String benRawData = "Ben 0 0 0";
        String johnRawData = "John 0 0 0 0";
        int baseDecade = 1990;
        int numRanks = 4;

        // testing if invalid NameRecord returns null as it's supposed to
        NameRecord benRecord = NameRecord.buildRecord(baseDecade, numRanks, benRawData);
        System.out.println((benRecord == null ? "passed" : "**FAILED**")
                + " - NameRecord with all 0's returns null test");
        NameRecord johnRecord = NameRecord.buildRecord(baseDecade, numRanks, johnRawData);
        System.out.println((johnRecord == null ? "passed" : "**FAILED**")
                + " - NameRecord with wrong # of ranks returns null test");

        // testing valid NameRecord toString()
        NameRecord ryanRecord = NameRecord.buildRecord(baseDecade, numRanks, ryanRawData);
        String expected = "Ryan\n1990: 1\n2000: 2\n2010: 3\n2020: 4\n";
        // System.out.println("expected ryan toString(): \n" + ryanExpected);
        // System.out.println("ryanRecord.toString():\n" + ryanRecord.toString());
        System.out.println((expected.equals(ryanRecord.toString()) ? "passed" : "**FAILED**")
                + " - ryan's toString() test");
        benRawData = "Ben 1 2 3 1";
        johnRawData = "John 0 3 2 1";
        benRecord = NameRecord.buildRecord(baseDecade, numRanks, benRawData);
        johnRecord = NameRecord.buildRecord(baseDecade, numRanks, johnRawData);
        expected = "Ben\n1990: 1\n2000: 2\n2010: 3\n2020: 1\n";
        System.out.println((expected.equals(benRecord.toString()) ? "passed" : "**FAILED**")
                + " - ben's toString() test");
        expected = "John\n1990: 0\n2000: 3\n2010: 2\n2020: 1\n";
        System.out.println((expected.equals(johnRecord.toString()) ? "passed" : "**FAILED**")
                + " - john's toString() test\n");

        // testing get methods
        System.out.println((ryanRecord.getName().equals("Ryan") ? "passed" : "**FAILED**")
                + " - ryan's getName() test");
        System.out.println((benRecord.getName().equals("Ben") ? "passed" : "**FAILED**")
                + " - ben's getName() test");
        System.out.println((ryanRecord.getBaseDecade() == 1990 ? "passed" : "**FAILED**")
                + " - ryan's getBaseDecade() test");
        System.out.println((benRecord.getBaseDecade() == 1990 ? "passed" : "**FAILED**")
                + " - ben's getBaseDecade() test");
        System.out.println(
                (ryanRecord.getRank(0) == 1 && ryanRecord.getRank(3) == 4 ? "passed" : "**FAILED**")
                        + " - getRank() test");
        System.out.println(
                (benRecord.getRank(0) == 1 && benRecord.getRank(3) == 1 ? "passed" : "**FAILED**")
                        + " - ben's getRank() test\n");

        // testing decade methods below

        // testing bestDecade() method
        System.out.println((ryanRecord.bestDecade() == 1990 ? "passed" : "**FAILED**")
                + " - ryan's bestDecade() test");
        System.out.println((benRecord.bestDecade() == 2020 ? "passed" : "**FAILED**")
                + " - ben's bestDecade() with multiple best decades test");

        // testing numDecadesRanked() method
        System.out.println((ryanRecord.numDecadesRanked() == 4 ? "passed" : "**FAILED**")
                + " - ryan's numDecadesRanked() test");
        System.out.println((johnRecord.numDecadesRanked() == 3 ? "passed" : "**FAILED**")
                + " - ryan's numDecadesRanked() test");

        // testing rankedEveryDecade() method
        System.out.println((ryanRecord.rankedEveryDecade() == true ? "passed" : "**FAILED**")
                + " - ryan's rankedEveryDecade() test");
        System.out.println((johnRecord.rankedEveryDecade() == false ? "passed" : "**FAILED**")
                + " - john's rankedEveryDecade() test");

        // testing rankedOnlyOneDecade() method
        System.out.println((ryanRecord.rankedOnlyOneDecade() == false ? "passed" : "**FAILED**")
                + " - ryan's rankedOnlyOneDecade() test");
        String samRawData = "Sam 0 0 0 100";
        NameRecord samRecord = NameRecord.buildRecord(baseDecade, numRanks, samRawData);
        System.out.println((samRecord.rankedOnlyOneDecade() == true ? "passed" : "**FAILED**")
                + " - sam's rankedOnlyOneDecade() test");

        // testing morePopularEachDecade() method
        System.out.println((ryanRecord.morePopularEachDecade() == false ? "passed" : "**FAILED**")
                + " - morePopularEachDecade() test where false");
        System.out.println((samRecord.morePopularEachDecade() == false ? "passed" : "**FAILED**")
                + " - morePopularEachDecade() test where multiple 0's ");
        System.out.println((johnRecord.morePopularEachDecade() == true ? "passed" : "**FAILED**")
                + " - morePopularEachDecade() test where true");

        // testing lessPopularEachDecade() method
        System.out.println((ryanRecord.lessPopularEachDecade() == true ? "passed" : "**FAILED**")
                + " - lessPopularEachDecade() test where true");
        System.out.println((samRecord.lessPopularEachDecade() == false ? "passed" : "**FAILED**")
                + " - lessPopularEachDecade() test where multiple 0's ");
        System.out.println((johnRecord.lessPopularEachDecade() == false ? "passed" : "**FAILED**")
                + " - lessPopularEachDecade() test where false");

        // testing compareTo() method
        System.out.println((ryanRecord.compareTo(benRecord) == 16 ? "passed" : "failed")
                + " - compareTo() test");
        System.out.println((benRecord.compareTo(johnRecord) == -8 ? "passed" : "failed")
                + " - compareTo() test");

        // testing suddenPopularity() method
        System.out.println((ryanRecord.suddenPopularity(1) == false ? "passed" : "failed")
                + " - suddenPopularity() menu option 7 method test");
        ryanRawData = "Ryan 0 231 0 1";
        ryanRecord = NameRecord.buildRecord(baseDecade, numRanks, ryanRawData);
        System.out.println((ryanRecord.suddenPopularity(231) == true ? "passed" : "failed")
                + " - suddenPopularity() menu option 7 method test");
        

        System.out.println("\n**** FINISHED testing NameRecord.java ****");
    }

    // A few simple tests for the Names and NameRecord class.
    public static void simpleTest() {
        // raw data for Jake. Alter as necessary for your NameRecord
        String jakeRawData = "Jake 262 312 323 479 484 630 751 453 225 117 97";
        int baseDecade = 1900;
        int numRanks = 11;
        NameRecord jakeRecord = NameRecord.buildRecord(baseDecade, numRanks, jakeRawData);
        String expected = "Jake\n1900: 262\n1910: 312\n1920: 323\n1930: 479\n1940: "
                + "484\n1950: 630\n1960: 751\n1970: 453\n1980: 225\n1990: " + "117\n2000: 97\n";
        String actual = jakeRecord.toString();
        System.out.println("expected string:\n" + expected);
        System.out.println("actual string:\n" + actual);
        if (expected.equals(actual)) {
            System.out.println("passed Jake toString test.");
        } else {
            System.out.println("FAILED Jake toString test.");
        }

        // Some getName Tests

        Names names = new Names(getFileScannerForNames("names.txt"));
        String[] testNames = { "Isabelle", "isabelle", "sel", "X1178", "ZZ", "via", "kelly" };
        boolean[] expectNull = { false, false, true, true, true, true, false };
        for (int i = 0; i < testNames.length; i++) {
            performGetNameTest(names, testNames[i], expectNull[i]);
        }
    }

    // Checks if given name is present in Names.
    private static void performGetNameTest(Names names, String name, boolean expectNull) {

        System.out.println("Performing test for this name: " + name);
        if (expectNull) {
            System.out.println("Expected return value is null");
        } else {
            System.out.println("Expected return value is not null");
        }
        NameRecord result = names.getName(name);
        if ((expectNull && result == null) || (!expectNull && result != null)) {
            System.out.println("PASSED TEST.");
        } else {
            System.out.println("Failed test");
        }
    }

    // main method. Driver for the whole program
    public static void main(String[] args) {
        // Alter name of file to try different data sources.
        final String NAME_FILE = "names4.txt";
        Scanner fileScanner = getFileScannerForNames(NAME_FILE);
        Names namesDatabase = new Names(fileScanner);
        fileScanner.close();
        runOptions(namesDatabase);
    }

    /*
     * pre: namesDatabase != null Ask user for options to perform on the given Names
     * object. Creates a Scanner connected to System.in.
     */
    private static void runOptions(Names namesDatabase) {
        Scanner keyboard = new Scanner(System.in);
        MenuChoices[] menuChoices = MenuChoices.values();
        MenuChoices menuChoice;
        do {
            showMenu();
            int userChoice = getChoice(keyboard) - 1;
            menuChoice = menuChoices[userChoice];
            if (menuChoice == MenuChoices.SEARCH) {
                search(namesDatabase, keyboard);
            } else if (menuChoice == MenuChoices.ONE_NAME) {
                oneName(namesDatabase, keyboard);
            } else if (menuChoice == MenuChoices.APPEAR_ONCE) {
                appearOnce(namesDatabase);
            } else if (menuChoice == MenuChoices.APPEAR_ALWAYS) {
                appearAlways(namesDatabase);
            } else if (menuChoice == MenuChoices.ALWAYS_MORE) {
                alwaysMore(namesDatabase);
            } else if (menuChoice == MenuChoices.ALWAYS_LESS) {
                alwaysLess(namesDatabase);
            } else if (menuChoice == MenuChoices.STUDENT_SEARCH) {
                suddenPopular(namesDatabase, keyboard);
            }
        } while (menuChoice != MenuChoices.QUIT);
        keyboard.close();
    }

    /*
     * Create a Scanner and return connected to a File with the given name. pre:
     * fileName != null post: Return a Scanner connected to the file or null if the
     * File does not exist in the current directory.
     */
    private static Scanner getFileScannerForNames(String fileName) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("\n***** ERROR IN READING FILE ***** ");
            System.out.println("Can't find this file " + fileName + " in the current directory.");
            System.out.println("Error: " + e);
            String currentDir = System.getProperty("user.dir");
            System.out.println("Be sure " + fileName + " is in this directory: ");
            System.out.println(currentDir);
            System.out.println("\nReturning null from method.");
            sc = null;
        }
        return sc;
    }

    /**
     * Display the names that have appeared in every decade. <br>
     * pre: n != null <br>
     * post: print out names that have appeared in ever decade
     */
    private static void appearAlways(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter namesDatabase cannot be null");
        }

        ArrayList<String> list = namesDatabase.rankedEveryDecade();
        System.out.println(list.size() + " names appear in every decade. The names are:");
        printList(list);
    }

    /**
     * Display the names that have appeared in only one decade. <br>
     * pre: n != null <br>
     * post: print out names that have appeared in only one decade
     */
    private static void appearOnce(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter" + " namesDatabase cannot be null");
        }

        ArrayList<String> list = namesDatabase.rankedOnlyOneDecade();
        System.out.println(list.size() + " names appear in exactly one decade. The names are:");
        printList(list);
    }

    /**
     * Display the names that have gotten more popular in each successive decade.
     * pre: n != null <br>
     * post: print out names that have gotten more popular in each decade
     */
    private static void alwaysMore(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter" + " namesDatabase cannot be null");
        }

        ArrayList<String> list = namesDatabase.alwaysMorePopular();
        System.out.println(list.size() + " names are more popular in every decade.");
        printList(list);
    }

    /**
     * Display the names that have gotten less popular in each successive decade.
     * pre: n != null <br>
     * post: print out names that have gotten less popular in each decade
     */
    private static void alwaysLess(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter" + " namesDatabase cannot be null");
        }

        ArrayList<String> list = namesDatabase.alwaysLessPopular();
        System.out.println(list.size() + " names are less popular in every decade.");
        printList(list);
    }

    /**
     * private helper method to print every String in list
     * 
     * @param list: every String in list will be printed to console
     */
    private static void printList(ArrayList<String> list) {
        for (String str : list) {
            System.out.println(str);
        }
    }

    /**
     * Display the data for one name or state that name has never been ranked.<br>
     * pre: n != null, keyboard != null and is connected to System.in <br>
     * post: print out the data for n or a message that n has never been in the top
     * 1000 for any decade
     */
    private static void oneName(Names namesDatabase, Scanner keyboard) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (namesDatabase == null || keyboard == null) {
            throw new IllegalArgumentException("The parameters cannot be null");
        }
        System.out.print("Enter a name: ");
        String name = keyboard.nextLine();
        NameRecord result = namesDatabase.getName(name);
        if (result != null) {
            System.out.println("\n" + namesDatabase.getName(name));
        } else {
            System.out.println("\n" + name + " does not appear in any decade.");
        }
    }

    /**
     * Display all names that contain a substring from the user and the decade they
     * were most popular. <br>
     * pre: n != null, keyboard != null and is connected to System.in <br>
     * post: display the data for each name.
     */
    private static void search(Names namesDatabase, Scanner keyboard) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (namesDatabase == null || keyboard == null) {
            throw new IllegalArgumentException("The parameters cannot be null");
        }

        System.out.print("Enter a partial name: ");
        String partial = keyboard.nextLine();
        ArrayList<NameRecord> list = namesDatabase.getMatches(partial);
        System.out.println("\nThere are " + list.size() + " matches for " + partial + ".");
        if (list.size() > 0) {
            System.out.println("\nThe matches with their highest ranking decade are:");
            for (NameRecord nameRecord : list) {
                System.out.println(nameRecord.getName() + " " + nameRecord.bestDecade());
            }
        }
    }

    /**
     * Display all names that did not make the top 1000 chart in one year, then made
     * it within the top 500 the next year, then fell off the chart again in the
     * following year.
     * 
     * pre: namesDatabase !=null post: display names with sudden popularity in any
     * decade based on previous and following decade
     * 
     * @param namesDatabase: database containing all NameRecords
     */
    private static void suddenPopular(Names namesDatabase, Scanner keyboard) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        System.out.print(
                "Enter a threshold between 0 and 1000 to find names \nthat experienced a sequence "
                        + "of ranks that went from \n0, to within the top (threshold), "
                        + "and then back to 0: ");
        int threshold = Integer.parseInt(keyboard.nextLine());
        ArrayList<NameRecord> list = namesDatabase.suddenlyPopular(threshold);
        System.out.println("\n" + list.size()
                + " names experienced a sudden increase in popularity from off the charts, "
                + "to within the top " + threshold + ", and then back off the charts.");
        for (NameRecord nameRecord : list) {
            System.out.println(nameRecord);
        }
    }

    /**
     * Get choice from the user keyboard != null and is connected to System.in <br>
     * return an int that is >= MenuChoices.SEARCH.ordinal() and <=
     * MenuChoices.QUIT.ordinal().
     */
    private static int getChoice(Scanner keyboard) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (keyboard == null) {
            throw new IllegalArgumentException("The parameter keyboard cannot be null");
        }

        int choice = getInt(keyboard, "Enter choice: ");
        keyboard.nextLine();
        // Add one due to zero based indexing of enums, but 1 based indexing of menu.
        final int MAX_CHOICE = MenuChoices.QUIT.ordinal() + 1;
        while (choice < 1 || choice > MAX_CHOICE) {
            System.out.println();
            System.out.println(choice + " is not a valid choice");
            choice = getInt(keyboard, "Enter choice: ");
            keyboard.nextLine();
        }
        return choice;
    }

    /**
     * Ensure an int is entered from the keyboard. <br>
     * pre: s != null and is connected to System.in <br>
     * post: return the int typed in by the user.
     */
    private static int getInt(Scanner s, String prompt) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (s == null) {
            throw new IllegalArgumentException("The parameter s cannot be null");
        }

        System.out.print(prompt);
        while (!s.hasNextInt()) {
            s.next();
            System.out.println("That was not an int.");
            System.out.print(prompt);
        }
        return s.nextInt();
    }

    // Show the user the menu.
    private static void showMenu() {
        System.out.println();
        System.out.println("Options:");
        System.out.println("Enter 1 to search for names.");
        System.out.println("Enter 2 to display data for one name.");
        System.out.println("Enter 3 to display all names that appear in only " + "one decade.");
        System.out.println("Enter 4 to display all names that appear in all " + "decades.");
        System.out.println(
                "Enter 5 to display all names that are more popular " + "in every decade.");
        System.out.println(
                "Enter 6 to display all names that are less popular " + "in every decade.");
        System.out.println("Enter 7 to display data for all names that experienced a "
                + "sudden increase in popularity.");
        System.out.println("Enter 8 to quit.");
        System.out.println();
    }

    /**
     * An enumerated type to hold the menu choices for the NameSurfer program.
     */
    private static enum MenuChoices {
        SEARCH, ONE_NAME, APPEAR_ONCE, APPEAR_ALWAYS, ALWAYS_MORE, ALWAYS_LESS, STUDENT_SEARCH,
        QUIT;
    }
}
