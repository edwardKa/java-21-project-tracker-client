package com.project.tracker.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tracker.client.model.*;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    @SneakyThrows
    public static void main(String[] args) {
        try {
            String sessionId = login();
            getAllUsers(sessionId);


        } catch (HttpClientErrorException e) {
            String errorResponse = e.getResponseBodyAsString();
            System.out.println(errorResponse);
            OuterErrorResponse response = new ObjectMapper()
                    .readValue(errorResponse, OuterErrorResponse.class);

            System.out.println(response.getErrorResponse().toString());
        }
    }

    private static String login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("tel-ran@site.com");
        loginRequest.setPassword("123456");
        RestTemplate restTemplate = new RestTemplate();
        RegistrationResponse body = restTemplate
                .postForEntity(
                        "http://localhost:8080/users/login",
                        loginRequest,
                        RegistrationResponse.class)
                .getBody();

        System.out.println(body.getSessionId());

        return body.getSessionId();
    }

    private static void getAllUsers(String sessionId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
//            String body = restTemplate
//                    .getForEntity(
//                            "http://localhost:8080/users/",
//                            String.class)
//                    .getBody();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", sessionId);

        HttpEntity<RegistrationRequest> entity = new HttpEntity<RegistrationRequest>(
                null,
                httpHeaders);

        String body = restTemplate.exchange(
                "http://localhost:8080/users/",
                HttpMethod.GET,
                entity,
                String.class).getBody();

        List<UserWeb> users = new ObjectMapper()
                .readValue(
                        body,
                        new TypeReference<List<UserWeb>>() {
                        });

        System.out.println(users.toString());
        for (UserWeb userWeb : users) {
            System.out.println(String.format("%s %s",
                    userWeb.getFirstName(),
                    userWeb.getLastName()));
        }
    }


    private static String registration() {
        //http://localhost:8080/users/register
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("Tel-Ran");
        request.setLastName("Tel-Ran");
        request.setEmail("tel-ran4@site.com");
        request.setPassword("123456");

        RestTemplate restTemplate = new RestTemplate();
        RegistrationResponse response =
                restTemplate
                        .postForEntity(
                                "http://localhost:8080/users/register",
                                request,
                                RegistrationResponse.class)
                        .getBody();


        String welcomeMessage = String.format("Welcome, %s %s",
                response.getUser().getFirstName(),
                response.getUser().getLastName());

        String sessionIdMessage = String.format("Your session ID is %s",
                response.getSessionId());

        System.out.println(welcomeMessage);
        System.out.println(sessionIdMessage);

        System.setProperty("sessionId", response.getSessionId());

        return response.getSessionId();
    }
}
