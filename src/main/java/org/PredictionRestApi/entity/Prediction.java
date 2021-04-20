package org.PredictionRestApi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "prediction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "homeTeam")
    private String homeTeam;
    @Column(name = "awayTeam")
    private String awayTeam;
    @Column(name = "competitionName")
    private String competitionName;
    @Column(name = "prediction")
    private String prediction;
    @Column(name = "competitionCluster")
    private String competitionCluster;
    @Column(name = "status")
    private String status;
    @Column(name = "result")
    private String result;
    @Column(name = "match_date")
    private Date date;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "odds_id")
    private Odds odds;

    public void setOdds(Odds odds) {
        this.odds = odds;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "id=" + id +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", competitionName='" + competitionName + '\'' +
                ", prediction='" + prediction + '\'' +
                ", competitionCluster='" + competitionCluster + '\'' +
                ", status='" + status + '\'' +
                ", result='" + result + '\'' +
                ", date=" + date +

                '}';
    }

}




