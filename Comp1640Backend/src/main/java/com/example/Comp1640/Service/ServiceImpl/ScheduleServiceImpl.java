package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.ScheduleDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.DTO.TutorDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Schedule;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Repository.ScheduleRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.ScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<ScheduleDto> getStudentSchedules(Long studentId) {
        List<Schedule> schedules = scheduleRepository.findByStudentId(studentId);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (schedules.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < schedules.size(); i++){
                ScheduleDto scheduleDto = new ScheduleDto(schedules.get(i).getId(),new StudentDto(schedules.get(i).getStudent()),new TutorDto(schedules.get(i).getTutor()),schedules.get(i).getStartTime(),schedules.get(i).getEndTime(),schedules.get(i).getStatus());
                scheduleDtos.add(scheduleDto);
            }
        }


        return scheduleDtos;
    }

    @Override
    public List<ScheduleDto> getTutorSchedules(Long tutorId) {
        List<Schedule> schedules = scheduleRepository.findByTutorId(tutorId);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (schedules.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < schedules.size(); i++){
                ScheduleDto scheduleDto = new ScheduleDto(schedules.get(i).getId(),new StudentDto(schedules.get(i).getStudent()),new TutorDto(schedules.get(i).getTutor()),schedules.get(i).getStartTime(),schedules.get(i).getEndTime(),schedules.get(i).getStatus());
                scheduleDtos.add(scheduleDto);
            }
        }
        return scheduleDtos;
    }

    @Override
    public ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto) {
        try {
            Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
            existingSchedule.setStatus(scheduleDto.getStatus());
            scheduleRepository.save(existingSchedule);
            return scheduleDto;

        }
        catch(Exception e){
            return null;
        }
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id) {
        System.out.println(id + " is deleteádadadd");
        try {
            scheduleRepository.deleteById(id);
            List<Schedule> schedules = scheduleRepository.findAll();
            System.out.println(schedules.size());

        } catch(Exception e ) {
            System.out.println(e);
        }
    }



}
