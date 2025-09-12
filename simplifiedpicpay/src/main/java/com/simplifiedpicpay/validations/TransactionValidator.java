package com.simplifiedpicpay.validations;

import com.simplifiedpicpay.domain.user.User;
import com.simplifiedpicpay.domain.user.UserType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionValidator {

    public void validate(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo lojista não está autorizado a realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0 ) {
            throw new Exception("Saldo insuficiente");
        }
    }
}
