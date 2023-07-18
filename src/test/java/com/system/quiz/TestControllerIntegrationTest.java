package com.system.quiz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateTest() {
        String testJson = "{\"title\": \"Test Title\", \"description\": \"Test Description\", \"type\": \"Test Type\", \"time\": \"Test Time\", \"completeType\": \"Test Complete Type\", \"randomSort\": true, \"submitMode\": \"Test Submit Mode\", \"retakeRule\": \"Test Retake Rule\", \"safeCheck\": true, \"answerShowModel\": \"Test Answer Show Model\", \"teacherId\": 1, \"questions\": [ { \"id\": 5, \"type\": \"Question Type\", \"content\": \"Question Content\", \"answer\": \"Question Answer\", \"choice\": \"Question Choices\", \"score\": 10.0, \"teacher\": { \"id\": 1, \"number\": \"1\", \"department\": \"1\", \"user\": null } }, {  \"id\": 6, \"type\": \"Question Type\", \"content\": \"Question Content\", \"answer\": \"Question Answer\", \"choice\": \"Question Choices\", \"score\": 5.0, \"teacher\": { \"id\": 1, \"number\": \"1\", \"department\": \"1\", \"user\": null } } ]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(testJson, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/test/create", HttpMethod.POST, requestEntity, Void.class);

        // Assert the response status code is 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());
    }



    @Test
    public void testEditTest() {
        String testJson = "{\"title\": \"Test Title Updated\", \"description\": \"Test Description Updated\", \"type\": \"Test Type Updated\", \"time\": \"Test Time Updated\", \"completeType\": \"Test Complete Type Updated\", \"randomSort\": true, \"submitMode\": \"Test Submit Mode Updated\", \"retakeRule\": \"Test Retake Rule Updated\", \"safeCheck\": true, \"answerShowModel\": \"Test Answer Show Model Updated\", \"teacherId\": 1, \"questions\": [ { \"id\": 5, \"type\": \"Question Type\", \"content\": \"Question Content\", \"answer\": \"Question Answer\", \"choice\": \"Question Choices\", \"score\": 10.0, \"teacher\": { \"id\": 1, \"number\": \"1\", \"department\": \"1\", \"user\": null } }, {  \"id\": 6, \"type\": \"Question Type\", \"content\": \"Question Content\", \"answer\": \"Question Answer\", \"choice\": \"Question Choices\", \"score\": 5.0, \"teacher\": { \"id\": 1, \"number\": \"1\", \"department\": \"1\", \"user\": null } } ]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(testJson, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/test/edit", HttpMethod.PUT, requestEntity, Void.class);

        // Assert the response status code is 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testSetTestSettings() {
        // Test code for setTestSettings method
    }

    @Test
    public void testGetTestListByTeacherId() {
        int teacherId = 1;

        ResponseEntity<List> responseEntity = restTemplate.getForEntity("/test/list/{teacherId}", List.class, teacherId);

        // Assert the response status code is 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());

        List<Test> testList = responseEntity.getBody();
        // Assert additional assertions based on the expected behavior of the method
    }


    @Test
    public void testGetTestList() {
        ResponseEntity<List> responseEntity = restTemplate.exchange("/tests", HttpMethod.GET, null, List.class);

        // Assert the response status code is 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Test> testList = responseEntity.getBody();
        // Assert additional assertions based on the expected behavior of the method
    }

    @Test
    public void testDeleteTest() {
        int testId = 10;

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/test/delete/{testId}", HttpMethod.DELETE, null, Void.class, testId);

        // Assert the response status code is 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}







