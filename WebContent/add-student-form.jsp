<html>
<head>
<title>Add Student</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>
<body>

<div id="wrapper">
  <div id="header">
		<h2>FooBar University</h2>
	</div>
</div>

<div id="container">
	<h3>Add Student</h3>
	
	<form action="StudentControllerServlet" method="GET">
		<input type="hidden" name="command" value="ADD" />
			<table>
				<tbody>
					<tr>
						<td><label>First Name: </label></td>
						<td><input type="text" name="firstname" /></td>
					</tr>
					
										<tr>
						<td><label>Last Name: </label></td>
						<td><input type="text" name="lastname" /></td>
					</tr>
					
										<tr>
						<td><label>Email: </label></td>
						<td><input type="text" name="email" /></td>
					</tr>
					
										<tr>
						<td><label> </label></td>
						<td><input type="submit" value="Submit" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
	</form>
	
	<div style="clear: both;"></div>
	<div>
		<p>
			<a href="StudentControllerServlet">Back to List</a>
		</p>
	</div>
			
</div>
	
</body>
</html>