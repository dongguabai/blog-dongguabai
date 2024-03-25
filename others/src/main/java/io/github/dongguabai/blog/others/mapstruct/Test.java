package io.github.dongguabai.blog.others.mapstruct;

/**
 * @author dongguabai
 * @date 2024-03-22 16:42
 */
public class Test {

    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setName("John Doe");
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        customer.setAddress(address);

        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
        System.out.println(customerDTO);
    }
}