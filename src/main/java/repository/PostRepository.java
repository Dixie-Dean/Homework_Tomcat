package repository;

import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepository {
    private final List<Post> postList = new ArrayList<>();

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
