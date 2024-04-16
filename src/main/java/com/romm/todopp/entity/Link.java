package com.romm.todopp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tb_link") @Entity @NoArgsConstructor @Setter @Getter @ToString
public class Link {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Task task;

    @ManyToOne
    private TaskList taskList;
    // será que depois da pra deixar @Unique? pq nao tem nada diretamente impedindo que dois links tenham a mesma posição numa lista.
    private int taskListPosition;
}
