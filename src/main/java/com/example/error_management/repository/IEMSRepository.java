package com.example.error_management.repository;

import com.example.error_management.entity.*;
import com.example.error_management.entity.Error;

import java.util.List;

public interface IEMSRepository {

    User findAcc(LoginForm loginForm);

    List<Error> findErrorName();

    List<Case> findAllCase();

    List<NamePiece> findNamePiece();

    NamePiece findById(int id);

    List<Case> findByIdCase(int id);

    int insertErrorName(String name);

    int insertCase(Case errorCase);

    int deleteCase(int id);

    Case findUserId(int id);

    int updateCase(Case errorCase);

    Case findByCaseId(int id);

    int insertUser(User user);
}
