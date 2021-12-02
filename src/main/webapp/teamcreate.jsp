<!DOCTYPE html>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" isErrorPage="false"%>
<%@page import="com.container.beans.ListTeam"%>
<%@page import="com.container.beans.Users"%>
<html dir="ltr" lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="noindex,nofollow">
    <title>Bug Tracker - Dashboard</title>
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/favicon.png">
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
                        <h3 class="page-title mb-0 p-0">Team</h3> 
                    </div>
                </div>
            </div>
            <div class="container-fluid">
	                <div class="row mb-4">
	                	<div class="col-md-6 col-8 align-self-center">
	                		 ${message}
                      		 <form action="${request}" method="post">
                                 <div class="form-row align-items-center">
                                     <div style="float: left; margin-right: 10px;">
                                         <input type="text" class="form-control mb-2" value="${teamname}" name="teamname" style="width: 350px; padding: 15px;" id="inlineFormInput" placeholder="Team Name..." required />
                                         <input type="hidden" name="teamcode" value="${teamid}" />
                                     </div>
                                     <div style="float: left;">
                                    		<input type="submit" class="btn btn-success d-none d-md-inline-block text-white" name="btnaction" value="${teambtn}">
                                     </div>
                                 </div>
                             </form>
                     	 </div>
	                </div>
	                <div class="row p-2">
	                		<div class="col-4">	
					               <%
						                List<ListTeam> teams = (ArrayList) request.getAttribute("teams");
						                Iterator<ListTeam> iterator = teams.iterator();
						                while (iterator.hasNext()) {
						                ListTeam team = iterator.next();
				 	               %>
				 	               <div class="col-12 p-3" style="background: #FFF; margin-right: 30px; margin-top: 34px; padding-bottom: 25px;">
				                         <h4 class="card-title" style="margin-bottom: 20px;"><%=team.getTeam_name()%></h4>
					                     <ul class="list-group list-group-flush border-bottom" style="margin-bottom: 25px;">
				                            <%
								                List<Users> users = (ArrayList) request.getAttribute("users");
								                Iterator<Users> iteratoruser = users.iterator();
								                while (iteratoruser.hasNext()) {
								               	Users user = iteratoruser.next();
					 	               			
								                if(user.getTeam_id() == team.getTeam_id()){  %>
					 	               				<li class="list-group-item" style="border: 0px;">
				                                		 <%=user.getLastname()%> &nbsp; <%=user.getFirstname()%> 
								 	          	  	</li>
								 	          	  <% }
				                            	 }
				                            	 %>
				                        </ul>  
				                        <a href="updateteam?teamcode=<%=team.getTeam_id()%>" class="btn btn-primary btn-sm"  style="float: right !important; margin-left: 15px;">Update</a>
				                        <a href="deleteteam?teamcode=<%=team.getTeam_id()%>" class="btn btn-danger btn-sm"  style="float: right !important; margin-left: 15px;">Archive</a>
				                        <br /><br />
				                    </div>    
				                    <% } %>
				            </div>
				            <div class="col-4">
						            <div class="col-12 p-3" style="background: #FFF;  margin-top: 34px; margin-left: 10px;">
						           		 <h4>List of Members</h4>
						           		 <p>Assign user by clicking the team name</p>
						            </div>
				            		<div class="col-12 p-3" style="background: #FFF; margin-left: 10px;">
				                            <ul class="list-group list-group-flush border-bottom" style="margin-bottom: 25px;">
				                            	 <% 
									                List<Users> users = (ArrayList) request.getAttribute("users");
									                Iterator<Users> iteratoruser = users.iterator();
									                while (iteratoruser.hasNext()) {
									               	Users user = iteratoruser.next(); %>
								               		<li class="list-group-item">
					                                    <% if(user.getUserrole() == 1){  %>
							 	               				<label style="float: left; padding: 3px 5px; font-size: 9pt; background: #D26900; color: #FFF; margin-right: 25px;">Admin</label>
					                   	 	          	<% } %>
					                   	 	          	<label style="float: left !important;"><%=user.getLastname()%> &nbsp; <%=user.getFirstname()%></label>			
					                                    <%
														    List<ListTeam> team_bt = (ArrayList) request.getAttribute("teams");
														    Iterator<ListTeam> iterator_bt = team_bt.iterator();
														    while (iterator_bt.hasNext()) {
														    ListTeam team = iterator_bt.next();														          
														    
																  if(user.getTeam_id() == team.getTeam_id()){  %>
									 	               				  <a href="userassignto?teamnumber=<%=team.getTeam_id()%>&usernumber=<%=user.getUser_id()%>" class="btn btn-success btn-sm" style="color: #FFF; float: right !important; margin-left: 10px;"><%=team.getTeam_name()%></a>
							                   	 	          	 
							                   	 	          	 <% }else{ %>
																	  <a href="userassignto?teamnumber=<%=team.getTeam_id()%>&usernumber=<%=user.getUser_id()%>" class="btn btn-light btn-sm" style="color: #B4B4B4; float: right !important; margin-left: 10px;"><%=team.getTeam_name()%></a>
							                    	 	         <% } %>
												 	          	 
											       	    <% } %>
					                                </li>
									               	<% } %>
				                            </ul>
			                         </div>  
			                         <div class="col-12 p-3" style="background: #FFF; margin-left: 10px; padding-bottom: 50px;">
						           		 <a href="" class="btn btn-primary btn-sm" style="color: #FFF; margin-left: 10px;">Send an email invite to join</a>
						           		 <br /><br />
						            </div> 
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