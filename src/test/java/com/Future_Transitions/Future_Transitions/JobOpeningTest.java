//package com.Future_Transitions.Future_Transitions;
//
//
//import com.Future_Transitions.Future_Transitions.model.JobOpening;
//import com.Future_Transitions.Future_Transitions.model.Role;
//import com.Future_Transitions.Future_Transitions.model.User;
//import com.Future_Transitions.Future_Transitions.repository.JobOpeningRepository;
//import com.Future_Transitions.Future_Transitions.service.Imp.JobOpeningServiceImp;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.access.AccessDeniedException;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class JobOpeningTest {
//
//
//    @Mock
//    private JobOpeningRepository jobOpeningRepository;
//
//    @InjectMocks
//    private JobOpeningServiceImp jobOpeningServiceImp;
//    private JobOpening testjobOpening;
//    private User admin;
//
//
//    @BeforeEach
//    void setup() {
//
//        admin = new User();
//        admin.setRole(Role.ADMIN);
//
//        testjobOpening = new JobOpening();
//        testjobOpening.setTitle("IT");
//        testjobOpening.setDescription("Front-end Developer");
//        testjobOpening.setRequirements("Diploma in IT");
//
//    }
//    @Test
//    void testcreateJobopening_ShouldReturnSavedJobopening(){
//        when(jobOpeningRepository.save(any(JobOpening.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        JobOpening result = jobOpeningServiceImp.createJob(testjobOpening , admin);
//
//        verify(jobOpeningRepository, times(1)).save(any(JobOpening.class));
//
//        // the expected output as from input
//        assertNotNull(result);
//        assertEquals("IT", result.getTitle());
//        assertEquals("Front-end Developer", result.getDescription());
//        assertEquals("Diploma in IT", result.getRequirements());
//        assertEquals(admin, result.getCreatedBy());
//        assertNotNull(result.getPostedDate());
//
//    }
//    @Test
//    void testCreateJobOpening_NonoAdminRole_ShouldThrowAccessDenied(){
//
//        User noAdmin= new User();
//        noAdmin.setId(1L);
//        noAdmin.setRole(Role.USER);
//
//        assertThrows(
//                AccessDeniedException.class,
//                () -> jobOpeningServiceImp.createJob(testjobOpening, noAdmin),
//                "Only admins can create job openings."
//        );
//
//        verify(jobOpeningRepository, never()).save(any());
//    }
//
//    @Test
//    void testDeleteJobOpening_ShouldDelete(){
//        testjobOpening.setId(1L);
//
//        when(jobOpeningRepository.findById(1L)).thenReturn(Optional.of(testjobOpening));
//
//      jobOpeningServiceImp.deleteJob(1L);
//
//      verify(jobOpeningRepository).delete(testjobOpening);
//    }
//    @Test
//    void testDeleteJobOpening_NotFound_ShouldThrowException(){
//        when(jobOpeningRepository.findById(2L)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () ->
//            jobOpeningServiceImp.deleteJob(2l));
//
//        verify(jobOpeningRepository, never()).delete(any());
//        }
//}
