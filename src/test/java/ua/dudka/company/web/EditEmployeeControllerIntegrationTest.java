package ua.dudka.company.web;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractWebIntegrationTest;
import ua.dudka.company.domain.Admin;
import ua.dudka.company.service.AdminReader;
import ua.dudka.company.service.EmployeeEditor;
import ua.dudka.company.web.dto.EditEmployeeRequest;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Employee;
import ua.dudka.employee.domain.Salary;
import ua.dudka.employee.repository.EmployeeRepository;

import static java.math.BigDecimal.valueOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.company.web.EditEmployeeController.Links.EDIT_EMPLOYEE_PAGE_URL;
import static ua.dudka.company.web.EditEmployeeController.Links.EDIT_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(EditEmployeeController.class)
public class EditEmployeeControllerIntegrationTest extends AbstractWebIntegrationTest {

    private static final int EMPLOYEE_ID = 100;
    private static Admin testAdmin;
    private static Employee testEmployee;

    @MockBean
    private AdminReader adminReader;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeEditor employeeEditor;

    @BeforeClass
    public static void setUp() throws Exception {
        testAdmin = new Admin();
        testEmployee = Employee.builder()
                .id(EMPLOYEE_ID)
                .name("name")
                .surname("surname")
                .email("email@mail.com")
                .phoneNumber("050505055")
                .position("middle")
                .salary(Salary.of(valueOf(1000), Currency.USD))
                .build();
    }

    @Before
    public void setUpMock() throws Exception {
        when(adminReader.read()).thenReturn(testAdmin);
        when(employeeRepository.findOne(eq(EMPLOYEE_ID))).thenReturn(testEmployee);
    }

    @Test
    public void getEditEmployeePageShouldReturnOK() throws Exception {
        mockMvc.perform(get(EDIT_EMPLOYEE_PAGE_URL, EMPLOYEE_ID).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }

    @Test
    public void editEmployeeRequestShouldInvokeProcessingInServiceAndRefreshPage() throws Exception {
        EditEmployeeRequest request = new EditEmployeeRequest(EMPLOYEE_ID, "senior", Salary.of(valueOf(4000), Currency.USD));

        mockMvc.perform(post(EDIT_EMPLOYEE_URL)
                .param("employeeId", EMPLOYEE_ID + "")
                .param("newPosition", request.getNewPosition())
                .param("newSalaryAmount", request.getNewSalary().getAmount().toString())
                .param("newSalaryCurrency", request.getNewSalary().getCurrency().toString())
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

        verify(employeeEditor).edit(eq(request));
    }
}