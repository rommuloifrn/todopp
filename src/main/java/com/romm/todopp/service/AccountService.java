package com.romm.todopp.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romm.todopp.DTO.AccountDTO;

@Service
public class AccountService {
    @Autowired AuthenticationService authenticationService;

    public AccountDTO AccountDetails() {
        var user = authenticationService.getPrincipal();

        Integer expiredListsQtt=0;
        Integer activeListsQtt=0;
        Integer listsQtt = user.getTaskLists().size();
        
        for (var list : user.getTaskLists()) {
            if (list.getDeadline()==null) activeListsQtt++;
            else if (list.getDeadline().before(Date.from(Instant.now()))) expiredListsQtt++;
            else activeListsQtt++;
        }

        return new AccountDTO(user.getUsername(), user.getEmail(), activeListsQtt, expiredListsQtt, listsQtt);
    }

}
