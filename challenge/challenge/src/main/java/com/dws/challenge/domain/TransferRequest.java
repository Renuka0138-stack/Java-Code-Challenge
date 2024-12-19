package com.dws.challenge.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

        @NotBlank
        private String accountFromId;

        @NotBlank
        private String accountToId;

        @NotNull
        @Min(1)
        private BigDecimal amount;
    }



