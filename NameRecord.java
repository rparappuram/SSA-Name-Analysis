import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * A record of a name. Stores the data for an individual name, including the
 * name itself (a String), the base decade (decade of the first rank), and the
 * rank of the name for each decade. *
 * 
 * @author Ryan Parappuram
 *
 */
public class NameRecord implements Comparable<NameRecord> {

    private int baseDecade;
    private String name;
    private ArrayList<Integer> ranks;

    private static final int DECADE_INCREMENT = 10;

    /**
     * Private constructor which is only called by static method buildRecord() if
     * valid input. Creates a new NameRecord object with a baseDecade, name, and
     * list of ranks for each decade after baseDecade (inclusive).
     * 
     * @param firstDecade:  baseDecade of input file
     * @param nameOfPerson: name or this NameRecord
     * @param personsRanks: ranks for each decade starting from firstDecade
     */
    private NameRecord(int firstDecade, String nameOfPerson, int[] personsRanks) {
        baseDecade = firstDecade;
        name = nameOfPerson;
        ranks = new ArrayList<>();
        for (int i = 0; i < personsRanks.length; i++) {
            ranks.add(personsRanks[i]);
        }
    }

    /**
     * Called by client first, checks if input is valid for a new creation of a
     * NameRecord according to below preconditions. <br>
     * Second, (if valid input) creates new NameRecord by calling private
     * constructor.
     * 
     * Pre: ranks in input == numRanks and not every rank is a 0
     * 
     * Returns null if preconditions not met.
     * 
     * @param firstDecade: baseDecade for this NameRecord
     * @param numRanks:    number of ranks to be held in this NameRecord's
     *                     ArrayList<Integer> ranks
     * @param input:       line containing name followed by numRanks number of ranks
     *                     for each decade
     * @return new NameRecord with name and ranks if input valid, otherwise returns
     *         null.
     */
    public static NameRecord buildRecord(int firstDecade, int numRanks, String input) {
        // check conditions for input
        String[] parsedData = input.split("\\s+");
        boolean correctSize = parsedData.length == numRanks + 1;
        boolean notAllZeroes = false;
        int[] rankByDecade = new int[parsedData.length - 1];
        for (int i = 1; i < parsedData.length; i++) {
            rankByDecade[i - 1] = Integer.parseInt(parsedData[i]);
            if (!notAllZeroes) {
                notAllZeroes = rankByDecade[i - 1] != 0;
            }
        }

        if (correctSize && notAllZeroes) {
            return new NameRecord(firstDecade, parsedData[0], rankByDecade);
        } else {
            return null;
        }
    }

    /**
     * Gets the name for this NameRecord.
     * 
     * @return name for this NameRecord.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the base decade (decade of the first rank) for this NameRecord.
     * 
     * @return base decade for this NameRecord.
     */
    public int getBaseDecade() {
        return baseDecade;
    }

    /**
     * Gets rank for a given decade for this NameRecord.
     * 
     * Pre: 0 <= decade < max number of ranks
     * 
     * Throws IllegalArgumentException if parameter preconditions not met.
     * 
     * @param decade: decade for which rank will be returned <br>
     *                0 <= decade < max number of ranks
     * @return rank for given decade for this Name Record. <br>
     *         returns 0 if the name is unranked.
     */
    public int getRank(int decade) {
        if (decade < 0 || decade >= ranks.size()) {
            throw new IllegalArgumentException(
                    "decade parameter must be between 0 and (max number of ranks - 1)");
        }

        return ranks.get(decade);
    }

    /**
     * Gets the decade this name was most popular for this NameRecord.
     * 
     * @return this NameRecord's best decade <br>
     *         most recent decade in the event of a tie.
     */
    public int bestDecade() {
        Collections.replaceAll(ranks, 0, Integer.MAX_VALUE);
        int index = ranks.lastIndexOf(Collections.min(ranks));
        Collections.replaceAll(ranks, Integer.MAX_VALUE, 0);
        // convert index to decade
        return baseDecade + (DECADE_INCREMENT * index);
    }

