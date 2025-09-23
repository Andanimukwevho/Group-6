package com.Future_Transitions.Future_Transitions;


import com.Future_Transitions.Future_Transitions.model.Application;
import com.Future_Transitions.Future_Transitions.model.ApplicationStatus;
import com.Future_Transitions.Future_Transitions.repository.ApplicationRepository;
import com.Future_Transitions.Future_Transitions.service.Imp.ApplicationImp;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ApplicationImpTest {


    @Mock
   private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationImp applicationImp;

    private Application testapplication;

    @BeforeEach
    void setUp(){

        testapplication = new Application();
        testapplication.setId(1L);
        testapplication.setCvPath("cv.pdf");
        testapplication.setCoverLetterPath("cover.pdf");
        testapplication.setIdDocumentPath("id.pdf");
        testapplication.setStatus(ApplicationStatus.PENDING);
    }

    @Test
    void testCreateApplication_shouldReturnSavedApplication(){
        when(applicationRepository.save(testapplication)).thenReturn(testapplication);

        Application result = applicationImp.createApplication(testapplication);

        assertEquals(testapplication , result);

        verify(applicationRepository , times(1)).save(testapplication);
    }

    @Test
    void testGetApplicationById_ShouldReturnOptionalApplication(){
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(testapplication));

        Optional<Application> result= applicationImp.getApplicationById(1L);

        assertTrue(result.isPresent());
        assertEquals(testapplication, result.get());
        verify(applicationRepository, times(1)).findById(1L);
    }
    @Test
    void testUpdateApplication_ShouldReturnUpdatedApplication(){
        Application updatedApplication = new Application();
        updatedApplication.setStatus(ApplicationStatus.APPROVED);
        updatedApplication.setCvPath("new_cv.pdf");
        updatedApplication.setCoverLetterPath("new_cover.pdf");
        updatedApplication.setIdDocumentPath("new_id.pdf");

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(testapplication));
        when(applicationRepository.save(any(Application.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Application result = applicationImp.updateApplication(1L, updatedApplication);

        assertEquals(ApplicationStatus.APPROVED, result.getStatus());
        assertEquals("new_cv.pdf", result.getCvPath());
        assertEquals("new_cover.pdf", result.getCoverLetterPath());
        assertEquals("new_id.pdf", result.getIdDocumentPath());

        verify(applicationRepository).findById(1L);
        verify(applicationRepository).save(any(Application.class));

}
    @Test
    void testUpdateApplication_NotFound_ShouldThrowException(){
        Application updateApplication = new Application();
        when(applicationRepository.findById(2l)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            applicationImp.updateApplication(2L , updateApplication);
        });

        verify(applicationRepository).findById(2l);
        verify(applicationRepository, never()).save(any());

    }
    @Test
    void testDeleteApplication_ShouldDelete(){
      when(applicationRepository.existsById(1L)).thenReturn(true);

      applicationImp.deleteApplication(1L);

      verify(applicationRepository).deleteById(1L);
    }
    @Test
    void testDeleteApplication_NotFound_ShouldThrowException(){
        when(applicationRepository.existsById(2L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () ->{
            applicationImp.deleteApplication(2L);
        });

        verify(applicationRepository, never()).deleteById(2L);

    }
}

