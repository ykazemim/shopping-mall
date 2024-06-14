import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final static Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    private final static Pattern usernamePattern = Pattern.compile("^[A-Za-z0-9_-]+$");
    private final static Pattern namePattern = Pattern.compile("^[A-Za-z\\s]+$");
    private final static Pattern phonePattern = Pattern.compile("^09\\d*$");

    public static ArrayList<String> validateSignInForm(String username, String password) {
        ArrayList<String> errors = new ArrayList<>();

        Matcher usernameMatcher = usernamePattern.matcher(username);
        if (!usernameMatcher.matches() || username.length() < 4)
            errors.add("Username should at least contain 4 characters and only contain alphabetic characters, digits, underscores and dashes");

        Matcher passwordMatcher = passwordPattern.matcher(password);
        if(!passwordMatcher.matches())
            errors.add("Password's length should be at least 8\nPassword should contains at least one uppercase letter, one lowercase letter, one digit and one special characters");

        return errors;
    }

    public static ArrayList<String> validateSingUpForm(String name, String username, String password, String phone, String address) {
        ArrayList<String> errors = new ArrayList<>();

        Matcher nameMatcher = namePattern.matcher(name);
        if (!nameMatcher.matches())
            errors.add("Name should only contain alphabetic characters and white spaces");

        Matcher usernameMatcher = usernamePattern.matcher(username);
        if (!usernameMatcher.matches() || username.length() < 4)
            errors.add("Username should at least contain 4 characters and only contain alphabetic characters, digits, underscores and dashes");

        Matcher passwordMatcher = passwordPattern.matcher(password);
        if(!passwordMatcher.matches())
            errors.add("Password's length should be at least 8\nPassword should contains at least one uppercase letter, one lowercase letter, one digit and one special characters");

        Matcher phoneMatcher = phonePattern.matcher(phone);
        if (phone.length() != 11 || !phoneMatcher.matches()) {
            errors.add("Please enter phone number in this pattern : 09xxxxxxxxx");
        }

        if (address.isBlank()) {
            errors.add("Invalid address");
        }

        return errors;
    }

}
