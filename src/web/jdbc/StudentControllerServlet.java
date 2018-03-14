package web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	

	@Override
	public void init() throws ServletException {
		super.init();
		
		//create our student db util.. and pass in conn pool / datasource
		
		try
		{
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch(Exception exc)
		{
			throw new ServletException(exc);
		}
		
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
		//read the command parameter
			String theCommand = request.getParameter("command");
			
		//if the command is missing then default to listing students
			if(theCommand == null)
			{
				theCommand = "LIST";
			}
			
		//route to the appropriate method
			switch(theCommand)
			{
				case "LIST":
					//list the students... in MVC fashion
					listStudents(request, response);
					break;
					
				case "ADD":
					addStudent(request, response);
					break;
					
				case "LOAD":
					loadStudent(request,response);
					break;
					
				case "UPDATE":
					updateStudent(request,response);
				    break;
				    
				case "DELETE":
					deleteStudent(request,response);
					break;
					
				default:
					listStudents(request, response);
			}

		}
		catch (Exception exc)
		{
			throw new ServletException(exc);
		}
	}




	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) 
	throws Exception {
		//read student id from form data
	    String theStudentId = request.getParameter("studentId");
	   
	    
	    //delete student to the database
		StudentDbUtil.deleteStudent(theStudentId);
		
	    //send back to the main page(the student list)
		listStudents(request, response);
		
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		   //read student id from form data
		    String theStudentId = request.getParameter("studentId");
		    int id = Integer.parseInt(theStudentId);
		    String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
		
		//create new student object
			Student thestudent = new Student(id, firstname, lastname, email);
		
		//add student to the database
			StudentDbUtil.updateStudent(thestudent);
			
		//send back to the main page(the student list)
			listStudents(request, response);
		
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
	throws Exception {
		
		//read student id from form data
			String theStudentId = request.getParameter("studentId");
			
		//get student from database
			Student theStudent = studentDbUtil.getStudent(theStudentId);
			
			
		//place student in the request attribute
			request.setAttribute("THE_STUDENT", theStudent);
			
		//send to jsp page: update-student-form.jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
			dispatcher.forward(request, response);
		
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student info from form data
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
		
		//create new student object
			Student thestudent = new Student(firstname, lastname, email);
		
		//add student to the database
			StudentDbUtil.addStudent(thestudent);
			
		//send back to the main page(the student list)
			listStudents(request, response);

	}



	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
	throws Exception {
		
		//get students from db util
			List<Student> students = studentDbUtil.getStudents();
		
		//add students to the request
			request.setAttribute("STUDENTS", students);
			
		//send to JSP page
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
			dispatcher.forward(request, response);
			
	}
}
