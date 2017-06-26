package ua.dudka.hrm.web;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractWebIntegrationTest;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Salary;
import ua.dudka.hrm.application.CurrentCompanyReader;
import ua.dudka.hrm.domain.service.EmployeeCreator;
import ua.dudka.hrm.web.dto.CreateEmployeeRequest;

import java.math.BigDecimal;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.hrm.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_PAGE_URL;
import static ua.dudka.hrm.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(CreateEmployeeController.class)
public class CreateEmployeeControllerIntegrationTest extends AbstractWebIntegrationTest {

    @MockBean
    private CurrentCompanyReader currentCompanyReader;

    @MockBean
    private EmployeeCreator employeeCreator;

    private static Company testCompany;

    @BeforeClass
    public static void setUp() throws Exception {
        testCompany = new Company();
    }

    @Before
    public void setUpMock() throws Exception {
        when(currentCompanyReader.read()).thenReturn(testCompany);
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
                .param("salary.amount", request.getSalary().getAmount().toString())
                .param("salary.currency", request.getSalary().getCurrency().toString())
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