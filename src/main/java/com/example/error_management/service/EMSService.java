package com.example.error_management.service;

import com.example.error_management.entity.*;
import com.example.error_management.entity.Error;
import com.example.error_management.repository.EMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EMSService implements  IEMSService{

    @Autowired
    private EMSRepository emsRepository;

    @Override
    public User findAcc(LoginForm loginForm) { return emsRepository.findAcc(loginForm);}

    @Override
    public List<Error> findErrorName() {return emsRepository.findErrorName();}

    @Override
    public List<Case> findAllCase() {return emsRepository.findAllCase();}

    @Override
    public List<NamePiece> findNamePiece() { return emsRepository.findNamePiece();}

    @Override
    public NamePiece findById(int id) { return emsRepository.findById(id);}

    @Override
    public List<Case> findByIdCase(int id) {return emsRepository.findByIdCase(id);}

    @Override
    public int insertErrorName(String name) {return emsRepository.insertErrorName(name);}

    @Override
    public int insertCase(Case errorCase) { return emsRepository.insertCase(errorCase);}

    @Override
    public int deleteCase(int id) { return emsRepository.deleteCase(id);}

    @Override
    public Case findUserId(int id){ return emsRepository.findUserId(id);}

    @Override
    public int updateCase(Case errorCase){ return emsRepository.updateCase(errorCase);}

    @Override
    public Case findByCaseId(int id) { return emsRepository.findByCaseId(id);}

    @Override
    public int insertUser(User user) { return emsRepository.insertUser(user);}
}
