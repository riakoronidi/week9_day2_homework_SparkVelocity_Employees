package controllers;

import db.DBHelper;
import models.Department;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {
    public DepartmentsController(){
        this.setupEndpoint();
    }

    private void setupEndpoint() {

//        http://localhost:4567/departments
        get("/departments", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/departments/index.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

//        http://localhost:4567/departments/new
        get("/departments/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/departments", (req, res) -> {
            String title = req.queryParams("title");
            Department department = new Department(title);
            DBHelper.save(department);
            res.redirect("/departments");
            return null;
        }, new VelocityTemplateEngine());


        post("/departments/:id/delete", (req, res) -> {
            int departmentId = Integer.parseInt(req.queryParams(":id"));
            Department department = DBHelper.find(departmentId, Department.class);
            DBHelper.delete(department);
            res.redirect("/departments");
            return null;
        },new VelocityTemplateEngine());


        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });
    }
}
