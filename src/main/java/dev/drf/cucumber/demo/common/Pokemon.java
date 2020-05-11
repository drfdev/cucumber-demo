package dev.drf.cucumber.demo.common;

import dev.drf.cucumber.demo.common.skill.Bonus;
import dev.drf.cucumber.demo.common.skill.Defence;
import dev.drf.cucumber.demo.common.skill.Skill;

import java.util.List;

public class Pokemon {
    private Long id;
    private String name;
    private PokemonClass pokemonClass;
    private PokemonKind pokemonKind;

    private List<Skill> skills;
    private List<Bonus> bonuses;
    private List<Defence> defences;

    public Pokemon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PokemonClass getPokemonClass() {
        return pokemonClass;
    }

    public void setPokemonClass(PokemonClass pokemonClass) {
        this.pokemonClass = pokemonClass;
    }

    public PokemonKind getPokemonKind() {
        return pokemonKind;
    }

    public void setPokemonKind(PokemonKind pokemonKind) {
        this.pokemonKind = pokemonKind;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<Bonus> bonuses) {
        this.bonuses = bonuses;
    }

    public List<Defence> getDefences() {
        return defences;
    }

    public void setDefences(List<Defence> defences) {
        this.defences = defences;
    }
}
