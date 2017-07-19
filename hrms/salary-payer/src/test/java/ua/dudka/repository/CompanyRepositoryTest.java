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
public class CompanyRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CompanyRepository repository;

    private Company testCompany;

    @Before
    public void setUp() throws Exception {
        tearDown();

        testCompany = new Company(1);
        addNewEmployee(testCompany);
    }

    private void addNewEmployee(Company company) {
        Salary salary = Salary.of(BigDecimal.valueOf(3800), Currency.USD);
        Employee testEmployee = new Employee(1, salary);
        company.addEmployee(testEmployee);
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void repositoryShouldSaveCompanyWithEmployee() throws Exception {
        Company savedCompany = repository.save(testCompany);

        assertNotNull(savedCompany);
        assertEquals(testCompany, savedCompany);
    }

    @Test
    public void repositoryShouldFindAllCompanies() throws Exception {
        Company savedCompany = repository.save(testCompany);

        List<Company> companies = repository.findAll();

        assertFalse(companies.isEmpty());
        assertEquals(savedCompany, companies.get(0));
    }

    @Test
    public void repositoryShouldDeleteExistentCompany() throws Exception {
        Company savedCompany = repository.save(testCompany);

        repository.delete(savedCompany.getId());

        assertFalse(repository.exists(savedCompany.getId()));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void repositoryShouldThrowExceptionWhenDeleteNonexistentEmployee() throws Exception {

        Integer nonexistentCompanyId = 404;
        repository.delete(nonexistentCompanyId);
    }
}