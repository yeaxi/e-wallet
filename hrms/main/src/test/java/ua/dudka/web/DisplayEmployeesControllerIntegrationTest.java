package ua.dudka.web;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractWebIntegrationTest;
import ua.dudka.application.CurrentCompanyReader;
import ua.dudka.domain.model.Company;
import ua.dudka.domain.model.Employee;
import ua.dudka.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.dudka.web.DisplayEmployeesController.Links.EMPLOYEES_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(DisplayEmployeesController.class)
public class DisplayEmployeesControllerIntegrationTest extends AbstractWebIntegrationTest {

    private static List<Employee> testEmployees;

    private static Company testCompany;

    @MockBean
    private CurrentCompanyReader currentCompanyReader;

    @MockBean
    private EmployeeRepository employeeRepository;


    @BeforeClass
    public static void setUp() throws Exception {
        testCompany = new Company();
        testEmployees = new ArrayList<>();

        testEmployees.add(new Employee());
        testEmployees.add(new Employee());
        testEmployees.add(new Employee());
    }

    @Before
    public void setUpMock() throws Exception {
        when(currentCompanyReader.read()).thenReturn(testCompany);
        when(employeeRepository.findAll()).thenReturn(testEmployees);
    }

    @Test
    public void getEmployeesPageShouldSetEmployeesToModel() throws Exception {
        mockMvc.perform(get(EMPLOYEES_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attribute("employees", testEmployees));
    }

}