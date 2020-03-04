package ru.kravchenko.designer.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class AbstractEntity {

    String id = UUID.randomUUID().toString();

}
