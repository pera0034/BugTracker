<!DOCTYPE html>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" isErrorPage="false"%>
<%@page import="com.container.beans.ListProjects"%>
<%@page import="com.container.beans.Projects"%>

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
                        href="javascript:void(0)">
                        <i class="ti-menu ti-close"></i>
                    </a>
                </div>
                <div class="navbar-collapse collapse" id="navbarSupportedContent" data-navbarbg="skin5">
                    <div class="navbar-nav me-auto mt-md-0" style="padding-left: 30px; color: #FFF; font-size: 12pt; text-transform: uppercase;">${hello_user}</div>
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
                        <h3 class="page-title mb-0 p-0">Project Dashboard</h3> 
                    </div>
                    <div class="col-md-10">
                        <ul class="nav nav-pills" style="padding-top: 25px;">
                            <li class="nav-item" style="margin-right: 10px;">
                            	${messageString}
                                <form action="${request}" method="post">
                                    <div class="form-row align-items-center">
                                        <div style="float: left; margin-right: 10px;">
                                            <input type="text" class="form-control mb-2" value="${projectname}" name="projectname" style="width: 350px; padding: 15px;" id="inlineFormInput" placeholder="Project Name.." required/ >
                                            <input type="hidden" name="projectid" value="${projectid}" />
                                        </div>
                                        <div style="float: left;">
                                       		<input type="submit" class="btn btn-success d-none d-md-inline-block text-white" name="btnaction" value=${btn}>
                                        </div>
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="container-fluid">
                <div class="row">
                	<%
		                List<ListProjects> projects = (ArrayList) request.getAttribute("projects");
		                Iterator<ListProjects> iterator = projects.iterator();
		                while (iterator.hasNext()) {
		                ListProjects project = iterator.next();
 	               %>
			       <div class="col-sm-4">
			    			<div class="card">
	                            <div class="card-body">
	                                <h4 class="card-title"><%=project.getProjectName()%></h4>
	                                <div class="text-end">
	                                    <span class="text-muted">Progress</span>
	                                </div>
	                                <span class="text-success">0%</span>
	                                <div class="progress">
	                                    <div class="progress-bar bg-success" role="progressbar"
	                                        style="width: 0%; height: 6px;" aria-valuenow="25" aria-valuemin="0"
	                                        aria-valuemax="100"></div>
	                                </div>
	                                <div style="margin-top: 25px;">	                                    
										<a href="updateproject?id=<%=project.getProjectID()%>" class="btn btn-info btn-sm"  style="margin-right: 10px;  color: #fff;">Update</a>
										<a href="deleteproject?id=<%=project.getProjectID()%>" class="btn btn-danger btn-sm ml-2">Archive</a>
	                                </div>    
	                            </div>
	                        </div>
		    		</div>
				   <% } %>
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