package controllers;

import db.DBHelper;
import db.Seeds;
import models.Employee;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import  static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;


public class EmployeesController {

    public static void main(String[] args) {
        staticFileLocation("/public");
        DepartmentsController departmentsController = new DepartmentsController();
        ManagersController managersController = new ManagersController();
        EngineersController engineersController = new EngineersController();
        Seeds.seedData();

//        http://localhost:4567/employees
        get("/employees", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Employee> employees = DBHelper.getAll(Employee.class);
            model.put("template", "templates/employees/index.vtl");
            model.put("employees", employees);
            return new ModelAndView(model, "templates/layout.vtl");
        },new VelocityTemplateEngine());


    }
}
