package ua.dudka.hrm.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractSystemTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.hrm.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class CreateEmployeeControllerSystemTest extends AbstractSystemTest {

    @Test
    public void getCreateEmployeePageShouldReturnOK() throws Exception {
        mockMvc.perform(get(CREATE_EMPLOYEE_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}