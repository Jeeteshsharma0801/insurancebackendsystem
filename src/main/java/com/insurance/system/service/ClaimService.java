package com.insurance.system.service;

import com.insurance.system.entity.Claim;
import com.insurance.system.exception.InsuranceException;
import com.insurance.system.repository.ClaimRepository;
import com.insurance.system.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private PolicyRepository policyRepository;

    private final String uploadDir = "D:/insurance-system/uploads";

    public Claim create(Claim claim, Long policyId) {
        claim.setPolicy(policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found")));
        claim.setPolicyHolder(claim.getPolicy().getPolicyHolder());
        return claimRepository.save(claim);
    }

    public String uploadFile(Long claimId, MultipartFile file) throws IOException {
//        Claim claim = claimRepository.findById(claimId)
//                .orElseThrow(() -> new RuntimeException("Claim not found"));

        Optional<Claim > claim = claimRepository.findById(claimId);

        if(claim.isEmpty()){
            throw new InsuranceException(601,"Claim not found");
        }

        Claim claim1 = claim.get();
        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = claimId + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        file.transferTo(new File(filePath));
        claim1.setFilePath(filePath);
        claimRepository.save(claim1);

        return filePath;
    }

    public Claim getById(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    public List<Claim> getAll() {
        return claimRepository.findAll();
    }
}
