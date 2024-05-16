package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.SchedulerRequestDto;
import com.sparta.scheduler.dto.SchedulerResponseDto;
import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.repository.SchedulerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SchedulerService {
    //jdbc template 주입
    private final JdbcTemplate jdbcTemplate;

    public SchedulerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*일정 등록*/
    public SchedulerResponseDto createSchedule(SchedulerRequestDto requestDto) {

        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // Repository에서 저장
        SchedulerRepository schedulerRepository = new SchedulerRepository(jdbcTemplate);
        schedulerRepository.save(schedule);

        // Entity -> ResponseDto
        SchedulerResponseDto schedulerResponseDto = new SchedulerResponseDto(schedule);

        return schedulerResponseDto;
    }

    /*일정 조회*/
    public List<SchedulerResponseDto> getSchedules() {
        SchedulerRepository schedulerRepository = new SchedulerRepository(jdbcTemplate);
        return schedulerRepository.findAll();
    }

    /*일정 수정*/
    public Long updateSchedule(Long id, SchedulerRequestDto requestDto) {
        SchedulerRepository schedulerRepository = new SchedulerRepository(jdbcTemplate);
        // 해당 일정가 DB에 존재하는지 확인
        Schedule schedule = schedulerRepository.findById(id);
        if (schedule != null) {
            schedulerRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    /*일정 삭제*/
    public Long deleteSchedule(Long id) {
        SchedulerRepository schedulerRepository = new SchedulerRepository(jdbcTemplate);
        // 해당 일정가 DB에 존재하는지 확인
        Schedule schedule = schedulerRepository.findById(id);
        if (schedule != null) {
            schedulerRepository.delete(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정는 존재하지 않습니다.");
        }
    }


}
