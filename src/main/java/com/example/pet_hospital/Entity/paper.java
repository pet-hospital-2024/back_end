package com.example.pet_hospital.Entity;


import cn.hutool.json.JSONObject;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.springframework.data.relational.core.mapping.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class paper {
    String paper_id;
    String paper_name;
    String duration;
    String question_id;
    Integer question_number;
    Integer value;
    Integer order;
    //ArrayList<paperItem> questions;
    //String questionsJSON;
}
