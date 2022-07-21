package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private final String regex = "^(.+)@(.+).com$";
    final Pattern pattern = Pattern.compile(regex);

    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException {
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Mail address does not match the pattern name@domain.com.");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "Customer - first name : " + firstName +
                ", last name : " + lastName +
                ", email : " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(regex, customer.regex) && Objects.equals(pattern, customer.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, regex, pattern);
    }
}
