package com.challenge.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidCpfCnpjException extends PicPayException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Invalid CPF/CNPJ");
        pb.setDetail("The field cpf/cnpj must be valid");

        return pb;
    }
}
