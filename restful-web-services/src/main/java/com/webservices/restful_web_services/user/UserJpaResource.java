package com.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webservices.restful_web_services.jpa.PostRepository;
import com.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	@Autowired
	private UserRepository userRepository;
	
	private PostRepository postRepository;

	public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	// GET ALL USERS

	@GetMapping("/jpa/users")     
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	
	//	GET Specific User
	
	@GetMapping("/jpa/users/{id}")
	public Optional<User> retrieveUser(@PathVariable int id) {
		return userRepository.findById(id);
		
	}
	
	// POST (Create) NEW USER  
	
	
		@PostMapping("/jpa/users")
		public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
			
			User savedUser = userRepository.save(user);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedUser.getId())
							.toUri();   
			
			return ResponseEntity.created(location).build();
		}
		
	
	//	DELETE USER BY ID
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	
	// GET USER's ALL POSTS
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
	
		
		return user.get().getPosts();

	}
	
	

	// GET USER's specific POST
	
	@GetMapping("/jpa/users/{id}/posts/{pid}")
	public ResponseEntity<Post> retrievePostForUser(@PathVariable int id, @PathVariable int pid) {
		Optional<User> user = userRepository.findById(id);
		
		 if (user.isEmpty()) {
		        return ResponseEntity.notFound().build();
		    }
		 Post post = user.get().getPosts().stream()
                 .filter(p -> p.getId() == pid )
                 .findFirst()
                 .orElse(null);
		
		 if (post == null) {
		        return ResponseEntity.notFound().build();
		    }

	
		    return ResponseEntity.ok(post);

	}
	

	// CREATE NEW POST for a USER

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
	    Optional<User> user = userRepository.findById(id);
	    
	    if (user.isPresent()) 
	    {
	        post.setUser(user.get()); // Set the user for the post
	        
	        Post savedPost = postRepository.save(post); // Save the post
	        
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	            .path("/{id}")
	            .buildAndExpand(savedPost.getId())  // This will now contain the generated id
	            .toUri();   

	        return ResponseEntity.created(location).build(); // Return response with location of the created post
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
	    }
	}
	
	// Delete a POST general

			@DeleteMapping("/jpa/posts/{id}")
			public ResponseEntity<Void> deletePost(@PathVariable int id) {
			    Optional<Post> post = postRepository.findById(id);
			    
			    if (post.isPresent())
			    { 
			        postRepository.deleteById(id);
			        return ResponseEntity.noContent().build(); 
			        
			    } else {
			        return ResponseEntity.notFound().build(); 
			    }
			}

	
	// Delete a POST for a USER

//		@PostMapping("/jpa/users/{id}/posts/{id}")
//		public void deletePostForUser(@PathVariable int id, @Valid @RequestBody int pid) {
//		    Optional<User> user = userRepository.findById(id);
//		    
//		    if (user.isPresent()) 
//		    { // Set the user for the post
//		        user.postRepository.deleteById(pid);
//		        // Return response with location of the created post
//		    } else {
//		        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
//		    }
//		}

	
}
