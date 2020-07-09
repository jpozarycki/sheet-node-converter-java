package com.jpozarycki.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Node {
    private Integer id;
    private String name;
    private List<Node> nodes;

    public Node (Integer id, String name) {
        this.id = id;
        this.name = name;
        this.nodes = new ArrayList<>();
    }
}
