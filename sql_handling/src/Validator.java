import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final static Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    public static boolean validateSignInForm(String username, String password) throws Exception {
        if (username.length() < 4){
            throw new Exception("Short username");
        }

        Matcher passwordMatcher = passwordPattern.matcher(password);
        if(passwordMatcher.matches())
            return true;
        else
            throw new Exception("Invalid password");
    }

}
