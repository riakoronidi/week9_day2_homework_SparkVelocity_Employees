package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import spark.template.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;


public class EngineersController {

    public EngineersController(){
        this.setupEndpoints();
    }

    private void setupEndpoints(){

//        http://localhost:4567/engineers
        get("/engineers", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("template", "templates/engineers/index.vtl");
            model.put("engineers", engineers);
            return new ModelAndView(model, "templates/layout.vtl");
        },new VelocityTemplateEngine());


//        http://localhost:4567/engineers/new
        get("/engineers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/engineers/create.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        },new VelocityTemplateEngine());


        post("/engineers", (req, res) -> {
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            Engineer engineer = new Engineer(firstName, lastName, salary, department);
            DBHelper.save(engineer);
            res.redirect("/engineers");
            return null;
        },new VelocityTemplateEngine());

    }

}
