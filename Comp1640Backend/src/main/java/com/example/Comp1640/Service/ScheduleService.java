package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.ScheduleDto;
import com.example.Comp1640.Entity.Schedule;

import java.util.List;

public interface ScheduleService {

    ScheduleDto saveSchedule(ScheduleDto scheduleDto);
    List<Schedule> getStudentSchedules(Long studentId);
    List<Schedule> getTutorSchedules(Long tutorId);

}
