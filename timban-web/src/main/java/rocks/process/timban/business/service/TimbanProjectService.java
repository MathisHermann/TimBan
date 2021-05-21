package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.process.timban.data.domain.TimbanProject;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.data.repository.TimbanProjectRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimbanProjectService {

    @Autowired
    TimbanProjectRepository timbanProjectRepository;

    @Autowired
    TimbanTimeRecordService timbanTimeRecordService;

    @Autowired
    TimbanUserService timbanUserService;

    public TimbanProject save(TimbanProject timbanProject) throws Exception {
        try {
            List<TimbanProject> allProjects = timbanProjectRepository.findAll();
            for (TimbanProject tbp : allProjects) {
                if (tbp.getProjectName().equals(timbanProject.getProjectName()))
                    return null;
            }
            return timbanProjectRepository.save(timbanProject);
        } catch (Exception e) {
            return null;
        }
    }

    public TimbanProject update(TimbanProject timbanProject, Long id) {
        try {
            TimbanProject updatedTimbanProject = timbanProjectRepository.findById(id).get();
            updatedTimbanProject.setProjectName(timbanProject.getProjectName());
            return timbanProjectRepository.save(updatedTimbanProject);
        } catch (Exception e) {
            return null;
        }
    }

    public void delete(Long id) {
        timbanProjectRepository.deleteById(id);
    }

    public List<TimbanProject> getAllTimbanProjects() {
        return timbanProjectRepository.findAll();
    }

    public List<TimbanUser> getAttachedUsers(Long projectId) {
        List<TimbanTimeRecord> allTimeRecords = timbanTimeRecordService.getTimeRecordByProjectId(projectId);
        List<TimbanUser> allUsersByProject = new ArrayList<>();

        for (TimbanTimeRecord ttr : allTimeRecords) {

            if (timbanUserService.getUserById(ttr.getUserId()).isPresent()) {
                TimbanUser timbanUser = timbanUserService.getUserById(ttr.getUserId()).get();

                if (!allUsersByProject.contains(timbanUser))
                    allUsersByProject.add(timbanUser);
            }
        }
        return allUsersByProject;
    }
}
