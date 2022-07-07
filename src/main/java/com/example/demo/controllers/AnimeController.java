package com.example.demo.controllers;

import com.example.demo.DAO.AnimeListDAO;
import com.example.demo.entity.AnimeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class AnimeController {

    @Autowired
    private AnimeListDAO animeListDAO;

    @GetMapping("/myAnime")
    public String getAnimeList(Model model) {
        List<AnimeList> animeList = animeListDAO.findAllAnime();
        List<AnimeList>  plannedAnime = new ArrayList<>();
        List<AnimeList>  watchingAnime = new ArrayList<>();
        List<AnimeList>  watchedAnime = new ArrayList<>();
        for (int i = 0; i < animeList.size(); i++) {
          switch (animeList.get(i).getStatus()){
              case "planned" :
                  plannedAnime.add(animeList.get(i));
                  break;
              case "watching":
                  watchingAnime.add(animeList.get(i));
                  break;
              case "watched":
                  watchedAnime.add(animeList.get(i));
                  break;
          }
        }
        if(!plannedAnime.isEmpty()) {
            model.addAttribute("plannedAnime", plannedAnime);
        }else {
            model.addAttribute("plannedAnime","NoData");
        }
        if(!watchingAnime.isEmpty()) {
            model.addAttribute("watchingAnime", watchingAnime);
        }else {
            model.addAttribute("watchingAnime","NoData");
        }
        if(!watchedAnime.isEmpty()) {
            model.addAttribute("watchedAnime", watchedAnime);
        }else {
            model.addAttribute("watchedAnime","NoData");
        }
        return "animeList";
    }

    @GetMapping("/addAnime")
    public String addAnime() {
        return "addAnime";
    }

    @PostMapping("/addAnime")
    public String addAnimeList(AnimeList animeList, Model model) {
        if (animeList.getName().equals("") ||
        animeList.getGenre().equals("")  ||
        animeList.getRating().equals("")  ||
        animeList.getNumberOfEpisodes().equals("")) {
            model.addAttribute("Error","Incorrect data");
            return "addAnime";
        }
        try {
            Integer.parseInt(animeList.getNumberOfEpisodes());
            Integer.parseInt(animeList.getRating());
        }catch (Exception e){
            model.addAttribute("Error","Incorrect data");
            return "addAnime";
        }
        animeListDAO.saveAnime(animeList);
        return "redirect:myAnime";
    }


    @GetMapping("/deleteAnime/{id}")
    public String deleteAnime(@PathVariable("id") long id) {
        animeListDAO.deleteAnime(id);
        return "redirect:/myAnime";
    }

    @GetMapping("/sort/{status}/{type}")
    public String sortAnime(@PathVariable("status") String status, @PathVariable("type") String sortItem, Model model) {
        List<AnimeList> animeList = animeListDAO.findAllAnime();
        List<AnimeList>  plannedAnime = new ArrayList<>();
        List<AnimeList>  watchingAnime = new ArrayList<>();
        List<AnimeList>  watchedAnime = new ArrayList<>();
        List<AnimeList> sortingStatusAnime = null;
        for (int i = 0; i < animeList.size(); i++) {
            switch (animeList.get(i).getStatus()){
                case "planned" :
                    plannedAnime.add(animeList.get(i));
                    break;
                case "watching":
                    watchingAnime.add(animeList.get(i));
                    break;
                case "watched":
                    watchedAnime.add(animeList.get(i));
                    break;
            }
        }
        switch (status) {
            case "plannedAnime":
                sortingStatusAnime = plannedAnime;
                break;
            case "watchingAnime":
                sortingStatusAnime = watchingAnime;
                break;
            case "watchedAnime":
                sortingStatusAnime = watchedAnime;
                break;
        }
        if (sortItem.equals("numberOfEpisodes")) {
            Collections.sort(sortingStatusAnime, new Comparator<AnimeList>() {
                @Override
                public int compare(AnimeList firstAnime, AnimeList secondAnime) {
                    if(Integer.parseInt(firstAnime.getNumberOfEpisodes()) <Integer.parseInt(secondAnime.getNumberOfEpisodes())){
                        return 1;
                    }else if (Integer.parseInt(firstAnime.getNumberOfEpisodes()) >Integer.parseInt(secondAnime.getNumberOfEpisodes())){
                        return -1;
                    }else {
                        return 0;
                    }
                }
            });
        }else if (sortItem.equals("rating")){
            Collections.sort(sortingStatusAnime, new Comparator<AnimeList>() {
                @Override
                public int compare(AnimeList firstAnime, AnimeList secondAnime) {
                    if(Integer.parseInt(firstAnime.getRating()) <Integer.parseInt(secondAnime.getRating())){
                        return 1;
                    }else if (Integer.parseInt(firstAnime.getRating()) >Integer.parseInt(secondAnime.getRating())){
                        return -1;
                    }else {
                        return 0;
                    }
                }
            });
        }
        if(!plannedAnime.isEmpty()) {
            model.addAttribute("plannedAnime", plannedAnime);
        }else {
            model.addAttribute("plannedAnime","NoData");
        }
        if(!watchingAnime.isEmpty()) {
            model.addAttribute("watchingAnime", watchingAnime);
        }else {
            model.addAttribute("watchingAnime","NoData");
        }
        if(!watchedAnime.isEmpty()) {
            model.addAttribute("watchedAnime", watchedAnime);
        }else {
            model.addAttribute("watchedAnime","NoData");
        }
        return "animeList";
    }

}
