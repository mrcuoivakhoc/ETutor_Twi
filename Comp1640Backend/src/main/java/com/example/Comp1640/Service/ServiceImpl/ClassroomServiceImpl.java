package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.Entity.Classroom;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Repository.ClassroomRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ClassroomDto> getAllClassroomDto() {
        List<Classroom> classrooms = classroomRepository.findAll();
        List<ClassroomDto> classroomDtos = new ArrayList<>();

        for (Classroom classroom : classrooms) {
            ClassroomDto dto = new ClassroomDto();
            dto.setId(classroom.getId());
            dto.setTutorId(classroom.getTutor().getId());
            List<Long> studentList = new ArrayList<>();
            studentList.add(classroom.getStudent().getId());
            dto.setStudentsId(studentList);
            classroomDtos.add(dto);
        }

        return classroomDtos;
    }

    @Override
    public String saveClassroom(ClassroomDto classroomDto) {
        Tutor tutor = tutorRepository.findById(classroomDto.getTutorId()).orElse(null);
        if (tutor == null) return "❌ Tutor not found.";

        String tutorName = tutor.getName();
        String tutorEmail = tutor.getUser().getEmail();

        for (Long studentId : classroomDto.getStudentsId()) {
            Student student = studentRepository.findById(studentId).orElse(null);
            if (student == null) continue;

            String studentName = student.getName();
            String studentEmail = student.getUser().getEmail();
            String dateNow = java.time.LocalDate.now().toString();

            // Gửi mail cho học sinh
            String subjectStudent = "📚 Thông báo phân lớp";
            String contentStudent = "Chào " + studentName + ",\n\n"
                    + "Bạn đã được phân lớp với gia sư " + tutorName + " (ID: " + tutor.getId() + ") vào ngày " + dateNow + ".\n"
                    + "Hãy kiểm tra lịch học hoặc liên hệ lại nếu có thay đổi.\n\n"
                    + "Trân trọng,\nHệ thống eTutoring";
            mailService.sendMail(studentEmail, subjectStudent, contentStudent);

            // Gửi mail cho gia sư
            String subjectTutor = "👨‍🏫 Thông báo phân lớp";
            String contentTutor = "Chào " + tutorName + ",\n\n"
                    + "Bạn đã được phân lớp với học sinh " + studentName + " (ID: " + student.getId() + ") vào ngày " + dateNow + ".\n"
                    + "Hãy chuẩn bị bài giảng và theo dõi lịch dạy của bạn.\n\n"
                    + "Trân trọng,\nHệ thống eTutoring";
            mailService.sendMail(tutorEmail, subjectTutor, contentTutor);

            // Lưu phân lớp
            classroomRepository.save(new Classroom(tutor, student));
        }

        return "✅ Classify and send mail successfully.";
    }

    @Override
    public String updateClassroom(ClassroomDto classroomDtoInput) {
        List<Classroom> classrooms = classroomRepository.findAll();

        for (Classroom classroom : classrooms) {
            if (classroom.getTutor().getId().equals(classroomDtoInput.getTutorId())) {
                classroomRepository.deleteById(classroom.getId());
            }
        }

        saveClassroom(classroomDtoInput);
        return "Updated successfully";
    }

    @Override
    public void deleteClassroom(Long tutorId) {
        classroomRepository.deleteByTutorId(tutorId);
    }
}
