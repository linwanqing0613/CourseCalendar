package com.example.coursecalendar.service;

import com.example.coursecalendar.dao.ClassScheduleRepository;
import com.example.coursecalendar.dto.ClassScheduleSpecification;
import com.example.coursecalendar.model.ClassSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ClassScheduleService {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<ClassSchedule> getAllClassSchedules() {
        return classScheduleRepository.findAll();
    }

    public ClassSchedule getClassScheduleOptionalById(Long id) {
        Optional<ClassSchedule> classScheduleOptional = classScheduleRepository.findById(id);
        return classScheduleOptional.orElse(null);
    }

    public ClassSchedule createClassSchedule(ClassSchedule classSchedule) {
        return classScheduleRepository.save(classSchedule);
    }

    public ClassSchedule updateClassSchedule(Long id, ClassSchedule classScheduleDetails) {
        Optional<ClassSchedule> classScheduleOptional = classScheduleRepository.findById(id);
        if (classScheduleOptional.isPresent()) {
            ClassSchedule classSchedule = classScheduleOptional.get();
            classSchedule.setClassName(classScheduleDetails.getClassName());
            classSchedule.setCourse(classScheduleDetails.getCourse());
            classSchedule.setTeacher(classScheduleDetails.getTeacher());
            classSchedule.setScheduleDate(classScheduleDetails.getScheduleDate());
            classSchedule.setStartTime(classScheduleDetails.getStartTime());
            classSchedule.setEndTime(classScheduleDetails.getEndTime());
            classSchedule.setClassroom(classScheduleDetails.getClassroom());
            classSchedule.setUpdatedAt(classScheduleDetails.getUpdatedAt());
            return classScheduleRepository.save(classSchedule);
        }
        return null;
    }

    public boolean deleteClassSchedule(Long id) {
        if (classScheduleRepository.existsById(id)) {
            classScheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ClassSchedule> searchClassSchedules(String teacherName, String courseName, LocalDate scheduleDate) {
        Specification<ClassSchedule> spec = ClassScheduleSpecification.searchByCriteria(teacherName, courseName, scheduleDate);
        return classScheduleRepository.findAll(spec);
    }
}
