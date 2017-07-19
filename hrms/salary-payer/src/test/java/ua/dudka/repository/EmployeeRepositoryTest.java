package ua.dudka.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.dudka.abstract_test.AbstractRepositoryTest;
import ua.dudka.domain.model.Company;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.model.Employee;
import ua.dudka.domain.model.Salary;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Rostislav Dudka
 */
public class EmployeeRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    private Employee testEmployee;

    @Before
    public void setUp() throws Exception {
        tearDown();

        Salary salary = Salary.of(BigDecimal.valueOf(3800), Currency.USD);
        testEmployee = new Employee(1, salary);
    }


    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void repositoryShouldUpdateEmployeesSalary() throws Exception {
        Employee savedEmployee = repository.save(testEmployee);

        Salary expectedSalary = Salary.of(BigDecimal.valueOf(10_000), Currency.UAH);

        savedEmployee.changeSalary(expectedSalary);

        Employee actualEmployee = repository.save(savedEmployee);

        assertEquals(expectedSalary, actualEmployee.getSalary());
    }
}