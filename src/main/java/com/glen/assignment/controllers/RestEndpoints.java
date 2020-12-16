package com.glen.assignment.controllers;

import com.glen.assignment.common.utils.helpers.GenericResponse;
import com.glen.assignment.dto.EventRequestDTO;
import com.glen.assignment.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/")
@CrossOrigin
public class RestEndpoints {
    @Autowired
    ScraperService service;

    @PostMapping("events/{page}/{limit}")
    public CompletableFuture<ResponseEntity<GenericResponse>> getEvents(@RequestBody EventRequestDTO request, @PathVariable int page, @PathVariable int limit){
        return service.getEvents(request, page, limit).thenApply(ResponseEntity::ok);
    }
}
