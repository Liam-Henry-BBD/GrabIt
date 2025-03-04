//package com.grabit.app.controllerTests;
//
//import com.grabit.app.controller.TaskCollaboratorController;
//import com.grabit.app.model.TaskCollaborator;
//import com.grabit.app.service.TaskCollaboratorService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class TaskCollaboratorControllerTests {
//
//    @Mock
//    private TaskCollaboratorService taskCollaboratorService;
//
//    @InjectMocks
//    private TaskCollaboratorController taskCollaboratorController;
//    private TaskCollaborator taskCollaborator;
//
//    @BeforeEach
//    public void setUp() {
//        taskCollaborator = new TaskCollaborator();
//        List.of(taskCollaborator);
//    }
//
//    @Test
//    public void testGetAllTaskCollaborators() {
//        taskCollaboratorController.getAllTaskCollaborators();
//
//        verify(taskCollaboratorService).getAllTaskCollaborators();
//    }
//
//    @Test
//    public void testGetTaskCollaboratorByID() {
//
//        ResponseEntity<TaskCollaborator> response = taskCollaboratorController.getTaskCollaboratorByID(55);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        verify(taskCollaboratorService).getTaskCollaboratorByID(55);
//    }
//
//    @Test
//    public void testDeactivateTaskCollaboratorByID() {
//        ResponseEntity<Void> response = taskCollaboratorController.deactivateTaskCollaboratorByID(1);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
//        verify(taskCollaboratorService).deactivateTaskCollaboratorByID(1);
//    }
//
//    @Test
//    public void testActivateTaskCollaboratorByID() {
//        ResponseEntity<Void> response = taskCollaboratorController.activateTaskCollaboratorByID(1);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        verify(taskCollaboratorService).activateTaskCollaboratorByID(1);
//    }
//
//    @Test
//    public void testGetCollaboratorByTaskID() {
//        ResponseEntity<List<TaskCollaborator>> response = taskCollaboratorController.getCollaboratorByTaskID(1);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        verify(taskCollaboratorService).getCollaboratorByTaskID(1);
//    }
//}
