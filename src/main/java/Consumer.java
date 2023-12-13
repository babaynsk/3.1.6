import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        HttpHeaders headers = response.getHeaders();
        String sessionId = headers.getFirst(HttpHeaders.SET_COOKIE);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.COOKIE, sessionId);


        Map<String, Object> newUser = new HashMap<>();
        newUser.put("id", 3);
        newUser.put("name", "James");
        newUser.put("lastName", "Brown");
        newUser.put("age", 30);
        HttpEntity<Map<String, Object>> userEntity = new HttpEntity<>(newUser, requestHeaders);
        ResponseEntity<String> addedUserResponse = restTemplate.exchange(url, HttpMethod.POST, userEntity, String.class);


        Map<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("id", 3);
        updatedUser.put("name", "Thomas");
        updatedUser.put("lastName", "Shelby");
        updatedUser.put("age", 30);
        HttpEntity<Map<String, Object>> updateUserEntity = new HttpEntity<>(updatedUser, requestHeaders);
        ResponseEntity<String> updatedUserResponse = restTemplate.exchange(url, HttpMethod.PUT, updateUserEntity, String.class);


        String deleteUserUrl = "http://94.198.50.185:7081/api/users/3";
        ResponseEntity<String> deletedUserResponse = restTemplate.exchange(deleteUserUrl, HttpMethod.DELETE, new HttpEntity<>(requestHeaders), String.class);


        String finalCode = sessionId + addedUserResponse.getBody() + updatedUserResponse.getBody() + deletedUserResponse.getBody();
        System.out.println(finalCode);
    }
}