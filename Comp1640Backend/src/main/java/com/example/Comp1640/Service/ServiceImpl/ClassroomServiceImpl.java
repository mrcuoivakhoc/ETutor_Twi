package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.Entity.Classroom;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Repository.ClassroomRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public List<ClassroomDto> getAllClassroomDto() {
        List<Classroom> classrooms = classroomRepository.findAll();
        List<ClassroomDto> classroomDtos = new ArrayList<>();



        Map<Long, List<Classroom>> groupedClassroomMap = classrooms.stream()
                            .filter(c -> c.getTutor() != null) // Lọc các bản ghi có tutor
                            .collect(Collectors.groupingBy(c -> c.getTutor().getId()));




        groupedClassroomMap.forEach((tutorId, classroomsOfTutor) -> {
            List<Long> StudentsId = new ArrayList<>();
            for (Classroom classroom : classroomsOfTutor) {
                StudentsId.add(classroom.getStudent().getId());
            }
            classroomDtos.add(new ClassroomDto(tutorId,StudentsId));
        });

        return classroomDtos;
    }

    @Override
    public String saveClassroom(ClassroomDto classroomDto) {
        Tutor tutor = tutorRepository.findById(classroomDto.getTutorId()).get();

        for(int i = 0 ; i < classroomDto.getStudentsId().size() ; i++){
            Student student = studentRepository.findById(classroomDto.getStudentsId().get(i)).get();

            classroomRepository.save(new Classroom(tutor, student));
        }

        return "Saved successfully";
    }

    @Override
    public String updateClassroom(ClassroomDto classroomDtoInput) {
        List<ClassroomDto> allClassroomDtos = this.getAllClassroomDto();
        List<ClassroomDto> filteredListClassroomDtos = allClassroomDtos.stream()
                .filter(classroomDto -> classroomDto.getTutorId() == classroomDtoInput.getTutorId())
                .collect(Collectors.toList());


        for (int i = 0; i < filteredListClassroomDtos.get(0).getStudentsId().size(); i++) {
//            System.out.println("Student " + (i + 1) + ": " + filteredListClassroomDtos.get(0).getStudentsId().get(i));
            classroomRepository.deleteByStudentIdAndTutorId(filteredListClassroomDtos.get(0).getStudentsId().get(i),filteredListClassroomDtos.get(0).getTutorId());
        }

        saveClassroom(classroomDtoInput);

        return "Updated successfully";
    }


    @Override
    public void deleteClassroom(Long tutorId) {
        classroomRepository.deleteByTutorId(tutorId);
    }
}
