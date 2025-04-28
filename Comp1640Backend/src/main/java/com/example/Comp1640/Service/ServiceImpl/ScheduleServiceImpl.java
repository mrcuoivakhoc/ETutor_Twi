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

import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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

            String dayOfWeek = scheduleDto.getStartTime().getDayOfWeek().toString();
            int year = scheduleDto.getStartTime().getYear();
            int weekOfYearDraft = scheduleDto.getStartTime().get(WeekFields.of(Locale.getDefault()).weekOfYear());

            String weekOfYear = String.format("%d-W%02d", year, weekOfYearDraft);


            Schedule schedule = new Schedule(student,
                    tutor,
                    scheduleDto.getStartTime(),
                    scheduleDto.getEndTime(),
                    dayOfWeek,
                    weekOfYear,
                    scheduleDto.getStatus(),
                    scheduleDto.getScheduleFormat(),
                    scheduleDto.getAddress());
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
                ScheduleDto scheduleDto = new ScheduleDto(schedules.get(i).getId(),
                        new StudentDto(schedules.get(i).getStudent()),
                        new TutorDto(schedules.get(i).getTutor()),
                        schedules.get(i).getStartTime(),
                        schedules.get(i).getEndTime(),
                        schedules.get(i).getDayOfWeek(),
                        schedules.get(i).getWeekOfYear(),
                        schedules.get(i).getStatus(),
                        schedules.get(i).getScheduleFormat(),
                        schedules.get(i).getAddress());
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
                ScheduleDto scheduleDto = new ScheduleDto(schedules.get(i).getId(),
                        new StudentDto(schedules.get(i).getStudent()),
                        new TutorDto(schedules.get(i).getTutor()),
                        schedules.get(i).getStartTime(),
                        schedules.get(i).getEndTime(),
                        schedules.get(i).getDayOfWeek(),
                        schedules.get(i).getWeekOfYear(),
                        schedules.get(i).getStatus(),
                        schedules.get(i).getScheduleFormat(),
                        schedules.get(i).getAddress());
                scheduleDtos.add(scheduleDto);
            }
        }
        return scheduleDtos;
    }

    @Override
    public List<ScheduleDto> getSchedulesAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (schedules.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < schedules.size(); i++){
                ScheduleDto scheduleDto = new ScheduleDto(schedules.get(i).getId(),
                        new StudentDto(schedules.get(i).getStudent()),
                        new TutorDto(schedules.get(i).getTutor()),
                        schedules.get(i).getStartTime(),
                        schedules.get(i).getEndTime(),
                        schedules.get(i).getDayOfWeek(),
                        schedules.get(i).getWeekOfYear(),
                        schedules.get(i).getStatus(),
                        schedules.get(i).getScheduleFormat(),
                        schedules.get(i).getAddress());
                scheduleDtos.add(scheduleDto);
            }
        }
        return scheduleDtos;
    }

    @Override
    public ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto) {
        Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
        if (existingSchedule == null) {
            throw new RuntimeException("Schedule not found with id: " + id);
        }

        existingSchedule.setStartTime(scheduleDto.getStartTime());
        existingSchedule.setEndTime(scheduleDto.getEndTime());
        existingSchedule.setStatus(scheduleDto.getStatus());
        existingSchedule.setScheduleFormat(scheduleDto.getScheduleFormat());
        existingSchedule.setAddress(scheduleDto.getAddress());

        // cập nhật lại ngày và tuần nếu thay đổi thời gian
        String newDayOfWeek = scheduleDto.getStartTime().getDayOfWeek().toString();
        int year = scheduleDto.getStartTime().getYear();
        int weekOfYearInt = scheduleDto.getStartTime().get(java.time.temporal.WeekFields.of(java.util.Locale.getDefault()).weekOfYear());
        String weekOfYear = String.format("%d-W%02d", year, weekOfYearInt);

        existingSchedule.setDayOfWeek(newDayOfWeek);
        existingSchedule.setWeekOfYear(weekOfYear);

        scheduleRepository.save(existingSchedule);

        return new ScheduleDto(
                existingSchedule.getId(),
                new StudentDto(existingSchedule.getStudent()),
                new TutorDto(existingSchedule.getTutor()),
                existingSchedule.getStartTime(),
                existingSchedule.getEndTime(),
                existingSchedule.getDayOfWeek(),
                existingSchedule.getWeekOfYear(),
                existingSchedule.getStatus(),
                existingSchedule.getScheduleFormat(),
                existingSchedule.getAddress()
        );
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
