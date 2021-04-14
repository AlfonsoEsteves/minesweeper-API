package com.example.minesweeper.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface GameRepository extends CrudRepository<SavedGame, SavedGameId> {
}
