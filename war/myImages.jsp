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
<title>Picture Box - My Images</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- Custom CSS -->
<link rel="stylesheet" href="/css/style.css">
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
            <% if(userService.isUserLoggedIn()){ %>
                <li>
                <a href="/loginlogout">Logout</a>
                </li>
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
                    <li>
                    <a href="/adminHelp.jsp">Help</a>
                    </li>
                <%} else {%>
                    <li>
                    <a href="/userHelp.jsp">Help</a>
                    </li>
            <%}} else {%>
                <li>
                <a href="/loginlogout">Login</a>
                </li>
                <li>
                <a href="/guestHelp.jsp">Help</a>
                </li>
            <%}%>
        </ul>
        <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
                <input class="form-control" id="search" name="search" placeholder="Search" type="text" data-toggle="hideseek" data-list=".picture_list" data-nodata="No results found" autocomplete="off">
            </div>
      </form>
    </div>
    <!-- /.navbar-collapse -->
</div>
<!-- /.container -->
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-12">
         <ul class="picture_list" id="unstyled">
            <%
            Multimap<String, String> picInfoMap = (Multimap<String, String>) request.getAttribute("picInfoMap");
            String returnTo = (String) request.getAttribute("baseServlet");
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
                    <li>
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
                                <li class="list-group-item-warning"><a href="/updatevisibility?blobKey=<%=key%>&returnTo=<%=returnTo%>">Make <%=values.next()%></a></li>
                                <li class="divider"></li>
                                <li class="list-group-item-danger"><a href="/deleteblob?blobKey=<%=key%>&returnTo=<%=returnTo%>">Delete</a></li>
                            </ul>
                        </div>
                    </div>
                 </li>
                </div>
           <%}%>
           <hr>
        </ul>
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
        <!-- JS -->
        <script type="text/javascript" src="js/vendor/waypoints.min.js"></script>
        <script type="text/javascript" src="js/vendor/waypoints-sticky.min.js"></script>
        <script type="text/javascript" src="js/vendor/jquery.hideseek.min.js"></script>
        <script type="text/javascript" src="js/vendor/rainbow-custom.min.js"></script>
        <script type="text/javascript" src="js/vendor/jquery.anchor.js"></script>
        <script src="js/initializers.js"></script>
        <!-- JS ends -->

        </body>
        </html>