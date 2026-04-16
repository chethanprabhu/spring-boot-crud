package com.example.demo.todo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Transactional
    public Todo create(TodoRequest request) {
        return todoRepository.save(new Todo(normalizeTitle(request)));
    }

    @Transactional
    public Todo update(Long id, TodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Todo not found"));
        todo.setTitle(normalizeTitle(request));
        return todoRepository.save(todo);
    }

    @Transactional
    public void delete(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Todo not found");
        }
        todoRepository.deleteById(id);
    }

    private String normalizeTitle(TodoRequest request) {
        if (request == null || request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ResponseStatusException(BAD_REQUEST, "Title is required");
        }
        return request.getTitle().trim();
    }
}
