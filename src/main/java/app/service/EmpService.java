package app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import app.dto.EmpDto;
import app.entity.Dept;
import app.entity.Emp;
import app.repository.DeptRepository;
import app.repository.EmpRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmpService {

    private final EmpRepository empRepository;
    private final DeptRepository deptRepository;
	


    // 모든 직원 조회
    public List<EmpDto> findAllEmps() {
        return empRepository.findAll().stream()
                .map(Emp::toDto)
                .collect(Collectors.toList());
    }

    // 직원 ID로 조회
    public EmpDto findEmpById(int empno) {
        return empRepository.findById(empno)
                .map(Emp::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with empno: " + empno));
    }

    // 직원 추가
    public EmpDto addEmp(EmpDto empDto) {
        Dept dept = deptRepository.findById(empDto.getDeptno())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with deptno: " + empDto.getDeptno()));

        Emp emp = empDto.toEntity(dept);
        Emp savedEmp = empRepository.save(emp);
        return savedEmp.toDto();
    }

    // 직원 수정
    public EmpDto updateEmp(int empno, EmpDto empDto) {
        Emp emp = empRepository.findById(empno)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with empno: " + empno));

        Dept dept = deptRepository.findById(empDto.getDeptno())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with deptno: " + empDto.getDeptno()));

        emp = Emp.builder()
                .empno(empno)
                .ename(empDto.getEname())
                .job(empDto.getJob())
                .mgr(empDto.getMgr())
                .hiredate(empDto.getHiredate())
                .sal(empDto.getSal())
                .comm(empDto.getComm())
                .dept(dept)
                .build();

        Emp updatedEmp = empRepository.save(emp);
        return updatedEmp.toDto();
    }

    // 직원 삭제
    public void deleteEmp(int empno) {
        if (!empRepository.existsById(empno)) {
            throw new EntityNotFoundException("Employee not found with empno: " + empno);
        }
        empRepository.deleteById(empno);
    }
    
}
