package tn.esprit.spring.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseRestControllerTest {

    @Mock
    private ICourseRepository courseRepository; // Mock the dependency

    @InjectMocks
    private CourseServicesImpl courseService; //
    private Course testCourse;
    private List<Course> testCourses;


    @Test
    void addCourse() {
        // Arrange
        Course inputCourse = new Course(null, 3,TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 299.99f, 60, null);
        Course savedCourse = new Course(1L, 3, TypeCourse.COLLECTIVE_ADULT, Support.SKI, 299.99f, 60, null);

        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        // Act
        Course result = courseService.addCourse(inputCourse);

        // Assert
        assertThat(result.getNumCourse()).isEqualTo(1L);
        verify(courseRepository).save(inputCourse);
    }



    @Test
    void getAllCourses() {
        // Arrange
        List<Course> expectedCourses = List.of(
                new Course(1L, 3, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 299.99f, 60, null),
                new Course(2L, 2, TypeCourse.INDIVIDUAL, Support.SKI, 199.99f, 45, null)
        );

        when(courseRepository.findAll()).thenReturn(expectedCourses);

        // Act
        List<Course> result = courseService.retrieveAllCourses();

        // Assert
        assertThat(result.containsAll((expectedCourses)));

    }

    @Test
    void updateCourse() {
        Course nonExistingCourse = new Course(1L, 3, TypeCourse.COLLECTIVE_CHILDREN, Support.SNOWBOARD, 299.99f, 60, null);
        when(courseRepository.existsById(1L)).thenReturn(false);
        // Verify repository interactions
        verify(courseRepository, never()).save(any());
    }

    @Test
    void getById() {
        Long courseId = 1L;
        Course expectedCourse = new Course(courseId, 2, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 199.99f, 45, null);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(expectedCourse));

        // Act
        Course result = courseService.retrieveCourse(courseId);

        // Assert
        assertThat(result).isEqualTo(expectedCourse);
    }
}