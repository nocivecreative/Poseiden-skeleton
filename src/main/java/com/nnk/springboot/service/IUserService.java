package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.User;

/**
 * Contrat métier pour la gestion des utilisateurs.
 */
public interface IUserService {

    List<User> findAll();

    User findById(Integer id);

    void save(User user);

    void update(Integer id, User user);

    void delete(Integer id);
}
