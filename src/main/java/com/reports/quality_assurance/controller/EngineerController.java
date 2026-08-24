package com.reports.quality_assurance.controller;

import com.reports.quality_assurance.dtos.QualityAssuranceEngineers.RegisterQualityAssuranceEngineerDTO;
import com.reports.quality_assurance.dtos.QualityAssuranceEngineers.ViewEngineerDTO;
import com.reports.quality_assurance.dtos.QualityAssuranceEngineers.ViewNewEngineerDTO;
import com.reports.quality_assurance.service.QualityAssuranceEngineerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle requests regarding the Engineer
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@RestController
@RequestMapping("/v1/engineer")
@RequiredArgsConstructor
public class EngineerController {

    private final QualityAssuranceEngineerService engineerService;

    @PostMapping("/register")
    public ResponseEntity<ViewNewEngineerDTO> register(final @RequestBody RegisterQualityAssuranceEngineerDTO engineerData)
    {
        final ViewNewEngineerDTO response;
        response = engineerService.registerNewEngineer(engineerData);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getEngineers")
    public List<ViewEngineerDTO> getAllEngineers()
    {
        final List<ViewEngineerDTO> engineersList;
        engineersList = engineerService.getAllEngineers();

        return engineersList;
    }

    @GetMapping("/byDateCreated")
    public List<ViewEngineerDTO> getEngineersByDate(final @RequestParam(name = "startDate") String startDate,
                                         final @RequestParam(name = "endDate") String endDate) {
        final List<ViewEngineerDTO> foundEngineers;
        foundEngineers = engineerService.getEngineersInRange(startDate, endDate);

        return foundEngineers;
    }
}
