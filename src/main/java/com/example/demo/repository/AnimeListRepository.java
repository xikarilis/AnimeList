package com.example.demo.repository;

import com.example.demo.entity.AnimeList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeListRepository extends JpaRepository<AnimeList, Long> {

}
