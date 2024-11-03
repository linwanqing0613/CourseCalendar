package com.example.coursecalendar.controller;


import com.example.coursecalendar.dto.ClassScheduleSpecification;
import com.example.coursecalendar.model.ClassSchedule;
import com.example.coursecalendar.service.ClassScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/class-schedule")
public class ClassScheduleController {

    @Autowired
    private ClassScheduleService classScheduleService;
    private static final Logger logger = Logger.getLogger(ClassScheduleController.class.getName());
    @GetMapping
    public ResponseEntity<List<ClassSchedule>> getAllClassSchedules() {
        List<ClassSchedule> classScheduleList = classScheduleService.getAllClassSchedules();
        if(!classScheduleList.isEmpty()){
            return ResponseEntity.ok(classScheduleList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClassSchedule> getClassScheduleOptionalById(@PathVariable Long id) {
        ClassSchedule classSchedule = classScheduleService.getClassScheduleOptionalById(id);
        if (classSchedule != null) {
            return ResponseEntity.ok(classSchedule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ClassSchedule> createClassSchedule(@RequestBody ClassSchedule classSchedule) {
        ClassSchedule saveCourse = classScheduleService.createClassSchedule(classSchedule);
        return new ResponseEntity<>(saveCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSchedule> updateClassSchedule(@PathVariable Long id, @RequestBody ClassSchedule classScheduleDetails) {
        ClassSchedule updateClassSchedule = classScheduleService.updateClassSchedule(id, classScheduleDetails);
        if(updateClassSchedule != null){
            return ResponseEntity.ok(updateClassSchedule);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSchedule(@PathVariable Long id) {
        if (classScheduleService.deleteClassSchedule(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public List<ClassSchedule> searchClassSchedules(
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate scheduleDate) {
        return classScheduleService.searchClassSchedules(teacherName, courseName, scheduleDate);
    }
}
