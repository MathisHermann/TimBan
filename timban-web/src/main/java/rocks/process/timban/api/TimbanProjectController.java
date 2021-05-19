package rocks.process.timban.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rocks.process.timban.business.service.TimbanProjectService;
import rocks.process.timban.data.domain.TimbanProject;
import rocks.process.timban.data.repository.TimbanProjectRepository;

import javax.validation.Valid;
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
    public TimbanProject getProjects(@PathVariable Long id) throws Exception {
        try {
            return (TimbanProject) timbanProjectRepository.findById(id).get();
        } catch (Exception e) {
           return null;
        }
    }

    @PostMapping
    public TimbanProject createProject(@Valid TimbanProject timbanProject) throws Exception {
        return timbanProjectService.save(timbanProject);
    }

    @PutMapping(path = "/{id}")
    public TimbanProject updateProject(@Valid TimbanProject timbanProject, @PathVariable Long id) {
        return timbanProjectService.update(timbanProject, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProject(@PathVariable Long id) {
        timbanProjectService.delete(id);
    }

}
