<!DOCTYPE html>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" isErrorPage="false"%>
<%@page import="com.container.beans.ListTickets"%>
<%@page import="com.container.beans.TicketData"%>
<%@page import="com.container.beans.ListTeam"%>
<%@page import="com.container.beans.Users"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.container.beans.ListProjects"%>

<html dir="ltr" lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="noindex,nofollow">
    <title>Bug Tracker - Create Ticket</title>
    <link rel="icon" type="image/png" sizes="16x16" href="http://localhost/capstone/assets/images/favicon.png">
    <link href="assets/plugins/chartist/dist/chartist.min.css" rel="stylesheet">
    <link href="css/style.min.css" rel="stylesheet">
</head>

<body>
    <div id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full" data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
        <header class="topbar" data-navbarbg="skin6">
            <nav class="navbar top-navbar navbar-expand-md navbar-dark">
                <div class="navbar-header" data-logobg="skin6">
                    <a class="navbar-brand" href="">
                        <span class="logo-text">
                            Bug Tracker v1.0
                        </span>
                    </a>
                    <a class="nav-toggler waves-effect waves-light text-dark d-block d-md-none"
                        href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
                </div>
                <div class="navbar-collapse collapse" id="navbarSupportedContent" data-navbarbg="skin5">
                    <div class="navbar-nav me-auto mt-md-0">&nbsp;</div>
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a style="background: #0192C7;" class="nav-link dropdown-toggle waves-effect waves-dark" href="logout">
                               Logout
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <aside class="left-sidebar">
            <div class="scroll-sidebar">
                <nav class="sidebar-nav">
					<ul id="sidebarnav">
						<%
							Object user_role = request.getAttribute("user_role");
							Boolean user_type = Boolean.valueOf(user_role.toString());
							if(user_type){
						%>
						<li class="sidebar-item border-top">
							<a class="sidebar-link" href="dashboard" aria-expanded="false">
								<span>Project</span>
							</a>
						</li>
						<li class="sidebar-item">
							<a class="sidebar-link" href="createteam" aria-expanded="false">
								<span class="hide-menu">Team</span>
							</a>
						</li>
						<% }else{ /* return nothing if user role is "user" */  } %>
						<li class="sidebar-item">
							<a class="sidebar-link" href="tickets" aria-expanded="false">
								<span class="hide-menu">Tickets</span>
							</a>
						</li>
					</ul>
                </nav>
            </div>
        </aside>
        
        <div class="page-wrapper">
            <div class="page-breadcrumb">
                <div class="row align-items-center">
                    <div class="col-md-6 col-8 align-self-center">
                        <h3 class="page-title mb-0 p-0">Create a Ticket</h3>
                    </div>
                </div>

                <div class="row align-items-center">
                    <div class="col-md-6 col-8 align-self-center">
                            <ul class="nav nav-pills" style="padding-top: 25px;">
                                <li class="nav-item" style="margin-right: 10px;">
                                    <a href="tickets" class="btn btn-success d-none d-md-inline-block text-white">Ticket Board</a>
                                </li>
                                <li class="nav-item" style="margin-right: 10px;">
                                    <a href="createticket" class="btn btn-success d-none d-md-inline-block text-white active">Create Ticket</a>
                                </li>
                                <!-- <li class="nav-item" style="margin-right: 10px;">
                                    <a href="ticketbacklogs" class="btn btn-success d-none d-md-inline-block text-white">Backlogs Tickets</a>
                                </li> -->
                            </ul>
                      </div>
                </div>
            </div>
            <div class="container-fluid">
            	<div class="row">    
	                    <div class="col-3">
	                        <h5 style="margin-bottom: 20px;">Create a ticket</h5>
	                    </div>    
                </div>
                <div class="row p-2">
                    	<div class="col-6" style="margin-right: 15px;">
                    			${message}
		                        <form action="${request}" method="post" style="background: #FFF !important; margin-right: 10px; padding: 40px 30px;">
			                            <div class="form-group">
			                              <input type="text" name="tickettitle" value="${ticket_title}" class="form-control" id="exampleFormControlInput1" placeholder="Ticket Title..." required>
			                            </div>
			                            <div class="form-group">
				                              <label for="exampleFormControlSelect1">Assign to a project</label>
				                              
				                              <select name="ticketproject" class="form-control" required>
										        <option value="0">&nbsp;</option>
					                            <%	 
					                             	 Object object = request.getAttribute("project_id");
					                               	 int project_id =Integer.parseInt(object.toString());					                               	 
					                               	
					                               	 List<ListProjects> projects = (ArrayList) request.getAttribute("projects");
							                         Iterator<ListProjects> iteratorOne = projects.iterator();
											         while (iteratorOne.hasNext()) {					
											        	 
											         	 ListProjects project = iteratorOne.next();
											         	 
									 	          	     if(project_id == project.getProjectID()){  %>
												            <option value="<%=project.getProjectID()%>" selected><%=project.getProjectName()%></option>
												         <% }else{ %>
												        	<option value="<%=project.getProjectID()%>"><%=project.getProjectName()%></option>
												         <% }
											         } 
											    %>
				                               </select>
			                            </div>   
			                            <div class="form-group">
			                              <label for="exampleFormControlTextarea1">Ticket Description</label>
			                              <textarea name="ticketdescription" class="form-control" id="exampleFormControlTextarea1" rows="3" required>${ticket_desc}</textarea>
			                            </div>

			                            <div class="form-group">
			                                <label for="exampleFormControlSelect1">Select Team</label>
			                                <select name="assignteam" class="form-control" id="exampleFormControlSelect1" required>
										        <option value="0">&nbsp;</option>
					                            <%	 
					                             	 Object teamobj = request.getAttribute("team_id");
					                               	 int teamid =Integer.parseInt(teamobj.toString());					                               	 
					                               	 
					                               	 List<ListTeam> teams = (ArrayList) request.getAttribute("teams");
							                         Iterator<ListTeam> iteratorteam = teams.iterator();
											         while (iteratorteam.hasNext()) {	
											        	 
											        	 ListTeam team = iteratorteam.next();
											         	 
									 	          	     if(teamid == team.getTeam_id()){ %>
												            <option value="<%=team.getTeam_id()%>" selected><%=team.getTeam_name()%></option>
												         <% }else{ %>
												        	<option value="<%=team.getTeam_id()%>"><%=team.getTeam_name()%></option>
												         <% }
											         } 
											    %>
				                            </select>
			                            </div>
			                            <div class="form-group">
			                                <label for="exampleFormControlSelect1">Assign to: </label>		
			                                <select name="assignuser" class="form-control" required>
			                                  <option value="0">&nbsp;</option>
			                                  <%	 
					                             	 Object userobj = request.getAttribute("user_id");
					                               	 int userid =Integer.parseInt(userobj.toString());					                               	 
					                               	 
					                               	 List<Users> users = (ArrayList) request.getAttribute("users");
							                         Iterator<Users> iteratoruser = users.iterator();
											         while (iteratoruser.hasNext()) {		
											        	 
											        	 Users user = iteratoruser.next();
									 	          	     
											        	 if(userid == user.getTeam_id()){ %>
												            <option value="<%=user.getUser_id()%>" selected><%=user.getFirstname()%> <%=user.getLastname()%></option>
												         <% }else{ %>
												        	<option value="<%=user.getUser_id()%>"><%=user.getFirstname()%> <%=user.getLastname()%></option>
												         <% }
											         } 
											    %>
			                                </select>
			                            </div>
			                            <div class="form-group pt-3">
			                                 <input type="hidden" name="ticketnumber" value="${ticketnumber}" />
			                                 <input type="submit" class="btn btn-success" style="color: #FFF; float: right;" value="${btntype}" />
			                            </div>
		                        </form>
                    	</div>
                    	
                    	<div class="col-5 p-3" style="background: #FFF; margin-right: 30px; padding: 30px !important;">
                       		<h4 class="card-title" style="margin-bottom: 20px;">Latest Tickets</h4>
                       		 <%
					             List<ListTickets> tickets = (ArrayList) request.getAttribute("tickets");
					             Iterator<ListTickets> iterator = tickets.iterator();
					             while (iterator.hasNext()) {
					             ListTickets ticket = iterator.next();
			 	            %>
				    		<div class="card border" style="border: 1px solid #C5C5C5; margin-bottom: 15px;">
	                            <div class="card-body">
	                                <h5><%=ticket.getTickettitle()%></h5>
	                                <p class="card-text"><%=ticket.getTicketdescription()%></p>
	                              	<a href="ticketupdate?ticket=<%=ticket.getTicket_id()%>" style="color: #FFF;" type="button" class="btn btn-info btn-sm">Update</a>
	                                <a href="deleteticket?ticket=<%=ticket.getTicket_id()%>" style="color: #FFF;" type="button" class="btn btn-danger btn-sm">Archive</a>
	                            </div>
		                    </div>
					        <% } %>
	                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
    
    <script src="assets/plugins/jquery/dist/jquery.min.js"></script>
    <script src="assets/plugins/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/app-style-switcher.js"></script>
    <script src="js/waves.js"></script>
    <script src="js/sidebarmenu.js"></script>
    <script src="js/custom.js"></script>
    <script src="assets/plugins/flot/jquery.flot.js"></script>
    <script src="assets/plugins/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>
    <script src="js/pages/dashboards/dashboard1.js"></script>
    
    <style>
    	li.border-top{
    	 	border: 1px solid #F7F7F7 !important;
    	}
    
    	li.sidebar-item a{
    		color: #000 !important;
    		text-decoration: none;
    	}
    	
    	li.sidebar-item{
    	 	border-bottom: 1px solid #F7F7F7;
    	}
    </style>

</body>
</html>