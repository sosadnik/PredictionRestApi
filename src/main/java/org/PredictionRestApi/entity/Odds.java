package org.PredictionRestApi.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "odds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Odds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "won1")
    private Double won1;
    @Column(name = "draw")
    private Double draw;
    @Column(name = "won2")
    private Double won2;
    @Column(name = "won1Draw")
    private Double won1Draw;
    @Column(name = "won2Draw")
    private Double won2Draw;
    @Column(name = "won1Won2")
    private Double won1Won2;


}

