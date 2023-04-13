package com.example.exercise.repository;

import com.example.exercise.entity.SignoutAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignoutAccessTokenRedisRepository extends CrudRepository<SignoutAccessToken, String> {
}
