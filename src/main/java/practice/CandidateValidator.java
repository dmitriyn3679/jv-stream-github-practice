package practice;

import java.util.function.Predicate;
import model.Candidate;

public class CandidateValidator implements Predicate<Candidate> {
    private static final String REQUIRED_NATIONALITY = "Ukrainian";
    private static final int MIN_REQUIRED_AGE = 35;
    private static final int MIN_YEARS_IN_UA = 10;

    @Override
    public boolean test(Candidate c) {
        if (c == null) {
            return false;
        }

        return isOlderThan(c, MIN_REQUIRED_AGE)
                && isAllowedToVote(c)
                && hasRequiredNationality(c, REQUIRED_NATIONALITY)
                && livedInUkraineForAtLeast(c, MIN_YEARS_IN_UA);
    }

    private boolean isOlderThan(Candidate c, int age) {
        return c.getAge() >= age;
    }

    private boolean isAllowedToVote(Candidate c) {
        return c.isAllowedToVote();
    }

    private boolean hasRequiredNationality(Candidate c, String nationality) {
        String n = c.getNationality();

        return n != null && n.trim().equalsIgnoreCase(nationality);
    }

    private boolean livedInUkraineForAtLeast(Candidate c, int minYears) {
        String period = c.getPeriodsInUkr();
        if (period == null) {
            return false;
        }

        String[] parts = period.trim().split("-");

        if (parts.length != 2) {
            return false;
        }

        try {
            int from = Integer.parseInt(parts[0].trim());
            int to = Integer.parseInt(parts[1].trim());

            return (to - from) >= minYears;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
