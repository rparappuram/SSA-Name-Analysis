import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A collection of NameRecords. Stores NameRecord objects and provides methods
 * to select NameRecords based on various criteria.
 * 
 * @author Ryan Parappuram
 */
public class Names {

    private ArrayList<NameRecord> names;

    /**
     * Construct a new Names object based on the data source the Scanner sc is
     * connected to. Assume the first two lines in the data source are the base year
     * and number of decades to use. Any lines without the correct number of decades
     * are discarded and are not part of the resulting Names object. Any names with
     * ranks of all 0 are discarded and not part of the resulting Names object.
     * 
     * @param sc Is connected to a data file with baby names and positioned at the
     *           start of the data source.
     */
    public Names(Scanner sc) {
        names = new ArrayList<>();
        int baseDecade = Integer.parseInt(sc.nextLine());
        int numRanks = Integer.parseInt(sc.nextLine());
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            NameRecord nameRecord = NameRecord.buildRecord(baseDecade, numRanks, line);
            if (nameRecord != null) {
                names.add(nameRecord);
            }
        }
        Collections.sort(names);
    }

    /**
     * Returns an ArrayList of NameRecord objects that contain a given substring,
     * ignoring case. The names must be in sorted order based on name.
     * 
     * @param partialName != null, partialName.length() > 0
     * @return an ArrayList of NameRecords whose names contains partialName. If
     *         there are no NameRecords that meet this criteria returns an empty
     *         list.
     */
    public ArrayList<NameRecord> getMatches(String partialName) {
        if (partialName == null || partialName.length() == 0) {
            throw new IllegalArgumentException(
                    "partialName parameter may not be null or have length of 0");
        }

        ArrayList<NameRecord> result = new ArrayList<>();
        partialName = partialName.toLowerCase();
        for (NameRecord nameRecord : names) {
            if (nameRecord.getName().toLowerCase().contains(partialName)) {
                result.add(nameRecord);
            }
        }
        return result;
    }

    /**
     * Returns an ArrayList of Strings of names that have been ranked in the top
     * 1000 or better for every decade. The Strings must be in sorted order based on
     * name.
     * 
     * @return A list of the names that have been ranked in the top 1000 or better
     *         in every decade. The list is in sorted ascending order. If there are
     *         no NameRecords that meet this criteria returns an empty list.
     */
    public ArrayList<String> rankedEveryDecade() {
        ArrayList<String> result = new ArrayList<>();
        for (NameRecord nameRecord : names) {
            if (nameRecord.rankedEveryDecade()) {
                result.add(nameRecord.getName());
            }
        }
        return result;
    }

    /**
     * Returns an ArrayList of Strings of names that have been ranked in the top
     * 1000 or better in exactly one decade. The Strings must be in sorted order
     * based on name.
     * 
     * @return A list of the names that have been ranked in the top 1000 or better
     *         in exactly one decade. The list is in sorted ascending order. If
     *         there are no NameRecords that meet this criteria returns an empty
     *         list.
     */
    public ArrayList<String> rankedOnlyOneDecade() {
        ArrayList<String> result = new ArrayList<>();
        for (NameRecord nameRecord : names) {
            if (nameRecord.rankedOnlyOneDecade()) {
                result.add(nameRecord.getName());
            }
        }
        return result;
    }

    /**
     * Returns an ArrayList of Strings of names that have been getting more popular
     * every decade. The Strings must be in sorted order based on name.
     * 
     * @return A list of the names that have been getting more popular in every
     *         decade. The list is in sorted ascending order. If there are no
     *         NameRecords that meet this criteria returns an empty list.
     */
    public ArrayList<String> alwaysMorePopular() {
        ArrayList<String> result = new ArrayList<>();
        for (NameRecord nameRecord : names) {
            if (nameRecord.morePopularEachDecade()) {
                result.add(nameRecord.getName());
            }
        }
        return result;
    }

    /**
     * Returns an ArrayList of Strings of names that have been getting less popular
     * every decade. The Strings must be in sorted order based on name.
     * 
     * @return A list of the names that have been getting less popular in every
     *         decade. The list is in sorted ascending order. If there are no
     *         NameRecords that meet this criteria returns an empty list.
     */
    public ArrayList<String> alwaysLessPopular() {
        ArrayList<String> result = new ArrayList<>();
        for (NameRecord nameRecord : names) {
            if (nameRecord.lessPopularEachDecade()) {
                result.add(nameRecord.getName());
            }
        }
        return result;
    }

    /**
     * Returns an ArrayList of NameRecords that have experienced a sudden increase
     * in popularity one decade. The NameRecords must be in sorted order based on
     * name.
     * 
     * @return A list of the NameRecords that had one decade where it did not make
     *         the charts, then made the charts within the top 500 names, then fell
     *         off the charts again. If there are no NameRecords that meet this
     *         criteria, returns an empty list.
     */
    public ArrayList<NameRecord> suddenlyPopular(int threshold) {
        ArrayList<NameRecord> result = new ArrayList<>();
        for (NameRecord nameRecord : names) {
            if (nameRecord.suddenPopularity(threshold)) {
                result.add(nameRecord);
            }
        }
        return result;
    }

    /**
     * Return the NameRecord in this Names object that matches the given String.
     * <br>
     * <tt>pre: name != null</tt>
     * 
     * @param name The name to search for.
     * @return The name record with the given name or null if no NameRecord in this
     *         Names object contains the given name.
     */
    public NameRecord getName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The parameter name cannot be null");
        }

        name = name.toLowerCase();
        for (NameRecord nameRecord : names) {
            if (nameRecord.getName().toLowerCase().equals(name)) {
                return nameRecord;
            }
        }
        return null;

    }
}
