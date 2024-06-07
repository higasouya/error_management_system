package com.example.error_management.entity;

import lombok.Data;

//エラー名と件数を表示するためのクラス
@Data
public class NamePiece {
    private int id;
    private String name;
    private int sum;
}
