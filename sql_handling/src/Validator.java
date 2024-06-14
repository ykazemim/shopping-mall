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
        if (username.length() < 4){
            errors.add("Short username");
        }

        Matcher usernameMatcher = usernamePattern.matcher(username);
        if (!usernameMatcher.matches())
            errors.add("Username should only contains alphabetic characters, digits, underscores and dashes");

        Matcher passwordMatcher = passwordPattern.matcher(password);
        if(!passwordMatcher.matches())
            errors.add("Invalid password");

        return errors;
    }

    public static ArrayList<String> validateSingUpForm(String name, String username, String password, String phone, String address) {
        ArrayList<String> errors = new ArrayList<>();

        Matcher nameMatcher = namePattern.matcher(name);
        if (!nameMatcher.matches())
            errors.add("Invalid name");

        if (username.length() < 4){
            errors.add("Short username");
        }

        Matcher usernameMatcher = usernamePattern.matcher(username);
        if (!usernameMatcher.matches())
            errors.add("Username should only contains alphabetic characters, digits, underscores and dashes");

        Matcher passwordMatcher = passwordPattern.matcher(password);
        if(!passwordMatcher.matches())
            errors.add("Invalid password");

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
