package com.test.testproject.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable
public class Order {

    @DatabaseField(id = true, useGetSet = true)
    private String id;

    @DatabaseField(useGetSet = true)
    private String part_name;

    @DatabaseField(useGetSet = true)
    private String type_string;

    private List<String> type;

    public Order() {
    }

    public void prepareToSave() {
        if(type != null && type.size() > 0)
            type_string = type.get(0);
    }

    public void load() {
        if (type == null){
            type = new ArrayList<String>();
        }
        type.add(type_string);
    }

    public String getType_string() {
        return type_string;
    }

    public void setType_string(String type_string) {
        this.type_string = type_string;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}