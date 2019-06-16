package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public void registerUser(User newUser) {
        userRepository.registerUser(newUser);
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }

    //Validate password strength
    public boolean validatePassword(String password) {
        Pattern letter = Pattern.compile(".*[a-zA-Z]+.*");
        Pattern digit = Pattern.compile("(.)*(\\d)(.)*");
        Pattern special = Pattern.compile("[a-zA-Z0-9]*");

        Matcher hasLetter = letter.matcher(password);
        Matcher hasDigit = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);

        boolean atleastOneLetter = hasLetter.find();
        boolean atleastOneDigit = hasDigit.find();
        boolean atleastOneSpecial = !hasSpecial.matches();

        return (atleastOneLetter && atleastOneDigit && atleastOneSpecial);
    }

}
