package ua.dudka.company.web;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractWebIntegrationTest;
import ua.dudka.company.domain.Admin;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Salary;
import ua.dudka.company.service.AdminReader;
import ua.dudka.company.service.EmployeeCreator;
import ua.dudka.company.web.dto.CreateEmployeeRequest;

import java.math.BigDecimal;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.company.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_PAGE_URL;
import static ua.dudka.company.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(CreateEmployeeController.class)
public class CreateEmployeeControllerIntegrationTest extends AbstractWebIntegrationTest {

    @MockBean
    private AdminReader adminReader;

    @MockBean
    private EmployeeCreator employeeCreator;

    private static Admin testAdmin;

    @BeforeClass
    public static void setUp() throws Exception {
        testAdmin = new Admin();
    }

    @Before
    public void setUpMock() throws Exception {
        when(adminReader.read()).thenReturn(testAdmin);
    }

    @Test
    public void getCreateEmployeePageShouldReturnOK() throws Exception {
        mockMvc.perform(get(CREATE_EMPLOYEE_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }

    @Test
    public void createEmployeeRequestShouldInvokeProcessingInServiceAndRefreshPage() throws Exception {
        CreateEmployeeRequest request = buildCreateAccountRequest();

        mockMvc.perform(post(CREATE_EMPLOYEE_URL)
                .param("name", request.getName())
                .param("surname", request.getSurname())
                .param("email", request.getEmail())
                .param("phoneNumber", request.getPhoneNumber())
                .param("position", request.getPosition())
                .param("salaryAmount", request.getSalary().getAmount().toString())
                .param("salaryCurrency", request.getSalary().getCurrency().toString())
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

        verify(employeeCreator).create(eq(request));
    }

    private CreateEmployeeRequest buildCreateAccountRequest() {
        return CreateEmployeeRequest.builder()
                .name("name")
                .surname("surname")
                .email("email@mail.com")
                .phoneNumber("+380500000000")
                .position("position")
                .salary(Salary.of(BigDecimal.valueOf(3800), Currency.USD))
                .build();
    }
}