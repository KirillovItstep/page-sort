package org.itstep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping("/users/page")
    public Page<User> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        return (Page<User>) userService.findAll(pageable);
    }

    //page-size, offset-limit
    @GetMapping(value = "/users", params = {"page", "size"})
    public Page<User> paginationUsers(@RequestParam("page") int page,
                                      @RequestParam("size") int size) throws IOException {
        Pageable pageable = PageRequest.of(page, size);
        return userService.findAll(pageable);
    }

    @GetMapping(value = "/users", params = {"offset", "limit"})
    public Page<User> paginationUsers2(@RequestParam("offset") int offset,
                                       @RequestParam("limit") int limit) throws IOException {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return userService.findAll(pageable);
    }

    @GetMapping(value = "/users/sort", params = {"name", "order"})
    public List<User> sort(@RequestParam("name") String name,
                           @RequestParam("order") String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equals("desc"))
            direction = Sort.Direction.DESC;
            return userService.findByOrderByName(Sort.by(direction, name));
        }
    }

