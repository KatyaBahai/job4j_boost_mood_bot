package ru.job4j.model;

import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Include
    private long chatId;
    private Long id;
    private long clientId;
}
