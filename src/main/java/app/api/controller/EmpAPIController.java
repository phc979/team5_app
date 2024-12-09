package app.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.dto.EmpDto;
import app.service.EmpService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmpAPIController {

    private final EmpService empService;

    // 모든 직원 조회
    @GetMapping("/emps")
    public ResponseEntity<List<EmpDto>> getAllEmps() {
        return ResponseEntity.ok(empService.findAllEmps());
    }

    // 직원 ID로 조회
    @GetMapping("/emp/{empno}")
    public ResponseEntity<EmpDto> getEmpById(@PathVariable int empno) {
        return ResponseEntity.ok(empService.findEmpById(empno));
    }

    // 직원 추가
    @PostMapping("/emp")
    public ResponseEntity<EmpDto> addEmp(@RequestBody EmpDto empDto) {
        EmpDto newEmp = empService.addEmp(empDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmp);
    }

    // 직원 수정
    @PutMapping("/emp/{empno}")
    public ResponseEntity<EmpDto> updateEmp(@PathVariable int empno, @RequestBody EmpDto empDto) {
        EmpDto updatedEmp = empService.updateEmp(empno, empDto);
        return ResponseEntity.ok(updatedEmp);
    }
    
    @DeleteMapping("/emp/{empno}")
    public ResponseEntity<EmpDto> deleteEmp(@PathVariable int empno) {
        try {
            // 삭제 전에 직원 정보를 조회
            EmpDto deletedEmp = empService.findEmpById(empno);
            
            // 직원 삭제
            empService.deleteEmp(empno);
            
            // 삭제된 직원 정보 반환
            return ResponseEntity.ok(deletedEmp);
        } catch (EntityNotFoundException e) {
            // 직원이 존재하지 않을 경우 404 상태 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
}
