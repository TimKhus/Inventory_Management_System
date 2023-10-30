public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;



    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserDataInCSVFormat() {
        String csvString = String.format("%s,%s,%s,%s,%s", getClass().getSimpleName(), getFirstName(), getLastName(), getEmail(), getPassword());
        return csvString;
    }

    public String toString() {
        return String.format("Name: %-25s Email: %-20s", firstName + " " +  lastName, email);
    }
}
