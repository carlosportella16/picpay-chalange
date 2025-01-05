package com.challenge.picpay.controller.dto;

import com.challenge.picpay.entity.Wallet;
import com.challenge.picpay.entity.WalletType;
import com.challenge.picpay.exception.InvalidCpfCnpjException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.regex.Pattern;

import static com.challenge.picpay.util.CpfCnpjValidator.isValid;

public record CreateWalletDto(@NotBlank String fullName,
                              @NotBlank String cpfCnpj,
                              @NotBlank @Email String email,
                              @NotBlank String password,
                              @NotNull WalletType.Enum walletType) {

    public Wallet toWallet() {
        return new Wallet(
                fullName,
                validCpfCnpj(cpfCnpj),
                email,
                password,
                walletType.get()
        );
    }

    private String validCpfCnpj(String cpfCnpj) {
        if (!isValid(cpfCnpj)) {
            throw new InvalidCpfCnpjException();
        }
        return cpfCnpj.replaceAll("\\D", "");
    }
}
