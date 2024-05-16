package com.romm.todopp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.romm.todopp.security.Ownable;

@Service
public class AuthorizationService {

    @Autowired AuthenticationService authenticationService;
    
    public boolean requestUserIsOwnerOrError(Ownable obj) {
        if (obj.getOwner() == authenticationService.getPrincipal())
            return true;
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The user does not own this resorce.");
    }
}
