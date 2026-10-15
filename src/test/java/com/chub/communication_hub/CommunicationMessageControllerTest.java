
package com.chub.communication_hub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;




@SpringBootTest
@AutoConfigureMockMvc
class CommunicationMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getAllMessages_shouldReturn200() throws Exception {
        mockMvc.perform(get("/v1/communicationMessage"))
                .andExpect(status().isOk());
    }

    @Test
    void createMessage_shouldReturn201() throws Exception {

        String requestBody = """
            {
                "receiver": "test@example.com",
                "channel": "EMAIL",
                "content": "Test message",
                "state": "initial"
            }
            """;

        mockMvc.perform(post("/v1/communicationMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.receiver").value("test@example.com"))
                .andExpect(jsonPath("$.channel").value("EMAIL"));
    }


    @Test
    void updateMessage_shouldReturn200() throws Exception {

        String createBody = """
        {
            "receiver": "original@example.com",
            "channel": "EMAIL",
            "content": "Original message",
            "state": "initial"
        }
        """;

        String response = mockMvc.perform(post("/v1/communicationMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long id = Long.parseLong(
                response.replaceAll(".*\"id\":(\\d+).*", "$1")
        );

        String updateBody = """
        {
            "receiver": "updated@example.com",
            "channel": "EMAIL",
            "content": "Updated test message",
            "state": "inProgress"
        }
        """;

        mockMvc.perform(patch("/v1/communicationMessage/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receiver").value("updated@example.com"))
                .andExpect(jsonPath("$.state").value("inProgress"));
    }



    @Test
    void deleteMessage_shouldReturn204() throws Exception {

        String requestBody = """
        {
            "receiver": "delete@example.com",
            "channel": "EMAIL",
            "content": "Message to delete",
            "state": "initial"
        }
        """;

        String response = mockMvc.perform(post("/v1/communicationMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long id = Long.parseLong(
                response.replaceAll(".*\"id\":(\\d+).*", "$1")
        );

        mockMvc.perform(delete("/v1/communicationMessage/" + id))
                .andExpect(status().isNoContent());
    }




}