    /**
     * Gets number of decades this names has been ranked in the top 1000.
     * 
     * @return number of decades this names has been ranked in the top 1000.
     */
    public int numDecadesRanked() {
        int count = 0;
        for (int i : ranks) {
            if (i > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determines if this name has been ranked in the top 1000 in every decade.
     * 
     * @return true if this name has been ranked in the top 1000 in every decade,
     *         false otherwise.
     */
    public boolean rankedEveryDecade() {
        return numDecadesRanked() == ranks.size();
    }

    /**
     * Determines if this name has been ranked in the top 1000 in only one decade.
     * 
     * @return true if this name has been ranked in the top 1000 in only one decade,
     *         false otherwise.
     */
    public boolean rankedOnlyOneDecade() {
        return numDecadesRanked() == 1;
    }

    /**
     * Determines if this name has been getting more popular every decade in ranks.
     * 
     * @return true if every decades rank is better (closer to 1) than the previous
     *         decade, false otherwise.
     */
    public boolean morePopularEachDecade() {
        boolean descending = Collections.frequency(ranks, 0) <= 1;
        // only runs following checks if num 0s is not more than 1
        if (descending) {
            Collections.replaceAll(ranks, 0, Integer.MAX_VALUE);
            Iterator<Integer> itr = ranks.iterator();
            int lastDecade = itr.next();
            while (descending && itr.hasNext()) {
                int current = itr.next();
                descending = current < lastDecade;
                lastDecade = current;
            }
            Collections.replaceAll(ranks, Integer.MAX_VALUE, 0);
        }
        return descending;
    }

    /**
     * Determines if this name has been getting less popular every decade in ranks.
     * 
     * @return true if every decades rank is worse (farther from 1) than the
     *         previous decade, false otherwise.
     */
    public boolean lessPopularEachDecade() {
        boolean ascending = Collections.frequency(ranks, 0) <= 1;
        // only runs following checks if num 0s is not more than 1
        if (ascending) {
            Collections.replaceAll(ranks, 0, Integer.MAX_VALUE);
            Iterator<Integer> itr = ranks.iterator();
            int lastDecade = itr.next();
            while (ascending && itr.hasNext()) {
                int current = itr.next();
                ascending = current > lastDecade;
                lastDecade = current;
            }
            Collections.replaceAll(ranks, Integer.MAX_VALUE, 0);
        }
        return ascending;
    }

    /**
     * Determines if this name had been unranked the previous decade, ranked within
     * the top 500 this decade, and back to unranked the following decade.
     * 
     * @return true if a succession is found where rank was was 0 for one decade,
     *         within the top 500 for the next, and 0 again for the next. Returns
     *         false otherwise.
     */
    public boolean suddenPopularity(int threshold) {
        for (int i = 1; i < ranks.size() - 1; i++) {
            if (suddenPopularityCheck(ranks.get(i - 1), ranks.get(i), ranks.get(i + 1),
                    threshold)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Private helper method for sudden5Hundred()<br>
     * Checks if the rank in the previous decade is 0 (or unranked), the rank in the
     * current decade is within top 500, and the rank in the following decade is 0.
     * 
     * @param before:  rank for previous decade
     * @param current: rank for decade checking to be within top 500 names
     * @param after:   rank for following decade
     * @return true if before == 0, 0 < current <= 500, after == 0
     */
    private boolean suddenPopularityCheck(int before, int current, int after, int threshold) {
        return before == 0 && after == 0 && current > 0 && current <= threshold;
    }

    /**
     * Method required due to implementing Comparable interface. Will assist in
     * comparing two NameRecord objects.
     * 
     * @param other: NameRecord to which this NameRecord is being compared to
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    public int compareTo(NameRecord other) {
        return name.compareTo(other.name);
    }

    /**
     * Overrides toString() for this NameRecord object.
     * 
     * @return String version of this Name Record
     */
    public String toString() {
        final String newLine = "\n";
        final String colon = ": ";
        StringBuilder result = new StringBuilder(name).append(newLine);
        for (int i = 0; i < ranks.size(); i++) {
            result.append(baseDecade + i * DECADE_INCREMENT);
            result.append(colon);
            result.append(ranks.get(i));
            result.append(newLine);
        }
        return result.toString();
    }
}
