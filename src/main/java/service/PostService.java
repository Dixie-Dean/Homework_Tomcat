package service;

import exception.NotFoundException;
import model.Post;
import repository.PostRepository;

import java.util.List;
import java.util.Objects;

public class PostService {
  private long idCounter = 0;
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id).orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    return post.getId() != 0 ? changeOldPost(post) : addNewPost(post);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }

  private Post changeOldPost(Post post) {
    if (repository.getById(post.getId()).isPresent()) {
      repository.getById(post.getId()).get().setContent(post.getContent());
      return post;
    }
    return null;
  }

  private Post addNewPost(Post post) {
    post.setId(idCounter++);
    return repository.save(post);
  }
}

