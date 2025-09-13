package com.infosec.lab1.controller;

import com.infosec.lab1.model.DataItem;
import com.infosec.lab1.dto.UserDto;
import com.infosec.lab1.model.User;
import com.infosec.lab1.repo.DataRepository;
import com.infosec.lab1.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class DataController {

    private UserRepository userRepository;
    private DataRepository dataRepository;

    @GetMapping("/data")
    public ResponseEntity<List<UserDto>> getAllUsers(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) {
            log.error("User not entered");
            return ResponseEntity.status(401).build();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserDto> users = List.of(new UserDto(user.getId(), user.getUsername()));

        return ResponseEntity.ok(users);
    }

    @PostMapping("/data")
    public ResponseEntity<DataItem> createData(@Valid @RequestBody DataItem req,
                                               HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) {
            log.error("User not entered");
            return ResponseEntity.status(401).build();
        }

        if (!username.equals(req.getOwner())) {
            log.error("Owner in request does not match authenticated user: {}", username);
            return ResponseEntity.status(403).body(null);
        }

        DataItem item = new DataItem();
        item.setOwner(username);
        item.setTitle(req.getTitle());
        item.setContent(req.getContent());

        DataItem saved = dataRepository.save(item);
        log.info("Item was created successfully for user: " + username);
        return ResponseEntity.ok(saved);
    }
}
