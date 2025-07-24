/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import model.personnel.PersonnelDAO;
import utils.Helper;

/**
 * Lớp CreatePersonnelServlet dùng để xử lý việc tạo mới nhân sự trong hệ thống quản lý.
 * 
 * Chịu trách nhiệm tiếp nhận dữ liệu từ form tạo nhân sự, kiểm tra tính hợp lệ,
 * kiểm tra trùng lặp email và số điện thoại, sinh mã nhân sự tự động,
 * thực hiện lưu thông tin nhân sự vào cơ sở dữ liệu,
 * và trả về thông báo thành công hoặc thất bại cho người dùng.
 * 
 * Lớp này được sử dụng bởi tầng Controller trong mô hình MVC,
 * xử lý các yêu cầu POST gửi từ giao diện quản trị viên khi thêm nhân sự mới.
 * 
 * Ví dụ: khi admin nhập thông tin nhân viên mới và gửi form,
 * Servlet sẽ kiểm tra dữ liệu, gọi DAO lưu dữ liệu, 
 * và chuyển hướng về trang danh sách nhân sự với thông báo phù hợp.
 * 
 * @author HuyDV
 * @version 1.0
 */

@WebServlet(name="CreatePersonnelServlet", urlPatterns={"/admin/createpersonnel"})
public class CreatePersonnelServlet extends HttpServlet {
   
      @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
       String action = request.getParameter("action");
       String message="";
       String type= "fail";
       
       PersonnelDAO personnelDAO = new PersonnelDAO();
       if(action.equalsIgnoreCase("create")){
       String xfirstname = request.getParameter("firstname");
       String xlastname = request.getParameter("lastname");
       String xbirthday = request.getParameter("birthday");
       String xaddress = request.getParameter("address");
       String xgender = request.getParameter("gender");
       String xemail = request.getParameter("email");
       String xphone = request.getParameter("phone");
       String xrole = request.getParameter("role");
       String xavatar = request.getParameter("avatar");
       String xqualification = request.getParameter("qualification");
       String xspecialization = request.getParameter("specialization");
       String xachievement = request.getParameter("achievements");
       String xteaching_years = request.getParameter("teaching_years");
       String xcv_file = request.getParameter("cv_file");
       int role = Integer.parseInt(xrole);
       int gender = Integer.parseInt(xgender);
       int teaching_years = Integer.parseInt(xteaching_years);
       String id =generateId(role);

       if(!personnelDAO.checkPersonnelPhone(xphone)&&!personnelDAO.checkPersonnelEmail(xemail)){
            personnelDAO.insertPersonnel(id.trim(), Helper.formatName(xfirstname.trim()) ,Helper.formatName(xlastname.trim()) , gender, xbirthday.trim(), xaddress.trim(), xemail.trim(), xphone.trim(), role, xavatar.trim(),
                    xqualification.trim(),xspecialization.trim(),xachievement.trim(),teaching_years,xcv_file.trim());
       message="Thêm nhân viên thành công";
       type = "success" ;
       }else if(personnelDAO.checkPersonnelPhone(xphone)&&personnelDAO.checkPersonnelEmail(xemail)){
         message="Thêm nhân viên thất bại!Trùng dữ liệu email và số điện thoại";

       }else if(personnelDAO.checkPersonnelPhone(xphone)){
        message="Thêm nhân viên thất bại!Trùng dữ liệu số điện thoại ";

       }else if(personnelDAO.checkPersonnelEmail(xemail)){
        message="Thêm nhân viên thất bại!Trùng dữ liệu email ";

       }
           session.setAttribute("firstname", xfirstname);
           session.setAttribute("lastname", xlastname);
           session.setAttribute("birthday", xbirthday);
           session.setAttribute("address", xaddress);
           session.setAttribute("gender", xgender);
           session.setAttribute("email", xemail);
           session.setAttribute("phone", xphone);
           session.setAttribute("role", xrole);
           session.setAttribute("avatar", xavatar);
           session.removeAttribute("message");
           session.removeAttribute("type");
           session.setAttribute("message", message);
           session.setAttribute("type", type);
           response.sendRedirect("listpersonnel");


        
    }
  }


    private String generateId(int role){
        String id ;
        int newid ;
        PersonnelDAO personnelDAO = new PersonnelDAO();
        newid= personnelDAO.getNumberOfPersonByRole(role)+1;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        id= decimalFormat.format(newid);
        if (role == 0) {
            id = "AD" + id;
        } else if (role == 2) {
            id = "NV" + id;
        } else if (role==3){
            id = "GV"+ id;
        }else if (role == 5){
            id = "KT"+ id;
        }
        return id;
    }
        
}
