package ua.dudka.hrm.employee.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractSystemTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.account.web.DashboardController.Links.DASHBOARD_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class DashboardControllerSystemTest extends AbstractSystemTest {

    @Test
    public void getDashboardPageShouldReturnOK() throws Exception {
        mockMvc.perform(get(DASHBOARD_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}