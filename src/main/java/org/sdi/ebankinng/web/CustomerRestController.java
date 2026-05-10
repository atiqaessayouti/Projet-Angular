package org.sdi.ebankinng.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sdi.ebankinng.ditos.CustomerDTO;
import org.sdi.ebankinng.exeptions.CustomerNotFoundException;
import org.sdi.ebankinng.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
/** * IMPORTANT: Beddelna "*" b l-URL dial Angular (4200) 
 * bach n-7ellou mouchkil dial Security f Spring Boot 3
 */
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerRestController {

    private BankAccountService bankAccountService;

    // 1. Jbed ga3 l-customers
    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        log.info("Fetching all customers");
        return bankAccountService.listCustomers();
    }

    // 2. Qelleb 3la customers b keyword (Search)
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        log.info("Searching for customers with keyword: {}", keyword);
        // Zdna % hna bach l-SQL y-khdem b "LIKE %keyword%"
        return bankAccountService.searchCustomers("%" + keyword + "%");
    }

    // 3. Jbed customer wahed b ID
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        log.info("Fetching customer with ID: {}", customerId);
        return bankAccountService.getCustomer(customerId);
    }

    // 4. Zid customer jdid
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        log.info("Saving new customer: {}", customerDTO.getName());
        return bankAccountService.saveCustomer(customerDTO);
    }

    // 5. Modifi customer (Update)
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        log.info("Updating customer ID: {}", customerId);
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    // 6. Msah customer (Delete)
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        log.info("Deleting customer ID: {}", id);
        bankAccountService.deleteCustomer(id);
    }
}