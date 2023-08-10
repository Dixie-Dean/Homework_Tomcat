package service;

import exception.NotFoundException;
import model.Post;
import repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository repository;
    private long idCounter = 0;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) throws NotFoundException {
        return post.getId() != 0 ? changeOldPost(post) : addNewPost(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }

    private Post changeOldPost(Post post) throws NotFoundException {
        if (repository.getById(post.getId()).isPresent()) {
            repository.getById(post.getId()).get().setContent(post.getContent());
            return post;
        } else {
            throw new NotFoundException("Unable to find post with this ID");
        }
    }

    private Post addNewPost(Post post) {
        post.setId(idCounter++);
        return repository.save(post);
    }
}