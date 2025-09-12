package com.simplifiedpicpay.services;

import com.simplifiedpicpay.domain.user.User;
import com.simplifiedpicpay.dtos.UserDTO;
import com.simplifiedpicpay.repositories.UserRepository;
import com.simplifiedpicpay.validations.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TransactionValidator transactionValidator;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        transactionValidator.validate(sender, amount);
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception ("Usuário não encontrado"));
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }
}
