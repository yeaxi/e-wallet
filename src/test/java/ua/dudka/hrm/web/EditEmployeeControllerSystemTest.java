package ua.dudka.hrm.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractSystemTest;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.repository.EmployeeRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.hrm.web.EditEmployeeController.Links.EDIT_EMPLOYEE_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class EditEmployeeControllerSystemTest extends AbstractSystemTest {

    private Employee testEmployee;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        testEmployee = employeeRepository.save(new Employee());
    }

    @After
    public void tearDown() throws Exception {
        employeeRepository.delete(testEmployee);
    }

    @Test
    public void getCreateEmployeePageShouldReturnOK() throws Exception {
        mockMvc.perform(get(EDIT_EMPLOYEE_PAGE_URL, testEmployee.getId()).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}