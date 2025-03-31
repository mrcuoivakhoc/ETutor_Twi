package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.DTO.MajorDto;

import java.util.List;

public interface ClassroomService {

    List<ClassroomDto> getAllClassroomDto();

    String saveClassroom(ClassroomDto classroomDto);

    String updateClassroom(ClassroomDto classroomDto);

    void deleteClassroom(Long tutorId);
}
