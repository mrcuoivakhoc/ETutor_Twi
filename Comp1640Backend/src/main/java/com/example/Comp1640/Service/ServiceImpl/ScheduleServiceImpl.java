package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.ScheduleDto;
import com.example.Comp1640.Entity.Schedule;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Repository.ScheduleRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ScheduleDto saveSchedule(ScheduleDto scheduleDto) {
        if(scheduleDto == null){
            return null;
        }else {
            Student student = studentRepository.findById(scheduleDto.getStudentDto().getId()).orElse(null);
            Tutor tutor = tutorRepository.findById(scheduleDto.getTutorDto().getId()).orElse(null);

            Schedule schedule = new Schedule(student,tutor,scheduleDto.getStartTime(),scheduleDto.getEndTime(),scheduleDto.getStatus());
            scheduleRepository.save(schedule);
            return scheduleDto;
        }
    }

    public List<Schedule> getStudentSchedules(Long studentId) {
        return scheduleRepository.findByStudentId(studentId);
    }

    public List<Schedule> getTutorSchedules(Long tutorId) {
        return scheduleRepository.findByTutorId(tutorId);
    }



}
