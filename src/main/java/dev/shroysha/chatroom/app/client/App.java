package dev.shroysha.chatroom.app.client;

import dev.shroysha.chatroom.app.client.view.ChatroomClientFrame;
import dev.shroysha.chatroom.ejb.ChatroomEntityScanTag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

@SpringBootApplication
@EntityScan(basePackageClasses = ChatroomEntityScanTag.class)
public class App {

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .headless(false)
                .run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> SwingUtilities.invokeLater(() -> ctx.getBean(ChatroomClientFrame.class).setVisible(true));
    }

}
