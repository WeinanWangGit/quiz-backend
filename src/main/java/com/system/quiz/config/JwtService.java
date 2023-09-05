package com.system.quiz.config;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.*;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class JwtService {

    private final String CLIENT_ID = "769052452189-l67s5fik61rthvjk2vkil5jckrn9rb3l.apps.googleusercontent.com";
    private final HttpTransport transport = new NetHttpTransport();

    private final JsonFactory jsonFactory = new JacksonFactory();



    public GoogleIdToken verifyIdToken(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        try {
            return verifier.verify(idTokenString);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractEmailFromToken(GoogleIdToken idToken) {
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
//            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            return email;
        } else {
            throw new RuntimeException("Invalid Google ID token.");
        }
    }


    public boolean isTokenMatchUser(GoogleIdToken token, UserDetails userDetails){
        final String email = extractEmailFromToken(token);
        return (email.equals(userDetails.getUsername()));
    }
}
