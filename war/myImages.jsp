<%@ page import="com.google.appengine.labs.repackaged.com.google.common.collect.ArrayListMultimap,com.google.appengine.labs.repackaged.com.google.common.collect.Multimap,java.util.Set,java.util.Iterator,com.google.appengine.api.users.UserService,com.google.appengine.api.users.UserServiceFactory" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Image Hosting via Google App Engine">
<meta name="author" content="Darren Britton">
<title>Picture Box - Homepage</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- Custom CSS -->
<link href="css/style.css" rel="stylesheet">
<link rel="stylesheet" href="http://blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
<link rel="stylesheet" href="css/bootstrap-image-gallery.css">
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
<div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/getblobs">Picture Box</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <% UserService userService = UserServiceFactory.getUserService(); %>
        <ul class="nav navbar-nav">
            <li>
            <a href="/loginlogout">Login/Logout</a>
            </li>
            <% if(userService.isUserLoggedIn()){ %>
                <li>
                <a href="/upload.jsp">Upload</a>
                </li>
                <li>
                <a href="/getuserblobs">My Images</a>
                </li>
                <% if(userService.isUserAdmin()){ %>
                    <li>
                    <a href="/getadminblobs">Admin Panel</a>
                    </li>
                <%}%>
            <%}%>
        </ul>
    </div>
    <!-- /.navbar-collapse -->
</div>
<!-- /.container -->
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <% 
            Multimap<String, String> picInfoMap = (Multimap<String, String>) request.getAttribute("picInfoMap");
            String url;
            Iterator<String> values;
            Set<String> keys = picInfoMap.keySet();
            for(String key : keys)
            {
                values = picInfoMap.get(key).iterator();
                url = values.next(); %>
                <div class="row">
                    <div class="col-md-7">
                        <a href="<%=url%>=s0"> <center><img class="img-responsive well" src="<%=url%>=s700" alt="<%=url%>"></center>
                        </a>
                    </div>
                    <div class="col-md-5">
                        <h3><%=values.next()%></h3>
                        <h4>Uploaded on: <%=values.next()%></h4>
                        <p>
                            File Type: <%=values.next()%>
                        </p>
                        <p>
                            File Size: <%=values.next()%> KBytes
                        </p>
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            Options <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li class="list-group-item-warning"><a href="/updatevisibility?blobKey=<%=key%>">Make <%=values.next()%></a></li>
                                <li class="divider"></li>
                                <li class="list-group-item-danger"><a href="/deleteblob?blobKey=<%=key%>">Delete</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <hr>
           <%}%>
            <!-- Footer -->
            <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>
                         Copyright &copy; Darren Britton 2014
                    </p>
                </div>
            </div>
            <!-- /.row -->
            </footer>
        </div>
        <!-- /.container -->
        <!-- jQuery -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.1/js/bootstrap.min.js"></script>
        <script src="http://blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
        <script src="js/bootstrap-image-gallery.js"></script>
        <script src="js/demo.js"></script>
        </body>
        </html>