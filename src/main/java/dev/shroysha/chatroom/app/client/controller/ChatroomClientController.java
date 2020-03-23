package dev.shroysha.chatroom.app.client.controller;

import dev.shroysha.chatroom.ejb.ChatroomEntityScanTag;
import dev.shroysha.chatroom.ejb.ChatroomMessage;
import dev.shroysha.chatroom.ejb.ChatroomUser;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class ChatroomClientController {

    public static final WebClient WEB_CLIENT = WebClient.create(ChatroomEntityScanTag.BASE_URL);

    @Getter
    private static ChatroomUser loggedInUser;

    public static void createUser(String desiredUsername) {
        MultiValueMap<String, String> postData = new LinkedMultiValueMap<>();
        postData.add("username", desiredUsername);

        loggedInUser = WEB_CLIENT.post()
                .uri(ChatroomEntityScanTag.MESSAGE_CREATE)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(postData))
                .retrieve()
                .bodyToMono(ChatroomUser.class)
                .block();
    }

    public static void sendMessage(String message) {
        MultiValueMap<String, Object> postData = new LinkedMultiValueMap<>();

        postData.add("message", message);
        postData.add("user", loggedInUser);

        ChatroomMessage newMessage = WEB_CLIENT.post()
                .uri(ChatroomEntityScanTag.MESSAGE_CREATE)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(postData))
                .retrieve()
                .bodyToMono(ChatroomMessage.class)
                .block();
    }

    public static List getAllMessages() {
        return WEB_CLIENT.get()
                .uri(ChatroomEntityScanTag.MESSAGE_ALL)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    public static List getAllUsers() {
        return WEB_CLIENT.get()
                .uri(ChatroomEntityScanTag.USER_ALL)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

}
