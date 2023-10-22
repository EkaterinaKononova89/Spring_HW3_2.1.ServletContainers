package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

@Configuration
public class JavaConfig {

    @Bean
    public PostController controller(PostService service){
        return new PostController(service);
    }
//    public PostController controller() { - другой вариант, аннотация @Bean также необходима
//        return new PostController(service());
//    }

        @Bean
    public PostService service(PostRepository repository) {
        return new PostService(repository);
    }
//    public PostService service() {
//        return new PostService(repository());
//    }

        @Bean
    public PostRepository repository() {
        return new PostRepository();
    }
//    public PostRepository repository() {
//        return new PostRepository();
//    }
}
