package com.jpozarycki.sheetNodeConverter.model;

import lombok.Value;

import java.util.List;

@Value
public class Node {
    Integer id;
    String name;
    List<Node> nodes;
}
