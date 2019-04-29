package com.demo.SpringBootSSLDemo;

import com.demo.SpringBootSSLDemo.Controllers.SSLController;
import com.demo.SpringBootSSLDemo.Filters.TransactionFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootSslDemoApplicationTests {

    @Autowired
    SSLController sslController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(sslController).isNotNull();
    }

    @Test
    public void sslGetMessage() throws Exception {
        this.mockMvc.perform(get("/secured")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(containsString("Welcome User")));
    }

    @Test
    public void checkResponseHeaderFromFilter() throws Exception {
        assertThat(this.mockMvc.perform(get("/secured"))
                .andReturn()
                .getResponse()
                .getHeader("X-Correlation-Id"))
                .contains("abc123");
    }

    @Test
    public void passRequestHeaderAndCheckResponseHeaderFromFilter() throws Exception {
        assertThat(this.mockMvc.perform(get("/secured").header("X-Correlation-Id", "XYZ987"))
                .andReturn()
                .getResponse()
                .getHeader("X-Correlation-Id"))
                .contains("XYZ987");
    }

}
