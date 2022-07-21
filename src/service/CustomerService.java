package service;

import model.Customer;

import java.util.*;


public class CustomerService {
    private static CustomerService customerService = new CustomerService();
    private static Map<String, Customer> customerMap = new HashMap<String, Customer>();

    public static void addCustomer(String email, String firstName, String lastName) {
        customerMap.put(email, new Customer(firstName, lastName, email));
    }

    public static Customer getCustomer(String customerEmail) {
        for(Customer customer: customerMap.values()) {
            if (customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public static Collection<Customer> getAllCustomers() {
        return customerMap.values();
    }
}
