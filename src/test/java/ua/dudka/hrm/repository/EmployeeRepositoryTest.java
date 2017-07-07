package ua.dudka.hrm.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.dudka.abstract_test.AbstractRepositoryTest;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.model.employee.Salary;
import ua.dudka.hrm.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author Rostislav Dudka
 */
public class EmployeeRepositoryTest extends AbstractRepositoryTest {

    private static final Integer NONEXISTENT_EMPLOYEE_ID = 404;

    private Employee testEmployee;

    @Autowired
    private EmployeeRepository repository;

    @Before
    public void setUp() throws Exception {
        tearDown();
        testEmployee = Employee.builder()
                .name("name")
                .surname("surname")
                .email("email@mail.com")
                .phoneNumber("+380500000000")
                .position("position")
                .salary(Salary.of(BigDecimal.valueOf(3800), Currency.USD))
                .build();
    }


    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void repositoryShouldSaveEmployeeWithAccount() throws Exception {
        Employee savedEmployee = repository.save(testEmployee);

        assertNotNull(savedEmployee);

        assertEquals(testEmployee, savedEmployee);
    }

    @Test
    public void repositoryShouldFindAllEmployees() throws Exception {
        Employee createdEmployee = repository.save(testEmployee);

        List<Employee> employees = repository.findAll();

        assertFalse(employees.isEmpty());
        assertEquals(createdEmployee, employees.get(0));
    }

    @Test
    public void findByIdShouldReturnCreatedEmployee() throws Exception {
        Employee employee = repository.save(testEmployee);

        Optional<Employee> actualEmployee = repository.findById(employee.getId());

        assertTrue(actualEmployee.isPresent());
        assertEquals(employee, actualEmployee.get());

    }

    @Test
    public void findByEmailShouldReturnCreatedEmployee() throws Exception {
        Employee employee = repository.save(testEmployee);

        Optional<Employee> actualEmployee = repository.findByEmail(employee.getEmail());

        assertTrue(actualEmployee.isPresent());
        assertEquals(employee, actualEmployee.get());
    }

    @Test
    public void findByPhoneNumberShouldReturnCreatedEmployee() throws Exception {
        Employee employee = repository.save(testEmployee);

        Optional<Employee> actualEmployee = repository.findByPhoneNumber(employee.getPhoneNumber());

        assertTrue(actualEmployee.isPresent());
        assertEquals(employee, actualEmployee.get());
    }


    @Test
    public void repositoryShouldUpdateEmployee() throws Exception {
        Employee savedEmployee = repository.save(testEmployee);

        Salary expectedSalary = Salary.of(BigDecimal.valueOf(10_000), Currency.UAH);

        savedEmployee.changeSalary(expectedSalary);

        Employee actualEmployee = repository.save(savedEmployee);

        assertEquals(expectedSalary, actualEmployee.getSalary());
    }

    @Test
    public void repositoryShouldDeleteExistentEmployee() throws Exception {
        Employee employee = repository.save(testEmployee);

        repository.delete(employee.getId());

        assertFalse(repository.exists(employee.getId()));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void repositoryShouldThrowExceptionWhenDeleteNonexistentEmployee() throws Exception {

        repository.delete(NONEXISTENT_EMPLOYEE_ID);
    }
}