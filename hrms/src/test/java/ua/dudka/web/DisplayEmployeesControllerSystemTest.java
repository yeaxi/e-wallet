package ua.dudka.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractSystemTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.web.DisplayEmployeesController.Links.EMPLOYEES_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class DisplayEmployeesControllerSystemTest extends AbstractSystemTest {

    @Test
    public void getEmployeesPageShouldReturnOK() throws Exception {
        mockMvc.perform(get(EMPLOYEES_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}