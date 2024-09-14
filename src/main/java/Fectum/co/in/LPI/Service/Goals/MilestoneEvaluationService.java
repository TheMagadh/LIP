package Fectum.co.in.LPI.Service.Goals;

import Fectum.co.in.LPI.DTO.MilestoneEvaluationRequest;
import Fectum.co.in.LPI.Entity.Goals.Milestone;
import Fectum.co.in.LPI.Entity.Goals.MilestoneEvaluation;
import Fectum.co.in.LPI.Repository.Goals.MilestoneEvaluationRepository;
import Fectum.co.in.LPI.Repository.Goals.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MilestoneEvaluationService {

    @Autowired
    private MilestoneEvaluationRepository milestoneEvaluationRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private EvaluateScore eva;


    public List<MilestoneEvaluation> getAllMilestoneEvaluations() {
        return milestoneEvaluationRepository.findAll();
    }

    public Optional<MilestoneEvaluation> getMilestoneEvaluationById(Long evaluationId) {
        return milestoneEvaluationRepository.findById(evaluationId);
    }

    public ResponseEntity<?> saveMilestoneEvaluation(MilestoneEvaluationRequest request) {
        Optional<Milestone> optionalMilestone = milestoneRepository.findById(request.getMilestoneId());

        if (optionalMilestone.isEmpty()) {
            return ResponseEntity.status(400).body("Milestone with ID " + request.getMilestoneId() + " is not present.");
        } else if (!optionalMilestone.get().getEvaluations().isEmpty()) {
            return ResponseEntity.status(400).body("Milestone with ID " + request.getMilestoneId() + " is already Evaluated.");
        }

        MilestoneEvaluation milestoneEvaluation = new MilestoneEvaluation();
        milestoneEvaluation.setMilestone(optionalMilestone.get());
        milestoneEvaluation.setEvaluationDate(request.getEvaluationDate() != null ? request.getEvaluationDate() : LocalDateTime.now());
        milestoneEvaluation.setFeedback(request.getFeedback());

        milestoneEvaluation.setProgressScore(eva.milestoneScore(optionalMilestone.get())); // You can also add custom score calculation here

        MilestoneEvaluation savedMilestoneEvaluation = milestoneEvaluationRepository.save(milestoneEvaluation);
        return ResponseEntity.ok(savedMilestoneEvaluation);
    }

    public void deleteMilestoneEvaluation(Long evaluationId) {
        milestoneEvaluationRepository.deleteById(evaluationId);
    }
}
