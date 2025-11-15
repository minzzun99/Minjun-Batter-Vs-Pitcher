package com.woowaprecourse.minjun_baseball_game.domain;

import com.woowaprecourse.minjun_baseball_game.domain.strategy.NumberGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int totalAtBats;

    @Column(nullable = false)
    private int singles;

    @Column(nullable = false)
    private int doubles;

    @Column(nullable = false)
    private int triples;

    @Column(nullable = false)
    private int homeruns;

    public Player(String name, int totalAtBats, int singles, int doubles, int triples, int homeruns) {
        this.name = name;
        this.totalAtBats = totalAtBats;
        this.singles = singles;
        this.doubles = doubles;
        this.triples = triples;
        this.homeruns = homeruns;
    }

    public BattingRecord toBattingRecord(NumberGenerator numberGenerator) {
        return new BattingRecord(this.name, this.totalAtBats, this.singles, this.doubles, this.triples, this.homeruns,
                numberGenerator);
    }

    public double getBattingAverage() {
        if (totalAtBats == 0) {
            return 0.0;
        }
        int totalHits = singles + doubles + triples + homeruns;
        return (double) totalHits / totalAtBats;
    }

    public void update(String name, int totalAtBats, int singles, int doubles, int triples, int homeruns) {
        this.name = name;
        this.totalAtBats = totalAtBats;
        this.singles = singles;
        this.doubles = doubles;
        this.triples = triples;
        this.homeruns = homeruns;
    }
}
