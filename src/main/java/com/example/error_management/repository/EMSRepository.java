package com.example.error_management.repository;

import com.example.error_management.entity.*;
import com.example.error_management.entity.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EMSRepository implements IEMSRepository{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User findAcc(LoginForm loginForm){
        try{
            var param = new MapSqlParameterSource();
            param.addValue("login_id",loginForm.getLoginId());
            param.addValue("password",loginForm.getPassword());
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE login_id = :login_id AND password = :password",
                    param,new DataClassRowMapper<>(User.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Error> findErrorName(){
        return jdbcTemplate.query("SElECT * FROM errors",
                new DataClassRowMapper<>(Error.class));
    }

    @Override
    public List<Case> findAllCase(){
        return jdbcTemplate.query("SELECT cases.id,error_id,description,solve,solution,users.name FROM cases INNER JOIN users ON user_id = users.id",new DataClassRowMapper<>(Case.class));
    }

    @Override
    public List<NamePiece> findNamePiece(){
        return jdbcTemplate.query("SELECT errors.id, errors.error_name AS name,count(error_id) AS sum FROM cases INNER JOIN errors ON error_id = errors.id GROUP BY errors.error_name, errors.id ORDER BY errors.id"
                ,new DataClassRowMapper<>(NamePiece.class));
    }

    @Override
    public NamePiece findById(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return jdbcTemplate.queryForObject("SELECT errors.id , errors.error_name AS name,count(error_id) AS sum FROM cases INNER JOIN errors ON error_id = errors.id WHERE errors.id = :id GROUP BY errors.error_name, errors.id ORDER BY errors.id",
                param,new DataClassRowMapper<>(NamePiece.class));
    }

    @Override
    public List<Case> findByIdCase(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return jdbcTemplate.query("SELECT cases.id,error_id,description,solve,solution,users.name AS user_name FROM cases INNER JOIN users ON user_id = users.id WHERE error_id = :id ORDER BY cases.id;"
                                    ,param,new DataClassRowMapper<>(Case.class));
    }

    @Override
    public int insertErrorName(String name) {
        var param = new MapSqlParameterSource();
        param.addValue("name",name);
        return jdbcTemplate.update("INSERT INTO errors(error_name) VALUES(:name)",param);
    }

    @Override
    public int insertCase(Case errorCase) {
        var param = new MapSqlParameterSource();
        param.addValue("error_id",errorCase.getError_id());
        param.addValue("description",errorCase.getDescription());
        param.addValue("solve",errorCase.isSolve());
        param.addValue("solution",errorCase.getSolution());
        param.addValue("user_id",errorCase.getUser_id());
        return jdbcTemplate.update("INSERT INTO cases(error_id,description,solve,solution,user_id) VALUES(:error_id,:description,:solve,:solution,:user_id)",param);
    }

    @Override
    public int deleteCase(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return jdbcTemplate.update("DELETE FROM cases WHERE id = :id",param);
    }

    @Override
    public Case findUserId(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return jdbcTemplate.queryForObject("SELECT cases.id,error_id,description,solve,solution,users.name FROM cases INNER JOIN users ON user_id = users.id WHERE cases.id = :id",param,new DataClassRowMapper<>(Case.class));
    }

    @Override
    public int updateCase(Case errorCase) {
        var param = new MapSqlParameterSource();
        param.addValue("id",errorCase.getId());

        param.addValue("description",errorCase.getDescription());
        param.addValue("solve",errorCase.isSolve());
        param.addValue("solution",errorCase.getSolution());
        return jdbcTemplate.update("UPDATE cases SET description = :description, solve = :solve, solution = :solution WHERE id = :id",param);
    }

    @Override
    public Case findByCaseId(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id",id);
        return jdbcTemplate.queryForObject("SELECT * FROM cases WHERE id = :user_id",param,new DataClassRowMapper<>(Case.class));
    }

    @Override
    public int insertUser(User user) {
        var param = new MapSqlParameterSource();
        param.addValue("name",user.getName());
        param.addValue("loginId",user.getLogin_id());
        param.addValue("password",user.getPassword());
        return jdbcTemplate.update("INSERT INTO users (login_id,password,name) VALUES(:loginId, :password, :name)",param);
    }
}