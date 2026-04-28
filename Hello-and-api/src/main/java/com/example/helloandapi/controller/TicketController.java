package com.example.helloandapi.controller;

import com.example.helloandapi.model.Ticket;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    List<Ticket> ticketList=new ArrayList<>();
    @PostMapping
    public String createTickets(@RequestBody Ticket ticket){
        ticketList.add(ticket);
        return "Ticket submitted successfully!";
    }
    @GetMapping
    public List<Ticket> getTicketList(){
        return ticketList;
    }
}
