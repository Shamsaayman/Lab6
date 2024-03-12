package org.example.lab6.Controller;

import jakarta.validation.Valid;
import org.example.lab6.Api.ApiResponse;
import org.example.lab6.Model.EmployeeSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeSystemController {
    ArrayList<EmployeeSystem>employees= new ArrayList<>();
    @GetMapping("/get")
    public ResponseEntity getEmployee(){
        return ResponseEntity.status(200).body(employees);
    }
    @PostMapping("/add")
    public ResponseEntity createProject(@RequestBody @Valid EmployeeSystem employee, Errors errors){
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee Added"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateProject(@PathVariable @Valid  String id , @RequestBody @Valid EmployeeSystem employee, Errors errors){
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                employees.set(i, employee);
            }
        }
        return ResponseEntity.status(200).body(new ApiResponse("Employee Updated"));
        }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProject(@PathVariable @Valid String id, Errors errors ){
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                employees.remove(i);
                return ResponseEntity.status(200).body(new ApiResponse("Employee Deleted"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee doesn't exist"));
    }



    @GetMapping("/search/{position}")
    public ResponseEntity searchEmployee(@PathVariable String position){
        ArrayList<EmployeeSystem>search= new ArrayList<>();
        if(position.matches("^(supervisor|coordinator)$")){
          for(EmployeeSystem employee: employees){
            if(employee.getPosition().contains(position)){
                search.add(employee);
            }
          }
        return ResponseEntity.status(200).body(search);
        }

       else{
            return ResponseEntity.status(400).body(new ApiResponse("Invalid Position"));
        }
    }



    @GetMapping("/search/{minAge}/{maxAge}")
    public ResponseEntity searchAge(@PathVariable int minAge, @PathVariable int maxAge){
        ArrayList<EmployeeSystem>search= new ArrayList<>();
        if(minAge>25 && maxAge>25){
            for(EmployeeSystem employee: employees){
                if(employee.getAge()<maxAge && employee.getAge()>minAge){
                    search.add(employee);
                }
            }
            return ResponseEntity.status(200).body(search);
        }

        else{
            return ResponseEntity.status(400).body(new ApiResponse("Invalid Age"));
        }
    }

@PutMapping("/leave/{id}")
public ResponseEntity applyAnnualLeave(@PathVariable @Valid String id , Errors errors) {
    if (errors.hasErrors()) {
        String message = errors.getFieldError().getDefaultMessage();
        return ResponseEntity.status(400).body(message);
    }
    for (int i = 0; i < employees.size(); i++) {
        if (employees.get(i).getId().equals(id)) {
            if (employees.get(i).getOnLeave().equals(false) ) {
                if (employees.get(i).getAnnualLeave() >= 1) {
                    employees.get(i).setAnnualLeave(employees.get(i).getAnnualLeave() - 1);
                    employees.get(i).setOnLeave(true);
                } else {
                    return ResponseEntity.status(400).body(new ApiResponse("Employee doesn't have annual days left"));

                }
            } else {
                return ResponseEntity.status(400).body(new ApiResponse("Employee is on leave"));

            }
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("Employee doesn't exist"));

        }

    }

    return ResponseEntity.status(200).body(new ApiResponse("Annual Leave succesful"));

}



    @GetMapping("/search/{id}")
    public ResponseEntity searchProjectTitle(@PathVariable @Valid String id , Errors errors){
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        ArrayList<EmployeeSystem>search= new ArrayList<>();
        for(EmployeeSystem employee: employees){
            if(employee.getAnnualLeave()==0){
                search.add(employee);
            }
        }
        return ResponseEntity.status(200).body(search);

    }


@PutMapping("/promotion/{promoter}/{employee}")
    public ResponseEntity promoteEmployee(@PathVariable @Valid String promoter , @PathVariable @Valid String employee , Errors errors){
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        for (int i = 0; i < employees.size(); i++) {
            for (int j = 0; j <employees.size() ; j++) {
                if(employees.get(i).getId().equals(promoter) && employees.get(j).getId().equals(employee)){
                    if(employees.get(i).getPosition().equals("supervisor")){
                        if(employees.get(j).getAge()>29 && employees.get(j).getOnLeave().equals(false)){
                            employees.get(j).setPosition("supervisor");
                        }
                    }
                }
            }

        }
        return ResponseEntity.status(200).body(new ApiResponse("Promoted!"));

    }

}


