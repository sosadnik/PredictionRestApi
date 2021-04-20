package org.PredictionRestApi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "competition_efficiency")
public class CompetitionEfficiency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "competition_name")
    private String competitionName;
    private int won;
    private int lost;
    private int other;
    private Double efficacy;



}
