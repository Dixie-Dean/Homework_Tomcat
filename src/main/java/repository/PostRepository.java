package repository;

import model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostRepository {
    private final List<Post> postList = new CopyOnWriteArrayList<>();

    public List<Post> all() {
        return postList;
    }

    public Optional<Post> getById(long id) {
        for (Post post : postList) {
            if (post.getId() == id) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    public void removeById(long id) {
        postList.removeIf(post -> post.getId() == id);
    }
}
