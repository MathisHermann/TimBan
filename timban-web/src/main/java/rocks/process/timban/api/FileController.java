package rocks.process.timban.api;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * Editor: Mathis
 * This class is found on
 * https://kreepcode.com/spring-boot-and-angular-open-pdf-link-browser/
 * and modified to our needs.
 */

@RestController
@CrossOrigin
public class FileController {

    @RequestMapping("/reports/{userName}")
    public ResponseEntity<Resource> viewPdf(@PathVariable String userName) throws Exception {

        File file = new File("timban-web/src/main/resources/reports/" + userName + "/TimeReport.pdf");

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=TimeReport" + userName + ".pdf");

        Path path = Paths.get(file.getAbsolutePath());

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}