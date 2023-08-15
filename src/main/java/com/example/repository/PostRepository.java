package com.example.repository;

import com.example.exception.NotFoundException;
import com.example.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final AtomicLong idCounter = new AtomicLong(0);
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
        return post.getId() != 0 ? changeOldPost(post) : addNewPost(post);
    }

    public void removeById(long id) {
        postList.removeIf(post -> post.getId() == id);
    }

    private Post changeOldPost(Post post) throws NotFoundException {
        if (this.getById(post.getId()).isPresent()) {
            this.getById(post.getId()).get().setContent(post.getContent());
            return post;
        } else {
            throw new NotFoundException("Unable to find post with this ID");
        }
    }

    private Post addNewPost(Post post) {
        post.setId(idCounter.getAndIncrement());
        postList.add(post);
        return post;
    }
}
