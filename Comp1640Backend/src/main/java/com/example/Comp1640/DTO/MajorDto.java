package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Major;

public class MajorDto {

    private Long id;
    private String name;
    private String description;

    // ✅ Constructor mặc định (bắt buộc cho Spring và Jackson)
    public MajorDto() {}

    // ✅ Constructor đầy đủ
    public MajorDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // ✅ Constructor từ Entity
    public MajorDto(Major major) {
        if (major != null) {
            this.id = major.getId();
            this.name = major.getName();
            this.description = major.getDescription();
        }
    }

    // ✅ Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
