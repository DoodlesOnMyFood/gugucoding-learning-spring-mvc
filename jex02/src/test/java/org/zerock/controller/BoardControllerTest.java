package org.zerock.controller;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.config.RootConfig;
import org.zerock.config.ServletConfig;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class})
@Log4j
public class BoardControllerTest {
    @Setter(onMethod_ = @Autowired)
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void testList() throws Exception {
        log.info(
                mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
                        .andReturn()
                        .getModelAndView()
                        .getModelMap()
        );
    }

    @Test
    public void testGet() throws Exception {
        log.info(
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/board/get")
                        .param("bno", "3"))
                        .andReturn()
                        .getModelAndView().getModelMap()

        );
    }

    @Test
    public void testRegister() throws Exception {
        log.info(
                mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
                        .param("title", "테스트 제목")
                        .param("content", "테스트 내용")
                        .param("writer", "테스트 작가"))
                        .andReturn().getFlashMap().get("result")
        );
    }

    @Test
    public void testModify() throws Exception {
        String resultPage = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/board/modify")
                        .param("bno", "3")
                        .param("title", "수정된 컨트롤러 테스트 제목")
                        .param("content", "수정된 컨트롤러 테스트 내용")
                        .param("writer", "user00"))
                .andReturn()
                .getModelAndView()
                .getViewName();

        log.info(resultPage);
    }

    @Test
    public void testRemove() throws Exception {
        String resultPage = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/board/remove")
                                .param("bno", "21"))
                        .andReturn()
                        .getModelAndView()
                        .getViewName();

        log.info(resultPage);
    }
}