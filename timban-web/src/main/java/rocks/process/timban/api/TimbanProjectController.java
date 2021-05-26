package rocks.process.timban.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rocks.process.timban.business.service.TimbanProjectService;
import rocks.process.timban.data.domain.TimbanProject;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.data.repository.TimbanProjectRepository;
import rocks.process.timban.tools.LogToFile;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 19.05.2021
 */

@RestController
@RequestMapping(path = "/api/projects")
public class TimbanProjectController {

    @Autowired
    TimbanProjectRepository timbanProjectRepository;

    @Autowired
    TimbanProjectService timbanProjectService;

    @GetMapping
    public List<TimbanProject> getProjects() {
        return timbanProjectRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public List<TimbanUser> getProjects(@PathVariable Long id) {
        try {
            return timbanProjectService.getAttachedUsers(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<TimbanProject> createProject(@RequestBody TimbanProject timbanProject) {
        try {
            if (timbanProject.getProjectName().length() == 0)
                throw new Exception("Project Name cannot be empty!");
            timbanProject.setCreatedAt(Instant.now());
            TimbanProject updatedTimbanProject = timbanProjectService.save(timbanProject);
            LogToFile.logUser("Project created; Project name: " + updatedTimbanProject.getProjectName() + "; Duedate: "
                    + updatedTimbanProject.getDueDate().toString());
            return new ResponseEntity<>(updatedTimbanProject, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(timbanProject, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TimbanProject> updateProject(@RequestBody TimbanProject timbanProject, @PathVariable Long id) throws Exception {
        try {
            if (timbanProject.getProjectName().length() == 0)
                throw new Exception("Project Name cannot be empty!");

            return new ResponseEntity<>(timbanProjectService.update(timbanProject, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(timbanProject, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path = "/{id}")
    public void deleteProject(@PathVariable Long id) {
        timbanProjectService.delete(id);
    }

}
