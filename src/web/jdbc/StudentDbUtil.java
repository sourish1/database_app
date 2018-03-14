package web.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	private static DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource)
	{
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception
	{
		List<Student> students = new ArrayList<>();
	
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try{
			
		//get a connection
			myConn = dataSource.getConnection();
			
		//create sql statement
			String sql = "select * from student";
			myStmt = myConn.createStatement();
			
		//execute query
			myRs = myStmt.executeQuery(sql);
			
		//process result set
			while(myRs.next())
			{
				//retrieve the data from result 
					int id = myRs.getInt("id");
					String firstName = myRs.getString("first_name");
					String lastName = myRs.getString("last_name");
					String email = myRs.getString("email");
					
				//create new student
					Student tempStudent = new Student(id, firstName, lastName, email);
					
				//add it to the list of students
					students.add(tempStudent);
			}

			return students;
		}
		finally
		{
			//close JDBC objects
				close(myConn, myStmt, myRs);
				
		}

	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try
		{
			if(myRs != null)
			{
				myRs.close();
			}
			
			if(myStmt != null)
			{
				myStmt.close();
			}
			
			if(myConn != null)
			{
				myConn.close();
			}
			
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		
	}

	public static void addStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try
		{
			//get a connection
			myConn = dataSource.getConnection();
			
			//create sql insert statement
			String sql = "insert into student(first_name, last_name, email) values(?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			//set the param values for the student
			myStmt.setString(1, theStudent.getFirstname());
			myStmt.setString(2, theStudent.getLastname());
			myStmt.setString(3, theStudent.getEmail());
			
			//execute sql insert
			myStmt.execute();
			
		}
		finally
		{
			close(myConn, myStmt, null);
		}
		
	}

	public Student getStudent(String theStudentId) throws Exception{
		
		Student theStudent = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int StudentId;
		
		try
		{
			//convert student id to int
				StudentId = Integer.parseInt(theStudentId);
			
			//get connection to database
			  myConn = dataSource.getConnection();
				
			//	create sql to get selected student
			  String sql = "Select * from student where id=?";
			  
			//create prepared statement
			  myStmt = myConn.prepareStatement(sql);
			  
		  //set params
			  myStmt.setInt(1, StudentId);
			  
		  //execute statement
			  myRs = myStmt.executeQuery();
			  
		  //retrieve data from result set row
			  if(myRs.next())
			  {
				  String firstname = myRs.getString("first_name");
				  String lastname = myRs.getString("last_name");
				  String email = myRs.getString("email");
				  
				  //use the studentId during construction
				  	theStudent = new Student(StudentId, firstname, lastname, email);
			  }
			  else
			  {
				  throw new Exception("could not find studentid : " + StudentId);
			  }
			  
			 return theStudent;
		}		
		finally
		{
			//clean the JDBC objects
			close(myConn, myStmt, myRs);
		}

	}

	public static void updateStudent(Student thestudent) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try
		{
		//get connection to database
		  myConn = dataSource.getConnection();
		  
		//create sql update student
		  String sql = "update student set first_name=?, last_name=?, email=? where id=?";
		  
		//create prepared statement
		  myStmt = myConn.prepareStatement(sql);
		  
		//set the param values for the student
			myStmt.setString(1, thestudent.getFirstname());
			myStmt.setString(2, thestudent.getLastname());
			myStmt.setString(3, thestudent.getEmail());  
			myStmt.setInt(4, thestudent.getId());
			
		//execute sql insert
		   myStmt.execute();
		}
		finally
		{
			close(myConn, myStmt, null);
		}
	}

	public static void deleteStudent(String theStudentId) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try
		{
			//convert student id to int
			int StudentId = Integer.parseInt(theStudentId);
			
			//get connection to database
			myConn = dataSource.getConnection();
			  
			//create sql to delete student
			String sql = "delete from student where id=?";
			
			//create prepared statement
			  myStmt = myConn.prepareStatement(sql);
			  
			  //set params
			  myStmt.setInt(1, StudentId);
			  
		  //execute statement
			 myStmt.execute();
			
			
		}
		finally
		{
			close(myConn, myStmt, null);
		}
		
	}
}
