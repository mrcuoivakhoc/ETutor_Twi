package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.ScheduleDto;
import com.example.Comp1640.Entity.Schedule;

import java.util.List;

public interface ScheduleService {

    ScheduleDto saveSchedule(ScheduleDto scheduleDto);
    List<ScheduleDto> getStudentSchedules(Long studentId);
    List<ScheduleDto> getTutorSchedules(Long tutorId);
    ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto);
    void deleteSchedule(Long id);

}
