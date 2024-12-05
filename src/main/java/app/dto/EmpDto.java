package app.dto;


import java.time.LocalDate;

import app.entity.Dept;
import app.entity.Emp;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EmpDto {
	
    private int empno;
    private String ename;
    private String job;
    private Integer mgr;
    private LocalDate hiredate;
    private Double sal;
    private Double comm;
    private int deptno;
	
    @Builder
    public EmpDto(int empno, String ename, String job, Integer mgr, LocalDate hiredate, Double sal, Double comm, int deptno) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }
	
    public Emp toEntity(Dept dept) {
        return Emp.builder()
                .empno(empno)
                .ename(ename)
                .job(job)
                .mgr(mgr)
                .hiredate(hiredate)
                .sal(sal)
                .comm(comm)
                .dept(dept)
                .build();
    }
    
}
