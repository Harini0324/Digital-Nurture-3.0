package com.example.empMgmt;

public class DepartmentProjectionImpl {

    private Long id;
    private String name;

    public DepartmentProjectionImpl(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
