package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


public class PostRepository {

    private static final CopyOnWriteArrayList<Post> posts = new CopyOnWriteArrayList<>();
    private long cnt = 0;

    public CopyOnWriteArrayList<Post> all() { // или можно оставить просто лист?
        return posts;
    }

    public Optional<Post> getById(long id) {
        for (Post p : posts) {
            if (p.getId() == id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Optional<Post> save(Post post) {
        if (post.getId() == 0) {
            synchronized (posts) { // монитор на потокобезопасный объект для работы со счетчиком и id
                cnt++;
                post.setId(cnt);
                posts.add(post);
            }
        } else if (post.getId() != 0) { // удаление и добавление элементов в потокобезопасном списке, монитор не нужен
            for (Post p : posts) {
                if (p.getId() == post.getId()) {
                    posts.remove(p);
                    posts.add(post);
                    return Optional.of(post);
                }
            }
            return Optional.empty();
        }
        return Optional.of(post);
    }

    public void removeById(long id) throws NotFoundException {
        for (Post p : posts) {
            if (p.getId() == id) {
                posts.remove(p);
                return;
            }
        }
        throw new NotFoundException("Delete exception. Post with ID " + id + " not found");
    }
}
