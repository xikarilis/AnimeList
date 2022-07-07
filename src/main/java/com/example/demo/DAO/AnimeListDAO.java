package com.example.demo.DAO;

import com.example.demo.entity.AnimeList;
import com.example.demo.repository.AnimeListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeListDAO {
    @Autowired
    private AnimeListRepository animeListRepository;

    public List<AnimeList> findAllAnime(){
        return animeListRepository.findAll();
    }

    public void saveAnime(AnimeList anime){
        animeListRepository.save(anime);
    }

    public void deleteAnime(long id) {
        animeListRepository.deleteById(id);
    }

}
