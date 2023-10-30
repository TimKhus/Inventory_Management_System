import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {
    private static List<User> users = new ArrayList<>();

    public static List<User> getUsers() {
        return users;
    }

    // check if we have all needed info to register a user
    public static boolean registerUser(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // Checking for required fields. If any is empty, return false.
            return false;
        }
        User newUser = new User(firstName, lastName, email, password);
        getUsers().add(newUser);
        return true;
    }

    // check if the email is registered
    public static boolean emailRegistered(String email) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email)) {
                return true; // email already registered in our database
            }
        }
        return false; // email is not registered, user should register
    }

    //find user by email
    public static User findUser(String email) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    //checking if email is valid
    public static boolean isEmailValid(String userInput) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(userInput);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
