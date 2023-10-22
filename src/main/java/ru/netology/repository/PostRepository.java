package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PostRepository {

    private static final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private static final AtomicLong cnt = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        if (posts.containsKey(id)) {
            return Optional.of(posts.get(id));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Post> save(Post post) {
        if (post.getId() == 0) {
            post.setId(cnt.incrementAndGet());
            posts.put(post.getId(), post);
        } else if (post.getId() != 0) {
            if (posts.containsKey(post.getId())) {
                posts.replace(post.getId(), post);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(post);
    }


    public void removeById(long id) throws NotFoundException {
        if (posts.containsKey(id)) {
            posts.remove(id);
            return;
        }
        throw new NotFoundException("Delete exception. Post with ID " + id + " not found");
    }
}
