package com.example.crud.controller;

import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {


        @Autowired
        private UserRepository userRepository;

        // get all employees
        @GetMapping("/users")
        public List<User> getAllUsers(){
            return userRepository.findAll();
        }

        // create employee rest api
        @PostMapping("/users")
        public User createUser(@RequestBody User user) {
            return userRepository.save(user);
        }

        // get employee by id rest api
        @GetMapping("/users/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Long id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
            return ResponseEntity.ok(user);
        }

        // update employee rest api

        @PutMapping("/users/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmailId(userDetails.getEmailId());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        }

        // delete employee rest api
        @DeleteMapping("/users/{id}")
        public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

            userRepository.delete(user);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }
    }


