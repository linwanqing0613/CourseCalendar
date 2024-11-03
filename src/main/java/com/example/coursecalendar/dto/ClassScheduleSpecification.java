package com.example.coursecalendar.dto;

import com.example.coursecalendar.model.ClassSchedule;
import com.example.coursecalendar.model.Course;
import com.example.coursecalendar.model.Teacher;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ClassScheduleSpecification {
    public static Specification<ClassSchedule> searchByCriteria(String teacherName, String courseName, LocalDate scheduleDate) {
        return (root,  query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (teacherName != null && !teacherName.isEmpty()) {
                Join<ClassSchedule, Teacher> teacherJoin = root.join("teacher");
                Expression<String> fullName = criteriaBuilder.concat(
                        criteriaBuilder.concat(teacherJoin.get("firstName"), " "),
                        teacherJoin.get("lastName")
                );

                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(fullName, "%" + teacherName + "%"));
            }

            if (courseName != null && !courseName.isEmpty()) {
                Join<ClassSchedule, Course> courseJoin = root.join("course");
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(courseJoin.get("courseName"), "%" + courseName + "%"));
            }

            if (scheduleDate != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("scheduleDate"), scheduleDate));
            }

            return predicate;
        };
    }
}
