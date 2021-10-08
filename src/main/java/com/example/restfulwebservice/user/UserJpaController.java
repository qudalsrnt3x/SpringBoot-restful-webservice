package com.example.restfulwebservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpa")
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // localhost:8088/jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<User>> retrieveUser(@PathVariable Long id) {

        /*Optional<User> user = userRepository.findById(id);

        if(!user.isPresent())
            throw new UserNotFoundException(String.format("ID[%s] not found", id));

         retrun user.get();
         */

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("ID[%s] not found", id))
        );

        // HATEOAS
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        User savedUser = userRepository.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    // 전체 게시물 목록 가져오기
    // /jpa/users/1/posts
    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable Long id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("ID[%s] not found", id))
        );

        return findUser.getPosts();
    }

    // 게시물 추가하기
    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Long id, @RequestBody Post post) {

        User savedUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("ID[%s] not found", id))
        );

        post.setUser(savedUser);

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
