package com.challenge.picpay.service;

import com.challenge.picpay.client.AuthorizationClient;
import com.challenge.picpay.controller.dto.TransferDto;
import com.challenge.picpay.entity.Transfer;
import com.challenge.picpay.exception.PicPayException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDto transferDto) {

        var resp = authorizationClient.isAuthorized();
        if (resp.getStatusCode().isError()) {
            throw new PicPayException();
        }

        return resp.getBody().authorized();
    }
}
