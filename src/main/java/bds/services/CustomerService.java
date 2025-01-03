package bds.services;

import bds.api.*;
import bds.data.CustomerRepository;

import java.util.List;


public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerBasicView> getCustomerBasicView() {
        return customerRepository.getCustomerBasicView();
    }

    public void createCustomer(CustomerCreateView customerCreateView) {

        customerRepository.createCustomer(customerCreateView);
        System.out.println("Customer was created successfully: " + customerCreateView.getFirstName() + " " + customerCreateView.getLastName());
    }

    public void editCustomer(CustomerEditView customerEditView) {
        customerRepository.editCustomer(customerEditView);
        System.out.println("Customer was updated successfully: ID = " + customerEditView.getIdCustomer());
    }

    public void deleteCustomer(CustomerBasicView selectedCustomer) {
        customerRepository.deleteCustomer(selectedCustomer.getIdCustomer());
        System.out.println("Customer was deleted successfully: ID = " + selectedCustomer.getIdCustomer());
    }
}
