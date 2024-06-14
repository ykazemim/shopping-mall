import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final static Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    public static ArrayList<String> validateSignInForm(String username, String password) {
        ArrayList<String> errors = new ArrayList<>();
        if (username.length() < 4){
            errors.add("Short username");
        }

        Matcher passwordMatcher = passwordPattern.matcher(password);
        if(!passwordMatcher.matches())
            errors.add("Invalid password");

        return errors;
    }

}
